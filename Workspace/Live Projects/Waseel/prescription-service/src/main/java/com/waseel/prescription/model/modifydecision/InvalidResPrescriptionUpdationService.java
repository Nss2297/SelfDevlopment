package com.waseel.prescription.model.modifydecision;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.common.CommonPrescriptionUpdationResponseModel;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.service.management.SessionService;
import com.waseel.prescription.service.management.TransactionLogService;
import com.waseel.prescription.util.SourceTypeUtil;

@Service
public class InvalidResPrescriptionUpdationService {

	private static final String STR_INVALID = "INVALID";

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private PrescriptionRequestRepository prescriptionRequestRepository;

	public CommonPrescriptionUpdationResponseModel populateInvalidFailedResponse(Exception ex,
			ContentCachingRequestWrapper requestWrapper) {
		CommonPrescriptionUpdationResponseModel response = new CommonPrescriptionUpdationResponseModel();
		String errorCode = "FAILED";
		String errorMessage = HttpStatus.INTERNAL_SERVER_ERROR.name();
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException methodArgEx = (MethodArgumentNotValidException) ex;
			List<String> errors = methodArgEx.getBindingResult().getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
			errorCode = STR_INVALID;
			errorMessage = StringUtils.strip(errors.toString(), "[]");
		} else if (ex instanceof PrescriptionException) {
			PrescriptionException prescriptionEx = (PrescriptionException) ex;
			errorCode = STR_INVALID;
			errorMessage = prescriptionEx.getMessage();
		}
		String ePrescriptionReferenceNumber = fetchEPrescriptionRefNumberFromUri(requestWrapper);
		response.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		response.setErrorCode(errorCode);
		response.setErrorDescription(errorMessage);
		manageTransactionLog(requestWrapper, ePrescriptionReferenceNumber);
		return response;
	}
	
	public CommonPrescriptionUpdationResponseModel populateUnAuthorizedResponse(AccessDeniedException ex,
			ContentCachingRequestWrapper requestWrapper) {
		CommonPrescriptionUpdationResponseModel response = new CommonPrescriptionUpdationResponseModel();
		String ePrescriptionReferenceNumber = fetchEPrescriptionRefNumberFromUri(requestWrapper);
		response.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		response.setErrorCode(HttpStatus.UNAUTHORIZED.name());
		response.setErrorDescription(ex.getMessage());
		return response;
	}

	private String fetchEPrescriptionRefNumberFromUri(HttpServletRequest request) {
		Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		return (String) pathVariables.get("ePrescriptionReferenceNumber");
	}

	private String fetchPayerIdFromUri(HttpServletRequest request) {
		Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		return (String) pathVariables.get("payerId");
	}

	private void manageTransactionLog(ContentCachingRequestWrapper requestWrapper,
			String ePrescriptionReferenceNumber) {
		String payerId = fetchPayerIdFromUri(requestWrapper);
		PrescriptionRequest prescriptionRequest = getPrescriptionRequestDetails(ePrescriptionReferenceNumber);
		String providerId = null;
		String requestId = null;
		if (prescriptionRequest != null) {
			providerId = prescriptionRequest.getProviderId();
			requestId = prescriptionRequest.getRequestId();
		}
		addInTransactionLog(providerId, payerId, requestId, ePrescriptionReferenceNumber, requestWrapper);
	}

	private void addInTransactionLog(String providerId, String payerId, String requestId,
			String ePrescriptionReferenceNumber, ContentCachingRequestWrapper requestWrapper) {
		String transactionLogId = sessionService.getTransactionLogIdFromSession(requestWrapper);
		if(StringUtils.isBlank(transactionLogId)) {
			String sourceType = SourceTypeUtil
					.getSourceTypeBasedOnHeaderOrigin(requestWrapper.getHeader(HttpHeaders.ORIGIN));
			TransactionLog transactionLog = transactionLogService.addInquiryTransaction(RequestType.MODIFY_DECISION,
					payerId, providerId, requestId, ePrescriptionReferenceNumber, sourceType);
			if (transactionLog != null && transactionLog.getTransactionLogId() != null) {
				sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
			}
		}
	}

	private PrescriptionRequest getPrescriptionRequestDetails(String ePrescriptionReferenceNumber) {
		if (!StringUtils.isBlank(ePrescriptionReferenceNumber)) {
			Optional<PrescriptionRequest> prescriptionRequestOptional = prescriptionRequestRepository
					.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
			if (prescriptionRequestOptional.isPresent()) {
				return prescriptionRequestOptional.get();
			}
		}
		return null;
	}
}
