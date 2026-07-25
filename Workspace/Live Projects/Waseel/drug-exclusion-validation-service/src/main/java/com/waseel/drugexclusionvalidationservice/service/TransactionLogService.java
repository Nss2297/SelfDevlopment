package com.waseel.drugexclusionvalidationservice.service;

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

import com.waseel.drugexclusionvalidationservice.model.DrugExclusionRequestModel;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionResponseModel;
import com.waseel.drugexclusionvalidationservice.model.Rejections;
import com.waseel.drugexclusionvalidationservice.model.enums.Privileges;
import com.waseel.drugexclusionvalidationservice.model.enums.RequestType;
import com.waseel.drugexclusionvalidationservice.model.enums.TransactionStatusType;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.TransactionLog;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.TransactionLogRepository;

@Service
public class TransactionLogService {

	private static final Logger log = LoggerFactory.getLogger(TransactionLogService.class);

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private MapperService mapperService;

	@Autowired
	private AuditLogService auditLogService;

	public void manageTransactionLogFromResponse(ContentCachingRequestWrapper requestWrapper,
			ContentCachingResponseWrapper responseWrapper, HttpServletRequest request) {
		try {
			if (requestWrapper.getRequestURI().contains("/drug-exclusion")) {
				manageTransactionLogAndAuditTrail(requestWrapper, responseWrapper, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("TransactionLogService Response exception: ", e);
		}
	}

	private void manageTransactionLogAndAuditTrail(ContentCachingRequestWrapper requestWrapper,
			ContentCachingResponseWrapper responseWrapper, HttpServletRequest request) {
		DrugExclusionResponseModel responseModel = mapperService.mapSpecialityExclusionResponseModel(responseWrapper);
		String statusDesc;
		String status;
		String transactionLogId = sessionService.getTransactionLogIdFromSession(request);
		if (responseWrapper.getStatus() == HttpStatus.OK.value()) {
			statusDesc = getStatusDescFromList(responseModel);
			status = "SUCCESS";
		} else {
			statusDesc = responseModel.getErrorDescription();
			status = responseModel.getErrorCode();
		}
		DrugExclusionRequestModel requestModel = mapperService.mapSpecialityExclusionRequestModel(requestWrapper);
		TransactionLog updatedTranLog = updateDataInTransactionLog(Long.parseLong(transactionLogId), status, statusDesc,
				String.valueOf(responseWrapper.getStatus()), requestWrapper.getRequestURL().toString());
		if (updatedTranLog != null) {
			sessionService.removeTransactionLogIdFromSession(request);
			auditLogService.saveAuditLogInMongoDb(requestModel, responseModel, updatedTranLog.getTransactionLogId());
		}
	}

	private String getStatusDescFromList(DrugExclusionResponseModel responseModel) {
		List<String> statusDescList = new ArrayList<>();
		if (responseModel != null && responseModel.getDrugList() != null) {
			responseModel.getDrugList().forEach(drug -> {
				if (drug.getRejectionsList() != null) {
					drug.getRejectionsList().stream().map(Rejections::getStatusDescription)
							.filter(statusDesc -> !StringUtils.isBlank(statusDesc)).forEach(statusDescList::add);
				}
			});
		}
		return statusDescList.toString().replace("[", "").replace("]", "");
	}

	public TransactionLog addDataInTransactionLog(RequestType requestType, String requestId,String payerId,String providerId) {
		try {
			TransactionLog tLog = new TransactionLog();
			tLog.setTransactionId(getTransactionId(requestType));
			if (requestId.trim().getBytes().length <= 100)
				tLog.setRequestId(requestId);
			tLog.setPayerId(payerId);
			tLog.setProviderId(providerId);
			tLog.setTransactionType(RequestType.DRUG_EXCLUSION.value());
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
					log.info("Drug exclusion validation service updated for TransactionLogId[{}]",
							updatedTranLog.getTransactionLogId());
					return updatedTranLog;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Update Transaction log Exception: ", e);
		}
		return updatedTranLog;
	}

	private Double getTransactionId(RequestType type) {
		switch (type) {
		case DRUG_EXCLUSION:
			return Privileges.EXCLUSION_VALIDATION.value();
		default:
			return null;
		}
	}
}
