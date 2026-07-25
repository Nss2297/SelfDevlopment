package com.waseel.drugexclusionvalidationservice.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.drugexclusionvalidationservice.model.DrugExclusionRequestModel;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionResponseModel;
import com.waseel.drugexclusionvalidationservice.model.enums.RequestType;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.TransactionLog;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvalidResponseService {

	private static final String NOTVALID = "Invalid";

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private MapperService mapperService;

	public DrugExclusionResponseModel populateFailedResponse(ContentCachingRequestWrapper requestWrapper) {
		DrugExclusionResponseModel invalidResponse = new DrugExclusionResponseModel();
		DrugExclusionRequestModel requestModel = mapperService.mapSpecialityExclusionRequestModel(requestWrapper);
		String requestId = requestModel != null ? requestModel.getRequestId() : null;
		invalidResponse.setErrorCode("Failed");
		invalidResponse.setErrorDescription(HttpStatus.INTERNAL_SERVER_ERROR.name());
		invalidResponse.setRequestId(requestId);
		addInTransactionLog(requestId, requestWrapper, requestModel);
		return invalidResponse;
	}

	public DrugExclusionResponseModel populateInvalidResponse(MethodArgumentNotValidException ex,
			ContentCachingRequestWrapper requestWrapper) {
		List<String> errors = new ArrayList<>();
		DrugExclusionRequestModel requestModel = mapperService.mapSpecialityExclusionRequestModel(requestWrapper);
		DrugExclusionResponseModel invalidResponse = new DrugExclusionResponseModel();
		ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
		String requestId = requestModel != null ? requestModel.getRequestId() : null;
		invalidResponse.setErrorCode(NOTVALID);
		invalidResponse.setErrorDescription(errors.toString().replace("[", "").replace("]", ""));
		invalidResponse.setRequestId(requestId);
		addInTransactionLog(requestId, requestWrapper,requestModel);
		return invalidResponse;
	}

	public DrugExclusionResponseModel populateInvalidResponse(HttpMessageNotReadableException ex,
			ContentCachingRequestWrapper requestWrapper) {
		String validationMsg = "Required request body is missing";
		DrugExclusionResponseModel invalidResponse = new DrugExclusionResponseModel();
		invalidResponse.setErrorCode(NOTVALID);
		String message = ex.getMessage();
		if (message != null && !message.isEmpty() && message.contains(validationMsg + ":")) {
			invalidResponse.setErrorDescription(validationMsg);
		}
		addInTransactionLog(null, requestWrapper,null);
		return invalidResponse;
	}

	private void addInTransactionLog(String requestId, ContentCachingRequestWrapper requestWrapper,
			DrugExclusionRequestModel requestModel) {
		String transactionLogId = sessionService.getTransactionLogIdFromSession(requestWrapper);
		if (StringUtils.isBlank(transactionLogId)) {
			TransactionLog transactionLog = transactionLogService.addDataInTransactionLog(RequestType.DRUG_EXCLUSION,
					requestId, requestModel != null ? requestModel.getPayerId() : null,
					requestModel != null ? requestModel.getProviderId() : null);
			if (transactionLog != null) {
				sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
			}
		}
	}
}
