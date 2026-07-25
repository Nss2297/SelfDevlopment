package com.waseel.eligibility.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.waseel.eligibility.client.portal.PortalSwitchClient;
import com.waseel.eligibility.client.portal.model.TransactionWrapper;
import com.waseel.eligibility.enums.BusinessRulesPrivilage;
import com.waseel.eligibility.enums.EligibilityDenialCode;
import com.waseel.eligibility.enums.EligibilityStatusType;
import com.waseel.eligibility.enums.InvalidStatusDescription;
import com.waseel.eligibility.enums.RequestParameters;
import com.waseel.eligibility.enums.TransactionStatusType;
import com.waseel.eligibility.enums.TransactionType;
import com.waseel.eligibility.exception.EligibilityException;
import com.waseel.eligibility.logging.AuditLogService;
import com.waseel.eligibility.model.EligibilityRequestModel;
import com.waseel.eligibility.model.EligibilityResponseModel;
import com.waseel.eligibility.persist.businessrules.CommonDenials;
import com.waseel.eligibility.persist.businessrules.TransactionLog;
import com.waseel.eligibility.repository.businessrules.CommonDenialsRepository;
import com.waseel.eligibility.repository.businessrules.TransactionLogRepository;
import com.waseel.eligibility.service.management.SessionService;
import com.waseel.eligibility.service.management.TransactionLogService;
import com.waseel.eligibility.service.portal.EligibilityGenerator;
import com.waseel.eligibility.service.portal.EligibilityResponseHandler;

@Service
public class EligibilityService {
	private static final Logger logger = LoggerFactory.getLogger(EligibilityService.class);

	@Autowired
	EligibilityGenerator eligibilityGenerator;

	@Autowired
	EligibilityResponseHandler eligibilityResponseHandler;

	@Autowired
	PortalSwitchClient portalClient;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private CommonDenialsRepository commonDenialsRepository;

	public EligibilityResponseModel eligibilityController(String idNumber,
			EligibilityRequestModel eligibilityRequestModel, ContentCachingRequestWrapper requestWrapper,
			ContentCachingResponseWrapper cachingResponseWrapper) throws EligibilityException {
		TransactionWrapper transactionWrapperRequest = null;
		TransactionWrapper transactionWrapperResponse = null;
		try {
			transactionWrapperRequest = eligibilityGenerator.generate(idNumber, eligibilityRequestModel);
			logger.info("Eligibility request prepared to be sent to portal:{}", transactionWrapperRequest);
			transactionWrapperResponse = portalClient.send(transactionWrapperRequest);
			if (transactionWrapperResponse != null) {
				logger.info("Eligibility response recieved from portal:{}", transactionWrapperResponse);
				EligibilityResponseModel eligibilityResponseModel = new EligibilityResponseModel(
						eligibilityRequestModel.getRequestId());
				eligibilityResponseHandler.handleResponse(transactionWrapperResponse, eligibilityResponseModel,
						idNumber);
				Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
				TransactionLog transactionLog = new TransactionLog(
						Double.valueOf(BusinessRulesPrivilage.ELIGIBILITY_PRIVILAGE.value()),
						eligibilityRequestModel.getPayerId(), eligibilityRequestModel.getProviderId(), timestamp,
						timestamp, TransactionType.ELIGIBILITY.value(), eligibilityRequestModel.getRequestId(),
						TransactionStatusType.RECEIVED.value());
				transactionLog.setTransactionReferenceNumber(
						transactionWrapperResponse.getEligibilitySubmissionResponse().getStatus().getReferenceNumber());
				transactionLog = transactionLogRepository.save(transactionLog);
				sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
				eligibilityResponseModel.setHttpStatusCode(cachingResponseWrapper.getStatus());
				setDenialCode(eligibilityResponseModel, idNumber);
				return eligibilityResponseModel;
			}
		} catch (Exception e) {
			logger.error("Eligibility submission failed", e);
			throw new EligibilityException(populateEligiblityResponse(EligibilityStatusType.FAILED.value(),
					"Eligibility submission failed.", eligibilityRequestModel.getRequestId()));
		}
		throw new EligibilityException(populateEligiblityResponse(EligibilityStatusType.INVALID.value(),
				"No response received from portal. Please try again later.", eligibilityRequestModel.getRequestId()));
	}

	private void setDenialCode(EligibilityResponseModel eligibilityResponseModel, String idNumber) {
		String denialCode = "";
		if (eligibilityResponseModel.getStatus().equals(EligibilityStatusType.INVALID.value())) {
			denialCode = EligibilityDenialCode.INVALID.value();
			if (eligibilityResponseModel.getStatusDescription().trim()
					.equalsIgnoreCase(InvalidStatusDescription.INVALID_MEMBER.value())) {
				denialCode = EligibilityDenialCode.INVALID_MEMBER.value();
			}
		} else if (eligibilityResponseModel.getStatus().equals(EligibilityStatusType.FAILED.value())) {
			denialCode = EligibilityDenialCode.FAILED.value();
		} else if (eligibilityResponseModel.getStatus().equals(EligibilityStatusType.INELIGIBLE.value())) {
			denialCode = EligibilityDenialCode.INELIGIBLE.value();
		}
		if (StringUtils.isNotBlank(denialCode)) {
			Optional<CommonDenials> commonDenialsOp = commonDenialsRepository.findByDenialCode(denialCode);
			if (commonDenialsOp.isPresent()) {
				eligibilityResponseModel.setDenialCode(denialCode);
				eligibilityResponseModel.setDescription(
						commonDenialsOp.get().getDenialDescription().replace("<IdNumber> <MemberName>", idNumber));
				;
			}
		}
	}

	public EligibilityResponseModel populateEligiblityResponse(String status, String statusDescription,
			String requestId) {
		return new EligibilityResponseModel(status, statusDescription, "", "", requestId,
				HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	public EligibilityResponseModel populateEligibilityResponseModel(EligibilityException ex,
			HttpServletRequest request, ContentCachingRequestWrapper requestWrapper) {
		if (ex != null) {
			EligibilityRequestModel eligibilityRequestModel = new EligibilityRequestModel(
					request.getParameter(RequestParameters.PAYER_ID.value()),
					request.getParameter(RequestParameters.PROVIDER_ID.value()),
					request.getParameter(RequestParameters.REQUEST_ID.value()));
			Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			String idNumber = (String) pathVariables.get("idNumber");
			EligibilityResponseModel responseModel = new EligibilityResponseModel();
			responseModel.setStatus(ex.getInvalidResponse().getStatus());
			responseModel.setStatusDescription(ex.getInvalidResponse().getStatusDescription());
			responseModel.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
			responseModel.setRequestId(eligibilityRequestModel.getRequestId());
			setDenialCode(responseModel, idNumber);
			populateTransactionLog(eligibilityRequestModel, requestWrapper);
			return responseModel;
		}
		return null;
	}

	public EligibilityResponseModel populateInvalidEligibilityResponse(MethodArgumentNotValidException ex,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
		String error = errors.toString().replace("[", "").replace("]", "");
		EligibilityRequestModel eligibilityRequestModel = new EligibilityRequestModel(
				request.getParameter(RequestParameters.PAYER_ID.value()),
				request.getParameter(RequestParameters.PROVIDER_ID.value()),
				request.getParameter(RequestParameters.REQUEST_ID.value()));
		Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String idNumber = (String) pathVariables.get("idNumber");
		EligibilityResponseModel invalidResponse = new EligibilityResponseModel(EligibilityStatusType.INVALID.value(),
				error, "", "", eligibilityRequestModel.getRequestId(), HttpStatus.BAD_REQUEST.value());
		setDenialCode(invalidResponse, idNumber);
		populateTransactionLog(eligibilityRequestModel, requestWrapper);
		return invalidResponse;
	}

	public EligibilityResponseModel populateFailedEligibilityResponse(ContentCachingRequestWrapper requestWrapper,
			HttpServletRequest request) {
		EligibilityRequestModel eligibilityRequestModel = new EligibilityRequestModel(
				request.getParameter(RequestParameters.PAYER_ID.value()),
				request.getParameter(RequestParameters.PROVIDER_ID.value()),
				request.getParameter(RequestParameters.REQUEST_ID.value()));
		Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String idNumber = (String) pathVariables.get("idNumber");
		EligibilityResponseModel invalidResponse = new EligibilityResponseModel(EligibilityStatusType.FAILED.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), "", "", eligibilityRequestModel.getRequestId(),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		setDenialCode(invalidResponse, idNumber);
		populateTransactionLog(eligibilityRequestModel, requestWrapper);
		return invalidResponse;
	}

	private void populateTransactionLog(EligibilityRequestModel eligibilityRequestModel,
			ContentCachingRequestWrapper requestWrapper) {
		TransactionLog transactionLog = transactionLogService.addTransaction(eligibilityRequestModel);
		if (null != transactionLog && null != transactionLog.getTransactionLogId()
				&& !transactionLog.getTransactionLogId().toString().isEmpty()) {
			sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
			logger.info("Data saved for TransactionLogId [{}]", transactionLog.getTransactionLogId());
		}
	}
}
