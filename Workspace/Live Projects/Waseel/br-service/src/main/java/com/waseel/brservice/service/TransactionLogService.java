package com.waseel.brservice.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.waseel.brservice.model.InvalidResponseModel;
import com.waseel.brservice.model.SensitiveDrugRequestModel;
import com.waseel.brservice.model.SensitiveDrugResponseModel;
import com.waseel.brservice.model.enums.Privileges;
import com.waseel.brservice.model.enums.RequestType;
import com.waseel.brservice.model.enums.TransactionStatusType;
import com.waseel.brservice.model.enums.URLName;
import com.waseel.brservice.persist.businessrules.TransactionLog;
import com.waseel.brservice.repository.businessrules.TransactionLogRepository;

import io.swagger.v3.oas.models.PathItem.HttpMethod;

@Service
public class TransactionLogService {
	private static final Logger log = LoggerFactory.getLogger(TransactionLogService.class);

	@Autowired
	private MapperService mapperService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	@Autowired
	private AuditLogService auditLogService;

	public void manageTransactionLogFromResponse(ContentCachingRequestWrapper requestWrapper,
			ContentCachingResponseWrapper responseWrapper, HttpServletRequest request) {
		try {
			if (requestWrapper.getRequestURI().contains(URLName.SENSITIVE_DRUG.value())
					&& requestWrapper.getMethod().equals(HttpMethod.POST.name())) {
				manageTransactionLogAndAuditTrail(requestWrapper, responseWrapper, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("TransactionLogService Response exception:-", e);
		}
	}

	private void manageTransactionLogAndAuditTrail(ContentCachingRequestWrapper requestWrapper,
			ContentCachingResponseWrapper responseWrapper, HttpServletRequest request) {
		SensitiveDrugResponseModel responseModel = mapperService.mapSensitiveDrugResponseModel(responseWrapper);
		InvalidResponseModel invalidResponseModel = mapperService.mapInvalidResponseModel(responseWrapper);
		String statusDesc = null;
		String status = null;
		String transactionLogId = sessionService.getTransactionLogIdFromSession(request);
		if (responseWrapper.getStatus() == HttpStatus.OK.value() && responseModel != null) {
			statusDesc = getStatusDescFromList(responseModel);
			status = "SUCCESS";
		} else {
			if (invalidResponseModel != null) {
				statusDesc = StringUtils.strip(invalidResponseModel.getErrorDescription().toString(), "[]");
				status = invalidResponseModel.getErrorCode();
			}
		}
		SensitiveDrugRequestModel requestModel = mapperService.mapSensitiveDrugRequestModel(requestWrapper);
		TransactionLog updatedTranLog = updateDataInTransactionLog(Long.parseLong(transactionLogId), status, statusDesc,
				String.valueOf(responseWrapper.getStatus()), requestWrapper.getRequestURL().toString());
		if (updatedTranLog != null) {
			sessionService.removeTransactionLogIdFromSession(request);
			auditLogService.saveSensitiveDrugAuditLogInMongoDb(requestModel, responseModel,
					updatedTranLog.getTransactionLogId(), invalidResponseModel);
		}
	}

	private String getStatusDescFromList(SensitiveDrugResponseModel responseModelList) {
		List<String> statusDescList = new ArrayList<>();
		responseModelList.getDrugList().forEach(res -> {
			if (!StringUtils.isBlank(res.getStatusDescription())) {
				statusDescList.add(res.getStatusDescription());
			}
		});
		return statusDescList.toString().replace("[", "").replace("]", "");
	}

	public TransactionLog updateDataInTransactionLog(Long transactionLogId, String status, String statusDescription,
			String httpStatus, String apiUrl) {
		TransactionLog updatedTranLog = null;
		try {
			if (transactionLogId != null) {
				Optional<TransactionLog> transactionLog = transactionLogRepository
						.findByTransactionLogId(transactionLogId);
				if (transactionLog.isPresent()) {
					TransactionLog tLog = transactionLog.get();
					tLog.setStatus(status);
					tLog.setStatusDescription(statusDescription);
					tLog.setHttpStatus(httpStatus);
					tLog.setHttpStatusDescription(statusDescription);
					tLog.setSendingResponseDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					tLog.setTransactionStatus(TransactionStatusType.SENT.value());
					tLog.setTransactionURL(apiUrl);
					updatedTranLog = transactionLogRepository.save(tLog);
					log.info("Br-service updated for TransactionLogId[{}]", updatedTranLog.getTransactionLogId());
					return updatedTranLog;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Update Transaction log Exception: ", e);
		}
		return updatedTranLog;
	}
	
	public TransactionLog addDataInTransactionLog(RequestType requestType, String requestId,String payerId,String providerId) {
		try {
			TransactionLog tLog = new TransactionLog();
			tLog.setTransactionId(getTransactionId(requestType));
			if (requestId.trim().getBytes().length <= 100)
				tLog.setRequestId(requestId);
			tLog.setPayerId(payerId);
			tLog.setProviderId(providerId);
			tLog.setTransactionType(RequestType.SENSITIVE_DRUG.value());
			tLog.setTransactionStatus(TransactionStatusType.RECEIVED.value());
			tLog.setReceivingRequestDateTime((new Timestamp(Calendar.getInstance().getTimeInMillis())));
			tLog.setSendingResponseDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			return transactionLogRepository.save(tLog);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Add Transaction log Exception: ", e);
		}
		return null;
	}
	
	private Double getTransactionId(RequestType type) {
		switch (type) {
		case SENSITIVE_DRUG:
			return Privileges.SENSITIVE_DRUG.value();
		default:
			return null;
		}
	}
}
