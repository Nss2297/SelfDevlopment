package com.waseel.prescription.service.prescriptions;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.br.SensitiveDrugRequestModel;
import com.waseel.prescription.model.br.SensitiveDrugResponseModel;
import com.waseel.prescription.model.dss.DssResponse;
import com.waseel.prescription.model.dss.Result;
import com.waseel.prescription.model.eligibility.EligibilityResponseModel;
import com.waseel.prescription.model.enums.CommonWords;
import com.waseel.prescription.model.enums.Currency;
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
import com.waseel.prescription.model.prescription.BusinessRuleValidations;
import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.model.prescription.DrugList;
import com.waseel.prescription.model.prescription.MedicalValidations;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.model.prescription.ServiceResponse;
import com.waseel.prescription.persist.prescriptionservice.Diagnosis;
import com.waseel.prescription.persist.prescriptionservice.DiagnosisId;
import com.waseel.prescription.persist.prescriptionservice.EligibleDssPolicy;
import com.waseel.prescription.persist.prescriptionservice.MemberPolicyUsage;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRejection;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.persist.prescriptionservice.ServiceRejection;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.prescriptionservice.DiagnosisRepository;
import com.waseel.prescription.repository.prescriptionservice.EligibleDssPolicyRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberPolicyUsageRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.TransactionLogRepository;
import com.waseel.prescription.service.clienthandler.RestHandler;
import com.waseel.prescription.service.management.BusinessRuleService;
import com.waseel.prescription.service.management.CombineResponseService;
import com.waseel.prescription.service.management.DMLService;
import com.waseel.prescription.service.management.SessionService;
import com.waseel.prescription.service.management.TransactionLogService;
import com.waseel.prescription.service.mapper.MapperService;
import com.waseel.prescription.service.validation.TechnicalValidationService;

@Service
public class FollowUpPrescriptionService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EligibleDssPolicyRepository eligibleDssPolicyRepository;

	@Autowired
	private PayerMemberInfoService payerMemberInfoService;

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	@Autowired
	private MapperService mapperService;

	@Autowired
	private PrescriptionRequestRepository prescriptionRequestRepository;

	@Autowired
	private DiagnosisRepository diagnosisRepository;

	@Autowired
	private ServiceInfoRepository serviceInfoRepository;

	@Autowired
	private ServiceResponseInfoRepository serviceResponseInfoRepository;

	@Autowired
	private ServiceRejectionRepository serviceRejectionRepository;

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private RestHandler restHandler;

	@Autowired
	private TechnicalValidationService technicalValidationService;

	@Autowired
	private BusinessRuleService businessRuleService;

	@Autowired
	private DMLService dmlService;

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
	private NewPrescriptionService newPrescriptionService;

	@Value(value = "${feature.toggle: false}")
	private boolean featureToggleEnabled;

	@Value(value = "${dss.feature.toggle: false}")
	private boolean skipDssToggleEnabled;

	@Autowired
	private MappingPayerIdService mappingPayerIdService;

	@Autowired
	private FetchBenefitCodeService fetchBenefitCodeService;

	private static final String MSG_ISCANCELLED = "Request is already cancelled. You can't do FollowUp";
	private static final String MSG_DISPENSED = "Not allowed to do FollowUp because this request is already Dispensed.";
	private static final String MSG_CANNOTFOLLOWUP = "Not allowed to do FollowUp with this request.";
	private static final String MSG_NO_POLICY = "Policy details not found.";

	public PrescriptionResponseModel manageFollowUpRequest(PrescriptionRequestModel requestModel,
			ContentCachingRequestWrapper requestWrapper, PrescriptionRequest preRequest, String providerId,
			String sourceType) throws PrescriptionException {
		Timestamp sendingDateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
		String ePrescriptionReferenceNumber = requestModel.getePrescriptionReferenceNumber();
		Optional<TransactionLog> transactionLogOp = transactionLogRepository
				.findByePrescriptionReferenceNumberAndTransactionType(ePrescriptionReferenceNumber,
						RequestType.NEW.name());
		if (transactionLogOp.isPresent()) {
			String requestId = transactionLogOp.get().getRequestId();
			String payerId = requestModel.getPayerId();
			TransactionLog transactionLog = transactionLogService.addTransaction(RequestType.FOLLOWUP, payerId,
					providerId, requestId, ePrescriptionReferenceNumber, sourceType);
			if (null != transactionLog) {
				sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
				return handleValidInvalidResponse(preRequest, ePrescriptionReferenceNumber, requestModel,
						sendingDateTime, providerId, payerId, transactionLog);
			}
		} else {
			log.error("Invalid EPrescriptionReferenceNumber[{}].", ePrescriptionReferenceNumber);
			throw new PrescriptionException(technicalValidationService.populateInvalidPrescriptionResponse(
					requestWrapper, "Invalid EPrescriptionReferenceNumber.", ePrescriptionReferenceNumber));
		}
		return null;
	}

	private void manageServiceInfo(String requestId, PolicyResponseModel policyResponseModel) {
		List<ServiceInfo> servicelist = serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId);
		List<ServiceResponseInfo> serviceResponseInfos = new ArrayList<>();
		List<ServiceRejection> serviceRejections = new ArrayList<>();
		servicelist.stream()
				.forEach(service -> populateServiceResponseInfo(requestId, service.getDrugCode(), service.getId(),
						policyResponseModel, serviceResponseInfos, serviceRejections, service.getScientificCode()));
		if (!serviceResponseInfos.isEmpty()) {
			serviceResponseInfoRepository.saveAll(serviceResponseInfos);
			if (!serviceRejections.isEmpty()) {
				serviceRejectionRepository.saveAll(serviceRejections);
			}
		}
	}

	private void populateServiceResponseInfo(String requestId, String drugCode, long serviceId,
			PolicyResponseModel policyResponseModel, List<ServiceResponseInfo> serviceResponseInfos,
			List<ServiceRejection> serviceRejections, String scientificCode) {
		Optional<ServiceResponseInfo> serviceResponseInfoOp = serviceResponseInfoRepository
				.findByRequestIdAndServiceID(requestId, serviceId);
		if (serviceResponseInfoOp.isPresent()) {
			String status = policyResponseModel.getStatus();
			ServiceResponseInfo serviceResponseInfo = serviceResponseInfoOp.get();
			serviceResponseInfo.setStatus(status);
			serviceResponseInfo.setStatusDescription(policyResponseModel.getStatusDescription());
			serviceResponseInfos.add(serviceResponseInfo);
			if (status.equals(PolicyConsumptionStatus.REJECTED.getValue())) {
				modifyRejectionReason(drugCode, requestId, serviceResponseInfo.getId(), policyResponseModel,
						serviceRejections, scientificCode);
			}
		}
	}

	private void modifyRejectionReason(String drugCode, String requestId, Long serviceResponseInfoId,
			PolicyResponseModel policyResponseModel, List<ServiceRejection> serviceRejections, String scientificCode) {
		serviceRejections.add(new ServiceRejection(drugCode, policyResponseModel.getDenialCode(),
				policyResponseModel.getDenialDescription(), requestId, scientificCode, serviceResponseInfoId));
	}

	private void populateMemberPolicyUsage(PrescriptionRequestModel requestModel,
			PolicyResponseModel policyResponseModel, String providerId, String prescriptionStatus) {
		MemberPolicyUsage memberPolicyUsage = new MemberPolicyUsage(requestModel.getPayerId(), providerId,
				policyResponseModel.getMemberId(), Long.valueOf(requestModel.getIdNumber()),
				policyResponseModel.getPolicyNumber(), policyResponseModel.getPolicyClass(),
				policyResponseModel.getPolicyBenefit(),
				null != policyResponseModel.getBenefitLimitValue() ? policyResponseModel.getBenefitLimitValue()
						: new BigDecimal(0),
				policyResponseModel.getBenefitLimitCurrency(), new BigDecimal(policyResponseModel.getRemainingLimit()),
				policyResponseModel.getBenefitRemainingLimitCurrency(), requestModel.getePrescriptionReferenceNumber(),
				prescriptionStatus);
		memberPolicyUsageRepository.save(memberPolicyUsage);
	}

	private PrescriptionResponseModel manageDssResponse(PrescriptionRequestModel requestModel, String requestId,
			String ePrescriptionReferenceNumber, Timestamp sendingDateTime, String providerId,
			PolicyResponseModel policyResponseModel, String payerId) {
		PrescriptionResponseModel responseModel = null;

		// VALIDATE THROUGH SENSITIVE DRUG LIST
		SensitiveDrugRequestModel sensitiveDrugRequestModel = newPrescriptionService
				.populateSensitiveDrugRequestModel(requestModel, requestId, providerId, payerId);
		SensitiveDrugResponseModel sensitiveDrugResponseModel = null;
		if (sensitiveDrugRequestModel.getDrugList() != null && !sensitiveDrugRequestModel.getDrugList().isEmpty()) {
			sensitiveDrugResponseModel = businessRuleService
					.validateNewPrescriptionForSensitiveDrugs(sensitiveDrugRequestModel);
		}

		if (sensitiveDrugResponseModel != null && sensitiveDrugResponseModel.getErrorCode() != null) {
			return mapperService.createPrescriptionResponseFromSensitiveDrugResponse(sensitiveDrugResponseModel,
					ePrescriptionReferenceNumber);
		}

		List<DrugFormularyResponseModel> drugFormularyResponseModelList = businessRuleService.drugFormularyCheck(
				requestModel.getPayerId(), requestModel.getIdNumber(), requestModel.getDrugList(), requestId);
		if (drugFormularyResponseModelList != null && drugFormularyResponseModelList.size() == 1
				&& drugFormularyResponseModelList.get(0).getDrugCode() == null) {
			return mapperService.createPrescriptionResponseFromDrugFormularyResponse(
					drugFormularyResponseModelList.get(0), ePrescriptionReferenceNumber);
		}
		DrugExclusionResponseModel drugExclusionResponseModel = businessRuleService.checkDrugExclusion(
				requestModel.drugList, requestId, requestModel.getPhysicianLicenseNumber(),
				requestModel.getPhysicianSpeciality(), payerId, providerId);
		if (drugExclusionResponseModel != null && (drugExclusionResponseModel.getDrugList() == null
				|| drugExclusionResponseModel.getDrugList().isEmpty())) {
			return mapperService.createPrescriptionResponseFromDrugExclusionResponse(drugExclusionResponseModel,
					ePrescriptionReferenceNumber);
		}
		mappingPayerIdService.mapPayerIdForDss(requestModel, payerId);
		DssResponse dssResponse = restHandler
				.handleFollowupPrescriptionRequest(mapperService.createDssRequest(requestModel, requestId, providerId));
		if (null != dssResponse) {
			combineResponseService.combineResponseWithDssResponse(dssResponse, drugFormularyResponseModelList,
					drugExclusionResponseModel, sensitiveDrugResponseModel);
			if (dssResponse.getResults() != null)
				combineResponseService.setRequestStatus(dssResponse.getResults());
			responseModel = mapperService.createPrescriptionResponse(dssResponse, requestModel,
					ePrescriptionReferenceNumber, policyResponseModel);
			setRequestStatusPending(drugFormularyResponseModelList, drugExclusionResponseModel, responseModel);
			if (responseModel.getHttpStatusCode() == HttpStatus.OK.value()) {
				Optional<PrescriptionRequest> prescriptionReq = prescriptionRequestRepository
						.findByRequestId(responseModel.getRequestId());
				if (prescriptionReq.isPresent()) {
					PrescriptionRequest prescriptionRequest = prescriptionReq.get();
					manageFollowupDiagnosis(requestId, requestModel);
					manageFollowupServiceInfo(requestId, requestModel, responseModel);
					updateDataInPrescriptionRequest(prescriptionRequest, responseModel, sendingDateTime, requestModel);
					responseModel.setCanCancel(prescriptionRequest.getCanCancel());
					responseModel.setCanFollowUp(prescriptionRequest.getCanFollowUp());
					requestModel.setPayerId(payerId);
					responseModel = prescriptionApprovalService.manageEPrescriptionApprovalForNewOrFollowUp(
							requestModel, ePrescriptionReferenceNumber, requestId, responseModel,
							PbmRequestType.FOLLOWUP);
					if (responseModel != null) {
						emailAndSmsNotificationService.notifyPatientByEmailAndSMS(requestId, requestModel.getIdNumber(),
								ePrescriptionReferenceNumber, PbmRequestType.FOLLOWUP.value());
					}
				}
			}
		}
		return responseModel;
	}

	private void setRequestStatusPending(List<DrugFormularyResponseModel> drugFormularyResponseModelList,
			DrugExclusionResponseModel drugExclusionResponseModel, PrescriptionResponseModel responseModel) {
		if ((drugFormularyResponseModelList != null && drugFormularyResponseModelList.stream()
				.anyMatch(model -> model.getStatusCode().equals(RequestStatusType.REJECTED.value())))
				|| (drugExclusionResponseModel != null && drugExclusionResponseModel.getDrugList().stream()
						.anyMatch(drugList -> drugList.getStatusCode().equals(RequestStatusType.REJECTED.value())))) {
			responseModel.setStatus(RequestStatusType.PENDING.value());
		}
	}

	private void updateDataInPrescriptionRequest(PrescriptionRequest prescriptionRequest,
			PrescriptionResponseModel responseModel, Timestamp sendingDateTime, PrescriptionRequestModel requestModel) {
		String reqStatus = responseModel.getStatus();
		prescriptionRequest.setStatusCode(responseModel.getStatus());
		prescriptionRequest.setStatusDescription(responseModel.getStatusDescription());
		prescriptionRequest.setSendDateTime(sendingDateTime);
		prescriptionRequest.setReceivedDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		prescriptionRequest.setLastUpdateDate(new Date());
		if (reqStatus.equals(RequestStatusType.REJECTED.value())) {
			prescriptionRequest.setCanCancel(false);
			prescriptionRequest.setCanFollowUp(true);
		} else {
			prescriptionRequest.setCanCancel(true);
			prescriptionRequest.setCanFollowUp(true);
		}
		prescriptionRequest.setCaseType(requestModel.getCaseType().toUpperCase());
		prescriptionRequest.setePrescriptionReferenceNumber(responseModel.getePrescriptionReferenceNumber());
		prescriptionRequest.setPatientShare(
				responseModel.getPatientShare() != null ? responseModel.getPatientShare() : BigDecimal.ZERO);
		prescriptionRequest
				.setPayerShare(responseModel.getPayerShare() != null ? responseModel.getPayerShare() : BigDecimal.ZERO);
		prescriptionRequestRepository.save(prescriptionRequest);
	}

	private void manageFollowupDiagnosis(String requestId, PrescriptionRequestModel requestModel) {
		List<DiagnosisCodes> diagnosisCodes = requestModel.getDiagnosisCodes();
		if (null != diagnosisCodes && !diagnosisCodes.isEmpty()) {
			List<Diagnosis> list = new ArrayList<>();
			populateDiagnosisEntitiesToDelete(diagnosisCodes, requestId, list);
			populateDiagnosisEntitiesToAddOrUpdate(diagnosisCodes, requestId, list);
			diagnosisRepository.saveAll(list);
		}
	}

	private void populateDiagnosisEntitiesToDelete(List<DiagnosisCodes> diagnosisCodes, String requestId,
			List<Diagnosis> list) {
		List<Diagnosis> diagnosisList = diagnosisRepository.findByRequestIdAndIsDeleted(requestId, false);
		List<String> codes = new ArrayList<>();
		diagnosisCodes.forEach(diagnosis -> codes.add(diagnosis.getDiagnosisCode()));
		diagnosisList.stream().forEach(diagnosis -> {
			if (!codes.contains(diagnosis.getDiagnosisId().getDiagnosisCode())) {
				markDiagnosisDeleted(diagnosis, list);
			}
		});
	}

	private void markDiagnosisDeleted(Diagnosis diagnosis, List<Diagnosis> list) {
		diagnosis.setDeleted(true);
		list.add(diagnosis);
	}

	private void populateDiagnosisEntitiesToAddOrUpdate(List<DiagnosisCodes> diagnosisCodes, String requestId,
			List<Diagnosis> list) {
		diagnosisCodes.stream().forEach(diagnosisCode -> populateDiagnosis(diagnosisCode.getDiagnosisCode(), requestId,
				diagnosisCode.getDiagnosisType(), list));
	}

	private void populateDiagnosis(String diagnosisCode, String requestId, String diagnosisType, List<Diagnosis> list) {
		Optional<Diagnosis> diagnosisOp = diagnosisRepository.findByDiagnosisCodeAndRequestId(diagnosisCode, requestId);
		Diagnosis diagnosis = null;
		DiagnosisId diagnosisId = null;
		if (diagnosisOp.isPresent()) {
			diagnosis = diagnosisOp.get();
			diagnosisId = diagnosis.getDiagnosisId();
		} else {
			diagnosis = new Diagnosis();
			diagnosisId = new DiagnosisId();
		}
		diagnosisId.setDiagnosisCode(diagnosisCode);
		diagnosisId.setRequestId(requestId);
		diagnosis.setDiagnosisId(diagnosisId);
		diagnosis.setDiagnosisType(diagnosisType);
		diagnosis.setDeleted(false);
		list.add(diagnosis);
	}

	private ServiceInfo populateServiceInfo(DrugList drug, String requestId) {
		Long drugListId = Long.valueOf(drug.getDrugListId());
		Optional<ServiceInfo> serviceInfoOp = !StringUtils.isBlank(drug.getDrugCode())
				&& !drug.getDrugCode().equalsIgnoreCase(CommonWords.UNDEFINED.value())
						? serviceInfoRepository.findByRequestIdAndDrugCodeAndDrugListId(requestId, drug.getDrugCode(),
								drugListId)
						: serviceInfoRepository.findByRequestIdAndScientificCodeAndDrugListId(requestId,
								drug.getScientificCode(), drugListId);
		ServiceInfo serviceInfo;
		if (serviceInfoOp.isPresent()) {
			serviceInfo = serviceInfoOp.get();
		} else {
			serviceInfo = new ServiceInfo();
			serviceInfo.setDeleted(false);
			serviceInfo.setDrugListId(drugListId);
		}
		if (!StringUtils.isBlank(drug.getDrugCode())) {
			serviceInfo.setDrugCode(drug.getDrugCode());
		} else {
			serviceInfo.setDrugCode(CommonWords.UNDEFINED.value());
		}
		if (!StringUtils.isBlank(drug.getDuration())) {
			serviceInfo.setDuration(Long.parseLong(drug.getDuration()));
		}
		if (drug.getQuantity() != null) {
			serviceInfo.setQuantity(drug.getQuantity());
		}
		if (!StringUtils.isBlank(drug.getScientificCode())) {
			serviceInfo.setScientificCode(drug.getScientificCode());
		}
		serviceInfo.setUnitPrice(drug.getUnitPrice() == null ? 0 : drug.getUnitPrice());
		serviceInfo.setUnitType(drug.getUnitType());
		serviceInfo.setOrderingClinician(drug.getOrderingClinician());
		serviceInfo.setServiceStartDate(convertStringToDate(drug.getServiceStartDate()));
		if (!StringUtils.isBlank(drug.getServiceEndDate())) {
			serviceInfo.setServiceEndDate(convertStringToDate(drug.getServiceEndDate()));
		}
		serviceInfo.setFrequency(drug.getFrequency());
		serviceInfo.setFrequencyOthersDescription(drug.getFrequencyOthersDescription());
		serviceInfo.setRequestId(requestId);
		if (!StringUtils.isBlank(drug.getUseUnitValue())) {
			serviceInfo.setUseUnitValue(Double.valueOf(drug.getUseUnitValue()));
		}
		serviceInfo.setDeleted(false);
		return serviceInfoRepository.save(serviceInfo);
	}

	private Timestamp convertStringToDate(String dateStr) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = format.parse(dateStr);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			log.error("Exception:-", e);
		}
		return null;
	}

	@Transactional
	private void manageFollowupServiceInfo(String requestId, PrescriptionRequestModel requestModel,
			PrescriptionResponseModel responseModel) {
		List<ServiceInfo> serviceList = serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId);
		List<ServiceInfo> removeServicesList = serviceList.stream()
				.filter(service -> requestModel.getDrugList().stream()
						.noneMatch(drug -> (((StringUtils.isNotBlank(service.getDrugCode())
								&& service.getDrugCode().equals(drug.getDrugCode()))
								|| (StringUtils.isNotBlank(service.getScientificCode())
										&& service.getScientificCode().equalsIgnoreCase(drug.getScientificCode())))
								&& String.valueOf(service.getDrugListId()).equals(drug.getDrugListId()))))
				.collect(Collectors.toList());
		if (null != requestModel.getDrugList()) {
			addOrUpdateServices(requestModel.getDrugList(), requestId, responseModel);
			if (null != removeServicesList && !removeServicesList.isEmpty()) {
				removeServices(removeServicesList, requestId);
			}
		}
	}

	private void populateServiceResponseInfo(ServiceResponse serviceResponse, String requestId,
			ServiceInfo serviceInfo) {
		Optional<ServiceResponseInfo> serviceResponseInfoOp = serviceResponseInfoRepository
				.findByRequestIdAndServiceID(requestId, serviceInfo.getId());
		ServiceResponseInfo serviceResponseInfo = null;
		if (serviceResponseInfoOp.isPresent()) {
			serviceResponseInfo = serviceResponseInfoOp.get();
		} else {
			String currency = Currency.SAR.value();
			serviceResponseInfo = new ServiceResponseInfo();
			serviceResponseInfo.setRequestId(requestId);
			serviceResponseInfo.setServiceID(serviceInfo.getId());
			serviceResponseInfo.setNetCurrency(
					StringUtils.isNotBlank(serviceResponse.getNetCurrency()) ? serviceResponse.getNetCurrency()
							: currency);
			serviceResponseInfo
					.setPatientShareCurrency(StringUtils.isNotBlank(serviceResponse.getPatientShareCurrency())
							? serviceResponse.getPatientShareCurrency()
							: currency);
		}
		serviceResponseInfo.setRequestedAmount(serviceInfo.getRequestedAmount());
		serviceResponseInfo.setApprovedAmount(serviceResponse.getApprovedAmount());
		serviceResponseInfo.setDiscount(serviceResponse.getDiscount());
		serviceResponseInfo.setNet(serviceResponse.getNet() != null ? serviceResponse.getNet() : BigDecimal.ZERO);
		serviceResponseInfo.setPatientShare(
				serviceResponse.getPatientShare() != null ? serviceResponse.getPatientShare() : BigDecimal.ZERO);
		serviceResponseInfo.setStatus(serviceResponse.getStatus());
		serviceResponseInfo.setStatusDescription(serviceResponse.getStatusDescription());
		ServiceResponseInfo newServiceResponseInfo = serviceResponseInfoRepository.save(serviceResponseInfo);
		if (null != serviceResponse.getBusinessRuleError()) {
			populateServiceRejectionForPolicyCheck(newServiceResponseInfo.getId(), requestId,
					serviceResponse.getBusinessRuleError());
		} else {
			populateServiceRejection(newServiceResponseInfo.getId(), requestId, serviceResponse.getErrors());
		}
	}

	private void populateServiceRejectionForPolicyCheck(Long serviceResponseInfoId, String requestId,
			BusinessRuleValidations businessRuleError) {
		List<ServiceRejection> errorList = new ArrayList<>();
		Optional<List<ServiceRejection>> serviceRejectionOp = serviceRejectionRepository
				.findByRequestIdAndServiceResponseId(requestId, serviceResponseInfoId);
		if (serviceRejectionOp.isPresent() && !serviceRejectionOp.get().isEmpty()) {
			List<ServiceRejection> serviceRejectionList = serviceRejectionOp.get();
			serviceRejectionList.stream().forEach(rejection -> {
				ServiceRejection serviceRejection = null;
				if (!rejection.getRejectionReason().equals(businessRuleError.getRejectionReason())) {
					serviceRejection = new ServiceRejection();
					serviceRejection.setRequestId(requestId);
					serviceRejection.setServiceResponseId(serviceResponseInfoId);
				} else {
					serviceRejection = rejection;
				}
				populateRejectionCommonDetails(businessRuleError, serviceRejection);
				errorList.add(rejection);
			});
		} else {
			ServiceRejection serviceRejection = new ServiceRejection();
			serviceRejection.setRequestId(requestId);
			serviceRejection.setServiceResponseId(serviceResponseInfoId);
			populateRejectionCommonDetails(businessRuleError, serviceRejection);
			errorList.add(serviceRejection);
		}
		if (!errorList.isEmpty()) {
			serviceRejectionRepository.saveAll(errorList);
		}
	}

	private void populateRejectionCommonDetails(BusinessRuleValidations error, ServiceRejection serviceRejection) {
		serviceRejection.setDenialCode(error.getDenialCode());
		serviceRejection.setDrugCode(error.getDrugCode());
		serviceRejection.setRejectionReason(error.getRejectionReason());
	}

	private void populateServiceRejection(Long serviceResponseInfoId, String requestId,
			List<MedicalValidations> errors) {
		List<ServiceRejection> errorList = new ArrayList<>();
		if (errors != null && !errors.isEmpty()) {
			errors.forEach(error -> {
				Optional<List<ServiceRejection>> serviceRejectionOp = serviceRejectionRepository
						.findServiceRejectionsByResponseIdAndRequestId(serviceResponseInfoId, requestId);
				if (serviceRejectionOp.isPresent() && !serviceRejectionOp.get().isEmpty()) {
					List<ServiceRejection> serviceRejectionList = serviceRejectionOp.get();
					// DELETE OLD REJECTION AND ADD ALL NEW REJECTIONS
					serviceRejectionList.forEach(rejection -> serviceRejectionRepository.delete(rejection));
//					List<String> reasons = serviceRejectionList.stream().map(ServiceRejection::getRejectionReason)
//							.distinct().collect(Collectors.toList());
//					serviceRejectionList.stream().forEach(rejection -> {
					ServiceRejection serviceRejection = null;
//						if (!rejection.getRejectionReason().equals(error.getRejectionReason())) {
					serviceRejection = new ServiceRejection();
					serviceRejection.setRequestId(requestId);
					serviceRejection.setServiceResponseId(serviceResponseInfoId);
//						} else {
//							serviceRejection = rejection;
//						}
					populateServiceRejectionCommonDetails(error, serviceRejection);
					String rejectionReason = serviceRejection.getRejectionReason();
					if (errorList.stream().noneMatch(
							addedError -> Objects.equals(addedError.getRejectionReason(), rejectionReason))) {
						errorList.add(serviceRejection);
					}
//					});
				} else {
					ServiceRejection serviceRejection = new ServiceRejection();
					serviceRejection.setRequestId(requestId);
					serviceRejection.setServiceResponseId(serviceResponseInfoId);
					populateServiceRejectionCommonDetails(error, serviceRejection);
					errorList.add(serviceRejection);
				}
			});
		} else {
			Optional<List<ServiceRejection>> serviceRejectionOp = serviceRejectionRepository
					.findServiceRejectionsByResponseIdAndRequestId(serviceResponseInfoId, requestId);
			if (serviceRejectionOp.isPresent() && !serviceRejectionOp.get().isEmpty()) {
				serviceRejectionOp.get().stream().forEach(rejection -> serviceRejectionRepository.delete(rejection));
			}
		}
		if (!errorList.isEmpty()) {
			serviceRejectionRepository.saveAll(errorList);
		}
	}

	private void populateServiceRejectionCommonDetails(MedicalValidations error, ServiceRejection serviceRejection) {
		serviceRejection.setDenialCode(error.getDenialCode());
		serviceRejection.setDrugCode(error.getDrugCode());
		serviceRejection.setRejectionReason(error.getRejectionReason());
		serviceRejection.setScientificCode(error.getScientificCode());
	}

	private void addOrUpdateServices(List<DrugList> drugList, String requestId,
			PrescriptionResponseModel responseModel) {
		for (int i = 0; i < drugList.size(); i++) {
			DrugList drug = drugList.get(i);
			ServiceInfo serviceInfo = populateServiceInfo(drug, requestId);
			if ((drug.getDrugCode() != null && drug.getDrugCode().equals(serviceInfo.getDrugCode()))
					|| drug.getScientificCode() != null
							&& drug.getScientificCode().equals(serviceInfo.getScientificCode())) {
				ServiceResponse serviceResponse = responseModel.getResults().get(i);
				serviceInfo.setRequestedAmount(serviceResponse.getRequestedAmount());
				serviceInfoRepository.save(serviceInfo);
				populateServiceResponseInfo(serviceResponse, requestId, serviceInfo);
			}
		}
	}

	private void removeServices(List<ServiceInfo> removeServicesList, String requestId) {
		removeServicesList.stream().forEach(service -> service.setDeleted(true));
		serviceInfoRepository.saveAll(removeServicesList);

		// LOGIC TO REMOVE SERVICE REJECTION FOR REMOVED SERVICES
		removeServicesList.stream().forEach(service -> {
			Optional<List<ServiceRejection>> rejections = serviceRejectionRepository
					.findByRequestIdAndServiceResponseId(requestId, service.getServiceResponseInfo().getId());
			if (rejections.isPresent()) {
				serviceRejectionRepository.deleteAll(rejections.get());
			}
		});
	}

	public PrescriptionResponseModel populateInvalidPrescriptionResponse(String errorMessage, String requestId,
			String ePrescriptionReferenceNumber) {
		PrescriptionResponseModel invalidResponse = new PrescriptionResponseModel();
		List<String> errors = new ArrayList<>();
		errors.add(errorMessage);
		invalidResponse.setRequestId(requestId);
		invalidResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		invalidResponse.setStatus("Invalid");
		String error = errors.toString().replace("[", "").replace("]", "");
		invalidResponse.setStatusDescription(error);
		invalidResponse.setHttpStatusCode(HttpStatus.OK.value());
		invalidResponse.setHttpStatusDescription(error);
		return invalidResponse;
	}

	private PrescriptionResponseModel handleValidInvalidResponse(PrescriptionRequest preRequest,
			String ePrescriptionReferenceNumber, PrescriptionRequestModel requestModel, Timestamp sendingDateTime,
			String providerId, String payerId, TransactionLog transactionLog) {
		String requestId = preRequest.getRequestId();
		if (preRequest.isCancelled()) {
			return populateInvalidPrescriptionResponse(MSG_ISCANCELLED, requestId, ePrescriptionReferenceNumber);
		} else if (!preRequest.getCanFollowUp()) {
			if (preRequest.getStatusCode().equals(RequestStatusType.DISPENSED.value())) {
				return populateInvalidPrescriptionResponse(MSG_DISPENSED, requestId, ePrescriptionReferenceNumber);
			}
			return populateInvalidPrescriptionResponse(MSG_CANNOTFOLLOWUP, requestId, ePrescriptionReferenceNumber);
		} else {
			return manageEligibilityResponse(requestModel, sendingDateTime, providerId, payerId, transactionLog,
					requestId, ePrescriptionReferenceNumber, preRequest);
		}
	}

	private PrescriptionResponseModel manageEligibilityResponse(PrescriptionRequestModel requestModel,
			Timestamp sendingDateTime, String providerId, String payerId, TransactionLog transactionLog,
			String requestId, String ePrescriptionReferenceNumber, PrescriptionRequest preRequest) {
		// EligibilityResponseModel eligibilityResponseModel = businessRuleService
		// .eligibilityCheck(requestModel.getIdNumber(), requestModel.getPayerId(),
		// providerId, requestId);
		EligibilityResponseModel eligibilityResponseModel = new EligibilityResponseModel();
		eligibilityResponseModel.setStatus(EligibilityStatus.ELIGIBLE.getValue());
		PrescriptionResponseModel responseModel;
		if (eligibilityResponseModel.getStatus().equals(EligibilityStatus.ELIGIBLE.getValue())) {
			if (featureToggleEnabled) {
				return manageDssResponse(requestModel, requestId, providerId, sendingDateTime, payerId);
			}
			return managePolicyConsumptionResponse(requestModel, sendingDateTime, providerId, payerId, requestId,
					ePrescriptionReferenceNumber, preRequest);
		} else {
			responseModel = mapperService.createPrescriptionResponseFromEligibilityResponse(eligibilityResponseModel,
					transactionLog.getePrescriptionReferenceNumber(), requestId, requestModel);
			if (eligibilityResponseModel.getStatus().equals(EligibilityStatus.FAILED.getValue())
					|| eligibilityResponseModel.getStatus().equals(EligibilityStatus.INVALID.getValue())) {
				populatePrescriptionRejection(eligibilityResponseModel.getDenialCode(),
						eligibilityResponseModel.getDescription(), eligibilityResponseModel.getReferenceNumber(),
						responseModel.getRequestId(), false);
			} else {
				dmlService.updatePrescriptionRequest(preRequest, requestModel, responseModel,
						eligibilityResponseModel.getReferenceNumber());
			}
			return responseModel;
		}
	}

	private PrescriptionResponseModel manageDssResponse(PrescriptionRequestModel requestModel, String requestId,
			String providerId, Timestamp sendingDateTime, String payerId) {
		PrescriptionResponseModel responseModel = null;
		mappingPayerIdService.mapPayerIdForDss(requestModel, payerId);
		DssResponse dssResponse = null;
		SensitiveDrugResponseModel sensitiveDrugResponseModel = null;
		// confirm location
		// VALIDATE THROUGH SENSITIVE DRUG LIST
		SensitiveDrugRequestModel sensitiveDrugRequestModel = newPrescriptionService
				.populateSensitiveDrugRequestModel(requestModel, requestId, providerId, payerId);
		if (sensitiveDrugRequestModel.getDrugList() != null && !sensitiveDrugRequestModel.getDrugList().isEmpty()) {
			sensitiveDrugResponseModel = businessRuleService
					.validateNewPrescriptionForSensitiveDrugs(sensitiveDrugRequestModel);
		}
		if (sensitiveDrugResponseModel != null && sensitiveDrugResponseModel.getErrorCode() != null) {
			return mapperService.createPrescriptionResponseFromSensitiveDrugResponse(sensitiveDrugResponseModel,
					requestModel.getePrescriptionReferenceNumber());
		}
		if (!skipDssToggleEnabled) {

			dssResponse = restHandler.handleFollowupPrescriptionRequest(
					mapperService.createDssRequest(requestModel, requestId, providerId));
		} else {
			ResponseEntity<MemberDemographicDataResponseModel> demographicData = payerMemberInfoService
					.getMemberDemographicData(Long.valueOf(requestModel.getIdNumber()));
			List<String> policies = new ArrayList<>();
			if (demographicData != null && demographicData.getBody() != null) {
				policies = demographicData.getBody().getPolicyInformation().stream()
						.map(PolicyInformationModel::getPolicyNumber).collect(Collectors.toList());
			}
			Optional<List<EligibleDssPolicy>> eligiblePolicy = eligibleDssPolicyRepository.findByPolicyNumber(policies);
			if (eligiblePolicy.isPresent() && !eligiblePolicy.get().isEmpty()) {
				dssResponse = restHandler.handleFollowupPrescriptionRequest(
						mapperService.createDssRequest(requestModel, requestId, providerId));

			} else {
				dssResponse = mapApprovedDssResponse(requestModel.getDrugList(), requestId);
			}
		}

		if (null != dssResponse) {
			String ePrescriptionReferenceNumber = requestModel.getePrescriptionReferenceNumber();
			combineResponseService.combineResponseWithDssResponse(dssResponse, null, null, sensitiveDrugResponseModel);
			responseModel = mapperService.createPrescriptionResponse(dssResponse, requestModel,
					ePrescriptionReferenceNumber, null);
			if (dssResponse.getResults() != null)
				responseModel.setStatus(combineResponseService.setRequestStatus(dssResponse.getResults()));
			if (responseModel.getHttpStatusCode() == HttpStatus.OK.value()) {
				Optional<PrescriptionRequest> prescriptionReq = prescriptionRequestRepository
						.findByRequestId(responseModel.getRequestId());
				if (prescriptionReq.isPresent()) {
					PrescriptionRequest prescriptionRequest = prescriptionReq.get();
					manageFollowupDiagnosis(requestId, requestModel);
					manageFollowupServiceInfo(requestId, requestModel, responseModel);
					updateDataInPrescriptionRequest(prescriptionRequest, responseModel, sendingDateTime, requestModel);
					responseModel.setCanCancel(prescriptionRequest.getCanCancel());
					responseModel.setCanFollowUp(prescriptionRequest.getCanFollowUp());
					requestModel.setPayerId(payerId);
					if (responseModel != null) {
						emailAndSmsNotificationService.notifyPatientByEmailAndSMS(requestId, requestModel.getIdNumber(),
								ePrescriptionReferenceNumber, PbmRequestType.FOLLOWUP.value());
					}
				}
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

	private boolean checkRequestedDrugHasScientificCode(PrescriptionRequestModel prescriptionRequestModel) {
		if (prescriptionRequestModel.getDrugList() != null) {
			return prescriptionRequestModel.getDrugList().stream()
					.anyMatch(serviceInfo -> !StringUtils.isBlank(serviceInfo.getScientificCode())
							&& (StringUtils.isBlank(serviceInfo.getDrugCode())
									|| serviceInfo.getDrugCode().equalsIgnoreCase(CommonWords.UNDEFINED.value())));
		}
		return false;
	}

	private PrescriptionResponseModel managePolicyConsumptionResponse(PrescriptionRequestModel requestModel,
			Timestamp sendingDateTime, String providerId, String payerId, String requestId,
			String ePrescriptionReferenceNumber, PrescriptionRequest preRequest) {
		PrescriptionResponseModel responseModel;
		String idNumber = requestModel.getIdNumber();
		String benefitCode = fetchBenefitCodeService.fetchBenefitCodeByRequestId(requestId);
		PolicyResponseModel policyResponseModel = businessRuleService.policyConsumptionCheck(idNumber, benefitCode,
				requestModel.getCaseType().toUpperCase(), String.valueOf(requestModel.getTotalPrice()), requestId,
				requestModel.getPayerId(), requestModel.getPolicyConsumptionDrugList(), providerId,
				RequestType.FOLLOWUP.value());
		if (null != policyResponseModel) {
			if (StringUtils.isNotBlank(policyResponseModel.getStatus())
					&& policyResponseModel.getStatus().equals(PolicyConsumptionStatus.APPROVED.getValue())) {
				populateMemberPolicyUsage(requestModel, policyResponseModel, providerId, RequestType.FOLLOWUP.name());
				return manageDssResponse(requestModel, requestId, ePrescriptionReferenceNumber, sendingDateTime,
						providerId, policyResponseModel, payerId);
			}
			responseModel = mapperService.createPrescriptionResponseFromPolicyConsumptionResponse(policyResponseModel,
					ePrescriptionReferenceNumber, requestId, requestModel.getDrugList());
			manageInvalidOrFailedOrRejectedResponse(preRequest, sendingDateTime, requestModel, responseModel,
					policyResponseModel);
			return responseModel;
		}
		return populateInvalidPrescriptionResponse(MSG_NO_POLICY, requestId, ePrescriptionReferenceNumber);
	}

	private void manageInvalidOrFailedOrRejectedResponse(PrescriptionRequest preRequest, Timestamp sendingDateTime,
			PrescriptionRequestModel requestModel, PrescriptionResponseModel responseModel,
			PolicyResponseModel policyResponseModel) {
		String requestId = preRequest.getRequestId();
		modifyPrescriptionRequest(preRequest, responseModel, sendingDateTime, requestModel);
		manageFollowupDiagnosis(requestId, requestModel);
		manageFollowupServiceInfo(requestId, requestModel, responseModel);
		if (policyResponseModel.getStatus().equals(PolicyConsumptionStatus.FAILED.getValue())
				|| policyResponseModel.getStatus().equals(PolicyConsumptionStatus.INVALID.getValue())) {
			populatePrescriptionRejection(policyResponseModel.getDenialCode(),
					policyResponseModel.getDenialDescription(), null, responseModel.getRequestId(), true);
		}
	}

	private void modifyPrescriptionRequest(PrescriptionRequest preRequest, PrescriptionResponseModel responseModel,
			Timestamp sendingDateTime, PrescriptionRequestModel requestModel) {
		String policyStatus = responseModel.getStatus();
		preRequest.setStatusCode(policyStatus);
		preRequest.setStatusDescription(responseModel.getStatusDescription());
		preRequest.setSendDateTime(sendingDateTime);
		preRequest.setReceivedDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		if (policyStatus.equals(RequestStatusType.REJECTED.value())) {
			preRequest.setCanCancel(false);
			preRequest.setCanFollowUp(true);
		} else {
			preRequest.setCanCancel(true);
			preRequest.setCanFollowUp(true);
		}
		preRequest.setCaseType(requestModel.getCaseType().toUpperCase());
		preRequest.setePrescriptionReferenceNumber(responseModel.getePrescriptionReferenceNumber());
		preRequest.setPatientShare(responseModel.getPatientShare());
		preRequest.setPayerShare(responseModel.getPayerShare());
		prescriptionRequestRepository.save(preRequest);
	}

	private void populatePrescriptionRejection(String denialCode, String denialDescription, String referenceNo,
			String requestId, boolean showUnderBusinessValidation) {
		PrescriptionRejection prescriptionRejection = new PrescriptionRejection(denialCode, denialDescription,
				requestId, referenceNo, showUnderBusinessValidation);
		prescriptionRejectionRepository.save(prescriptionRejection);
	}

}