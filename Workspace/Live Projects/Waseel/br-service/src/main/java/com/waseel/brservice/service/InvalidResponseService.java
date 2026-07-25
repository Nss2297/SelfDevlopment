package com.waseel.brservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.brservice.model.InvalidResponseModel;
import com.waseel.brservice.model.SensitiveDrugRequestModel;
import com.waseel.brservice.model.enums.RequestType;
import com.waseel.brservice.model.enums.URLName;
import com.waseel.brservice.persist.businessrules.TransactionLog;

@Service
public class InvalidResponseService {

	@Autowired
	private MapperService mapperService;
	@Autowired
	private TransactionLogService transactionLogService;
	@Autowired
	private SessionService sessionService;

	public InvalidResponseModel populateFailedResponse(ContentCachingRequestWrapper requestWrapper) {
		return populateResponse("FAILED", Arrays.asList(HttpStatus.INTERNAL_SERVER_ERROR.name()), requestWrapper);
	}

	public InvalidResponseModel populateInvalidResponse(MethodArgumentNotValidException ex,
			ContentCachingRequestWrapper requestWrapper) {
		List<String> errors = ex.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());
		return populateResponse("INVALID", errors, requestWrapper);
	}

	private InvalidResponseModel populateResponse(String errorCode, List<String> errors,
			ContentCachingRequestWrapper requestWrapper) {
		InvalidResponseModel invalidResponse = new InvalidResponseModel();
		String requestId = null;
		String payerId = null;
		String providerId = null;
		RequestType requestType = null;
		invalidResponse.setErrorCode(errorCode);
		invalidResponse.setErrorDescription(errors);
		if (requestWrapper.getRequestURI().contains(URLName.SENSITIVE_DRUG.value())) {
			SensitiveDrugRequestModel requestModel = mapperService.mapSensitiveDrugRequestModel(requestWrapper);
			if (requestModel != null) {
				requestId = requestModel.getRequestId();
				payerId = requestModel.getPayerId();
				providerId = requestModel.getProviderId();
				requestType = RequestType.SENSITIVE_DRUG;
			}
		}
		invalidResponse.setRequestId(requestId);
		addInTransactionLog(requestId, requestWrapper, payerId, providerId, requestType);
		return invalidResponse;
	}

	private void addInTransactionLog(String requestId, ContentCachingRequestWrapper requestWrapper, String payerId,
			String providerId, RequestType requestType) {
		String transactionLogId = sessionService.getTransactionLogIdFromSession(requestWrapper);
		if (StringUtils.isBlank(transactionLogId)) {
			TransactionLog transactionLog = transactionLogService.addDataInTransactionLog(requestType, requestId,
					payerId, providerId);
			if (transactionLog != null) {
				sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
			}
		}
	}

}
