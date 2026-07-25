package com.waseel.drugformulary.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.drugformulary.model.DrugFormularyRequestModel;
import com.waseel.drugformulary.model.DrugFormularyResponseModel;
import com.waseel.drugformulary.model.enums.RequestType;
import com.waseel.drugformulary.persist.businessrules.TransactionLog;

@Service
public class InvalidResponseService {

	private static final String NOTVALID = "Invalid";

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private MapperService mapperService;

	public DrugFormularyResponseModel populateFailedResponse(ContentCachingRequestWrapper requestWrapper) {
		DrugFormularyResponseModel invalidResponse = new DrugFormularyResponseModel();
		DrugFormularyRequestModel requestModel = mapperService.mapDrugFormularyRequestModel(requestWrapper);
		invalidResponse.setStatusCode("Failed");
		invalidResponse.setStatusDescription(HttpStatus.INTERNAL_SERVER_ERROR.name());
		addInTransactionLog(requestModel.getRequestId(), getPayerIdFromPathVariable(requestWrapper), requestWrapper);
		return invalidResponse;
	}

	public DrugFormularyResponseModel populateInvalidResponse(MethodArgumentNotValidException ex,
			ContentCachingRequestWrapper requestWrapper) {
		List<String> errors = new ArrayList<>();
		DrugFormularyRequestModel requestModel = mapperService.mapDrugFormularyRequestModel(requestWrapper);
		DrugFormularyResponseModel invalidResponse = new DrugFormularyResponseModel();
		ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
		invalidResponse.setStatusCode(NOTVALID);
		invalidResponse.setStatusDescription(errors.toString().replace("[", "").replace("]", ""));
		addInTransactionLog(requestModel.getRequestId() != null ? requestModel.getRequestId() : null,
				getPayerIdFromPathVariable(requestWrapper), requestWrapper);
		return invalidResponse;
	}

	public DrugFormularyResponseModel populateInvalidResponse(HttpMessageNotReadableException ex,
			ContentCachingRequestWrapper requestWrapper) {
		String validationMsg = "Required request body is missing";
		DrugFormularyResponseModel invalidResponse = new DrugFormularyResponseModel();
		invalidResponse.setStatusCode(NOTVALID);
		String message = ex.getMessage();
		if (message != null && !message.isEmpty() && message.contains(validationMsg + ":")) {
			invalidResponse.setStatusDescription(validationMsg);
		}
		addInTransactionLog(null,getPayerIdFromPathVariable(requestWrapper),requestWrapper);
		return invalidResponse;
	}

	private void addInTransactionLog(String requestId, String payerId, ContentCachingRequestWrapper requestWrapper) {
		TransactionLog transactionLog = transactionLogService.addDataInTransactionLog(RequestType.DRUG_FORMULARY,
				requestId, payerId);
		if (transactionLog != null) {
			sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
		}
	}

	private String getPayerIdFromPathVariable(HttpServletRequest request) {
		Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		return (String) pathVariables.get("payerId");
	}
}
