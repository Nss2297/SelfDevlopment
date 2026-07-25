package com.waseel.prescription.service.prescriptions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.common.ScientificCodeModel;
import com.waseel.prescription.model.common.ServiceCodeModel;
import com.waseel.prescription.model.dispense.DispensableDrugs;
import com.waseel.prescription.model.dispense.DispensableDrugsResponseModel;
import com.waseel.prescription.model.dispense.DispensableDrugsSummaryModel;
import com.waseel.prescription.model.dispense.DispenseDrugsRequestModel;
import com.waseel.prescription.model.dispense.DispensedPrescriptionModel;
import com.waseel.prescription.model.dispense.PrescriptionDispenseRequestModel;
import com.waseel.prescription.model.dispense.PrescriptionDispenseResponseModel;
import com.waseel.prescription.model.dispense.PrescriptionDrug;
import com.waseel.prescription.model.dispense.SuggestedDrug;
import com.waseel.prescription.model.dispense.SuggestedDrugsModel;
import com.waseel.prescription.model.eligibility.EligibilityResponseModel;
import com.waseel.prescription.model.enums.CommonDenialsCode;
import com.waseel.prescription.model.enums.CommonWords;
import com.waseel.prescription.model.enums.EligibilityStatus;
import com.waseel.prescription.model.enums.PbmRequestType;
import com.waseel.prescription.model.enums.PolicyConsumptionStatus;
import com.waseel.prescription.model.enums.PrescriptionUrl;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryResponseModel;
import com.waseel.prescription.model.inquiry.detail.ServiceInquiryResponse;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionRequestModel;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionResponseModel;
import com.waseel.prescription.model.policyconsumption.CancellAndDispensePolicyRequestModel;
import com.waseel.prescription.model.policyconsumption.DeactivatePrescriptionRequestModel;
import com.waseel.prescription.model.policyconsumption.DrugListModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.model.prescription.MedicalValidations;
import com.waseel.prescription.persist.mdss.DrugService;
import com.waseel.prescription.persist.prescriptionservice.DispensedPrescription;
import com.waseel.prescription.persist.prescriptionservice.DispensedService;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.MemberPolicyUsage;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionApprovalDrug;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRejection;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.persist.prescriptionservice.ServiceRejection;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.prescription.repository.mdss.DrugServiceRepository;
import com.waseel.prescription.repository.prescriptionservice.DiagnosisRepository;
import com.waseel.prescription.repository.prescriptionservice.DispensedPrescriptionRepository;
import com.waseel.prescription.repository.prescriptionservice.DispensedServiceRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberPolicyUsageRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionApprovalDrugRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;
import com.waseel.prescription.service.clienthandler.RestHandler;
import com.waseel.prescription.service.management.BusinessRuleService;
import com.waseel.prescription.service.management.DMLService;
import com.waseel.prescription.service.management.PrescriptionApprovalDrugService;
import com.waseel.prescription.service.management.SessionService;
import com.waseel.prescription.service.management.TransactionLogService;
import com.waseel.prescription.service.mapper.MapperService;
import com.waseel.prescription.service.validation.DispenseTechnicalValidationService;
import com.waseel.prescription.specification.DispensableDrugsSpecification;
import com.waseel.prescription.specification.DispensedPrescriptionSpecification;
import com.waseel.prescription.util.SourceTypeUtil;
import com.waseel.prescription.util.UserInfoUtil;

@Service
public class DispensePrescriptionService {

	private static final String STR_INVALID = "Invalid";

	@Autowired
	private PrescriptionRequestRepository prescriptionRequestRepository;

	@Autowired
	private ServiceResponseInfoRepository serviceResponseInfoRepository;

	@Autowired
	private DispenseTechnicalValidationService dispenseTechnicalValidationService;

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private DispensedPrescriptionRepository dispensedPrescriptionRepository;

	@Autowired
	private DispensedServiceRepository dispensedServiceRepository;

	@Autowired
	private ServiceInfoRepository serviceInfoRepository;

	@Autowired
	private DispensableDrugsSpecification dispensableDrugsSpecification;

	@Autowired
	private DispensedPrescriptionSpecification dispensedPrescriptionSpecification;

	@Autowired
	private MemberInfoRepository memberInfoRepository;

	@Autowired
	private BusinessRuleService businessRuleService;

	@Autowired
	private ServiceRejectionRepository serviceRejectionRepository;

	@Autowired
	private MemberPolicyUsageRepository memberPolicyUsageRepository;

	@Autowired
	private PrescriptionRejectionRepository prescriptionRejectionRepository;

	@Autowired
	private MapperService mapperService;

	@Autowired
	private DMLService dmlService;

	@Autowired
	private EPrescriptionApprovalService prescriptionApprovalService;

	@Autowired
	private EmailAndSmsNotificationService emailAndSmsNotificationService;

	@Autowired
	private DrugServiceRepository drugServiceRepository;

	@Autowired
	private PrescriptionApprovalDrugService prescriptionApprovalDrugService;

	@Autowired
	private PrescriptionApprovalDrugRepository prescriptionApprovedDrugRepository;

	@Autowired
	private DiagnosisRepository diagnosisRepository;

	@Autowired
	private DrugSuggestionsService drugSuggestionsService;

	@Autowired
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	@Autowired
	RestHandler restHandler;

	@Autowired
	private FetchBenefitCodeService fetchBenefitCodeService;

	private String msgDispensedSuccess = "Dispensed successfully";
	private String msgRejected = "Already rejected can't dispensed";
	private String msgCancelled = "Already cancelled ,can't dispensed";
	private String msgAlreadyDispensed = "Already dispensed.";
	private String msgInvalid = "Invalid";
	private String msgPartiallyDispensedSuccess = "Partially Dispensed successfully";
	private String msgNotFound = "Drugcode(s) not Found with this ePrescriptionReferenceNumber.";

	public PrescriptionDispenseResponseModel managePrescriptionDispensedRequest(
			PrescriptionDispenseRequestModel dispensedRequestModel, ContentCachingRequestWrapper requestWrapper,
			String payerId, String headerOrigin) throws PrescriptionException {
		String sourceType = SourceTypeUtil.getSourceTypeBasedOnHeaderOrigin(headerOrigin);
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		String ePrescriptionReferenceNumber = dispensedRequestModel.getePrescriptionReferenceNumber();
		PrescriptionRequest prescriptionRequest = dispenseTechnicalValidationService
				.validateDispenseRequest(ePrescriptionReferenceNumber, requestWrapper, providerId, payerId, null);
		return preparePrescriptionDispenseResponse(dispensedRequestModel, ePrescriptionReferenceNumber,
				prescriptionRequest.getRequestId(), requestWrapper, providerId, payerId, sourceType, null);
	}

	@Transactional
	public PrescriptionDispenseResponseModel managePrescriptionDispensedRequestWithoutPolicyCheck(
			DispenseDrugsRequestModel dispenseDrugsRequestModel, String ePrescriptionReferenceNumber,
			ContentCachingRequestWrapper requestWrapper, String payerId, String headerOrigin)
			throws PrescriptionException {
		String sourceType = SourceTypeUtil.getSourceTypeBasedOnHeaderOrigin(headerOrigin);
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		PrescriptionRequest prescriptionRequest = dispenseTechnicalValidationService.validateDispenseRequest(
				ePrescriptionReferenceNumber, requestWrapper, providerId, payerId,
				dispenseDrugsRequestModel.getDrugList());
		return preparePrescriptionDispenseResponse(null, ePrescriptionReferenceNumber,
				prescriptionRequest.getRequestId(), requestWrapper, providerId, payerId, sourceType,
				dispenseDrugsRequestModel);
	}

	public PrescriptionDispenseResponseModel preparePrescriptionDispenseResponse(
			PrescriptionDispenseRequestModel dispensedRequest, String ePrescriptionReferenceNumber, String requestId,
			ContentCachingRequestWrapper requestWrapper, String providerId, String payerId, String sourceType,
			DispenseDrugsRequestModel dispenseDrugsRequestModel) {
		addInTransactionLog(payerId, providerId, requestId, ePrescriptionReferenceNumber, requestWrapper, sourceType);
		PrescriptionDispenseResponseModel dispensedResponse = new PrescriptionDispenseResponseModel();
		handlePrescriptionStatus(dispensedResponse, requestId, dispensedRequest, providerId, payerId,
				dispenseDrugsRequestModel);
		dispensedResponse.setPayerId(payerId);
		dispensedResponse.setProviderId(providerId);
		dispensedResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		return dispensedResponse;
	}

	private void managePartiallyOrFullDispense(List<String> drugList, PrescriptionRequest prescriptionRequest,
			PrescriptionDispenseResponseModel dispensedResponse, String payerId, String providerId, String idNumber,
			List<DispensableDrugs> dispensableDrugs) {
		String ePrescriptionReferenceNumber = prescriptionRequest.getePrescriptionReferenceNumber();
		String responseStatusDesc;
		String requestId = prescriptionRequest.getRequestId();
		List<String> notFoundDrugsList = new ArrayList<>();
		List<String> rejectedDrugsList = new ArrayList<>();
		List<String> alreadyDispensedDrugsList = new ArrayList<>();
		List<String> approvedDrugsList = new ArrayList<>();
		List<String> pendingDrugsList = new ArrayList<>();
		List<String> alreadyPendingDrugsList = new ArrayList<>();
		List<ServiceInfo> notDeletedServiceInfosBeforeDispensed = serviceInfoRepository
				.findByIsDeletedAndRequestIdAndStatuses(prescriptionRequest.getRequestId(),
						Arrays.asList(ServiceStatus.APPROVED.name(), ServiceStatus.PENDING.name()));
		boolean isAllNotFound = dispensableDrugs == null
				? handlePartiallyDispenseBusinessLogic(drugList, requestId, approvedDrugsList, rejectedDrugsList,
						notFoundDrugsList, alreadyDispensedDrugsList)
				: handlePartiallyDispenseBusinessLogicWithScientificCode(dispensableDrugs, prescriptionRequest,
						approvedDrugsList, rejectedDrugsList, notFoundDrugsList, alreadyDispensedDrugsList,
						pendingDrugsList, alreadyPendingDrugsList);
		if (isAllNotFound) {
			responseStatusDesc = msgNotFound;
		} else {
			if (!approvedDrugsList.isEmpty() || !pendingDrugsList.isEmpty()) {
				handleDispensedOrPartiallyDispensedStatus(prescriptionRequest, dispensedResponse,
						notDeletedServiceInfosBeforeDispensed, approvedDrugsList, ePrescriptionReferenceNumber, payerId,
						providerId);
			}
			responseStatusDesc = getResponseMsg(approvedDrugsList, rejectedDrugsList, notFoundDrugsList,
					alreadyDispensedDrugsList, notDeletedServiceInfosBeforeDispensed, pendingDrugsList,
					alreadyPendingDrugsList);
		}
		dispensedResponse.setStatusDescription(responseStatusDesc);
		prescriptionRequest.setStatusDescription(getAllRejections(requestId));
		prescriptionRequest.setLastUpdateDate(new Date());
		prescriptionRequest = prescriptionRequestRepository.save(prescriptionRequest);
		if (dispensableDrugs == null
				&& (prescriptionRequest.getStatusCode().equals(RequestStatusType.DISPENSED.value()))) {
			businessRuleService.deactivatePrescriptionAfterCompletingParialDispense(idNumber,
					new DeactivatePrescriptionRequestModel(payerId, requestId, providerId));
		}
	}

	private boolean handlePartiallyDispenseBusinessLogicWithScientificCode(List<DispensableDrugs> drugList,
			PrescriptionRequest prescriptionRequest, List<String> approvedDrugsList, List<String> rejectedDrugsList,
			List<String> notFoundDrugsList, List<String> alreadyDispensedDrugsList, List<String> pendingDrugsList,
			List<String> alreadyPendingDrugsList) {
		var wrapper = new Object() {
			boolean isAllNotFound = true;
		};
		drugList.forEach(dispensableDrugs -> {
			String drugCode = dispensableDrugs.getDrugCode();
			Optional<ServiceInfo> serviceInfoOptional = getServiceInfoData(prescriptionRequest.getRequestId(), drugCode,
					dispensableDrugs.getScientificCode());
			if (serviceInfoOptional.isPresent()) {
				ServiceInfo serviceInfo = serviceInfoOptional.get();
				serviceInfo.setUnitPrice(dispensableDrugs.getUnitPrice() != null ? dispensableDrugs.getUnitPrice()
						: serviceInfo.getUnitPrice());
				serviceInfo.setDrugCode(drugCode);
				serviceInfoRepository.save(serviceInfo);
				wrapper.isAllNotFound = false;
				processServiceResponseInfo(prescriptionRequest, dispensableDrugs, approvedDrugsList, rejectedDrugsList,
						alreadyDispensedDrugsList, pendingDrugsList, alreadyPendingDrugsList, serviceInfo, drugCode);
			} else {
				// Not Found
				notFoundDrugsList.add(drugCode);
			}
		});
		return wrapper.isAllNotFound;
	}

	private void processServiceResponseInfo(PrescriptionRequest prescriptionRequest, DispensableDrugs dispensableDrugs,
			List<String> approvedDrugsList, List<String> rejectedDrugsList, List<String> alreadyDispensedDrugsList,
			List<String> pendingDrugsList, List<String> alreadyPendingDrugsList, ServiceInfo serviceInfo,
			String drugCode) {
		Optional<ServiceResponseInfo> serviceResponseInfoOptional = serviceResponseInfoRepository
				.findByRequestIdAndServiceID(prescriptionRequest.getRequestId(), serviceInfo.getId());
		if (serviceResponseInfoOptional.isPresent()) {
			ServiceResponseInfo serviceResponseInfo = serviceResponseInfoOptional.get();
			String status = serviceResponseInfo.getStatus();
			processSameDrugCodeStatus(status, dispensableDrugs, approvedDrugsList, rejectedDrugsList,
					alreadyDispensedDrugsList, pendingDrugsList, alreadyPendingDrugsList, drugCode, serviceResponseInfo,
					prescriptionRequest);
		}
	}

	private void processSameDrugCodeStatus(String status, DispensableDrugs dispensableDrugs,
			List<String> approvedDrugsList, List<String> rejectedDrugsList, List<String> alreadyDispensedDrugsList,
			List<String> pendingDrugsList, List<String> alreadyPendingDrugsList, String drugCode,
			ServiceResponseInfo serviceResponseInfo, PrescriptionRequest prescriptionRequest) {
		if (status.equals(ServiceStatus.PENDING.name())) {
			alreadyPendingDrugsList.add(drugCode);
		} else if (status.equals(ServiceStatus.REJECTED.name())) {
			rejectedDrugsList.add(drugCode);
		} else if (status.equals(ServiceStatus.DISPENSED.name())) {
			alreadyDispensedDrugsList.add(drugCode);
		} else if (dispensableDrugs.isApprovalRequired()) {
			String strPending = ServiceStatus.PENDING.name();
			pendingDrugsList.add(drugCode);
			updatePayerPatientShareAndStatus(strPending, dispensableDrugs, serviceResponseInfo);
			setErrorInServiceRejection(drugCode, dispensableDrugs.getScientificCode(), serviceResponseInfo.getId(),
					prescriptionRequest.getRequestId());
			prescriptionApprovalDrugService.addDataInPrescriptionApprovalDrug(
					prescriptionRequest.getePrescriptionReferenceNumber(), dispensableDrugs.getScientificCode(),
					strPending, drugCode);
		} else if (status.equals(ServiceStatus.APPROVED.name())) {
			approvedDrugsList.add(drugCode);
			updatePayerPatientShareAndStatus(ServiceStatus.DISPENSED.name(), dispensableDrugs, serviceResponseInfo);
		}
	}

	private boolean handlePartiallyDispenseBusinessLogic(List<String> drugList, String requestId,
			List<String> approvedDrugsList, List<String> rejectedDrugsList, List<String> notFoundDrugsList,
			List<String> alreadyDispensedDrugsList) {
		var wrapper = new Object() {
			boolean isAllNotFound = true;
		};

		drugList.forEach(drugCode -> {
			Optional<ServiceInfo> serviceInfo = serviceInfoRepository.findByRequestIdAndDrugCodeAndIsDeleted(requestId,
					drugCode, false);
			if (serviceInfo.isPresent()) {
				wrapper.isAllNotFound = false;
				Optional<ServiceResponseInfo> serviceResponseInfoOptional = serviceResponseInfoRepository
						.findByRequestIdAndServiceID(requestId, serviceInfo.get().getId());
				if (serviceResponseInfoOptional.isPresent()) {
					ServiceResponseInfo serviceResponseInfo = serviceResponseInfoOptional.get();
					String status = serviceResponseInfo.getStatus();
					if (status.equals(ServiceStatus.APPROVED.name())) {
						// Success cases
						approvedDrugsList.add(drugCode);
						serviceResponseInfo.setStatus(ServiceStatus.DISPENSED.name());
						serviceResponseInfoRepository.save(serviceResponseInfo);
					} else if (status.equals(ServiceStatus.REJECTED.name())) {
						rejectedDrugsList.add(drugCode);
					} else if (status.equals(ServiceStatus.DISPENSED.name())) {
						alreadyDispensedDrugsList.add(drugCode);
					}
				}
			} else {
				// Not Found
				notFoundDrugsList.add(drugCode);
			}
		});
		return wrapper.isAllNotFound;
	}

	private String getResponseMsg(List<String> approvedDrugsList, List<String> rejectedDrugsList,
			List<String> notFoundDrugsList, List<String> alreadyDispensedDrugsList,
			List<ServiceInfo> notDeletedServiceInfosBeforeDispensed, List<String> pendingDrugsList,
			List<String> alreadyPendingDrugsList) {
		String strNotFound = "not Found.";
		String strAnd = " And ";
		String strRejected = "already rejected can't be dispensed";
		String strAlreadyDispensed = "already Dispensed.";
		String responseStatusDesc = "";
		String strApprovalRequired = " required an approval.";
		String strDrugCode = "DrugCode(s) ";
		String strAlreadyPending = " already Pending.";

		if (!approvedDrugsList.isEmpty()) {
			responseStatusDesc = getDispensedOrPartiallyDispensedStatusDesc(notDeletedServiceInfosBeforeDispensed,
					approvedDrugsList);
		}

		if (!pendingDrugsList.isEmpty()) {
			if (responseStatusDesc.isEmpty()) {
				responseStatusDesc = strDrugCode + pendingDrugsList.toString() + strApprovalRequired;
			} else {
				responseStatusDesc += strAnd + strDrugCode + pendingDrugsList.toString() + strApprovalRequired;
			}
		}

		if (!alreadyPendingDrugsList.isEmpty()) {
			if (responseStatusDesc.isEmpty()) {
				responseStatusDesc = strDrugCode + alreadyPendingDrugsList.toString() + strAlreadyPending;
			} else {
				responseStatusDesc += strAnd + strDrugCode + alreadyPendingDrugsList.toString() + strAlreadyPending;
			}
		}

		if (!notFoundDrugsList.isEmpty()) {
			String msg = giveMsgBasedOnListSize(notFoundDrugsList);
			if (responseStatusDesc.isEmpty()) {
				responseStatusDesc = strDrugCode + notFoundDrugsList.toString() + msg + strNotFound;
			} else {
				responseStatusDesc += strAnd + strDrugCode + notFoundDrugsList.toString() + msg + strNotFound;
			}
		}
		if (!rejectedDrugsList.isEmpty()) {
			String msg = giveMsgBasedOnListSize(rejectedDrugsList);
			if (responseStatusDesc.isEmpty()) {
				responseStatusDesc = strDrugCode + rejectedDrugsList.toString() + msg + strRejected;
			} else {
				responseStatusDesc += strAnd + strDrugCode + rejectedDrugsList.toString() + msg + strRejected;
			}
		}
		if (!alreadyDispensedDrugsList.isEmpty()) {
			String msg = giveMsgBasedOnListSize(alreadyDispensedDrugsList);
			if (responseStatusDesc.isEmpty()) {
				responseStatusDesc = strDrugCode + alreadyDispensedDrugsList.toString() + msg + strAlreadyDispensed;
			} else {
				responseStatusDesc += strAnd + strDrugCode + alreadyDispensedDrugsList.toString() + msg
						+ strAlreadyDispensed;
			}
		}
		return responseStatusDesc;
	}

	private String getDispensedOrPartiallyDispensedStatusDesc(List<ServiceInfo> notDeletedServiceInfosBeforeDispensed,
			List<String> approvedDrugsList) {
		return notDeletedServiceInfosBeforeDispensed.size() == approvedDrugsList.size() ? msgDispensedSuccess
				: msgPartiallyDispensedSuccess;
	}

	private String giveMsgBasedOnListSize(List<String> list) {
		return list.size() == 1 ? " is " : " are ";
	}

	private void handleDispensedOrPartiallyDispensedStatus(PrescriptionRequest prescriptionRequest,
			PrescriptionDispenseResponseModel dispensedResponse,
			List<ServiceInfo> notDeletedserviceInfosBeforeDispensed, List<String> approvedDrugsList,
			String ePrescriptionReferenceNumber, String payerId, String providerId) {
		String status = serviceResponseInfoRepository
				.fetchPrescriptionStatusCodeByRequestId(prescriptionRequest.getRequestId());
		Long dispensedDrugSize = (long) approvedDrugsList.size();
		prescriptionRequest.setStatusCode(status);
		prescriptionRequest.setReceivedDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		prescriptionRequest.setCanCancel(false);
		prescriptionRequest.setCanFollowUp(false);
		dispensedResponse.setStatus(status);
		dispensedResponse.setCountOfService(dispensedDrugSize);
		List<ServiceInfo> dispensedServiceInfo = notDeletedserviceInfosBeforeDispensed.stream()
				.filter(serviceInfo -> approvedDrugsList.stream()
						.anyMatch(approvedDrug -> serviceInfo.getDrugCode().equalsIgnoreCase(approvedDrug)))
				.collect(Collectors.toList());
		trackValidDispensedDataInDispensedPrescription(ePrescriptionReferenceNumber, prescriptionRequest,
				dispensedServiceInfo, providerId, payerId);
	}

	private void handlePrescriptionStatus(PrescriptionDispenseResponseModel dispensedResponse, String requestId,
			PrescriptionDispenseRequestModel dispenseRequest, String providerId, String payerId,
			DispenseDrugsRequestModel dispenseDrugsRequestModel) {
		Timestamp sendingDateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
		Optional<PrescriptionRequest> prescriptionRequest = prescriptionRequestRepository.findByRequestId(requestId);
		if (prescriptionRequest.isPresent()) {
			PrescriptionRequest preReq = prescriptionRequest.get();
			preReq.setSendDateTime(sendingDateTime);
			String status = preReq.getStatusCode();
			dispensedResponse.setStatus(msgInvalid);
			if (status.equals(RequestStatusType.DISPENSED.value())) {
				dispensedResponse.setStatusDescription(msgAlreadyDispensed);
			} else if (status.equals(RequestStatusType.CANCELLED.value())) {
				dispensedResponse.setStatusDescription(msgCancelled);
			} else if (status.equals(RequestStatusType.REJECTED.value())) {
				dispensedResponse.setStatusDescription(msgRejected);
			} else {
				Optional<MemberInfo> memberOpt = memberInfoRepository.findByRequestId(requestId);
				if (memberOpt.isPresent()) {
					String idNumber = String.valueOf(memberOpt.get().getIdNumber());
					manageEligibilityResponse(dispensedResponse, requestId, dispenseRequest, providerId, payerId,
							idNumber, preReq, sendingDateTime, dispenseDrugsRequestModel);
				} else {
					dispensedResponse.setStatusDescription("RequestId does not exists.");
				}
			}
		}
	}

	private void manageEligibilityResponse(PrescriptionDispenseResponseModel dispensedResponse, String requestId,
			PrescriptionDispenseRequestModel dispenseRequest, String providerId, String payerId, String idNumber,
			PrescriptionRequest preReq, Timestamp sendingDateTime,
			DispenseDrugsRequestModel dispenseDrugsRequestModel) {
		// EligibilityResponseModel eligibilityResponseModel =
		// businessRuleService.eligibilityCheck(idNumber, payerId,
		// providerId, requestId);
		EligibilityResponseModel eligibilityResponseModel = new EligibilityResponseModel();
		eligibilityResponseModel.setStatus(EligibilityStatus.ELIGIBLE.getValue());
		if (eligibilityResponseModel.getStatus().equals(EligibilityStatus.ELIGIBLE.getValue())) {
			if (dispenseRequest != null) {
				managePolicyConsumptionResponse(dispensedResponse, requestId, dispenseRequest, providerId, payerId,
						idNumber, preReq, sendingDateTime);
			} else if (dispenseDrugsRequestModel != null) {
				manageDrugDispenseWithoutPolicyCheck(dispensedResponse, providerId, payerId, idNumber, preReq,
						dispenseDrugsRequestModel);
			}
		} else {
			if (eligibilityResponseModel.getStatus().equals(EligibilityStatus.INELIGIBLE.getValue())) {
				dispensedResponse.setStatus(RequestStatusType.REJECTED.value());
			}
			dispensedResponse.setStatusDescription(eligibilityResponseModel.getStatusDescription());
			if (eligibilityResponseModel.getStatus().equals(EligibilityStatus.FAILED.getValue())
					|| eligibilityResponseModel.getStatus().equals(EligibilityStatus.INVALID.getValue())) {
				populatePrescriptionRejection(eligibilityResponseModel.getDenialCode(),
						eligibilityResponseModel.getDescription(), eligibilityResponseModel.getReferenceNumber(),
						requestId, false);
			} else {
				dmlService.updatePrescriptionRequestForDispense(preReq, dispenseRequest.getDrugList(),
						eligibilityResponseModel, requestId);
			}
		}
	}

	private void manageDrugDispenseWithoutPolicyCheck(PrescriptionDispenseResponseModel dispensedResponse,
			String providerId, String payerId, String idNumber, PrescriptionRequest preReq,
			DispenseDrugsRequestModel dispenseDrugsRequestModel) {
		String ePrescriptionReferenceNumber = preReq.getePrescriptionReferenceNumber();
		manageEPrescriptionApproval(ePrescriptionReferenceNumber, dispensedResponse,
				mapperService.getEPrescriptionRequestModelFromPrescriptionRequest(preReq,
						getRequestTypeForEPrescriptionApprovalRequest(ePrescriptionReferenceNumber),
						dispenseDrugsRequestModel));
		boolean isPartialDispense = dispenseDrugsRequestModel.getDrugList().size() != serviceInfoRepository
				.findByIsDeletedAndRequestIdAndStatuses(preReq.getRequestId(),
						Arrays.asList(ServiceStatus.APPROVED.name(), ServiceStatus.PENDING.name()))
				.size();
		if (!StringUtils.isBlank(dispensedResponse.getApprovalReferenceNumber())) {
			preReq.setPatientShare(dispenseDrugsRequestModel.getTotalPatientShare());
			preReq.setPayerShare(dispenseDrugsRequestModel.getTotalNet());
			preReq.setPatientShareCurrency(dispenseDrugsRequestModel.getTotalPatientShareCurrency());
			preReq.setPayerShareCurrency(dispenseDrugsRequestModel.getTotalNetCurrency());
			/*
			 * Partial and Full both dispense process covered in partial dispense method if
			 * we have drugList
			 */
			managePartiallyOrFullDispense(null, preReq, dispensedResponse, payerId, providerId, idNumber,
					dispenseDrugsRequestModel.getDrugList());
		} else {
			dispensedResponse.setStatus("INVALID");
			dispensedResponse.setStatusDescription("Something went wrong. Please contact customer care.");
			return;
		}
		String requestType = isPartialDispense ? RequestStatusType.PARTIAL_DISPENSED.value()
				: RequestStatusType.DISPENSED.value();
		emailAndSmsNotificationService.notifyPatientByEmailAndSMS(preReq.getRequestId(), idNumber,
				ePrescriptionReferenceNumber, requestType);
	}

	private void manageEPrescriptionApproval(String ePrescriptionReferenceNumber,
			PrescriptionDispenseResponseModel responseModel, EPrescriptionRequestModel ePrescriptionRequestModel) {
		PbmRequestType requestType = PbmRequestType.DISPENSED;
		EPrescriptionResponseModel ePrescriptionResponseModel = prescriptionApprovalService
				.checkEPrescriptionApproval(ePrescriptionRequestModel, requestType);
		if (ePrescriptionResponseModel != null
				&& !StringUtils.isBlank(ePrescriptionResponseModel.getApprovalReferenceNumber())) {
			responseModel.setApprovalReferenceNumber(ePrescriptionResponseModel.getApprovalReferenceNumber());
			mapperService.createPrescriptionDispenseResponseFromEPrescriptionResponseModel(ePrescriptionResponseModel,
					ePrescriptionReferenceNumber, responseModel);
		} else {
			responseModel.setStatus(ePrescriptionResponseModel != null ? ePrescriptionResponseModel.getStatus() : null);
			responseModel.setStatusDescription(null);
		}
	}

	private void managePolicyConsumptionResponse(PrescriptionDispenseResponseModel dispensedResponse, String requestId,
			PrescriptionDispenseRequestModel dispenseRequest, String providerId, String payerId, String idNumber,
			PrescriptionRequest preReq, Timestamp sendingDateTime) {
		PolicyResponseModel policyResponseModel = businessRuleService.policyConsumptionCheckForDispense(idNumber,
				new CancellAndDispensePolicyRequestModel(fetchBenefitCodeService.fetchBenefitCodeByRequestId(requestId),
						preReq.getCaseType(), payerId, requestId,
						preparePolicyConsumptionDrugList(dispenseRequest.getDrugList(), requestId), providerId,
						RequestType.DISPENSED.value()));
		if (null != policyResponseModel) {
			if (StringUtils.isNotBlank(policyResponseModel.getStatus())
					&& policyResponseModel.getStatus().equals(PolicyConsumptionStatus.APPROVED.getValue())) {
				boolean isPartialDispense = null != dispenseRequest.getDrugList()
						&& !dispenseRequest.getDrugList().isEmpty();
				String requestType = isPartialDispense ? RequestStatusType.PARTIAL_DISPENSED.value()
						: RequestStatusType.DISPENSED.value();
				populateMemberPolicyUsage(payerId, providerId, idNumber, policyResponseModel,
						preReq.getePrescriptionReferenceNumber(), requestType);
				manageFullOrPartialDispense(isPartialDispense, dispenseRequest, preReq, dispensedResponse, payerId,
						providerId, idNumber);
				manageEPrescriptionApproval(preReq.getePrescriptionReferenceNumber(), dispensedResponse,
						mapperService.getEPrescriptionRequestModelFromPrescriptionRequest(preReq,
								PbmRequestType.DISPENSED.value(), null));
				emailAndSmsNotificationService.notifyPatientByEmailAndSMS(preReq.getRequestId(), idNumber,
						preReq.getePrescriptionReferenceNumber(), requestType);
			} else {
				manageInvalidOrRejectedPrescription(sendingDateTime, preReq, policyResponseModel);
				if (policyResponseModel.getStatus().equals(PolicyConsumptionStatus.FAILED.getValue())
						|| policyResponseModel.getStatus().equals(PolicyConsumptionStatus.INVALID.getValue())) {
					populatePrescriptionRejection(policyResponseModel.getDenialCode(),
							policyResponseModel.getDenialDescription(), null, requestId, true);
				}
				dispensedResponse.setStatus(policyResponseModel.getStatus());
				dispensedResponse.setStatusDescription(policyResponseModel.getStatusDescription());
			}
		} else {
			dispensedResponse.setStatusDescription("Policy details not found.");
		}
	}

	private void manageFullOrPartialDispense(boolean isPartialDispense,
			PrescriptionDispenseRequestModel dispenseRequest, PrescriptionRequest preReq,
			PrescriptionDispenseResponseModel dispensedResponse, String payerId, String providerId, String idNumber) {
		if (isPartialDispense) {
			// Partially Dispense
			managePartiallyOrFullDispense(dispenseRequest.getDrugList(), preReq, dispensedResponse, payerId, providerId,
					idNumber, null);
		} else {
			// Full Dispense
			manageFullDispenseSuccessRequest(preReq, dispensedResponse, preReq.getePrescriptionReferenceNumber(),
					providerId, payerId);
		}
	}

	private List<DrugListModel> preparePolicyConsumptionDrugList(List<String> requestedDrugList, String requestId) {
		List<DrugListModel> policyConsumptionDrugList = new ArrayList<>();
		if (null != requestedDrugList && !requestedDrugList.isEmpty()) {
			List<ServiceInfo> prescribedDrugs = serviceInfoRepository.findDrugsByRequestIdAndIsDeleted(requestId,
					false);
			if (null != prescribedDrugs && !prescribedDrugs.isEmpty()) {
				requestedDrugList.stream().forEach(drugCode -> {
					Optional<ServiceInfo> serviceInfoOpt = prescribedDrugs.stream()
							.filter(serviceInfo -> serviceInfo.getDrugCode().equals(drugCode)).findAny();
					if (serviceInfoOpt.isPresent()) {
						ServiceInfo serviceInfo = serviceInfoOpt.get();
						BigDecimal quantity = serviceInfo.getQuantity();
						BigDecimal unitPrice = BigDecimal.valueOf(serviceInfo.getUnitPrice());
						BigDecimal amount = quantity.multiply(unitPrice);
						policyConsumptionDrugList.add(new DrugListModel(drugCode, amount, null, null, null, null));
					}
				});
			}
		}
		return policyConsumptionDrugList;
	}

	private void manageFullDispenseSuccessRequest(PrescriptionRequest prescriptionRequest,
			PrescriptionDispenseResponseModel dispensedResponse, String providerId, String payerId,
			String ePrescriptionReferenceNumber) {
		String statusDispensed = RequestStatusType.DISPENSED.value();
		List<Long> serviceIds = new ArrayList<>();
		List<ServiceInfo> notDeletedServiceInfo = serviceInfoRepository.findByIsNotDeletedAndRequestIdAndStatus(
				prescriptionRequest.getRequestId(), ServiceStatus.APPROVED.name());
		notDeletedServiceInfo.forEach(service -> serviceIds.add(service.getId()));
		List<ServiceResponseInfo> serviceResponseInfoList = serviceResponseInfoRepository.findByServiceIds(serviceIds);
		serviceResponseInfoList.stream().forEach(service -> service.setStatus(ServiceStatus.DISPENSED.name()));
		serviceResponseInfoRepository.saveAll(serviceResponseInfoList);
		prescriptionRequest.setStatusCode(statusDispensed);
		prescriptionRequest.setStatusDescription(msgDispensedSuccess);
		prescriptionRequest.setReceivedDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		prescriptionRequest.setCanCancel(false);
		prescriptionRequest.setCanFollowUp(false);
		prescriptionRequest.setLastUpdateDate(new Date());
		prescriptionRequestRepository.save(prescriptionRequest);
		dispensedResponse.setStatus(statusDispensed);
		dispensedResponse.setStatusDescription(msgDispensedSuccess);
		trackValidDispensedDataInDispensedPrescription(ePrescriptionReferenceNumber, prescriptionRequest,
				notDeletedServiceInfo, providerId, payerId);
	}

	private void addInTransactionLog(String payerId, String providerId, String requestId,
			String ePrescriptionReferenceNumber, ContentCachingRequestWrapper requestWrapper, String sourceType) {
		TransactionLog transactionLog = transactionLogService.addInquiryTransaction(RequestType.DISPENSED, payerId,
				providerId, requestId, ePrescriptionReferenceNumber, sourceType);
		if (transactionLog != null && transactionLog.getTransactionLogId() != null) {
			sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
		}
	}

	private void trackValidDispensedDataInDispensedPrescription(String ePrescriptionReferenceNumber,
			PrescriptionRequest preReq, List<ServiceInfo> notDeletedServiceInfo, String providerId, String payerId) {
		if (notDeletedServiceInfo != null && !notDeletedServiceInfo.isEmpty()) {
			DispensedPrescription dispensedPrescription = new DispensedPrescription(preReq.getRequestId(),
					ePrescriptionReferenceNumber, providerId, payerId, preReq.getStatusCode(),
					Timestamp.from(Instant.now()));
			DispensedPrescription addedDispensedPrescription = dispensedPrescriptionRepository
					.save(dispensedPrescription);
			trackDispensedServices(notDeletedServiceInfo, addedDispensedPrescription.getId());
		}
	}

	private void trackDispensedServices(List<ServiceInfo> serviceInfoList, Long dispensedId) {
		List<DispensedService> list = new ArrayList<>();
		serviceInfoList.forEach(service -> list
				.add(new DispensedService(service.getId(), dispensedId, service.getQuantity(), service.getQuantity())));
		dispensedServiceRepository.saveAll(list);
	}

	public Page<DispensableDrugsResponseModel> getApprovedAndPartiallyDispenseDrugs(String ePrescriptionReferenceNumber,
			String payerId, ContentCachingRequestWrapper requestWrapper, int pageNumber, int recordSize)
			throws PrescriptionException {
		PrescriptionRequest prescriptionRequest = dispenseTechnicalValidationService
				.validateEPrescriptionReferenceNumber(ePrescriptionReferenceNumber, payerId, requestWrapper);
		DispensableDrugsResponseModel drugsResponseModel = new DispensableDrugsResponseModel(
				prescriptionRequest.getRequestId());
		Page<DispensableDrugsResponseModel> result = dispensableDrugsSpecification
				.findDispensableDrugsWithPagination(pageNumber, recordSize, drugsResponseModel);
		result.forEach(model -> model.setDrugDescription(getDrugDescriptionByDrugCode(model.getDrugCode())));
		return result;
	}

	private String getDrugDescriptionByDrugCode(String drugCode) {
		Optional<DrugService> result = drugServiceRepository.findFirstByOtherCodesValue(drugCode);
		if (result.isPresent()) {
			return result.get().getDisplay();
		}
		return null;
	}

	public Page<DispensedPrescriptionModel> getDispenseDetail(String ePrescriptionReferenceNumber, int pageNumber,
			int recordSize) {
		DispensedPrescriptionModel model = new DispensedPrescriptionModel(ePrescriptionReferenceNumber);
		return dispensedPrescriptionSpecification.findDispensedPrescriptionWithPagination(pageNumber, recordSize,
				model);
	}

	public DispensableDrugsSummaryModel getDispensableDrugsSummary(String ePrescriptionReferenceNumber) {
		List<ServiceInfo> serviceInfoList = serviceInfoRepository
				.findByRequestIdAndIsDeleted(ePrescriptionReferenceNumber, false);
		DispensableDrugsSummaryModel drugsSummaryModel = new DispensableDrugsSummaryModel(new BigDecimal(0),
				new BigDecimal(0), serviceInfoList.size());
		serviceInfoList.stream().forEach(serviceInfo -> {
			drugsSummaryModel.setTotalQuantity(serviceInfo.getQuantity().add(drugsSummaryModel.getTotalQuantity())
					.setScale(2, RoundingMode.HALF_UP));
			drugsSummaryModel
					.setGrandTotal(serviceInfo.getQuantity().multiply(BigDecimal.valueOf(serviceInfo.getUnitPrice()))
							.setScale(2, RoundingMode.HALF_UP).add(drugsSummaryModel.getGrandTotal()));
		});
		return drugsSummaryModel;
	}

	private void manageInvalidOrRejectedPrescription(Timestamp sendingDateTime, PrescriptionRequest prescriptionRequest,
			PolicyResponseModel policyResponseModel) {
		modifyPresrciptionRequest(sendingDateTime, prescriptionRequest, policyResponseModel);
		manageServiceInfo(prescriptionRequest.getRequestId(), policyResponseModel);
	}

	private void modifyPresrciptionRequest(Timestamp sendingDateTime, PrescriptionRequest prescriptionRequest,
			PolicyResponseModel policyResponseModel) {
		String status = policyResponseModel.getStatus();
		prescriptionRequest.setSendDateTime(sendingDateTime);
		prescriptionRequest.setReceivedDateTime(sendingDateTime);
		prescriptionRequest.setStatusCode(status);
		prescriptionRequest.setStatusDescription(policyResponseModel.getStatusDescription());
		if (status.equals(PolicyConsumptionStatus.REJECTED.getValue())) {
			prescriptionRequest.setCanCancel(false);
			prescriptionRequest.setCanFollowUp(false);
			prescriptionRequest.setPayerShare(BigDecimal.ZERO);
			prescriptionRequest.setPatientShare(BigDecimal.ZERO);
		} else {
			prescriptionRequest.setCanCancel(true);
			prescriptionRequest.setCanFollowUp(true);
		}
		prescriptionRequestRepository.save(prescriptionRequest);
	}

	private void manageServiceInfo(String requestId, PolicyResponseModel policyResponseModel) {
		List<ServiceInfo> servicelist = serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId);
		servicelist.stream().forEach(service -> modifyServiceResponseInfo(requestId, service.getDrugCode(),
				service.getId(), policyResponseModel, service.getScientificCode()));

	}

	private void modifyServiceResponseInfo(String requestId, String drugCode, long serviceId,
			PolicyResponseModel policyResponseModel, String scientificCode) {
		Optional<ServiceResponseInfo> serviceResponseInfoOp = serviceResponseInfoRepository
				.findByRequestIdAndServiceID(requestId, serviceId);
		if (serviceResponseInfoOp.isPresent()) {
			String status = policyResponseModel.getStatus();
			ServiceResponseInfo serviceResponseInfo = serviceResponseInfoOp.get();
			serviceResponseInfo.setStatus(status);
			serviceResponseInfo.setStatusDescription(policyResponseModel.getStatusDescription());
			if (status.equals(PolicyConsumptionStatus.REJECTED.getValue())) {
				serviceResponseInfo.setNet(BigDecimal.ZERO);
				serviceResponseInfo.setPatientShare(BigDecimal.ZERO);
			}
			serviceResponseInfo = serviceResponseInfoRepository.save(serviceResponseInfo);
			if (status.equals(PolicyConsumptionStatus.REJECTED.getValue())) {
				modifyRejectionReason(drugCode, requestId, serviceResponseInfo.getId(), policyResponseModel,
						scientificCode);
			}
		}
	}

	private void modifyRejectionReason(String drugCode, String requestId, Long serviceResponseInfoId,
			PolicyResponseModel policyResponseModel, String scientificCode) {
		ServiceRejection serviceRejection = new ServiceRejection(drugCode, policyResponseModel.getDenialCode(),
				policyResponseModel.getDenialDescription(), requestId, scientificCode, serviceResponseInfoId);
		serviceRejectionRepository.save(serviceRejection);
	}

	private void populateMemberPolicyUsage(String payerId, String providerId, String idNumber,
			PolicyResponseModel policyResponseModel, String ePrescriptionReferenceNumber, String status) {
		MemberPolicyUsage memberPolicyUsage = new MemberPolicyUsage(payerId, providerId,
				policyResponseModel.getMemberId(), Long.valueOf(idNumber), policyResponseModel.getPolicyNumber(),
				policyResponseModel.getPolicyClass(), policyResponseModel.getPolicyBenefit(),
				null != policyResponseModel.getBenefitLimitValue() ? policyResponseModel.getBenefitLimitValue()
						: new BigDecimal("0"),
				policyResponseModel.getBenefitLimitCurrency(), new BigDecimal(policyResponseModel.getRemainingLimit()),
				policyResponseModel.getBenefitRemainingLimitCurrency(), ePrescriptionReferenceNumber, status);
		memberPolicyUsageRepository.save(memberPolicyUsage);
	}

	private void populatePrescriptionRejection(String denialCode, String denialDescription, String referenceNo,
			String requestId, boolean showUnderBusinessValidation) {
		PrescriptionRejection prescriptionRejection = new PrescriptionRejection(denialCode, denialDescription,
				requestId, referenceNo, showUnderBusinessValidation);
		prescriptionRejectionRepository.save(prescriptionRejection);
	}

	private void updatePayerPatientShareAndStatus(String status, DispensableDrugs dispensableDrugs,
			ServiceResponseInfo serviceResponseInfo) {
		serviceResponseInfo.setStatus(status);
		serviceResponseInfo
				.setPatientShare(dispensableDrugs.getPatientShare() != null ? dispensableDrugs.getPatientShare()
						: serviceResponseInfo.getPatientShare());
		serviceResponseInfo
				.setNet(dispensableDrugs.getNet() != null ? dispensableDrugs.getNet() : serviceResponseInfo.getNet());
		serviceResponseInfo.setPatientShareCurrency(
				dispensableDrugs.getPatientShareCurrency() != null ? dispensableDrugs.getPatientShareCurrency()
						: serviceResponseInfo.getPatientShareCurrency());
		serviceResponseInfo.setNetCurrency(dispensableDrugs.getNetCurrency() != null ? dispensableDrugs.getNetCurrency()
				: serviceResponseInfo.getNetCurrency());
		serviceResponseInfoRepository.save(serviceResponseInfo);
	}

	private Optional<ServiceInfo> getServiceInfoData(String requestId, String drugCode, String scientificCode) {
		Optional<ServiceInfo> serviceInfoOptional = serviceInfoRepository
				.findByRequestIdAndDrugCodeAndIsDeleted(requestId, drugCode, false);
		if (serviceInfoOptional.isEmpty()) {
			return serviceInfoRepository.findByRequestIdAndScientificCodeAndIsDeleted(requestId, scientificCode, false);
		}
		return serviceInfoOptional;
	}

	private String getAllRejections(String requestId) {
		List<String> serviceRejections = serviceRejectionRepository.getAllRejectionsByRequestId(requestId);
		if (serviceRejections != null && !serviceRejections.isEmpty()) {
			return StringUtils.strip(serviceRejections.toString(), "[]");
		}
		return null;
	}

	private void setErrorInServiceRejection(String drugCode, String scientificCode, Long serviceResponseId,
			String requestId) {
		String denialCode = CommonDenialsCode.REQUIRED_PAYER_APPROVAL.value();
		String rejectionReason = mapperService.getRejectionReason(drugCode, denialCode);
		Optional<ServiceRejection> serviceRejectionOpt = serviceRejectionRepository
				.findByRequestIdAndDenialCodeAndScientificCode(requestId, denialCode, scientificCode);
		if (serviceRejectionOpt.isPresent()) {
			ServiceRejection existingServiceRejection = serviceRejectionOpt.get();
			existingServiceRejection.setDrugCode(drugCode);
			existingServiceRejection.setRejectionReason(rejectionReason);
			serviceRejectionRepository.save(existingServiceRejection);
		} else {
			ServiceRejection serviceRejection = new ServiceRejection(drugCode, denialCode, rejectionReason, requestId,
					scientificCode, serviceResponseId);
			serviceRejectionRepository.save(serviceRejection);
		}
	}

	private String getRequestTypeForEPrescriptionApprovalRequest(String ePrescriptionReferenceNumber) {
		Optional<List<PrescriptionApprovalDrug>> prescriptionAppDrugList = prescriptionApprovedDrugRepository
				.findByEprescriptionReferenceNumber(ePrescriptionReferenceNumber);
		if (prescriptionAppDrugList.isPresent() && prescriptionAppDrugList.get().isEmpty()) {
			/*
			 * prescriptionAppDrugList contains only Pending status drugs during dispense
			 * process
			 */
			List<DispensedPrescription> dispensedPrescriptionOpt = dispensedPrescriptionRepository
					.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
			if (dispensedPrescriptionOpt.isEmpty()) {
				/*
				 * dispensedPrescriptionOpt contains only Dispensed status drugs during dispense
				 * process
				 */
				return PbmRequestType.NEW.value();
			}
		}
		return PbmRequestType.FOLLOWUP.value();
	}

	@Transactional
	public PrescriptionDetailInquiryResponseModel manageThirdPartyPrescriptionDispensedRequest(
			DispenseDrugsRequestModel dispenseDrugsRequestModel, String ePrescriptionReferenceNumber,
			ContentCachingRequestWrapper requestWrapper, String payerId, String headerOrigin)
			throws PrescriptionException {
		String sourceType = SourceTypeUtil.getSourceTypeBasedOnHeaderOrigin(headerOrigin);
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		PrescriptionRequest prescriptionRequest = dispenseTechnicalValidationService
				.validateDispenseRequest(ePrescriptionReferenceNumber, requestWrapper, providerId, payerId, null);
		SuggestedDrugsModel suggestedDrugs = drugSuggestionsService.getSuggestedDrugs(ePrescriptionReferenceNumber,
				payerId, true);
		Set<String> errorMessages = new HashSet<>();

		// DRUG CODE VALIDATION
		Long drugListId = getDrugListId();
		List<DispensableDrugs> drugList = dispenseDrugsRequestModel.getDrugList();
		validateDrugCodeAgainstDrugService(drugList, errorMessages, drugListId);
		validateScientificCodeAgainstDrugService(drugList, errorMessages, drugListId);

		if (!errorMessages.isEmpty()) {
			throw new PrescriptionException(
					populateInvalidDispenseResponse(requestWrapper, errorMessages.stream().collect(Collectors.toList()),
							null, ePrescriptionReferenceNumber, providerId, payerId));
		}

		verifyDrugCodeWithTheirScientificCode(drugList, drugListId, errorMessages, requestWrapper,
				ePrescriptionReferenceNumber, providerId, payerId);

		List<ServiceInfo> serviceInfoList = validatePrescriptionDrugs(ePrescriptionReferenceNumber, drugList,
				prescriptionRequest.getRequestId(), requestWrapper, providerId, payerId, errorMessages,
				suggestedDrugs.getPrescriptionDrugs());
		validateDrugCode(drugList, prescriptionRequest.getRequestId(), providerId, payerId,
				ePrescriptionReferenceNumber, requestWrapper, suggestedDrugs, errorMessages, serviceInfoList);

		// PATIENT SHARE VALIDATION
		validatePatientShareDetails(dispenseDrugsRequestModel, requestWrapper, prescriptionRequest.getRequestId(),
				prescriptionRequest.getRequestId(), prescriptionRequest.getRequestId());

		// manipulate request for isApprovalRequired
		manipulateDispensableDrugForIsApprovalFlag(suggestedDrugs, dispenseDrugsRequestModel);

		String alreadyDispensedDrugs = fetchAlreadyDispenseDrugs(dispenseDrugsRequestModel, serviceInfoList);

		// EXISITING API CALL FOR DRUG DISPENSE
		PrescriptionDispenseResponseModel dispenseResponseModel = null;
		if (!dispenseDrugsRequestModel.getDrugList().isEmpty()) {
			dispenseResponseModel = preparePrescriptionDispenseResponse(null, ePrescriptionReferenceNumber,
					prescriptionRequest.getRequestId(), requestWrapper, providerId, payerId, sourceType,
					dispenseDrugsRequestModel);
		}

		PrescriptionDetailInquiryResponseModel responseModel = new PrescriptionDetailInquiryResponseModel();
		responseModel.setRequestId(prescriptionRequest.getRequestId());
		responseModel.setCanCancel(prescriptionRequest.getCanCancel());
		responseModel.setCanFollowUp(prescriptionRequest.getCanFollowUp());
		responseModel.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		responseModel.setDiagnosisCodes(getDiagnosisCodes(prescriptionRequest.getRequestId()));
		responseModel.setResults(getServiceDetails(prescriptionRequest.getRequestId(), suggestedDrugs));
		if (null != dispenseResponseModel
				&& org.apache.commons.lang3.StringUtils.isNotBlank(dispenseResponseModel.getStatus())
				&& org.apache.commons.lang3.StringUtils.isNotBlank(dispenseResponseModel.getStatusDescription())) {
			responseModel.setStatus(dispenseResponseModel.getStatus());
			responseModel.setStatusDescription(org.apache.commons.lang3.StringUtils.isNotBlank(alreadyDispensedDrugs)
					? (alreadyDispensedDrugs + ", " + dispenseResponseModel.getStatusDescription()).strip()
					: dispenseResponseModel.getStatusDescription().strip());
		} else {
			responseModel.setStatus(STR_INVALID);
			responseModel.setStatusDescription(org.apache.commons.lang3.StringUtils.isNotBlank(alreadyDispensedDrugs)
					? alreadyDispensedDrugs.strip()
					: org.apache.commons.lang3.StringUtils.join(errorMessages, ","));
		}
		return responseModel;
	}

	private List<ServiceInfo> validatePrescriptionDrugs(String ePrescriptionReferenceNumber,
			List<DispensableDrugs> dispensableDrugs, String requestId, ContentCachingRequestWrapper requestWrapper,
			String providerId, String payerId, Set<String> errorMessages, List<PrescriptionDrug> suggestedDrugs)
			throws PrescriptionException {
		List<ServiceInfo> prescribedDrugs = serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId);
		if (null != prescribedDrugs && !prescribedDrugs.isEmpty()) {
			Set<String> dispensableDrugCodes = new HashSet<>();
			Set<String> dispensableScientificCodes = new HashSet<>();
			populateDispensableDrugCodesAndScientificCodes(dispensableDrugCodes, dispensableScientificCodes,
					dispensableDrugs);
			verifyWithPrescribedDrugs(prescribedDrugs, dispensableDrugCodes, dispensableScientificCodes,
					suggestedDrugs);
			if (!dispensableDrugCodes.isEmpty() || !dispensableScientificCodes.isEmpty()) {
				populateResponseForDrugsNotFound(dispensableDrugCodes, dispensableScientificCodes, requestWrapper,
						ePrescriptionReferenceNumber, providerId, payerId, errorMessages);
			}
		}
		return prescribedDrugs;
	}

	private void manipulateDispensableDrugForIsApprovalFlag(SuggestedDrugsModel suggestedDrugs,
			DispenseDrugsRequestModel dispenseDrugsRequestModel) {
		dispenseDrugsRequestModel.getDrugList().stream()
				.filter(dispenseDrug -> dispenseDrug.getScientificCode() != null)
				.forEach(dispenseDrug -> suggestedDrugs.getPrescriptionDrugs().stream()
						.filter(suggestDrug -> suggestDrug.getIsBrand() == null
								|| suggestDrug.getIsBrand().equals(Boolean.FALSE))
						.filter(suggestDrug -> suggestDrug.getScientificCode().equals(dispenseDrug.getScientificCode()))
						.forEach(suggestDrug -> {
							SuggestedDrug suggestedDrug = suggestDrug.getSuggestedDrugs().stream()
									.filter(suggestedListDrug -> suggestedListDrug.getSfdaCode()
											.equals(dispenseDrug.getDrugCode()))
									.findFirst().orElse(null);
							if (suggestedDrug != null) {
								dispenseDrug.setApprovalRequired(suggestedDrug.getIsApprovalRequired());
							}
						}));
	}

	private void validatePatientShareDetails(DispenseDrugsRequestModel dispenseDrugsRequestModel,
			ContentCachingRequestWrapper requestWrapper, String ePrescriptionReferenceNumber, String providerId,
			String payerId) throws PrescriptionException {
		BigDecimal totalPatientShare = dispenseDrugsRequestModel.getTotalPatientShare();
		BigDecimal totalNet = dispenseDrugsRequestModel.getTotalNet();

		var amountValues = new Object() {
			BigDecimal servicePatientShare = BigDecimal.ZERO;
			BigDecimal serviceTotalNet = BigDecimal.ZERO;
		};
		dispenseDrugsRequestModel.getDrugList().stream().forEach(dispenseDrug -> {
			if (dispenseDrug.getPatientShare() != null) {
				amountValues.servicePatientShare = amountValues.servicePatientShare.add(dispenseDrug.getPatientShare());
			}
			if (dispenseDrug.getNet() != null) {
				amountValues.serviceTotalNet = amountValues.serviceTotalNet.add(dispenseDrug.getNet());
			}
		});

		List<String> errorMessages = new ArrayList<>();
		if (totalPatientShare.compareTo(amountValues.servicePatientShare) < 0) {
			errorMessages.add("Service(s) Patient Share exceeded limit of Total Patient Share.");
		}
		if (totalNet.compareTo(amountValues.serviceTotalNet) < 0) {
			errorMessages.add("Service(s) Total Net exceeded limit of Total Net.");
		}
		if (!errorMessages.isEmpty()) {
			throw new PrescriptionException(populateInvalidDispenseResponse(requestWrapper, errorMessages, null,
					ePrescriptionReferenceNumber, providerId, payerId));
		}
	}

	private List<DiagnosisCodes> getDiagnosisCodes(String requestId) {
		return diagnosisRepository.findByRequestIdAndIsNotDeleted(requestId);
	}

	private List<ServiceInquiryResponse> getServiceDetails(String requestId, SuggestedDrugsModel suggestedDrugsModel) {
		List<ServiceInquiryResponse> serviceInfo = serviceInfoRepository.getServiceDetailsOfInquiry(requestId);
		serviceInfo.forEach(service -> {
			processTosetServiceRejections(service, requestId);
		});
		return serviceInfo;
	}

	private void processTosetServiceRejections(ServiceInquiryResponse service, String requestId) {
		String drugCode = service.getDrugCode();
		List<MedicalValidations> serviceRejections = getMedicalValidations(drugCode, requestId,
				service.getScientificCode());
		if (serviceRejections != null && !serviceRejections.isEmpty()) {
			service.setErrors(serviceRejections);
		}
	}

	private List<MedicalValidations> getMedicalValidations(String drugCode, String requestId, String scientificCode) {
		if (StringUtils.isBlank(drugCode) || drugCode.equalsIgnoreCase(CommonWords.UNDEFINED.value())) {
			return serviceRejectionRepository.findByRequestIdAndScientificCode(requestId, scientificCode);
		} else {
			return serviceRejectionRepository.findByRequestIdAndDrugCode(requestId, drugCode);
		}
	}

	private void validateDrugCode(List<DispensableDrugs> drugList, String requestId, String providerId, String payerId,
			String ePrescriptionReferenceNumber, ContentCachingRequestWrapper requestWrapper,
			SuggestedDrugsModel suggestedDrugs, Set<String> errorMessages, List<ServiceInfo> serviceInfoList)
			throws PrescriptionException {

		if (serviceInfoList != null && !serviceInfoList.isEmpty() && drugList != null
				&& suggestedDrugs.getPrescriptionDrugs() != null && !suggestedDrugs.getPrescriptionDrugs().isEmpty()) {
			// INVALID DRUG IN SCIENTIFIC CODE VALIDATION
			serviceInfoList.stream().filter(serviceInfo -> !StringUtils.isBlank(serviceInfo.getScientificCode()))
					.forEach(service -> drugList.stream()
							.filter(dispensableDrugs -> dispensableDrugs.getScientificCode()
									.equals(service.getScientificCode()))
							.forEach(dispensableDrugs -> validateDrugAndScientificCode(service, dispensableDrugs,
									suggestedDrugs, errorMessages)));
			if (!errorMessages.isEmpty()) {
				throw new PrescriptionException(populateInvalidDispenseResponse(requestWrapper,
						errorMessages.stream().collect(Collectors.toList()), null, ePrescriptionReferenceNumber,
						providerId, payerId));
			}
		}
	}

	private void validateDrugAndScientificCode(ServiceInfo service, DispensableDrugs dispensableDrugs,
			SuggestedDrugsModel suggestedDrugs, Set<String> errorMessages) {
		PrescriptionDrug suggestedDrg = getSuggestedDrug(suggestedDrugs, dispensableDrugs);

		if (isPendingService(service) && isMismatchedDrugCode(service, dispensableDrugs)) {
			if (isPartOfSuggestedDrugList(suggestedDrg, dispensableDrugs)) {
				errorMessages.add("For scientific code:" + dispensableDrugs.getScientificCode() + ", drug code ["
						+ service.getDrugCode() + "] already sent for an approval.");
			} else if (isNotPartOfSuggestedDrugList(suggestedDrg, dispensableDrugs)) {
				errorMessages.add("Scientific Code " + dispensableDrugs.getScientificCode() + " with this Drug Code: "
						+ dispensableDrugs.getDrugCode() + " not found.");
			}
		} else if (isMissingOrUndefinedDrugCode(service)
				&& (isNotPartOfSuggestedDrugList(suggestedDrg, dispensableDrugs))) {
			errorMessages.add("Scientific Code " + dispensableDrugs.getScientificCode() + " with this Drug Code: "
					+ dispensableDrugs.getDrugCode() + " not found.");
		}
	}

	private boolean isPendingService(ServiceInfo service) {
		return service.getServiceResponseInfo().getStatus().equalsIgnoreCase(ServiceStatus.PENDING.name());
	}

	private boolean isMismatchedDrugCode(ServiceInfo service, DispensableDrugs dispensableDrugs) {
		return StringUtils.isNotBlank(service.getDrugCode())
				&& !service.getDrugCode().equalsIgnoreCase(CommonWords.UNDEFINED.value())
				&& !service.getDrugCode().equalsIgnoreCase(dispensableDrugs.getDrugCode());
	}

	private boolean isPartOfSuggestedDrugList(PrescriptionDrug suggestedDrg, DispensableDrugs dispensableDrugs) {
		return suggestedDrg != null && suggestedDrg.getSuggestedDrugs().stream()
				.anyMatch(suggestDrug -> suggestDrug.getSfdaCode().equals(dispensableDrugs.getDrugCode()));
	}

	private boolean isNotPartOfSuggestedDrugList(PrescriptionDrug suggestedDrg, DispensableDrugs dispensableDrugs) {
		return suggestedDrg != null && suggestedDrg.getSuggestedDrugs().stream()
				.noneMatch(suggestDrug -> suggestDrug.getSfdaCode().equals(dispensableDrugs.getDrugCode()));
	}

	private boolean isMissingOrUndefinedDrugCode(ServiceInfo service) {
		return StringUtils.isBlank(service.getDrugCode())
				|| service.getDrugCode().equalsIgnoreCase(CommonWords.UNDEFINED.value());
	}

	private PrescriptionDrug getSuggestedDrug(SuggestedDrugsModel suggestedDrugs, DispensableDrugs dispensableDrugs) {
		return suggestedDrugs.getPrescriptionDrugs().stream()
				.filter(suggestDrug -> suggestDrug.getIsBrand() == null
						|| suggestDrug.getIsBrand().equals(Boolean.FALSE))
				.filter(suggestDrug -> suggestDrug.getScientificCode().equals(dispensableDrugs.getScientificCode()))
				.findFirst().orElse(null);
	}

	private void validateScientificCodeAgainstDrugService(List<DispensableDrugs> drugList, Set<String> errorMessages,
			Long drugListId) {
		List<String> scientificCodes = drugList.stream().map(DispensableDrugs::getScientificCode)
				.collect(Collectors.toList());
		List<ScientificCodeModel> drugServiceList = drugServiceRepository.findByScientificCodes(scientificCodes,
				drugListId);
		drugServiceList.stream().filter(drug -> drug.getIsValid() == 0)
				.forEach(drug -> errorMessages.add("ScientificCode " + drug.getScientificCode() + " not found."));
	}

	private void validateDrugCodeAgainstDrugService(List<DispensableDrugs> drugList, Set<String> errorMessages,
			Long drugListId) {
		List<String> serviceCodes = drugList.stream().map(DispensableDrugs::getDrugCode).collect(Collectors.toList());
		List<ServiceCodeModel> drugServiceList = drugServiceRepository.findByServiceCodes(serviceCodes, drugListId);
		drugServiceList.stream().filter(drug -> drug.getIsValid() == 0)
				.forEach(drug -> errorMessages.add("Drugcode " + drug.getserviceCode() + " not found."));
	}

	private Long getDrugListId() {
		return drugServiceMetaDataRepository.getActiveDrugServiceList(new Date()).orElse(0L);
	}

	private PrescriptionDispenseResponseModel populateInvalidDispenseResponse(
			ContentCachingRequestWrapper requestWrapper, List<String> errorMessages, String requestId,
			String ePrescriptionReferenceNumber, String providerId, String payerId) {
		PrescriptionDispenseResponseModel invalidResponse = new PrescriptionDispenseResponseModel();
		invalidResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		invalidResponse.setStatus(STR_INVALID);
		invalidResponse.setErrors(errorMessages);
		invalidResponse.setPayerId(payerId);
		invalidResponse.setProviderId(providerId);
		if (requestWrapper.getRequestURI().endsWith(PrescriptionUrl.DISPENSE.getValue())) {
			populateTransactionLogForDispense(requestWrapper, payerId, providerId, requestId,
					ePrescriptionReferenceNumber);
		}
		return invalidResponse;
	}

	private void populateTransactionLogForDispense(ContentCachingRequestWrapper requestWrapper, String payerId,
			String providerId, String requestId, String ePrescriptionReferenceNumber) {
		String transactionLogId = sessionService.getTransactionLogIdFromSession(requestWrapper);
		if (StringUtils.isBlank(transactionLogId)) {
			String sourceType = SourceTypeUtil
					.getSourceTypeBasedOnHeaderOrigin(requestWrapper.getHeader(HttpHeaders.ORIGIN));
			TransactionLog transactionLog = transactionLogService.addTransaction(RequestType.DISPENSED, payerId,
					providerId, requestId, ePrescriptionReferenceNumber, sourceType);
			if (transactionLog != null) {
				sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
			}
		}
	}

	private void populateDispensableDrugCodesAndScientificCodes(Set<String> dispensableDrugCodes,
			Set<String> dispensableScientificCodes, List<DispensableDrugs> dispensableDrugs) {
		dispensableDrugs.stream().forEach(dispensableDrug -> {
			dispensableDrugCodes.add(dispensableDrug.getDrugCode());
			dispensableScientificCodes.add(dispensableDrug.getScientificCode());
		});
	}

	private void verifyWithPrescribedDrugs(List<ServiceInfo> prescribedDrugs, Set<String> dispensableDrugCodes,
			Set<String> dispensableScientificCodes, List<PrescriptionDrug> suggestedDrugs) {
		prescribedDrugs.stream().forEach(prescribedDrug -> {
			String drugWithTradeName = prescribedDrug.getDrugCode();
			String drugWithOutTradeName = prescribedDrug.getScientificCode();
			if (StringUtils.isNotBlank(drugWithTradeName) && !drugWithTradeName.equals(CommonWords.UNDEFINED.value())
					&& StringUtils.isBlank(drugWithOutTradeName)) {
				removeDrugDetailsFromDispensableDrugs(drugWithTradeName, "", dispensableDrugCodes, null);
				drugWithOutTradeName = findScientificCode(drugWithTradeName, prescribedDrug.getDrugListId());
				if (StringUtils.isNotBlank(drugWithOutTradeName)) {
					removeDrugDetailsFromDispensableDrugs("", drugWithOutTradeName, null, dispensableScientificCodes);
				}
			} else if (StringUtils.isNotBlank(drugWithOutTradeName)
					&& (StringUtils.isBlank(drugWithTradeName) || StringUtils.isNotBlank(drugWithTradeName)
							|| drugWithTradeName.equals(CommonWords.UNDEFINED.value()))) {
				removeDrugDetailsFromDispensableDrugs("", drugWithOutTradeName, null, dispensableScientificCodes);
				if (null != suggestedDrugs && !suggestedDrugs.isEmpty()) {
					removeAllSuggestedDrugsForTheScientificCode(suggestedDrugs, dispensableDrugCodes,
							drugWithOutTradeName);
				}
			}
		});
	}

	private void removeAllSuggestedDrugsForTheScientificCode(List<PrescriptionDrug> suggestedDrugs,
			Set<String> dispensableDrugCodes, String scientificCode) {
		Optional<PrescriptionDrug> prescribedScientificCode = suggestedDrugs.stream()
				.filter(suggestedDrug -> suggestedDrug.getScientificCode().equals(scientificCode)).findFirst();
		prescribedScientificCode.ifPresent(prescribedScientificCodeDrug -> {
			List<SuggestedDrug> suggestedDrugsForScientificCode = prescribedScientificCodeDrug.getSuggestedDrugs();
			if (null != suggestedDrugsForScientificCode && !suggestedDrugsForScientificCode.isEmpty()) {
				suggestedDrugsForScientificCode.stream()
						.forEach(suggestedDrugForScientificCode -> removeDrugDetailsFromDispensableDrugs(
								suggestedDrugForScientificCode.getSfdaCode(), "", dispensableDrugCodes, null));
			}
		});
	}

	private void removeDrugDetailsFromDispensableDrugs(String drugWithTradeName, String drugWithOutTradeName,
			Set<String> dispensableDrugCodes, Set<String> dispensableScientificCodes) {
		if (StringUtils.isNotBlank(drugWithTradeName) && null == dispensableScientificCodes) {
			dispensableDrugCodes.remove(drugWithTradeName);
		} else {
			dispensableScientificCodes.remove(drugWithOutTradeName);
		}
	}

	private String findScientificCode(String drugCode, Long drugListId) {
		Optional<DrugService> drugServiceOpt = drugServiceRepository.findByOtherCodesValueAndDrugListId(drugCode,
				drugListId);
		if (drugServiceOpt.isPresent()) {
			return drugServiceOpt.get().getScientificCode();
		}
		return null;
	}

	private void populateResponseForDrugsNotFound(Set<String> dispensableDrugCodes,
			Set<String> dispensableScientificCodes, ContentCachingRequestWrapper requestWrapper,
			String ePrescriptionReferenceNumber, String providerId, String payerId, Set<String> errorMessages)
			throws PrescriptionException {
		List<String> drugsNotFound = fetchDrugsNotInPrescription(dispensableDrugCodes, dispensableScientificCodes);
		if (!drugsNotFound.isEmpty()) {
			errorMessages.addAll(drugsNotFound.stream().collect(Collectors.toSet()));
		}
	}

	private List<String> fetchDrugsNotInPrescription(Set<String> dispensableDrugCodes,
			Set<String> dispensableScientificCodes) {
		List<String> drugs = new ArrayList<>();
		if (!dispensableDrugCodes.isEmpty()) {
			drugs.addAll(dispensableDrugCodes.stream()
					.map(drug -> "Drug Code " + drug + " not found with this ePrescriptionReferenceNumber.")
					.collect(Collectors.toList()));
		}

		if (!dispensableScientificCodes.isEmpty()) {
			drugs.addAll(dispensableScientificCodes.stream()
					.map(drug -> "Scientific Code " + drug + " not found with this ePrescriptionReferenceNumber.")
					.collect(Collectors.toList()));
		}
		return drugs;
	}

	private void verifyDrugCodeWithTheirScientificCode(List<DispensableDrugs> dispensableDrugs, Long activeDrugListId,
			Set<String> errorMessages, ContentCachingRequestWrapper requestWrapper, String ePrescriptionReferenceNumber,
			String providerId, String payerId) throws PrescriptionException {
		var dispensableDrugCodes = List
				.copyOf(dispensableDrugs.stream().map(DispensableDrugs::getDrugCode).collect(Collectors.toSet()));
		if (null != dispensableDrugCodes && !dispensableDrugCodes.isEmpty()) {
			Optional<List<DrugService>> drugServiceListOpt = drugServiceRepository
					.findByDrugListIdAndOtherCodesValueIn(activeDrugListId, dispensableDrugCodes);
			if (drugServiceListOpt.isPresent()) {
				List<DrugService> drugServiceList = drugServiceListOpt.get();
				if (null != drugServiceList && !drugServiceList.isEmpty()) {
					validationForInvalidDrugs(drugServiceList, dispensableDrugs, errorMessages, requestWrapper,
							ePrescriptionReferenceNumber, providerId, payerId);
				}
			}
		}
	}

	private void validationForInvalidDrugs(List<DrugService> drugServiceList, List<DispensableDrugs> dispensableDrugs,
			Set<String> errorMessages, ContentCachingRequestWrapper requestWrapper, String ePrescriptionReferenceNumber,
			String providerId, String payerId) throws PrescriptionException {
		List<DispensableDrugs> invalidDrugs = dispensableDrugs.stream()
				.filter(dispensableDrug -> drugServiceList.stream()
						.anyMatch(drugService -> drugService.getOtherCodesValue().equals(dispensableDrug.getDrugCode())
								&& !drugService.getScientificCode().equals(dispensableDrug.getScientificCode())))
				.collect(Collectors.toList());
		if (null != invalidDrugs && !invalidDrugs.isEmpty()) {
			errorMessages.addAll(invalidDrugs.stream()
					.map(invalidDrug -> "Drug Code: " + invalidDrug.getDrugCode()
							+ " not found for this Scientific Code: " + invalidDrug.getScientificCode() + ".")
					.collect(Collectors.toSet()));
			if (!errorMessages.isEmpty()) {
				var errors = List.copyOf(errorMessages);
				throw new PrescriptionException(populateInvalidDispenseResponse(requestWrapper, errors, null,
						ePrescriptionReferenceNumber, providerId, payerId));
			}
		}
	}

	private String fetchAlreadyDispenseDrugs(DispenseDrugsRequestModel requestedDrugsToDispense,
			List<ServiceInfo> prescribedDrugList) {
		if (null != prescribedDrugList && !prescribedDrugList.isEmpty()) {
			Set<String> dispensedDrugs = new HashSet<>();
			List<DispensableDrugs> requestedDrugs = requestedDrugsToDispense.getDrugList();
			prescribedDrugList.stream().forEach(prescribedDrug -> {
				String drugWithTradeName = prescribedDrug.getDrugCode();
				String drugWithOutTradeName = prescribedDrug.getScientificCode();
				if (verificationForDrugWithTradeName(drugWithTradeName, drugWithOutTradeName,
						prescribedDrug.getServiceResponseInfo().getStatus())) {
					drugWithOutTradeName = drugServiceRepository
							.findByOtherCodesValueAndDrugListId(drugWithTradeName, prescribedDrug.getDrugListId())
							.map(DrugService::getScientificCode).orElse(drugWithOutTradeName);
					if (verifyWithRequestForTradeName(requestedDrugs, drugWithTradeName, drugWithOutTradeName)) {
						manageAlreadyDispensedDrugByTradeName(dispensedDrugs, requestedDrugsToDispense,
								drugWithTradeName, drugWithOutTradeName);
					}
				} else if (verificationForDrugWithScientificCode(drugWithTradeName, drugWithOutTradeName,
						requestedDrugs, prescribedDrug.getServiceResponseInfo().getStatus())) {
					manageAlreadyDispensedDrugByScientificCode(dispensedDrugs, requestedDrugsToDispense,
							drugWithTradeName, drugWithOutTradeName);
				}
			});
			return !dispensedDrugs.isEmpty() ? org.apache.commons.lang3.StringUtils.join(dispensedDrugs, ",") : "";
		}
		return "";
	}

	private boolean verificationForDrugWithTradeName(String drugCode, String scientificCode, String status) {
		return org.apache.commons.lang3.StringUtils.isNotBlank(drugCode)
				&& !drugCode.equals(CommonWords.UNDEFINED.value())
				&& org.apache.commons.lang3.StringUtils.isBlank(scientificCode) && checkIfDrugIsDispensed(status);
	}

	private boolean verificationForDrugWithScientificCode(String drugCode, String scientificCode,
			List<DispensableDrugs> requestedDrugs, String status) {
		return org.apache.commons.lang3.StringUtils.isNotBlank(scientificCode)
				&& org.apache.commons.lang3.StringUtils.isNotBlank(drugCode)
				&& !drugCode.equals(CommonWords.UNDEFINED.value()) && checkIfDrugIsDispensed(status)
				&& verifyRequestWithScientificCode(requestedDrugs, scientificCode);
	}

	private boolean checkIfDrugIsDispensed(String status) {
		return org.apache.commons.lang3.StringUtils.isNotBlank(status) && status.equals(ServiceStatus.DISPENSED.name());
	}

	private boolean verifyWithRequestForTradeName(List<DispensableDrugs> requestedDrugs, String drugCode,
			String scientificCode) {
		return requestedDrugs.stream().anyMatch(requestedDrug -> requestedDrug.getDrugCode().equals(drugCode)
				&& requestedDrug.getScientificCode().equals(scientificCode));
	}

	private boolean verifyRequestWithScientificCode(List<DispensableDrugs> requestedDrugs, String scientificCode) {
		return requestedDrugs.stream()
				.anyMatch(requestedDrug -> requestedDrug.getScientificCode().equals(scientificCode));
	}

	private void manageAlreadyDispensedDrugByTradeName(Set<String> dispensedDrugs,
			DispenseDrugsRequestModel requestedDrugsToDispense, String drugCode, String scientificCode) {
		dispensedDrugs.add(" Drug code: " + drugCode + " already dispensed.");
		requestedDrugsToDispense.getDrugList().removeIf(requestedDrug -> requestedDrug.getDrugCode().equals(drugCode)
				&& requestedDrug.getScientificCode().equals(scientificCode));
	}

	private void manageAlreadyDispensedDrugByScientificCode(Set<String> dispensedDrugs,
			DispenseDrugsRequestModel requestedDrugsToDispense, String drugCode, String scientificCode) {
		dispensedDrugs
				.add(" For Scientific code: " + scientificCode + " Drug code: " + drugCode + " already dispensed.");
		requestedDrugsToDispense.getDrugList()
				.removeIf(requestedDrug -> requestedDrug.getScientificCode().equals(scientificCode));
	}
}
