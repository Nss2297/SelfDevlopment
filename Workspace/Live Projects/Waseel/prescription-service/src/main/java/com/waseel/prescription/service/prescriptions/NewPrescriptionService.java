package com.waseel.prescription.service.prescriptions;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.prescription.model.br.SensitiveDrugRequestModel;
import com.waseel.prescription.model.br.SensitiveDrugResponseModel;
import com.waseel.prescription.model.dss.DssResponse;
import com.waseel.prescription.model.dss.Result;
import com.waseel.prescription.model.eligibility.EligibilityResponseModel;
import com.waseel.prescription.model.enums.BusinessRulesType;
import com.waseel.prescription.model.enums.EligibilityStatus;
import com.waseel.prescription.model.enums.PbmRequestType;
import com.waseel.prescription.model.enums.PolicyConsumptionStatus;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.exclusion.DrugExclusionResponseModel;
import com.waseel.prescription.model.formulary.DrugFormularyResponseModel;
import com.waseel.prescription.model.pbmpayerapis.MemberDemographicDataResponseModel;
import com.waseel.prescription.model.pbmpayerapis.PolicyInformationModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.model.prescription.CommonDrugList;
import com.waseel.prescription.model.prescription.DrugList;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.persist.prescriptionservice.EligibleDssPolicy;
import com.waseel.prescription.persist.prescriptionservice.MemberPolicyUsage;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRejection;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.prescriptionservice.EligibleDssPolicyRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberPolicyUsageRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRejectionRepository;
import com.waseel.prescription.service.clienthandler.RestHandler;
import com.waseel.prescription.service.management.BusinessRuleService;
import com.waseel.prescription.service.management.CombineResponseService;
import com.waseel.prescription.service.management.DMLService;
import com.waseel.prescription.service.management.SessionService;
import com.waseel.prescription.service.management.TransactionLogService;
import com.waseel.prescription.service.mapper.MapperService;

@Service
public class NewPrescriptionService {

	@Value(value = "${feature.toggle: false}")
	private boolean featureToggleEnabled;

	@Value(value = "${dss.feature.toggle: false}")
	private boolean skipDssToggleEnabled;

	@Autowired
	private EligibleDssPolicyRepository eligibleDssPolicyRepository;

	@Autowired
	private PayerMemberInfoService payerMemberInfoService;

	@Autowired
	private MapperService mapperService;

	@Autowired
	private RestHandler restHandler;

	@Autowired
	private DMLService dmlService;

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private BusinessRuleService businessRuleService;

	@Autowired
	private MemberPolicyUsageRepository memberPolicyUsageRepository;

	@Autowired
	private CombineResponseService combineResponseService;

	@Autowired
	private PrescriptionRejectionRepository prescriptionRejectionRepository;

	@Autowired
	private EPrescriptionApprovalService prescriptionApprovalService;

	@Autowired
	private EmailAndSmsNotificationService emailAndSmsNotificationService;

	@Autowired
	private MappingPayerIdService mappingPayerIdService;

	@Autowired
	private FetchBenefitCodeService fetchBenefitCodeService;

	public PrescriptionResponseModel newSubmissionController(PrescriptionRequestModel prescriptionRequest,
			ContentCachingRequestWrapper requestWrapper, String requestId, String providerId, String sourceType,
			String payerId) {
		PrescriptionResponseModel responseModel = null;
		TransactionLog transactionLog = transactionLogService.addTransaction(RequestType.NEW,
				prescriptionRequest.getPayerId(), providerId, requestId, null, sourceType);
		if (null != transactionLog && null != transactionLog.getTransactionLogId()
				&& !transactionLog.getTransactionLogId().toString().isEmpty()) {
			sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
			// Business Rule: Eligibility check
			// EligibilityResponseModel eligibilityResponseModel =
			// businessRuleService.eligibilityCheck(
			// prescriptionRequest.getIdNumber(), prescriptionRequest.getPayerId(),
			// providerId, requestId);
			EligibilityResponseModel eligibilityResponseModel = new EligibilityResponseModel();
			eligibilityResponseModel.setStatus(EligibilityStatus.ELIGIBLE.getValue());
			if (null != eligibilityResponseModel) {
				responseModel = manageEligibilityResponse(eligibilityResponseModel, prescriptionRequest, transactionLog,
						requestId, providerId, payerId);
			}
		}
		return responseModel;
	}

	private PrescriptionResponseModel manageEligibilityResponse(EligibilityResponseModel eligibilityResponseModel,
			PrescriptionRequestModel prescriptionRequest, TransactionLog transactionLog, String requestId,
			String providerId, String payerId) {
		PrescriptionResponseModel responseModel = null;
		Timestamp sendingDateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());

		if (eligibilityResponseModel.getStatus().equals(EligibilityStatus.ELIGIBLE.getValue())) {
			if (featureToggleEnabled) {
				prescriptionRequest.setePrescriptionReferenceNumber(transactionLog.getePrescriptionReferenceNumber());
				return manageDssResponse(prescriptionRequest, requestId, providerId, sendingDateTime, payerId);
			} else {
				// Business Rule : Policy Consumption check
				String benefitCode = fetchBenefitCodeService.fetchBenefitCodeByPhysicianDetails(
						prescriptionRequest.getPhysicianLicenseNumber(), prescriptionRequest.getPhysicianSpeciality());
				PolicyResponseModel policyResponseModel = businessRuleService.policyConsumptionCheck(
						prescriptionRequest.getIdNumber(), benefitCode, prescriptionRequest.getCaseType().toUpperCase(),
						String.valueOf(prescriptionRequest.getTotalPrice()), requestId,
						prescriptionRequest.getPayerId(), prescriptionRequest.getPolicyConsumptionDrugList(),
						providerId, RequestType.NEW.value());
				if (null != policyResponseModel) {
					responseModel = managePolicyResponse(policyResponseModel, providerId, prescriptionRequest, payerId,
							requestId, sendingDateTime, transactionLog);
				}
			}
		} else {
			responseModel = mapperService.createPrescriptionResponseFromEligibilityResponse(eligibilityResponseModel,
					transactionLog.getePrescriptionReferenceNumber(), requestId, prescriptionRequest);
			dmlService.savePrescriptionRequest(prescriptionRequest, responseModel, sendingDateTime, providerId,
					BusinessRulesType.ELIGIBILITY_CHECK.value(), eligibilityResponseModel.getReferenceNumber());
			if (eligibilityResponseModel.getStatus().equals(EligibilityStatus.FAILED.getValue())
					|| eligibilityResponseModel.getStatus().equals(EligibilityStatus.INVALID.getValue())) {
				populatePrescriptionRejection(eligibilityResponseModel.getDenialCode(),
						eligibilityResponseModel.getDescription(), eligibilityResponseModel.getReferenceNumber(),
						responseModel.getRequestId());
			}
		}
		return responseModel;
	}

	private void manageSensitiveDrugResponse(SensitiveDrugResponseModel sensitiveDrugResponseModel,
			PrescriptionRequestModel prescriptionRequest, SensitiveDrugResponseModel sensitiveDrugResponseModel2,
			String requestId, String providerId, String payerId) {
		prescriptionRequest.getDrugList().stream()
				.filter(requestedDrug -> requestedDrug.getScientificCode() == null
						|| StringUtils.isEmpty(requestedDrug.getScientificCode()))
				.filter(requestedDrug -> requestedDrug.getDrugCode() != null).forEach(requestedDrug -> {
					sensitiveDrugResponseModel.getDrugList().stream().filter(
							sensitiveDrug -> !requestedDrug.getDrugCode().equalsIgnoreCase(sensitiveDrug.getDrugCode()))
							.forEach(sensitiveDrug -> {
								// durg code is not valid(not in sensitive drug list)
							});
				});
	}

	public SensitiveDrugRequestModel populateSensitiveDrugRequestModel(PrescriptionRequestModel prescriptionRequest,
			String requestId, String providerId, String payerId) {
		List<String> drugList = prescriptionRequest.drugList.stream()
				.filter(requestedDrug -> StringUtils.isEmpty(requestedDrug.getScientificCode())
						|| requestedDrug.getScientificCode() == null)
				.filter(requestedDrug -> requestedDrug.getDrugCode() != null
						&& StringUtils.isNotEmpty(requestedDrug.getDrugCode()))
				.map(CommonDrugList::getDrugCode).collect(Collectors.toList());
		SensitiveDrugRequestModel requestModel = new SensitiveDrugRequestModel();
		requestModel.setDrugList(drugList);
		requestModel.setPayerId(payerId);
		requestModel.setProviderId(providerId);
		requestModel.setRequestId(requestId);
		return requestModel;
	}

	private boolean checkRequestedDrugHasScientificCode(PrescriptionRequestModel prescriptionRequestModel) {
		if (prescriptionRequestModel.getDrugList() != null) {
			return prescriptionRequestModel.getDrugList().stream()
					.anyMatch(drugList -> !StringUtils.isBlank(drugList.getScientificCode())
							&& StringUtils.isBlank(drugList.getDrugCode()));
		}
		return false;
	}

	private PrescriptionResponseModel manageDssResponse(PrescriptionRequestModel prescriptionRequest, String requestId,
			String providerId, Timestamp sendingDateTime, String payerId) {
		PrescriptionResponseModel responseModel = null;
		mappingPayerIdService.mapPayerIdForDss(prescriptionRequest, payerId);
		DssResponse dssResponse = null;
		SensitiveDrugResponseModel sensitiveDrugResponseModel = null;
		// confirm location
		// VALIDATE THROUGH SENSITIVE DRUG LIST
		SensitiveDrugRequestModel sensitiveDrugRequestModel = populateSensitiveDrugRequestModel(prescriptionRequest,
				requestId, providerId, payerId);
		if (sensitiveDrugRequestModel.getDrugList() != null && !sensitiveDrugRequestModel.getDrugList().isEmpty()) {
			sensitiveDrugResponseModel = businessRuleService
					.validateNewPrescriptionForSensitiveDrugs(sensitiveDrugRequestModel);
		}
		if (sensitiveDrugResponseModel != null && sensitiveDrugResponseModel.getErrorCode() != null) {
			return mapperService.createPrescriptionResponseFromSensitiveDrugResponse(sensitiveDrugResponseModel,
					prescriptionRequest.getePrescriptionReferenceNumber());
		}
		if (!skipDssToggleEnabled) {

			dssResponse = restHandler.handlePrescriptionRequest(
					mapperService.createDssRequest(prescriptionRequest, requestId, providerId));
		} else {
			ResponseEntity<MemberDemographicDataResponseModel> demographicData = payerMemberInfoService
					.getMemberDemographicData(Long.valueOf(prescriptionRequest.getIdNumber()));
			List<String> policies = new ArrayList<>();
			if (demographicData != null && demographicData.getBody() != null) {
				policies = demographicData.getBody().getPolicyInformation().stream()
						.map(PolicyInformationModel::getPolicyNumber).collect(Collectors.toList());
			}
			Optional<List<EligibleDssPolicy>> eligiblePolicy = eligibleDssPolicyRepository.findByPolicyNumber(policies);
			if (eligiblePolicy.isPresent() && !eligiblePolicy.get().isEmpty()) {
				dssResponse = restHandler.handlePrescriptionRequest(
						mapperService.createDssRequest(prescriptionRequest, requestId, providerId));

			} else {
				dssResponse = mapApprovedDssResponse(prescriptionRequest.getDrugList(), requestId);
			}
		}
		if (dssResponse != null) {
			String ePrescriptionReferenceNumber = prescriptionRequest.getePrescriptionReferenceNumber();
			combineResponseService.combineResponseWithDssResponse(dssResponse, null, null, sensitiveDrugResponseModel);
			responseModel = mapperService.createPrescriptionResponse(dssResponse, prescriptionRequest,
					ePrescriptionReferenceNumber, null);
			if (dssResponse.getResults() != null)
				responseModel.setStatus(combineResponseService.setRequestStatus(dssResponse.getResults()));
			manageDssResponse(responseModel, prescriptionRequest, sendingDateTime, providerId);
			prescriptionRequest.setPayerId(payerId);
			if (responseModel != null) {
				emailAndSmsNotificationService.notifyPatientByEmailAndSMS(requestId, prescriptionRequest.getIdNumber(),
						ePrescriptionReferenceNumber, PbmRequestType.NEW.value());
			}
		}
		return responseModel;
	}

	private DssResponse mapApprovedDssResponse(List<DrugList> drugList, String requestId) {
		DssResponse dssResponse = new DssResponse();
		dssResponse.setHttpStatusCode(200);
		dssResponse.setRequestId(requestId);

		List<Result> results = new ArrayList<>();

		for (DrugList drug : drugList) {
			Result result = new Result();
			result.setAmount(drug.getQuantity().multiply(BigDecimal.valueOf(drug.getUnitPrice())));
			result.setDaysOfSupply(drug.getDuration());
			result.setDispensedQuantity(drug.getQuantity());
			if (drug.getDrugCode() != null)
				result.setNdcDrugCode(drug.getDrugCode());

			if (drug.getScientificCode() != null)
				result.setScientificCode(drug.getScientificCode());

			result.setStatus("APPROVED");
			results.add(result);
		}
		dssResponse.setResults(results);
		dssResponse.setStatus("APPROVED");
		return dssResponse;
	}

	private PrescriptionResponseModel managePolicyResponse(PolicyResponseModel policyResponseModel, String providerId,
			PrescriptionRequestModel prescriptionRequest, String payerId, String requestId, Timestamp sendingDateTime,
			TransactionLog transactionLog) {
		PrescriptionResponseModel responseModel = null;
		String ePrescriptionReferenceNumber = transactionLog.getePrescriptionReferenceNumber();
		if (StringUtils.isNotBlank(policyResponseModel.getStatus())
				&& policyResponseModel.getStatus().equals(PolicyConsumptionStatus.APPROVED.getValue())) {

			// VALIDATE THROUGH SENSITIVE DRUG LIST
			SensitiveDrugRequestModel sensitiveDrugRequestModel = populateSensitiveDrugRequestModel(prescriptionRequest,
					requestId, providerId, payerId);
			SensitiveDrugResponseModel sensitiveDrugResponseModel = null;
			if (sensitiveDrugRequestModel.getDrugList() != null && !sensitiveDrugRequestModel.getDrugList().isEmpty()) {
				sensitiveDrugResponseModel = businessRuleService
						.validateNewPrescriptionForSensitiveDrugs(sensitiveDrugRequestModel);
			}

			if (sensitiveDrugResponseModel != null && sensitiveDrugResponseModel.getErrorCode() != null) {
				return mapperService.createPrescriptionResponseFromSensitiveDrugResponse(sensitiveDrugResponseModel,
						ePrescriptionReferenceNumber);
			}

			populateMemberPolicyUsage(prescriptionRequest.getPayerId(), providerId, prescriptionRequest.getIdNumber(),
					policyResponseModel, ePrescriptionReferenceNumber, policyResponseModel.getStatus());
			List<DrugFormularyResponseModel> drugFormularyResponseModelList = businessRuleService.drugFormularyCheck(
					payerId, prescriptionRequest.getIdNumber(), prescriptionRequest.drugList, requestId);
			if (drugFormularyResponseModelList != null && drugFormularyResponseModelList.size() == 1
					&& drugFormularyResponseModelList.get(0).getDrugCode() == null) {
				return mapperService.createPrescriptionResponseFromDrugFormularyResponse(
						drugFormularyResponseModelList.get(0), ePrescriptionReferenceNumber);
			}
			DrugExclusionResponseModel drugExclusionResponseModel = businessRuleService.checkDrugExclusion(
					prescriptionRequest.drugList, requestId, prescriptionRequest.getPhysicianLicenseNumber(),
					prescriptionRequest.getPhysicianSpeciality(), payerId, providerId);
			if (drugExclusionResponseModel != null && (drugExclusionResponseModel.getDrugList() == null
					|| drugExclusionResponseModel.getDrugList().isEmpty())) {
				return mapperService.createPrescriptionResponseFromDrugExclusionResponse(drugExclusionResponseModel,
						ePrescriptionReferenceNumber);
			}
			mappingPayerIdService.mapPayerIdForDss(prescriptionRequest, payerId);
			DssResponse dssResponse = restHandler.handlePrescriptionRequest(
					mapperService.createDssRequest(prescriptionRequest, requestId, providerId));
			if (dssResponse != null) {
				combineResponseService.combineResponseWithDssResponse(dssResponse, drugFormularyResponseModelList,
						drugExclusionResponseModel, sensitiveDrugResponseModel);
				if (dssResponse.getResults() != null)
					combineResponseService.setRequestStatus(dssResponse.getResults());
				responseModel = mapperService.createPrescriptionResponse(dssResponse, prescriptionRequest,
						ePrescriptionReferenceNumber, policyResponseModel);
				setRequestStatusPending(drugFormularyResponseModelList, drugExclusionResponseModel, responseModel);
				manageDssResponse(responseModel, prescriptionRequest, sendingDateTime, providerId);
				prescriptionRequest.setPayerId(payerId);
				responseModel = prescriptionApprovalService.manageEPrescriptionApprovalForNewOrFollowUp(
						prescriptionRequest, ePrescriptionReferenceNumber, requestId, responseModel,
						PbmRequestType.NEW);
				if (responseModel != null) {
					emailAndSmsNotificationService.notifyPatientByEmailAndSMS(requestId,
							prescriptionRequest.getIdNumber(), ePrescriptionReferenceNumber,
							PbmRequestType.NEW.value());
				}
			}
		} else {
			responseModel = mapperService.createPrescriptionResponseFromPolicyConsumptionResponse(policyResponseModel,
					ePrescriptionReferenceNumber, requestId, prescriptionRequest.getDrugList());
			dmlService.savePrescriptionRequest(prescriptionRequest, responseModel, sendingDateTime, providerId,
					BusinessRulesType.POLICY_CONSUMPTION_CHECK.value(), "");
			if (policyResponseModel.getStatus().equals(PolicyConsumptionStatus.FAILED.getValue())
					|| policyResponseModel.getStatus().equals(PolicyConsumptionStatus.INVALID.getValue())) {
				populatePrescriptionRejection(policyResponseModel.getDenialCode(),
						policyResponseModel.getDenialDescription(), null, responseModel.getRequestId());
			}
		}
		return responseModel;
	}

	private void setRequestStatusPending(List<DrugFormularyResponseModel> drugFormularyResponseModelList,
			DrugExclusionResponseModel drugExclusionResponseModel, PrescriptionResponseModel responseModel) {
		if (null != responseModel) {
			if ((drugFormularyResponseModelList != null && drugFormularyResponseModelList.stream()
					.anyMatch(model -> model.getStatusCode().equals(RequestStatusType.REJECTED.value())))
					|| (drugExclusionResponseModel != null
							&& drugExclusionResponseModel.getDrugList().stream().anyMatch(
									drugList -> drugList.getStatusCode().equals(RequestStatusType.REJECTED.value())))) {
				responseModel.setStatus(RequestStatusType.PENDING.value());
			}
		}
	}

	private void manageDssResponse(PrescriptionResponseModel responseModel,
			PrescriptionRequestModel prescriptionRequest, Timestamp sendingDateTime, String providerId) {
		if (responseModel != null && responseModel.getHttpStatusCode() == HttpStatus.OK.value()) {
			PrescriptionRequest prescriptionRequestEntity = dmlService.savePrescriptionRequest(prescriptionRequest,
					responseModel, sendingDateTime, providerId, "", "");
			if (prescriptionRequestEntity != null) {
				responseModel.setCanCancel(prescriptionRequestEntity.getCanCancel());
				responseModel.setCanFollowUp(prescriptionRequestEntity.getCanFollowUp());
			}
		}
	}

	private void populateMemberPolicyUsage(String payerId, String providerId, String idNumber,
			PolicyResponseModel policyResponseModel, String ePrescriptionReferenceNumber, String status) {
		MemberPolicyUsage memberPolicyUsage = new MemberPolicyUsage(payerId, providerId,
				policyResponseModel.getMemberId(), Long.valueOf(idNumber), policyResponseModel.getPolicyNumber(),
				policyResponseModel.getPolicyClass(), policyResponseModel.getPolicyBenefit(),
				null != policyResponseModel.getBenefitLimitValue() ? policyResponseModel.getBenefitLimitValue()
						: new BigDecimal(0),
				policyResponseModel.getBenefitLimitCurrency(), new BigDecimal(policyResponseModel.getRemainingLimit()),
				policyResponseModel.getBenefitRemainingLimitCurrency(), ePrescriptionReferenceNumber, status);
		memberPolicyUsageRepository.save(memberPolicyUsage);
	}

	private void populatePrescriptionRejection(String denialCode, String denialDescription, String referenceNo,
			String requestId) {
		PrescriptionRejection prescriptionRejection = new PrescriptionRejection(denialCode, denialDescription,
				requestId, referenceNo, true);
		prescriptionRejectionRepository.save(prescriptionRejection);
	}
}
