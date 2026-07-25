package com.waseel.drugformulary.service;

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

import com.waseel.drugformulary.model.DrugFormularyRequestModel;
import com.waseel.drugformulary.model.DrugFormularyResponseModel;
import com.waseel.drugformulary.model.enums.Privileges;
import com.waseel.drugformulary.model.enums.RequestType;
import com.waseel.drugformulary.model.enums.TransactionStatusType;
import com.waseel.drugformulary.persist.businessrules.TransactionLog;
import com.waseel.drugformulary.repository.businessrules.TransactionLogRepository;

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
			if (requestWrapper.getRequestURI().contains("/formulary")
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
		DrugFormularyResponseModel responseModel = null;
		List<DrugFormularyResponseModel> responseModelList = null;
		String statusDesc;
		String status;
		String transactionLogId = sessionService.getTransactionLogIdFromSession(request);
		if (responseWrapper.getStatus() == HttpStatus.OK.value()) {
			responseModelList = mapperService.mapDrugFormularyResponseModelList(responseWrapper);
			statusDesc = getStatusDescFromList(responseModelList);
			status = "SUCCESS";
		} else {
			responseModel = mapperService.mapDrugFormularyResponseModel(responseWrapper);
			statusDesc = responseModel.getStatusDescription();
			status = responseModel.getStatusCode();
		}
		DrugFormularyRequestModel requestModel = mapperService.mapDrugFormularyRequestModel(requestWrapper);
		TransactionLog updatedTranLog = updateDataInTransactionLog(Long.parseLong(transactionLogId), status, statusDesc,
				String.valueOf(responseWrapper.getStatus()));
		if (updatedTranLog != null) {
			sessionService.removeTransactionLogIdFromSession(request);
			auditLogService.saveAuditLogInMongoDb(requestModel, responseModel, updatedTranLog.getTransactionLogId(),
					responseModelList);
		}
	}

	private String getStatusDescFromList(List<DrugFormularyResponseModel> responseModelList) {
		List<String> statusDescList = new ArrayList<>();
		responseModelList.stream().forEach(res -> {
			if (!StringUtils.isBlank(res.getStatusDescription())) {
				statusDescList.add(res.getStatusDescription());
			}
		});
		return statusDescList.toString().replace("[", "").replace("]", "");
	}

	public TransactionLog addDataInTransactionLog(RequestType requestType, String requestId, String payerId) {
		try {
			TransactionLog tLog = new TransactionLog();
			tLog.setTransactionId(getTransactionId(requestType));
			tLog.setRequestId(requestId);
			tLog.setTransactionType(RequestType.DRUG_FORMULARY.value());
			tLog.setPayerId(payerId);
			tLog.setTransactionStatus(TransactionStatusType.RECEIVED.value());
			tLog.setReceivingRequestDateTime((new Timestamp(Calendar.getInstance().getTimeInMillis())));
			tLog.setSendingResponseDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			return transactionLogRepository.save(tLog);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Add Transaction log Exception:-", e);
		}
		return null;
	}

	public TransactionLog updateDataInTransactionLog(Long transactionLogId, String status, String statusDescription,
			String httpStatus) {
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
					updatedTranLog = transactionLogRepository.save(tLog);
					log.info("Drug Formulary updated for TransactionLogId[{}]", updatedTranLog.getTransactionLogId());
					return updatedTranLog;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Update Transaction log Exception:-", e);
		}
		return updatedTranLog;
	}

	private Double getTransactionId(RequestType type) {
		switch (type) {
		case DRUG_FORMULARY:
			return Privileges.DRUG_FORMULARY.value();
		default:
			return null;
		}
	}

}
