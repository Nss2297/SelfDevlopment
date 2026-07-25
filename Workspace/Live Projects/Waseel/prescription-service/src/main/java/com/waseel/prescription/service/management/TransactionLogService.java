package com.waseel.prescription.service.management;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.waseel.prescription.model.dispense.DispenseDrugsRequestModel;
import com.waseel.prescription.model.dispense.PrescriptionDispenseRequestModel;
import com.waseel.prescription.model.dispense.PrescriptionDispenseResponseModel;
import com.waseel.prescription.model.enums.PrescriptionUrl;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.TransactionStatusType;
import com.waseel.prescription.model.inquiry.InquiryInvalidResponseModel;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryRequestModel;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryResponseModel;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryRequestModel;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryResponseModel;
import com.waseel.prescription.model.modifydecision.ModifyDecisionRequestModel;
import com.waseel.prescription.model.modifydecision.ModifyDecisionResponseModel;
import com.waseel.prescription.model.prescription.PayerMemberPhysicianInfoModel;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.persist.prescriptionservice.InvalidPrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.prescriptionservice.InvalidPrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.TransactionLogRepository;
import com.waseel.prescription.service.mapper.MapperService;
import com.waseel.prescription.util.UserInfoUtil;

import feign.Request.HttpMethod;

@Service
public class TransactionLogService {

	private static final Logger log = LoggerFactory.getLogger(TransactionLogService.class);
	private static final String EPRESCRIPTION_REFERENCE_NUMBER = "ePrescriptionReferenceNumber";

	@Autowired
	private MapperService mapperService;

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	@Autowired
	private AuditLogService auditLogService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private InvalidPrescriptionRequestService invalidPrescriptionRequestService;

	@Autowired
	private InvalidPrescriptionRequestRepository invalidPrescriptionRequestRepository;

	@Autowired
	private PrescriptionRequestRepository prescriptionRequestRepository;

	public void manageTransactionLogFromResponse(HttpServletRequest request,
			ContentCachingResponseWrapper responseWrapper, ContentCachingRequestWrapper requestWrapper,
			Timestamp sendingTime) {
		try {
			if (requestWrapper.getRequestURI().contains(PrescriptionUrl.SUMMARY_INQUIRY.getValue())) {
				manageTransactionLogForSummaryInquiry(requestWrapper, responseWrapper, request);
			} else if (requestWrapper.getRequestURI().contains(PrescriptionUrl.DETAIL_INQUIRY.getValue())) {
				manageTransactionLogForDetailInquiry(requestWrapper, responseWrapper, request);
			} else if (requestWrapper.getRequestURI().endsWith(PrescriptionUrl.DISPENSE.getValue())
					|| (requestWrapper.getRequestURI().endsWith(PrescriptionUrl.DISPENSABLE_DRUGS.getValue()))
							&& requestWrapper.getMethod().equals(HttpMethod.POST.name())) {
				manageTransactionLogForDispensePrescription(requestWrapper, responseWrapper, request, sendingTime);
			} else if (requestWrapper.getRequestURI().contains(PrescriptionUrl.PAYERS.getValue())
					&& requestWrapper.getRequestURI().endsWith(PrescriptionUrl.PRESCRIPTIONS.getValue())) {
				// NEW or FOLLOWUP
				PrescriptionResponseModel res = mapperService.mapPrescriptionResponse(responseWrapper);
				manageTransactionLogForNewOrFollowupPrescription(res, requestWrapper, request, sendingTime);
			} else if (requestWrapper.getRequestURI().contains(PrescriptionUrl.PAYERS.getValue())
					&& requestWrapper.getRequestURI().contains(PrescriptionUrl.PRESCRIPTIONS.getValue())
					&& requestWrapper.getMethod().equals(HttpMethod.DELETE.name())) {
				// For Cancellation
				PrescriptionResponseModel res = mapperService.mapPrescriptionResponse(responseWrapper);
				manageTransactionLogForCancellationPrescription(res, requestWrapper, request, sendingTime);
			} else if (requestWrapper.getRequestURI().contains(PrescriptionUrl.PAYERS.getValue())
					&& requestWrapper.getRequestURI().contains(PrescriptionUrl.PRESCRIPTIONS.getValue())
					&& !requestWrapper.getRequestURI().contains("drugs")
					&& !requestWrapper.getRequestURI().contains("diagnosis")
					&& !requestWrapper.getRequestURI().contains("validations")
					&& requestWrapper.getMethod().equals(HttpMethod.GET.name())) {
				manageTransactionLogForInquiryDetail(responseWrapper, request);
			} else if (requestWrapper.getRequestURI().endsWith(PrescriptionUrl.MODIFY_DECISION.getValue())) {
				manageTransactionLogForModifyDecisionByPayer(requestWrapper, responseWrapper, request);
			} 
			log.info("Remove transactionLog id from session.");
		} catch (Exception e) {
			log.error("Response exception:-", e);
		}
	}

	private void manageTransactionLogForModifyDecisionByPayer(ContentCachingRequestWrapper requestWrapper,
			ContentCachingResponseWrapper responseWrapper, HttpServletRequest request) {
		String httpStatusCode = String.valueOf(responseWrapper.getStatus());
		String transactionLogId = sessionService.getTransactionLogIdFromSession(request);
		ModifyDecisionResponseModel response = mapperService.mapModifyDecisionResponseModel(responseWrapper);
		if (response != null) {
			String status = null;
			String statusDesc = null;
			if (responseWrapper.getStatus() == HttpStatus.OK.value()) {
				status = response.getStatus();
				statusDesc = response.getStatusDescription();
			} else {
				status = response.getErrorCode();
				statusDesc = response.getErrorDescription();
			}
			TransactionLog updatedTranLog = updateTransactionLog(Long.parseLong(transactionLogId), httpStatusCode,
					status, request, statusDesc);
			if (updatedTranLog != null) {
				ModifyDecisionRequestModel requestModel = mapperService.mapModifyDecisionRequestModel(requestWrapper);
				auditLogService.saveAuditLogInMongoDbForModifyDecision(requestModel, response,
						updatedTranLog.getTransactionLogId(), updatedTranLog.getePrescriptionReferenceNumber());
			}
		}
	}

	private void manageTransactionLogForDispensePrescription(ContentCachingRequestWrapper requestWrapper,
			ContentCachingResponseWrapper responseWrapper, HttpServletRequest request, Timestamp sendingTime) {
		String requestURI = requestWrapper.getRequestURI();
		PrescriptionDispenseRequestModel dispenseRequest = null;
		DispenseDrugsRequestModel dispenseDrugsRequestModel = null;
		PrescriptionDispenseResponseModel response = mapperService.mapPrescriptionDispenseResponse(responseWrapper);
		if (requestURI.endsWith(PrescriptionUrl.DISPENSE.getValue())) {
			dispenseRequest = mapperService.mapPrescriptionDispenseRequest(requestWrapper);
		} else {
			dispenseDrugsRequestModel = mapperService.mapDispenseDrugsRequestModel(requestWrapper);
		}
		String httpStatusCode = response.getStatus().equals("INVALID") ? String.valueOf(HttpStatus.BAD_REQUEST.value())
				: String.valueOf(responseWrapper.getStatus());
		TransactionLog updatedTranLog = handleTransactionLogAndMongoDbForDispense(dispenseRequest, request,
				httpStatusCode, response, dispenseDrugsRequestModel, requestWrapper);
		if (responseWrapper.getStatus() == HttpStatus.BAD_REQUEST.value() || response.getStatus().equals("INVALID")) {
			invalidPrescriptionRequestService.addInvalidDataForDispense(response,
					updatedTranLog != null ? updatedTranLog.getRequestId() : null, sendingTime);
		}
	}

	private TransactionLog handleTransactionLogAndMongoDbForDispense(PrescriptionDispenseRequestModel dispenseRequest,
			HttpServletRequest request, String httpStatusCode, PrescriptionDispenseResponseModel response,
			DispenseDrugsRequestModel dispenseDrugsRequestModel, ContentCachingRequestWrapper requestWrapper) {
		String status = response.getStatus();
		String statusDesc = response.getStatusDescription();
		String transactionLogId = sessionService.getTransactionLogIdFromSession(request);
		Object invalidDispenseRequest = null;
		if ((dispenseRequest == null && requestWrapper.getRequestURI().endsWith(PrescriptionUrl.DISPENSE.getValue()))
				|| (dispenseDrugsRequestModel == null
						&& requestWrapper.getRequestURI().endsWith(PrescriptionUrl.DISPENSABLE_DRUGS.getValue()))) {
			invalidDispenseRequest = mapperService.mapBadPrescriptionRequest(requestWrapper);
		}
		if (!StringUtils.isBlank(transactionLogId)) {
			TransactionLog updatedTranLog = updateTransactionLog(Long.parseLong(transactionLogId), httpStatusCode,
					status, request, statusDesc);
			if (updatedTranLog != null) {
				sessionService.removeTransactionLogIdFromSession(request);
				auditLogService.saveAuditLogInMongoDbForDispense(dispenseRequest, response, dispenseDrugsRequestModel,
						RequestType.DISPENSED, updatedTranLog.getTransactionLogId(), updatedTranLog.getRequestId(),
						invalidDispenseRequest);
			}
			return updatedTranLog;
		}
		return null;
	}

	private void manageTransactionLogForDetailInquiry(ContentCachingRequestWrapper requestWrapper,
			ContentCachingResponseWrapper responseWrapper, HttpServletRequest request) {
		PrescriptionDetailInquiryResponseModel validResponse = null;
		InquiryInvalidResponseModel invalidResponse = null;
		PrescriptionDetailInquiryRequestModel requestModel = mapperService
				.mapPrescriptionDetailInquiryRequest(requestWrapper);
		if (responseWrapper.getStatus() == HttpStatus.OK.value()) {
			validResponse = mapperService.mapPrescriptionDetailInquiryResponse(responseWrapper);
		} else {
			invalidResponse = mapperService.mapPrescriptionInquiryInvalidResponse(responseWrapper);
		}
		handleTransactionLogAndMongoDbForDetailInquiry(requestModel, request,
				String.valueOf(responseWrapper.getStatus()), validResponse, invalidResponse, requestWrapper);
	}

	private void manageTransactionLogForInquiryDetail(ContentCachingResponseWrapper responseWrapper,
			HttpServletRequest request) {
		PayerMemberPhysicianInfoModel validResponse = null;
		PrescriptionResponseModel invalidResponse = null;
		if (responseWrapper.getStatus() == HttpStatus.OK.value()) {
			validResponse = mapperService.mapPrescriptionInquiryDetailResponse(responseWrapper);
		} else {
			invalidResponse = mapperService.mapPrescriptionResponse(responseWrapper);
		}
		handleTransactionLogAndMongoDbForInquiryDetail(request, String.valueOf(responseWrapper.getStatus()),
				validResponse, invalidResponse);
	}

	private void manageTransactionLogForSummaryInquiry(ContentCachingRequestWrapper requestWrapper,
			ContentCachingResponseWrapper responseWrapper, HttpServletRequest request) {
		PrescriptionSummaryResponseModel validResponse = null;
		InquiryInvalidResponseModel invalidResponse = null;
		PrescriptionSummaryRequestModel requestModel = mapperService
				.mapPrescriptionSummaryInquiryRequest(requestWrapper);
		if (responseWrapper.getStatus() == HttpStatus.OK.value()) {
			validResponse = mapperService.mapPrescriptionSummaryInquiryResponse(responseWrapper);
		} else {
			invalidResponse = mapperService.mapPrescriptionInquiryInvalidResponse(responseWrapper);
		}
		handleTransactionLogAndMongoDbForSummaryInquiry(requestModel, request, validResponse, invalidResponse,
				responseWrapper);
	}

	private void handleTransactionLogAndMongoDbForDetailInquiry(PrescriptionDetailInquiryRequestModel requestModel,
			HttpServletRequest request, String httpStatusCode, PrescriptionDetailInquiryResponseModel validResponse,
			InquiryInvalidResponseModel invalidResponse, ContentCachingRequestWrapper requestWrapper) {
		String status = null;
		String statusDesc = null;
		String transactionLogId = sessionService.getTransactionLogIdFromSession(request);
		Object invalidDetailInquiryRequest = null;
		if (validResponse != null) {
			statusDesc = validResponse.getStatusDescription();
			status = validResponse.getStatus();
		} else if (invalidResponse != null) {
			statusDesc = invalidResponse.getErrorDescription();
			status = invalidResponse.getErrorStatus();
		}
		if (requestModel == null) {
			invalidDetailInquiryRequest = mapperService.mapBadPrescriptionRequest(requestWrapper);
		}
		if (!StringUtils.isBlank(transactionLogId)) {
			TransactionLog updatedTranLog = updateTransactionLog(Long.parseLong(transactionLogId), httpStatusCode,
					status, request, statusDesc);
			if (updatedTranLog != null) {
				sessionService.removeTransactionLogIdFromSession(request);
				auditLogService.saveAuditLogInMongoDbForDetailInquiry(requestModel, validResponse, invalidResponse,
						RequestType.DETAIL_INQUIRY, updatedTranLog.getTransactionLogId(), updatedTranLog.getRequestId(),
						invalidDetailInquiryRequest);
			}
		}
	}

	private void handleTransactionLogAndMongoDbForInquiryDetail(HttpServletRequest request, String httpStatusCode,
			PayerMemberPhysicianInfoModel validResponse, PrescriptionResponseModel invalidResponse) {
		String status = null;
		String statusDesc = null;
		String transactionLogId = sessionService.getTransactionLogIdFromSession(request);
		if (validResponse != null) {
			statusDesc = validResponse.getStatusDescription();
			status = validResponse.getStatus();
		} else if (invalidResponse != null) {
			statusDesc = invalidResponse.getHttpStatusDescription();
			status = invalidResponse.getHttpStatusCode() + "";
		}
		if (!StringUtils.isBlank(transactionLogId)) {
			TransactionLog updatedTranLog = updateTransactionLog(Long.parseLong(transactionLogId), httpStatusCode,
					status, request, statusDesc);
			if (updatedTranLog != null) {
				sessionService.removeTransactionLogIdFromSession(request);
				auditLogService.saveAuditLogInMongoDbForInquiryDetail(validResponse, invalidResponse,
						RequestType.DETAIL_INQUIRY, updatedTranLog.getTransactionLogId(),
						updatedTranLog.getRequestId());
			}
		}
	}

	private void handleTransactionLogAndMongoDbForSummaryInquiry(PrescriptionSummaryRequestModel requestModel,
			HttpServletRequest request, PrescriptionSummaryResponseModel validResponse,
			InquiryInvalidResponseModel invalidResponse, ContentCachingResponseWrapper responseWrapper) {
		String requestId = null;
		String transactionLogId = sessionService.getTransactionLogIdFromSession(request);
		int httpStatus = responseWrapper.getStatus();
		String status = "";
		String statusDescription = "";
		if (validResponse != null) {
			requestId = validResponse.getRequestId();
			status = validResponse.getRequestStatus();
			statusDescription = validResponse.getRequestStatus();
		} else if (invalidResponse != null) {
			requestId = invalidResponse.getRequestId();
			status = invalidResponse.getErrorStatus();
			statusDescription = invalidResponse.getErrorDescription();
		} else if (httpStatus == HttpStatus.UNAUTHORIZED.value()) {
			status = "Unauthorized";
			statusDescription = "Access is denied";
		}
		updateTransactionLog(Long.parseLong(transactionLogId), String.valueOf(httpStatus), status, request,
				statusDescription);
		auditLogService.saveAuditLogInMongoDbForSummaryInquiry(requestModel, validResponse, invalidResponse,
				RequestType.SUMMARY_INQUIRY, transactionLogId, requestId);
		sessionService.removeTransactionLogIdFromSession(request);
	}

	public void manageTransactionLogForNewOrFollowupPrescription(PrescriptionResponseModel prescriptionResponse,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request, Timestamp sendingTime) {
		PrescriptionRequestModel prescriptionRequest = mapperService.mapPrescriptionRequest(requestWrapper);
		JSONObject invalidPrescriptionRequest = null;
		if (null == prescriptionRequest) {
			invalidPrescriptionRequest = mapperService.mapBadPrescriptionRequest(requestWrapper);
		}
		if ((null != invalidPrescriptionRequest || null != prescriptionRequest) && null != prescriptionResponse) {
			manageTransactionLog(prescriptionResponse, requestWrapper, request, invalidPrescriptionRequest,
					prescriptionRequest, sendingTime);
		}
	}

	public void manageTransactionLogForCancellationPrescription(PrescriptionResponseModel prescriptionResponse,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request, Timestamp sendingTime) {
		if (prescriptionResponse != null) {
			String transactionLogId = sessionService.getTransactionLogIdFromSession(request);
			if (transactionLogId != null && !transactionLogId.isEmpty()) {
				updateTransactionLogForCancellationPrescription(prescriptionResponse, requestWrapper,
						RequestType.CANCELLATION, Long.parseLong(transactionLogId), sendingTime);
			}
		}
	}

	public void updateTransactionLogForCancellationPrescription(PrescriptionResponseModel prescriptionResponse,
			HttpServletRequest request, RequestType requestType, Long transactionLogId, Timestamp sendingTime) {
		try {
			String statusDescription = prescriptionResponse.getStatusDescription();
			TransactionLog updatedTranLog = updateTransactionLog(transactionLogId,
					String.valueOf(prescriptionResponse.getHttpStatusCode()), prescriptionResponse.getStatus(), request,
					statusDescription);
			if (updatedTranLog != null) {
				if (prescriptionResponse.getHttpStatusCode() == HttpStatus.BAD_REQUEST.value()) {
					addInvalidDataInInvalidPrescriptionRequest(request, updatedTranLog, sendingTime);
				}
				auditLogService.saveAuditLogInMongoDb(null, prescriptionResponse, requestType,
						updatedTranLog.getTransactionLogId(), null);
			}

		} catch (Exception e) {
			log.error("Transaction exception:-", e);
		}
	}

	private void addInvalidDataInInvalidPrescriptionRequest(HttpServletRequest request, TransactionLog updatedTranLog,
			Timestamp sendingTime) {
		Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String payerId = (String) pathVariables.get("payerId");
		String ePrescriptionReferenceNumber = (String) pathVariables.get(EPRESCRIPTION_REFERENCE_NUMBER);
		MemberInfo memberInfo = getMemberDetailsByEPrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		InvalidPrescriptionRequest invalidPrescriptionRequest = invalidPrescriptionRequestDetails(
				ePrescriptionReferenceNumber, payerId, memberInfo != null ? memberInfo.getPolicyNumber() : null,
				memberInfo != null ? memberInfo.getMemberId() : null,
				memberInfo != null ? memberInfo.getIdNumber().toString() : "0", updatedTranLog, sendingTime);
		invalidPrescriptionRequestRepository.save(invalidPrescriptionRequest);
	}

	private MemberInfo getMemberDetailsByEPrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		Optional<PrescriptionRequest> prescriptionRequest = prescriptionRequestRepository
				.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		if (prescriptionRequest.isPresent()) {
			return invalidPrescriptionRequestService.getMemberInfo(prescriptionRequest.get().getRequestId());
		}
		return null;
	}

	private void manageTransactionLog(PrescriptionResponseModel prescriptionResponse,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request,
			JSONObject invalidPrescriptionRequest, PrescriptionRequestModel prescriptionRequest,
			Timestamp sendingTime) {
		RequestType requestType;
		Optional<List<TransactionLog>> transactionLogOp = transactionLogRepository
				.findByRequestId(prescriptionResponse.getRequestId());
		if (requestWrapper.getRequestURI().contains("cancellation")) {
			requestType = RequestType.CANCELLATION;
		} else {
			requestType = transactionLogOp.isPresent() && transactionLogOp.get().size() > 1 ? RequestType.FOLLOWUP
					: RequestType.NEW;
		}
		String transactionLogId = sessionService.getTransactionLogIdFromSession(request);
		if (null != transactionLogId && !transactionLogId.isEmpty()) {
			updateTransactionLogForNewOrFollowUpPrescription(prescriptionResponse, prescriptionRequest, requestWrapper,
					requestType, Long.parseLong(transactionLogId), invalidPrescriptionRequest, sendingTime);
		}

	}

	public void updateTransactionLogForNewOrFollowUpPrescription(PrescriptionResponseModel prescriptionResponse,
			PrescriptionRequestModel prescriptionRequest, HttpServletRequest request, RequestType requestType,
			Long transactionLogId, JSONObject invalidPrescriptionRequest, Timestamp sendingTime) {
		try {
			String statusDescription = prescriptionResponse.getStatusDescription();
			TransactionLog updatedTranLog = updateTransactionLog(transactionLogId,
					String.valueOf(prescriptionResponse.getHttpStatusCode()), prescriptionResponse.getStatus(), request,
					statusDescription);
			if (updatedTranLog != null) {
				if (prescriptionResponse.getHttpStatusCode() == HttpStatus.BAD_REQUEST.value()) {
					manageInvalidPresriptionRequest(prescriptionRequest, prescriptionResponse,
							invalidPrescriptionRequest, updatedTranLog, sendingTime);
				}
				auditLogService.saveAuditLogInMongoDb(prescriptionRequest, prescriptionResponse, requestType,
						updatedTranLog.getTransactionLogId(), invalidPrescriptionRequest);
			}

		} catch (Exception e) {
			log.error("Transaction exception:-", e);
		}
	}

	private void manageInvalidPresriptionRequest(PrescriptionRequestModel prescriptionRequest,
			PrescriptionResponseModel prescriptionResponse, JSONObject invalidPrescriptionRequest,
			TransactionLog updatedTranLog, Timestamp sendingTime) {
		invalidPrescriptionRequestRepository.save(populateInvalidPrescriptionRequest(prescriptionRequest,
				prescriptionResponse, invalidPrescriptionRequest, updatedTranLog, sendingTime));
	}

	private InvalidPrescriptionRequest populateInvalidPrescriptionRequest(PrescriptionRequestModel prescriptionRequest,
			PrescriptionResponseModel prescriptionResponse, JSONObject invalidPrescriptionRequest,
			TransactionLog updatedTranLog, Timestamp sendingTime) {
		String ePrescriptionReferenceNumber = "";
		String payerId = "";
		String policyNumber = "";
		String memberId = "";
		String idNumber = "";
		if (null != prescriptionRequest) {
			ePrescriptionReferenceNumber = StringUtils.isNotBlank(prescriptionRequest.getePrescriptionReferenceNumber())
					? prescriptionRequest.getePrescriptionReferenceNumber()
					: prescriptionResponse.getePrescriptionReferenceNumber();
			payerId = prescriptionRequest.getPayerId();
			policyNumber = prescriptionRequest.getPolicyNumber();
			memberId = prescriptionRequest.getMemberId();
			idNumber = prescriptionRequest.getIdNumber();
		} else {
			ePrescriptionReferenceNumber = invalidPrescriptionRequest.has(EPRESCRIPTION_REFERENCE_NUMBER)
					? invalidPrescriptionRequest.getString(EPRESCRIPTION_REFERENCE_NUMBER)
					: prescriptionResponse.getePrescriptionReferenceNumber();
			payerId = invalidPrescriptionRequest.getString("payerId");
			policyNumber = invalidPrescriptionRequest.has("policyNumber")
					? invalidPrescriptionRequest.getString("policyNumber")
					: null;
			memberId = invalidPrescriptionRequest.has("memberId") ? invalidPrescriptionRequest.getString("memberId")
					: null;
			idNumber = invalidPrescriptionRequest.has("IdNumber") ? invalidPrescriptionRequest.getString("IdNumber")
					: null;
		}
		return invalidPrescriptionRequestDetails(ePrescriptionReferenceNumber, payerId, policyNumber, memberId,
				idNumber, updatedTranLog, sendingTime);
	}

	private InvalidPrescriptionRequest invalidPrescriptionRequestDetails(String ePrescriptionReferenceNumber,
			String payerId, String policyNumber, String memberId, String idNumber, TransactionLog updatedTranLog,
			Timestamp sendingTime) {
		InvalidPrescriptionRequest invalidPrescription = new InvalidPrescriptionRequest(
				null != updatedTranLog ? updatedTranLog.getRequestId() : null, ePrescriptionReferenceNumber,
				sendingTime, new Timestamp(Calendar.getInstance().getTimeInMillis()),
				null != updatedTranLog ? updatedTranLog.getStatus() : null,
				null != updatedTranLog ? updatedTranLog.getStatusDescription() : null, memberId, policyNumber, payerId,
				null != updatedTranLog ? updatedTranLog.getProviderId() : null);
		if (StringUtils.isNotBlank(idNumber)) {
			boolean isNumber = invalidPrescriptionRequestService.validateIdNumber(idNumber);
			if (isNumber) {
				invalidPrescription.setIdNumber(Long.valueOf(idNumber));
			}
		}

		return invalidPrescription;
	}

	public TransactionLog updateTransactionLog(Long transactionLogId, String httpStatus, String status,
			HttpServletRequest request, String statusDescription) {
		TransactionLog updatedTranLog = new TransactionLog();
		try {
			if (transactionLogId != null) {
				Optional<TransactionLog> transactionLogOp = transactionLogRepository.findById(transactionLogId);
				if (transactionLogOp.isPresent()) {
					updatedTranLog = transactionLogOp.get();
				}
				updatedTranLog.setHttpStatus(httpStatus);
				updatedTranLog.setHttpStatusDescription(statusDescription);
				updatedTranLog.setStatus(status);
				updatedTranLog.setTransactionStatus(TransactionStatusType.SENT.value());
				updatedTranLog.setSendingResponseDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				updatedTranLog.setStatusDescription(statusDescription);
				updatedTranLog = transactionLogRepository.save(updatedTranLog);
				sessionService.removeTransactionLogIdFromSession(request);
				log.info("Prescription updated for TransactionLogId[{}]", updatedTranLog.getTransactionLogId());
				return updatedTranLog;
			}
		} catch (Exception e) {
			log.error("Transaction log Exception:-", e);
		}
		return updatedTranLog;
	}

	public TransactionLog addTransaction(RequestType requestType, String payerId, String providerId, String requestId,
			String ePrescriptionReferenceNumber, String sourceType) {
		try {
			TransactionLog transactionLog = new TransactionLog();
			populateCommonTransactionLogData(payerId, providerId, requestId, requestType, transactionLog, sourceType);
			transactionLog.setePrescriptionReferenceNumber(
					null != ePrescriptionReferenceNumber && !ePrescriptionReferenceNumber.isEmpty()
							? ePrescriptionReferenceNumber
							: transactionLogRepository.generateEPrescriptionReferenceNumber());
			return transactionLogRepository.save(transactionLog);
		} catch (Exception e) {
			log.error("Exception:-", e);
		}
		return null;
	}

	public TransactionLog addInquiryTransaction(RequestType requestType, String payerId, String providerId,
			String requestId, String ePrescriptionReferenceNumber, String sourceType) {
		try {
			TransactionLog transactionLog = new TransactionLog();
			populateCommonTransactionLogData(payerId, providerId, requestId, requestType, transactionLog, sourceType);
			transactionLog.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
			return transactionLogRepository.save(transactionLog);
		} catch (Exception e) {
			log.error("Exception:-", e);
		}
		return null;
	}

	private void populateCommonTransactionLogData(String payerId, String providerId, String requestId,
			RequestType requestType, TransactionLog transactionLog, String sourceType) {
		Timestamp timestamp = (new Timestamp(Calendar.getInstance().getTimeInMillis()));
		if (null != payerId && payerId.getBytes().length <= 20) {
			transactionLog.setPayerId(payerId);
		}
		if (null != providerId && providerId.getBytes().length <= 20) {
			transactionLog.setProviderId(providerId);
		}
		transactionLog.setRequestId(requestId);
		transactionLog.setTransactionID(getTransactionId(requestType));
		transactionLog.setTransactionType(getStatusDescription(requestType));
		transactionLog.setReceivingRequestDateTime(timestamp);
		transactionLog.setSendingResponseDateTime(timestamp);
		transactionLog.setTransactionStatus(TransactionStatusType.RECEIVED.value());
		transactionLog.setUserID(UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication()));
		transactionLog.setSourceType(sourceType);
	}

	private Double getTransactionId(RequestType type) {
		switch (type) {
		case NEW:
			return 51.11;
		case FOLLOWUP:
			return 51.12;
		case CANCELLATION:
			return 51.13;
		case DETAIL_INQUIRY:
			return 51.141;
		case SUMMARY_INQUIRY:
			return 51.142;
		case DISPENSED:
			return 51.15;
		case MODIFY_DECISION:
			return 53D;
		default:
			return null;
		}
	}

	private String getStatusDescription(RequestType type) {
		switch (type) {
		case FOLLOWUP:
			return RequestType.FOLLOWUP.name();
		case CANCELLATION:
			return RequestType.CANCELLATION.name();
		case DETAIL_INQUIRY:
			return RequestType.DETAIL_INQUIRY.name();
		case SUMMARY_INQUIRY:
			return RequestType.SUMMARY_INQUIRY.name();
		case DISPENSED:
			return RequestType.DISPENSED.name();
		case MODIFY_DECISION:
			return RequestType.MODIFY_DECISION.name();
		default:
			return RequestType.NEW.name();
		}
	}
}