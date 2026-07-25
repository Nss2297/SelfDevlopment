package com.waseel.prescription.service.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.waseel.prescription.mapper.MapRequestModel;
import com.waseel.prescription.mapper.MapResponseModel;
import com.waseel.prescription.model.authentication.JwtResponse;
import com.waseel.prescription.model.br.SensitiveDrugResponseModel;
import com.waseel.prescription.model.cancellation.PrescriptionCancellationRequestModel;
import com.waseel.prescription.model.cancellation.PrescriptionCancellationResponseModel;
import com.waseel.prescription.model.dispense.DispensableDrugs;
import com.waseel.prescription.model.dispense.DispenseDrugsRequestModel;
import com.waseel.prescription.model.dispense.PrescriptionDispenseRequestModel;
import com.waseel.prescription.model.dispense.PrescriptionDispenseResponseModel;
import com.waseel.prescription.model.dss.DssRequest;
import com.waseel.prescription.model.dss.DssResponse;
import com.waseel.prescription.model.eligibility.EligibilityResponseModel;
import com.waseel.prescription.model.enums.CommonDenialsCode;
import com.waseel.prescription.model.enums.CommonWords;
import com.waseel.prescription.model.enums.EligibilityStatus;
import com.waseel.prescription.model.enums.PbmRequestType;
import com.waseel.prescription.model.enums.PolicyConsumptionStatus;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.enums.UnitType;
import com.waseel.prescription.model.exclusion.DrugExclusionResponseModel;
import com.waseel.prescription.model.formulary.DrugFormularyResponseModel;
import com.waseel.prescription.model.inquiry.InquiryInvalidResponseModel;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryRequestModel;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryResponseModel;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryRequestModel;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryResponseModel;
import com.waseel.prescription.model.modifydecision.ModifyDecisionRequestModel;
import com.waseel.prescription.model.modifydecision.ModifyDecisionResponseModel;
import com.waseel.prescription.model.notification.EmailNotificationResponseModel;
import com.waseel.prescription.model.notification.SmsNotificationResponseModel;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionDrugList;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionError;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionRequestModel;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionResponseModel;
import com.waseel.prescription.model.pbmpayerapis.MemberDemographicDataResponseModel;
import com.waseel.prescription.model.pbmpayerapis.PbmValidationResult;
import com.waseel.prescription.model.policyconsumption.DrugListModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.model.prescription.BusinessRuleValidations;
import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.model.prescription.DrugList;
import com.waseel.prescription.model.prescription.PayerMemberPhysicianInfoModel;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.model.prescription.ServiceResponse;
import com.waseel.prescription.persist.hira.DrugListService;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.Physician;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.persist.prescriptionservice.ServiceRejection;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.repository.businessrules.CommonDenialsRepository;
import com.waseel.prescription.repository.hira.DrugListServiceRepository;
import com.waseel.prescription.repository.prescriptionservice.DiagnosisRepository;
import com.waseel.prescription.repository.prescriptionservice.PhysicianRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;
import com.waseel.prescription.service.prescriptions.MappingPayerIdService;

@Service
public class MapperService {

    private final Logger log = LoggerFactory.getLogger(MapperService.class);

    @Autowired
    private DrugListServiceRepository drugListServiceRepository;
    @Autowired
    private DiagnosisRepository diagnosisRepository;
    @Autowired
    private ServiceInfoRepository serviceInfoRepository;
    @Autowired
    private ServiceResponseInfoRepository serviceResponseInfoRepository;
    @Autowired
    private PhysicianRepository physicianRepository;
    @Autowired
    private ServiceRejectionRepository serviceRejectionRepository;
    @Autowired
    private PrescriptionRequestRepository prescriptionRequestRepository;
    @Autowired
    private MappingPayerIdService mappingPayerIdService;
    @Autowired
    private CommonDenialsRepository commonDenialsRepository;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public DssRequest createDssRequest(PrescriptionRequestModel prescriptionRequest, String requestId,
                                       String providerId) {
        MapRequestModel instance = MapRequestModel.INSTANCE;
        DssRequest dss = instance.mapPrescriptionReqToDssReq(prescriptionRequest);
        if (StringUtils.isBlank(dss.getMemberId())) {
            dss.setMemberId(instance.mapMemberIdToDssReq(prescriptionRequest));
        }
        dss.setPrescriberId(providerId);
        List<String> icdCodes = instance.mapDiagnosisCodesToIcdCodes(prescriptionRequest.getDiagnosisCodes());
        dss.setIcdCodes(icdCodes);
        dss.setRequestId(requestId);
        prescriptionRequest.getDrugList().forEach(drug -> dss.getDrugList().forEach(dssDrug -> {
            if (dssDrug.getNdcDrugCode() != null) {
                if (dssDrug.getNdcDrugCode().equals(drug.getDrugCode())) {
                    BigDecimal quantity;
                    if (drug.getUnitType().equalsIgnoreCase(UnitType.PACKAGE.value())) {
                        quantity = createQuantity(dssDrug.getNdcDrugCode(), drug.getQuantity());
                    } else {
                        quantity = drug.getQuantity();
                    }
                    dssDrug.setDispensedQuantity(quantity);
                    dssDrug.setAmount(quantity.multiply(BigDecimal.valueOf(drug.getUnitPrice())));
                }
            } else {
                dssDrug.setAmount(BigDecimal.valueOf(0));
            }
        }));
        dss.setDateOfService(getLatestServiceStartDate(instance, prescriptionRequest.getDrugList()));
        return dss;
    }

    private BigDecimal createQuantity(String serviceCode, BigDecimal pquantity) {
        BigDecimal quantity = pquantity;
        Optional<DrugListService> drugListService = drugListServiceRepository
                .findByLatestUpdatedDateWithLatestDrugListServiceId(serviceCode);
        if (drugListService.isPresent()) {
            BigDecimal granularUnit = new BigDecimal(drugListService.get().getGranularUnit().intValue());
            quantity = pquantity.multiply(granularUnit);
        }
        return quantity;
    }

    private String getLatestServiceStartDate(MapRequestModel instance, List<DrugList> drugList) {
        List<String> serviceStartDateList = instance.mapServiceStartDate(drugList);
        serviceStartDateList
                .sort(Comparator.comparing(date -> LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        return serviceStartDateList.get(serviceStartDateList.size() - 1);
    }

    public DssResponse mapDssResponse(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, DssResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public DrugFormularyResponseModel mapDrugFormularyResponse(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, DrugFormularyResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public DrugExclusionResponseModel mapDrugExclusionResponse(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, DrugExclusionResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PrescriptionResponseModel mapPrescriptionResponse(ContentCachingResponseWrapper response) {
        ObjectMapper mapper = new ObjectMapper();
        PrescriptionResponseModel prescriptionResponse = null;
        try {
            prescriptionResponse = mapper.readValue(new String(response.getContentAsByteArray()),
                    PrescriptionResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prescriptionResponse;
    }

    public PrescriptionDetailInquiryResponseModel mapPrescriptionDetailInquiryResponse(
            ContentCachingResponseWrapper response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new String(response.getContentAsByteArray()),
                    PrescriptionDetailInquiryResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PayerMemberPhysicianInfoModel mapPrescriptionInquiryDetailResponse(ContentCachingResponseWrapper response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new String(response.getContentAsByteArray()), PayerMemberPhysicianInfoModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PrescriptionSummaryResponseModel mapPrescriptionSummaryInquiryResponse(
            ContentCachingResponseWrapper response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new String(response.getContentAsByteArray()),
                    PrescriptionSummaryResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public InquiryInvalidResponseModel mapPrescriptionInquiryInvalidResponse(ContentCachingResponseWrapper response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new String(response.getContentAsByteArray()), InquiryInvalidResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PrescriptionRequestModel mapPrescriptionRequest(ContentCachingRequestWrapper request) {
        ObjectMapper mapper = new ObjectMapper();
        PrescriptionRequestModel prescriptionRequest = null;
        try {
            prescriptionRequest = mapper.readValue(new String(request.getContentAsByteArray()),
                    PrescriptionRequestModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prescriptionRequest;
    }

    public PrescriptionDispenseRequestModel mapPrescriptionDispenseRequest(ContentCachingRequestWrapper request) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new String(request.getContentAsByteArray()),
                    PrescriptionDispenseRequestModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	public DispenseDrugsRequestModel mapDispenseDrugsRequestModel(ContentCachingRequestWrapper request) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(request.getContentAsByteArray()), DispenseDrugsRequestModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    public PrescriptionDispenseResponseModel mapPrescriptionDispenseResponse(ContentCachingResponseWrapper response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new String(response.getContentAsByteArray()),
                    PrescriptionDispenseResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PrescriptionDetailInquiryRequestModel mapPrescriptionDetailInquiryRequest(
            ContentCachingRequestWrapper request) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new String(request.getContentAsByteArray()),
                    PrescriptionDetailInquiryRequestModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PrescriptionSummaryRequestModel mapPrescriptionSummaryInquiryRequest(ContentCachingRequestWrapper request) {
        ObjectMapper mapper = new ObjectMapper();
        var content = request.getContentAsByteArray();
        if (content.length > 0) {
            try {
                return mapper.readValue(new String(content), PrescriptionSummaryRequestModel.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public PrescriptionCancellationRequestModel mapPrescriptionCancellationRequest(
            ContentCachingRequestWrapper request) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new String(request.getContentAsByteArray()),
                    PrescriptionCancellationRequestModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
        return null;
    }

    public String mapString(Object response) {
        ObjectMapper mapper = new ObjectMapper();
        String prescriptionResponse = null;
        try {
            prescriptionResponse = mapper.convertValue(new Gson().toJson(response), String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prescriptionResponse;
    }

    public PrescriptionResponseModel mapPrescriptionResponse(String response) {
        ObjectMapper mapper = new ObjectMapper();
        PrescriptionResponseModel result = null;
        try {
            result = mapper.readValue(response, PrescriptionResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
        return result;
    }

    public PrescriptionResponseModel createPrescriptionResponse(DssResponse dssResponse,
                                                                PrescriptionRequestModel prescriptionRequest, String ePrescriptionReferenceNum,
                                                                PolicyResponseModel policyResponseModel) {
        MapResponseModel instance = MapResponseModel.INSTANCE;
        PrescriptionResponseModel response = instance.mapDssResToPrescriptionRes(dssResponse);
        if (dssResponse.getErrors() != null && !dssResponse.getErrors().isEmpty()) {
            response.setStatusDescription(dssResponse.getErrors().toString().replace("[", "").replace("]", ""));
        }
        response.setePrescriptionReferenceNumber(ePrescriptionReferenceNum);
        if (policyResponseModel != null) {
            policyResponseModel.setPatientShare("0");
            policyResponseModel.setPayerShare("0");
        }
        prescriptionRequest.getDrugList().forEach(request -> {
            if (response.getResults() != null && !response.getResults().isEmpty()) {
                populateServiceResponse(response.getResults(), request, policyResponseModel);
            }
        });
        if (dssResponse.getResults() != null && !dssResponse.getResults().isEmpty())
            response.setDiagnosisCodes(prescriptionRequest.getDiagnosisCodes());
        if (policyResponseModel != null) {
            response.setPayerShare(new BigDecimal(policyResponseModel.getPayerShare()).setScale(2, RoundingMode.HALF_UP));
            response.setPatientShare(
                    new BigDecimal(policyResponseModel.getPatientShare()).setScale(2, RoundingMode.HALF_UP));
            response.setPatientShareCurrency(policyResponseModel.getPatientShareCurrency());
            response.setPayerShareCurrency(policyResponseModel.getPayerShareCurrency());
        }
        return response;
    }

    public PrescriptionResponseModel createPrescriptionResponseFromDrugFormularyResponse(
            DrugFormularyResponseModel model, String ePrescriptionReferenceNum) {
        PrescriptionResponseModel response = new PrescriptionResponseModel();
        if (model.getStatusDescription() != null) {
            response.setStatusDescription(model.getStatusDescription());
        }
        if (model.getStatusCode() != null) {
            response.setStatus(model.getStatusCode());
        }
        response.setePrescriptionReferenceNumber(ePrescriptionReferenceNum);
        return response;
    }
    
    public PrescriptionResponseModel createPrescriptionResponseFromSensitiveDrugResponse(
            SensitiveDrugResponseModel sensitiveDrugResponseModel, String ePrescriptionReferenceNum) {
        PrescriptionResponseModel response = new PrescriptionResponseModel();
        if (sensitiveDrugResponseModel.getErrorDescription() != null) {
            response.setStatusDescription(StringUtils.strip(sensitiveDrugResponseModel.getErrorDescription().toString(), "[]"));
        }
        if (sensitiveDrugResponseModel.getErrorCode() != null) {
            response.setStatus(sensitiveDrugResponseModel.getErrorCode());
        }
        response.setePrescriptionReferenceNumber(ePrescriptionReferenceNum);
        return response;
    }

    public PrescriptionResponseModel createPrescriptionResponseFromDrugExclusionResponse(
            DrugExclusionResponseModel model, String ePrescriptionReferenceNum) {
        PrescriptionResponseModel response = new PrescriptionResponseModel();
        if (model.getErrorDescription() != null) {
            response.setStatusDescription(model.getErrorDescription());
        }
        if (model.getErrorCode() != null) {
            response.setStatus(model.getErrorCode());
        }
        response.setePrescriptionReferenceNumber(ePrescriptionReferenceNum);
        return response;
    }

    private void populateServiceResponse(List<ServiceResponse> serviceResponse, DrugList drugList,
                                         PolicyResponseModel policyResponseModel) {
        for (ServiceResponse res : serviceResponse) {
            if ((res.getDrugCode() != null && res.getDrugCode().equals(drugList.getDrugCode()))
                    || (res.getScientificCode() != null && res.getScientificCode().equals(drugList.getScientificCode()))) {
                if (policyResponseModel != null) {
                    Optional<DrugListModel> drugListModelOpt = policyResponseModel.getDrugList().stream()
                            .filter(policyConsumptionDrug -> policyConsumptionDrug.getDrugCode().equals(res.getDrugCode()))
                            .findAny();
                    if (drugListModelOpt.isPresent()) {
                        DrugListModel drugListModel = drugListModelOpt.get();
                        calculatePayerAndPatientShare(policyResponseModel, drugListModel, res);
                    }
                }
                res.setUnitType(drugList.getUnitType());
                res.setUnitPrice(drugList.getUnitPrice());
                res.setApprovedAmount(
                        res.getStatus().equalsIgnoreCase(ServiceStatus.REJECTED.name()) ? BigDecimal.valueOf(0)
                                : res.getRequestedAmount());
                res.setDiscount(Double.valueOf("0.0"));
                if (res.getErrors() != null && !res.getErrors().isEmpty()) {
                    res.getErrors().forEach(error -> {
                        error.setDrugCode(res.getDrugCode());
                        error.setScientificCode(res.getScientificCode());
                    });
                }
                break;
            }
        }
    }

    private void calculatePayerAndPatientShare(PolicyResponseModel policyResponseModel, DrugListModel drugListModel,
                                               ServiceResponse res) {
        BigDecimal payerShare = new BigDecimal(0);
        BigDecimal patientShare = new BigDecimal(0);
        BigDecimal totalPatientShare = new BigDecimal(policyResponseModel.getPatientShare());
        BigDecimal totalPayerShare = new BigDecimal(policyResponseModel.getPayerShare());
        if (res.getStatus().equalsIgnoreCase(ServiceStatus.APPROVED.name())) {
            payerShare = drugListModel.getPayerShare();
            patientShare = drugListModel.getPatientShare();
            totalPatientShare = totalPatientShare.add(patientShare);
            totalPayerShare = totalPayerShare.add(payerShare);
            policyResponseModel.setPatientShare(String.valueOf(totalPatientShare));
            policyResponseModel.setPayerShare(String.valueOf(totalPayerShare));
        }
        res.setNet(payerShare.setScale(2, RoundingMode.HALF_UP));
        res.setPatientShare(patientShare.setScale(2, RoundingMode.HALF_UP));
        res.setNetCurrency(drugListModel.getPayerShareCurrency());
        res.setPatientShareCurrency(drugListModel.getPatientShareCurrency());
    }

	public JSONObject mapBadPrescriptionRequest(ContentCachingRequestWrapper request) {
		try {
			return new JSONObject(new String(request.getContentAsByteArray()));
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

    public EligibilityResponseModel mapEligibilityResponse(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, EligibilityResponseModel.class);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    private List<ServiceResponse> populateBusinessRuleServiceResponse(List<DrugList> drugList, String denialCode,
                                                                      String status, String statusDescription, String codeDescription, List<DrugListModel> policyCheckedDrugs) {
        List<ServiceResponse> serviceResponses = new ArrayList<>();
        drugList.stream().forEach(drug -> {
            ServiceResponse serviceResponse = new ServiceResponse(drug.getDrugCode(), drug.getUnitType(),
                    drug.getUnitPrice(), drug.getQuantity(),
                    calculateRequestedAmount(drug.getUnitPrice(), drug.getQuantity()), BigDecimal.valueOf(0),
                    Double.valueOf("0.0"), new BigDecimal(0), new BigDecimal(0), status, statusDescription);
            if (StringUtils.isNotBlank(status) && (status.equals(RequestStatusType.REJECTED.value())
                    || status.equals(EligibilityStatus.INVALID.getValue())
                    || status.equals(EligibilityStatus.FAILED.getValue())
                    || status.equals(PolicyConsumptionStatus.FAILED.getValue())
                    || status.equals(PolicyConsumptionStatus.INVALID.getValue()))) {
                serviceResponse.setBusinessRuleError(
                        new BusinessRuleValidations(serviceResponse.getDrugCode(), denialCode, codeDescription));
            }
            if (null != policyCheckedDrugs && !policyCheckedDrugs.isEmpty()) {
                policyCheckedDrugs.stream()
                        .filter(policyConsumptionDrug -> policyConsumptionDrug.getDrugCode().equals(drug.getDrugCode()))
                        .findAny().ifPresent(policyConsumptionDrug -> {
                            serviceResponse.setNetCurrency(policyConsumptionDrug.getPayerShareCurrency());
                            serviceResponse.setPatientShareCurrency(policyConsumptionDrug.getPatientShareCurrency());
                        });
            }
            serviceResponses.add(serviceResponse);
        });
        return serviceResponses;
    }

    public PrescriptionResponseModel createPrescriptionResponseFromEligibilityResponse(
            EligibilityResponseModel eligibilityResponseModel, String ePrescriptionReferenceNum, String requestId,
            PrescriptionRequestModel prescriptionRequest) {
        BigDecimal amount = new BigDecimal(0);
        MapResponseModel instance = MapResponseModel.INSTANCE;
        PrescriptionResponseModel response = instance.mapEligibilityResToPrescriptionRes(eligibilityResponseModel);
        response.setHttpStatusCode(
                response.getStatus().equals(EligibilityStatus.INVALID.getValue()) ? HttpStatus.BAD_REQUEST.value()
                        : eligibilityResponseModel.getHttpStatusCode());
        response.setStatusDescription(eligibilityResponseModel.getStatusDescription());
        response.setRequestId(requestId);
        response.setePrescriptionReferenceNumber(ePrescriptionReferenceNum);
        if (response.getStatus().equals(EligibilityStatus.INELIGIBLE.getValue())) {
            response.setStatus(RequestStatusType.REJECTED.value());
        }
        response.setResults(populateBusinessRuleServiceResponse(prescriptionRequest.getDrugList(),
                eligibilityResponseModel.getDenialCode(), response.getStatus(), response.getStatusDescription(),
                eligibilityResponseModel.getDescription(), null));
        response.setPatientShare(amount);
        response.setPayerShare(amount);
        return response;
    }

    private BigDecimal calculateRequestedAmount(Double unitPrice, BigDecimal quantity) {
        return unitPrice != null && quantity != null ? quantity.multiply(BigDecimal.valueOf(unitPrice))
                : BigDecimal.ZERO;
    }

    public PrescriptionResponseModel createPrescriptionResponseFromPolicyConsumptionResponse(
            PolicyResponseModel policyResponseModel, String ePrescriptionReferenceNum, String requestId,
            List<DrugList> drugList) {
        MapResponseModel instance = MapResponseModel.INSTANCE;
        PrescriptionResponseModel response = instance.mapPolicyResToPrescriptionRes(policyResponseModel);
        response.setHttpStatusCode(Integer.parseInt(policyResponseModel.getHttpStatusCode()));
        response.setStatusDescription(policyResponseModel.getStatusDescription());
        response.setRequestId(requestId);
        response.setePrescriptionReferenceNumber(ePrescriptionReferenceNum);
        response.setStatus(policyResponseModel.getStatus());
        response.setPatientShare(StringUtils.isNotBlank(policyResponseModel.getPatientShare())
                ? new BigDecimal(policyResponseModel.getPatientShare()).setScale(2, RoundingMode.HALF_UP)
                : new BigDecimal(0));
        response.setPatientShareCurrency(policyResponseModel.getPatientShareCurrency());
        response.setPayerShare(StringUtils.isNotBlank(policyResponseModel.getPayerShare())
                ? new BigDecimal(policyResponseModel.getPayerShare()).setScale(2, RoundingMode.HALF_UP)
                : new BigDecimal(0));
        response.setPayerShareCurrency(policyResponseModel.getPayerShareCurrency());
        response.setResults(populateBusinessRuleServiceResponse(drugList, policyResponseModel.getDenialCode(),
                response.getStatus(), response.getStatusDescription(), policyResponseModel.getDenialDescription(),
                policyResponseModel.getDrugList()));
        return response;
    }

    public PolicyResponseModel mapPolicyConsumptionResponse(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, PolicyResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
        return null;
    }

    public EPrescriptionRequestModel getEPrescriptionRequestModelFromPrescriptionRequest(
            PrescriptionRequest prescriptionRequest, String requestType,
            DispenseDrugsRequestModel dispenseDrugsRequestModel) {
        EPrescriptionRequestModel model = new EPrescriptionRequestModel();
        String requestId = prescriptionRequest.getRequestId();
        model.setCanCancel(prescriptionRequest.getCanCancel());
        model.setCaseType(prescriptionRequest.getCaseType());
        model.setCanFollowUp(prescriptionRequest.getCanFollowUp());
        model.setePrescriptionStatus(prescriptionRequest.getStatusCode());
		String payerId = mappingPayerIdService.fetchPayerIdByMappedPayerId(prescriptionRequest.getPayerId());
		model.setPayerId(payerId);
        setDiagnosisCodes(requestId, model);
        if (dispenseDrugsRequestModel != null) {
			List<DispensableDrugs> drugList = dispenseDrugsRequestModel.getDrugList();
			setDrugList(requestId, model, drugList);
            model.setTotalPatientShareValue(dispenseDrugsRequestModel.getTotalPatientShare());
            model.setTotalPatientShareCurrency(dispenseDrugsRequestModel.getTotalPatientShareCurrency());
            model.setTotalPayerShareValue(dispenseDrugsRequestModel.getTotalNet());
            model.setTotalPayerShareCurrency(dispenseDrugsRequestModel.getTotalNetCurrency());
        } else {
            setDrugList(requestId, model);
            model.setTotalPatientShareValue(prescriptionRequest.getPatientShare());
            model.setTotalPatientShareCurrency(prescriptionRequest.getPatientShareCurrency());
            model.setTotalPayerShareValue(prescriptionRequest.getPayerShare());
            model.setTotalPayerShareCurrency(prescriptionRequest.getPayerShareCurrency());
        }
        model.setePrescriptionReferenceNumber(prescriptionRequest.getePrescriptionReferenceNumber());
        model.setRequestType(requestType);
        setPhysicianDetails(requestId, model);
        setMemberDetails(prescriptionRequest.getMemberInfo(), model);
        model.setProviderId(prescriptionRequest.getProviderId());
        return model;
    }
    
    public PrescriptionResponseModel createPrescriptionResponseFromEPrescriptionResponseModel(
            EPrescriptionResponseModel model, String ePrescriptionReferenceNum, String requestId) {
        PrescriptionResponseModel response = new PrescriptionResponseModel();
        String statusDesc = !CollectionUtils.isEmpty(model.getErrors())
                ? StringUtils.strip(model.getErrors().toString(), "[]")
                : model.getStatusDescription();
        response.setStatus(model.getStatus());
        response.setStatusDescription(statusDesc);
        response.setePrescriptionReferenceNumber(ePrescriptionReferenceNum);
        response.setRequestId(requestId);
        return response;
    }

    public EPrescriptionRequestModel createEPrescriptionRequestModelForNewOrFollowup(
            PrescriptionRequestModel prescriptionRequestModel, PbmRequestType pbmRequestType,
            String ePrescriptionReferenceNumber, PrescriptionResponseModel prescriptionResponseModel) {
        MapRequestModel instance = MapRequestModel.INSTANCE;
        EPrescriptionRequestModel model = instance
                .mapPrescriptionRequestModelToEPrescriptionRequestModel(prescriptionRequestModel);
        model.setRequestType(pbmRequestType.value());
        model.setTotalPrescriptionPrice(prescriptionRequestModel.getTotalPrice());
        PrescriptionRequest prescriptionRequest = getPrescriptionRequest(ePrescriptionReferenceNumber);
        if (prescriptionRequest != null) {
            setModelDataFromPrescriptionResponseModel(prescriptionResponseModel, model,
                    prescriptionRequest.getRequestId());
            setModelDataFromPrescriptionRequest(prescriptionRequest, model);
        }
        return model;
    }

    private void setPayerAndPatientShareAtDrugLevelForApproval(EPrescriptionDrugList drugInfo, String requestId) {
        List<ServiceInfo> serviceInfoList = serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId);
        Map<String, ServiceInfo> serviceInfoMap = new HashMap<>();
        serviceInfoList.forEach(serviceInfo -> {
			if (serviceInfo.getDrugCode() != null
					&& !serviceInfo.getDrugCode().equalsIgnoreCase(CommonWords.UNDEFINED.value())) {
				serviceInfoMap.put(serviceInfo.getDrugCode(), serviceInfo);
			} else if (serviceInfo.getScientificCode() != null) {
				serviceInfoMap.put(serviceInfo.getScientificCode(), serviceInfo);
			}
        });
		String key = drugInfo.getDrugCode() != null
				&& !drugInfo.getDrugCode().equalsIgnoreCase(CommonWords.UNDEFINED.value()) ? drugInfo.getDrugCode()
						: drugInfo.getScientificCode();
        ServiceInfo serviceInfo = serviceInfoMap.get(key);
        if (serviceInfo != null) {
            Optional<ServiceResponseInfo> serviceResponseInfoOptional =
                    serviceResponseInfoRepository.findByRequestIdAndServiceID(requestId, serviceInfo.getId());
            if (serviceResponseInfoOptional.isPresent()) {
                ServiceResponseInfo serviceResponseInfo = serviceResponseInfoOptional.get();
                drugInfo.setPatientShareValue(serviceResponseInfo.getPatientShare() != null
                        ? serviceResponseInfo.getPatientShare() : BigDecimal.ZERO);
                drugInfo.setPatientShareCurrency(serviceResponseInfo.getPatientShareCurrency() != null
                        ? serviceResponseInfo.getPatientShareCurrency() : "SAR");
                drugInfo.setPayerShareValue(serviceResponseInfo.getNet() != null
                        ? serviceResponseInfo.getNet() : BigDecimal.ZERO);
                drugInfo.setPayerShareCurrency(serviceResponseInfo.getNetCurrency() != null
                        ? serviceResponseInfo.getNetCurrency() : "SAR");
            }
        } else {
            drugInfo.setPatientShareValue(BigDecimal.ZERO);
            drugInfo.setPayerShareValue(BigDecimal.ZERO);
            drugInfo.setPatientShareCurrency("SAR");
            drugInfo.setPayerShareCurrency("SAR");
        }
    }

    private void setModelDataFromPrescriptionResponseModel(PrescriptionResponseModel prescriptionResponseModel,
                                                           EPrescriptionRequestModel model, String requestId) {
        model.setePrescriptionReferenceNumber(prescriptionResponseModel.getePrescriptionReferenceNumber());
        if (prescriptionResponseModel.getHttpStatusCode() == HttpStatus.OK.value()) {
            model.setePrescriptionStatus(prescriptionResponseModel.getStatus());
            model.setePrescriptionStatusDescription(prescriptionResponseModel.getStatusDescription());
            List<ServiceResponse> results = prescriptionResponseModel.getResults();
            if (results != null) {
                Map<String, ServiceResponse> resultMap = new HashMap<>();
                results.forEach(serviceResponse -> {
					if (serviceResponse.getDrugCode() != null
							&& !serviceResponse.getDrugCode().equalsIgnoreCase(CommonWords.UNDEFINED.value())) {
						resultMap.put(serviceResponse.getDrugCode(), serviceResponse);
					} else if (serviceResponse.getScientificCode() != null) {
						resultMap.put(serviceResponse.getScientificCode(), serviceResponse);
					}
                });
                List<EPrescriptionDrugList> drugList = model.getDrugList();
                if (drugList != null) {
                    model.getDrugList().forEach(drugInfo -> {
                        drugInfo.setUnitPrice(drugInfo.getUnitPrice() != null ? drugInfo.getUnitPrice() : 0);
                        setPayerAndPatientShareAtDrugLevelForApproval(drugInfo, requestId);
                        String key = drugInfo.getDrugCode() != null &&
                                !drugInfo.getDrugCode().equalsIgnoreCase(CommonWords.UNDEFINED.value())
                                ? drugInfo.getDrugCode() : drugInfo.getScientificCode();
                        ServiceResponse matchingDrugCode = resultMap.get(key);
                        drugInfo.setPbmValidationResult(createPbmValidationResult(matchingDrugCode));
                    });
                }
            }
        }
    }

    private PbmValidationResult createPbmValidationResult(ServiceResponse result) {
        if (result != null) {
            PbmValidationResult pbmValidationResult = new PbmValidationResult();
            pbmValidationResult.setApprovedAmount(result.getApprovedAmount());
            pbmValidationResult.setRequestedAmount(result.getRequestedAmount());
            pbmValidationResult.setStatus(result.getStatus());
            if (result.getErrors() != null) {
                pbmValidationResult.setErrors(result.getErrors().stream().map(medicalValidation -> {
                    EPrescriptionError error = new EPrescriptionError();
                    error.setDenialCode(medicalValidation.getDenialCode());
                    error.setRejectionReason(medicalValidation.getRejectionReason());
                    return error;
                }).collect(Collectors.toList()));
            }
            return pbmValidationResult;
        }
        return new PbmValidationResult();
    }

    private void setModelDataFromPrescriptionRequest(PrescriptionRequest prescriptionRequest,
                                                     EPrescriptionRequestModel model) {
        model.setCaseType(prescriptionRequest.getCaseType());
        model.setProviderId(prescriptionRequest.getProviderId());
        model.setCanCancel(prescriptionRequest.getCanCancel());
        model.setCanFollowUp(prescriptionRequest.getCanFollowUp());
        model.setTotalPatientShareValue(prescriptionRequest.getPatientShare());
        model.setTotalPayerShareValue(prescriptionRequest.getPayerShare());
        model.setTotalPatientShareCurrency(prescriptionRequest.getPatientShareCurrency());
        model.setTotalPayerShareCurrency(prescriptionRequest.getPayerShareCurrency());
    }

    private PrescriptionRequest getPrescriptionRequest(String ePrescriptionReferenceNumber) {
        Optional<PrescriptionRequest> prescriptionRequestOpt = prescriptionRequestRepository
                .findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
        return prescriptionRequestOpt.isPresent() ? prescriptionRequestOpt.get() : null;
    }

    private void setMemberDetails(MemberInfo memberInfo, EPrescriptionRequestModel model) {
        model.setIdNumber(memberInfo.getIdNumber());
        model.setMemberGender(memberInfo.getGender());
        if (null != memberInfo.getHeight()) {
            model.setMemberHeight(BigDecimal.valueOf(memberInfo.getHeight()));
        }
        model.setMemberName(memberInfo.getMemberName());
        model.setDateOfBirth(sdf.format(memberInfo.getDob()));
        model.setPolicyNumber(memberInfo.getPolicyNumber());
    }

    private void setPhysicianDetails(String requestId, EPrescriptionRequestModel model) {
        Optional<Physician> physicianOptional = physicianRepository.findByRequestId(requestId);
        if (physicianOptional.isPresent()) {
            Physician physician = physicianOptional.get();
            model.setPhysicianCategory(physician.getPhysicianCategory());
            model.setPhysicianLicenseNumber(physician.getPhysicianLicenseNumber());
            model.setPhysicianSpeciality(physician.getPhysicianSpeciality());
            model.setPhysicianName(physician.getPhysicianName());
        }
    }

    private void setDrugList(String requestId, EPrescriptionRequestModel model, List<DispensableDrugs> drugsList) {
        List<ServiceInfo> serviceInfoList = serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId);
        List<EPrescriptionDrugList> drugLists = new ArrayList<>();
        AtomicReference<Boolean> anyDrugRequiresApproval = new AtomicReference<>(false);
        
        var prescriptionStatus = new Object() {
			int pending = 0;
			int approved = 0;
			int rejected = 0;
			int dispensed =0;
		};

        var ref = new Object() {
            Double totalPrescriptionPrice = 0.0;
        };
        if (serviceInfoList != null) {
            serviceInfoList.forEach(serviceInfo -> {
                EPrescriptionDrugList drugList = new EPrescriptionDrugList();
                DispensableDrugs dispensableDrugs = drugsList.stream()
                        .filter(drugs -> drugs.getDrugCode().equals(serviceInfo.getDrugCode())
                                || drugs.getScientificCode().equals(serviceInfo.getScientificCode()))
                        .findFirst()
                        .orElse(null);
                Double unitPrice;
                if (dispensableDrugs != null) {
                    drugList.setDrugCode(dispensableDrugs.getDrugCode());
                    drugList.setScientificCode(dispensableDrugs.getScientificCode());
                    unitPrice = dispensableDrugs.getUnitPrice();
                } else {
                    drugList.setDrugCode(serviceInfo.getDrugCode());
                    drugList.setScientificCode(serviceInfo.getScientificCode());
                    unitPrice = serviceInfo.getUnitPrice();
                }
                drugList.setUnitPrice(unitPrice);
                drugList.setDuration(BigDecimal.valueOf(serviceInfo.getDuration()));
                drugList.setFrequency(serviceInfo.getFrequency());
                drugList.setQuantity(serviceInfo.getQuantity());
                if (null != serviceInfo.getServiceEndDate()) {
                    drugList.setServiceEndDate(sdf.format(serviceInfo.getServiceEndDate()));
                }
                drugList.setServiceStartDate(sdf.format(serviceInfo.getServiceStartDate()));
                drugList.setFrequencyOthersDescription(serviceInfo.getFrequencyOthersDescription());
                drugList.setOrderingClinician(serviceInfo.getOrderingClinician());
                drugList.setUnitType(serviceInfo.getUnitType());
                drugList.setUseUnitValue(BigDecimal.valueOf(serviceInfo.getUseUnitValue()));
                Optional<ServiceResponseInfo> serviceResponseInfoOptional = serviceResponseInfoRepository
                        .findByRequestIdAndServiceID(requestId, serviceInfo.getId());
                if (serviceResponseInfoOptional.isPresent()) {
                    ServiceResponseInfo serviceResinfo = serviceResponseInfoOptional.get();
                	String drugStatus = serviceResinfo.getStatus();
                	
                    if (dispensableDrugs != null) {
						if (dispensableDrugs.isApprovalRequired()
								&& !serviceResinfo.getStatus().equalsIgnoreCase(ServiceStatus.PENDING.name())) {
							drugStatus = ServiceStatus.PENDING.name();
							anyDrugRequiresApproval.set(true);
							++prescriptionStatus.pending;
						}
						if (!dispensableDrugs.isApprovalRequired()
								&& (dispensableDrugs.getDrugCode().equals(serviceInfo.getDrugCode()) || dispensableDrugs.getScientificCode().equals(serviceInfo.getScientificCode()))
								&& !serviceResinfo.getStatus().equalsIgnoreCase(ServiceStatus.REJECTED.name())) {
							drugStatus = ServiceStatus.DISPENSED.name();
							++prescriptionStatus.dispensed;
						}
						if (dispensableDrugs.getDrugCode().equals(serviceInfo.getDrugCode())
								&& serviceResinfo.getStatus().equalsIgnoreCase(ServiceStatus.REJECTED.name())) {
							++prescriptionStatus.rejected;
						}
						if (drugStatus.equalsIgnoreCase(ServiceStatus.APPROVED.name())) {
							++prescriptionStatus.approved;
						}
                        drugList.setPatientShareValue(
                                dispensableDrugs.getPatientShare() != null ? dispensableDrugs.getPatientShare()
                                        : BigDecimal.ZERO);
                        drugList.setPatientShareCurrency(
                                dispensableDrugs.getPatientShareCurrency() != null ? dispensableDrugs.getPatientShareCurrency()
                                        : "SAR");
                        drugList.setPayerShareValue(
                                dispensableDrugs.getNet() != null ? dispensableDrugs.getNet() : BigDecimal.ZERO);
                        drugList.setPayerShareCurrency(
                                dispensableDrugs.getNetCurrency() != null ? dispensableDrugs.getNetCurrency() : "SAR");
                    } else {
                    	++prescriptionStatus.approved;
                        drugList.setPatientShareValue(
                                serviceResinfo.getPatientShare() != null ? serviceResinfo.getPatientShare()
                                        : BigDecimal.ZERO);
                        drugList.setPatientShareCurrency(
                                serviceResinfo.getPatientShareCurrency() != null ? serviceResinfo.getPatientShareCurrency()
                                        : "SAR");
                        drugList.setPayerShareValue(
                                serviceResinfo.getNet() != null ? serviceResinfo.getNet() : BigDecimal.ZERO);
                        drugList.setPayerShareCurrency(
                                serviceResinfo.getNetCurrency() != null ? serviceResinfo.getNetCurrency() : "SAR");
                    }
                    setPbmValidationResult(requestId, drugList, serviceResinfo,drugStatus);
                } else {
                    drugList.setPatientShareValue(BigDecimal.ZERO);
                    drugList.setPayerShareValue(BigDecimal.ZERO);
                    drugList.setPayerShareCurrency("SAR");
                    drugList.setPatientShareCurrency("SAR");
                }
                ref.totalPrescriptionPrice += serviceInfo.getQuantity().doubleValue() * unitPrice;
                drugLists.add(drugList);
            });
        }
		model.setTotalPrescriptionPrice(BigDecimal.valueOf(ref.totalPrescriptionPrice));
		model.setDrugList(drugLists);
		String status = setPrescriptionStatus(prescriptionStatus.approved, prescriptionStatus.pending,
				prescriptionStatus.rejected, prescriptionStatus.dispensed);
		model.setePrescriptionStatus(StringUtils.isNotBlank(status) ? status : RequestStatusType.PENDING.value());
    }
    
	private String setPrescriptionStatus(int approved, int pending, int rejected, int dispensed) {
		String prescriptionStatusCode = "";
		if (rejected == 0
		        && dispensed == 0
		        && pending == 0
		        && approved > 0) {
		    prescriptionStatusCode = "DISPENSED";
		} else if (approved > 0
		        && dispensed == 0
		        && pending == 0
		        && rejected > 0) {
		    prescriptionStatusCode = "PARTIAL_DISPENSED";
		} else if (approved == 0
		        && dispensed == 0
		        && pending == 0
		        && rejected > 0) {
		    prescriptionStatusCode = "REJECTED";
		} else if (approved == 0
		        && rejected >= 0
		        && pending == 0
		        && dispensed > 0) {
		    prescriptionStatusCode = "DISPENSED";
		} else if ((approved >= 0 || rejected >= 0)
		        && pending == 0
		        && dispensed > 0) {
		    prescriptionStatusCode = "PARTIAL_DISPENSED";
		} else if (pending > 0) {
		    prescriptionStatusCode = "PENDING";
		} 
		return prescriptionStatusCode;
	}

    private void setDrugList(String requestId, EPrescriptionRequestModel model) {
        List<ServiceInfo> serviceInfoList = serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId);
        List<EPrescriptionDrugList> drugLists = new ArrayList<>();
        var ref = new Object() {
            Double totalPrescriptionPrice = 0.0;
        };
        if (serviceInfoList != null) {
            serviceInfoList.forEach(serviceInfo -> {
                EPrescriptionDrugList drugList = new EPrescriptionDrugList();
                drugList.setDrugCode(serviceInfo.getDrugCode());
                drugList.setScientificCode(serviceInfo.getScientificCode());
                drugList.setDuration(BigDecimal.valueOf(serviceInfo.getDuration()));
                drugList.setFrequency(serviceInfo.getFrequency());
                drugList.setQuantity(serviceInfo.getQuantity());
                if (null != serviceInfo.getServiceEndDate()) {
                    drugList.setServiceEndDate(sdf.format(serviceInfo.getServiceEndDate()));
                }
                drugList.setServiceStartDate(sdf.format(serviceInfo.getServiceStartDate()));
                drugList.setFrequencyOthersDescription(serviceInfo.getFrequencyOthersDescription());
                drugList.setOrderingClinician(serviceInfo.getOrderingClinician());
                drugList.setUnitPrice(serviceInfo.getUnitPrice());
                drugList.setUnitType(serviceInfo.getUnitType());
                drugList.setUseUnitValue(BigDecimal.valueOf(serviceInfo.getUseUnitValue()));
                Optional<ServiceResponseInfo> serviceResponseInfoOptional = serviceResponseInfoRepository
                        .findByRequestIdAndServiceID(requestId, serviceInfo.getId());
                if (serviceResponseInfoOptional.isPresent()) {
                    ServiceResponseInfo serviceResinfo = serviceResponseInfoOptional.get();
                    drugList.setPatientShareValue(
                            serviceResinfo.getPatientShare() != null ? serviceResinfo.getPatientShare()
                                    : BigDecimal.ZERO);
                    drugList.setPatientShareCurrency(
                            serviceResinfo.getPatientShareCurrency() != null ? serviceResinfo.getPatientShareCurrency()
                                    : "SAR");
                    drugList.setPayerShareValue(
                            serviceResinfo.getNet() != null ? serviceResinfo.getNet() : BigDecimal.ZERO);
                    drugList.setPayerShareCurrency(
                            serviceResinfo.getNetCurrency() != null ? serviceResinfo.getNetCurrency() : "SAR");
                    setPbmValidationResult(requestId, drugList, serviceResinfo,serviceResinfo.getStatus());
                } else {
                    drugList.setPatientShareValue(BigDecimal.ZERO);
                    drugList.setPayerShareValue(BigDecimal.ZERO);
                    drugList.setPayerShareCurrency("SAR");
                    drugList.setPatientShareCurrency("SAR");
                }
                ref.totalPrescriptionPrice += serviceInfo.getQuantity().doubleValue() * serviceInfo.getUnitPrice();
                drugLists.add(drugList);
            });
        }
        model.setTotalPrescriptionPrice(BigDecimal.valueOf(ref.totalPrescriptionPrice));
        model.setDrugList(drugLists);
    }

	private void setPbmValidationResult(String requestId, EPrescriptionDrugList drugList,
			ServiceResponseInfo serviceResinfo, String drugStatus) {
		PbmValidationResult result = new PbmValidationResult();
		List<EPrescriptionError> errorList = getErrors(serviceResinfo.getId(), requestId);
		result.setApprovedAmount(serviceResinfo.getApprovedAmount());
		result.setStatus(drugStatus);
		result.setRequestedAmount(serviceResinfo.getRequestedAmount());
		if (drugStatus.equalsIgnoreCase(ServiceStatus.PENDING.name())
				&& !serviceResinfo.getStatus().equalsIgnoreCase(ServiceStatus.PENDING.name())) {
			String denialCode = CommonDenialsCode.REQUIRED_PAYER_APPROVAL.value();
			errorList.add(new EPrescriptionError(denialCode, getRejectionReason(drugList.getDrugCode(), denialCode)));
		}
		result.setErrors(errorList);
		drugList.setPbmValidationResult(result);
	}

	public String getRejectionReason(String drugCode, String denialCode) {
		return commonDenialsRepository.findByDenialCode(denialCode)
				.map(commonDenial -> commonDenial.getDenialDescription().replace("<DrugCode>", drugCode))
				.orElse(null);
	}
    
    private List<EPrescriptionError> getErrors(Long serviceResponseInfoId, String requestId) {
        List<EPrescriptionError> errorList = new ArrayList<>();
        Optional<List<ServiceRejection>> serviceRejectionListOptional = serviceRejectionRepository
                .findByServiceResponseIdAndRequestId(serviceResponseInfoId, requestId);
        if (serviceRejectionListOptional.isPresent()) {
            serviceRejectionListOptional.get().forEach(serviceRejection -> {
                EPrescriptionError error = new EPrescriptionError();
                error.setDenialCode(serviceRejection.getDenialCode());
                error.setRejectionReason(serviceRejection.getRejectionReason());
                errorList.add(error);
            });
        }
        return errorList;
    }

    private void setDiagnosisCodes(String requestId, EPrescriptionRequestModel model) {
        List<DiagnosisCodes> diagnosisCodesList = diagnosisRepository.findByRequestIdAndIsNotDeleted(requestId);
        List<com.waseel.prescription.model.pbmpayerapis.DiagnosisCodes> diagnosisCodes = new ArrayList<>();
        if (diagnosisCodesList != null) {
            diagnosisCodesList.forEach(diagnosisCode -> {
                com.waseel.prescription.model.pbmpayerapis.DiagnosisCodes codes = new com.waseel.prescription.model.pbmpayerapis.DiagnosisCodes();
                codes.setDiagnosisType(diagnosisCode.getDiagnosisType());
                codes.setDiagnosisCode(diagnosisCode.getDiagnosisCode());
                diagnosisCodes.add(codes);
            });
        }
        model.setDiagnosisCodes(diagnosisCodes);
    }

    public PrescriptionCancellationResponseModel createPrescriptionCancellationResponse(DssResponse dssResponse,
                                                                                        String ePrescriptionReferenceNumber) {
        return new PrescriptionCancellationResponseModel(ePrescriptionReferenceNumber, dssResponse.getStatus(),
                dssResponse.getHttpStatusDescription(), false, false, dssResponse.getHttpStatusCode());
    }

    public MemberDemographicDataResponseModel mapMemberDemographicDataResponseModel(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, MemberDemographicDataResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public EPrescriptionResponseModel mapEPrescriptionResponseModel(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, EPrescriptionResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PrescriptionCancellationResponseModel createPrescriptionCancellationResponseFromEPrescriptionResponseModel(
            EPrescriptionResponseModel model, String ePrescriptionReferenceNum) {
        PrescriptionCancellationResponseModel response = new PrescriptionCancellationResponseModel();
        String statusDesc = !CollectionUtils.isEmpty(model.getErrors())
                ? StringUtils.strip(model.getErrors().toString(), "[]")
                : model.getStatusDescription();
        response.setStatus(model.getStatus());
        response.setStatusDescription(statusDesc);
        response.setePrescriptionReferenceNumber(ePrescriptionReferenceNum);
        return response;
    }

    public void createPrescriptionDispenseResponseFromEPrescriptionResponseModel(
            EPrescriptionResponseModel model, String ePrescriptionReferenceNum,
            PrescriptionDispenseResponseModel responseModel) {
        String statusDesc = !CollectionUtils.isEmpty(model.getErrors())
                ? StringUtils.strip(model.getErrors().toString(), "[]")
                : model.getStatusDescription();
        if (!StringUtils.isBlank(model.getStatus())) {
            responseModel.setStatus(model.getStatus());
            responseModel.setStatusDescription(statusDesc);
        }
        responseModel.setePrescriptionReferenceNumber(ePrescriptionReferenceNum);
    }

    public SmsNotificationResponseModel mapSmsNotificationResponse(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, SmsNotificationResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
        return null;
    }

    public EmailNotificationResponseModel mapEmailNotificationResponseModel(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, EmailNotificationResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
        return null;
    }

    public JwtResponse mapJwtResponse(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, JwtResponse.class);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public ModifyDecisionResponseModel mapModifyDecisionResponseModel(ContentCachingResponseWrapper response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new String(response.getContentAsByteArray()), ModifyDecisionResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ModifyDecisionRequestModel mapModifyDecisionRequestModel(ContentCachingRequestWrapper requestWrapper) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new String(requestWrapper.getContentAsByteArray()),
                    ModifyDecisionRequestModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public SensitiveDrugResponseModel mapBRResponse(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, SensitiveDrugResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}