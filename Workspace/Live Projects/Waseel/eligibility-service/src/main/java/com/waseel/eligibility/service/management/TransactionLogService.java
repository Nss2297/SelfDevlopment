package com.waseel.eligibility.service.management;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.waseel.eligibility.enums.BusinessRulesPrivilage;
import com.waseel.eligibility.enums.BusinessRulesType;
import com.waseel.eligibility.enums.RequestParameters;
import com.waseel.eligibility.enums.TransactionStatusType;
import com.waseel.eligibility.enums.TransactionType;
import com.waseel.eligibility.logging.AuditLogService;
import com.waseel.eligibility.model.EligibilityRequestModel;
import com.waseel.eligibility.model.EligibilityResponseModel;
import com.waseel.eligibility.persist.businessrules.TransactionLog;
import com.waseel.eligibility.repository.businessrules.TransactionLogRepository;
import com.waseel.eligibility.service.mapper.MapperService;

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
	AuditLogService auditLogService;

	public void manageTransactionLogFromResponse(HttpServletRequest request,
			ContentCachingResponseWrapper responseWrapper, ContentCachingRequestWrapper requestWrapper) {
		try {
			if (requestWrapper.getRequestURI().endsWith(BusinessRulesType.ELIGIBILITY_CHECK.value())) {
				manageTransactionLogForEligibilityCheck(responseWrapper, request);
			}
			log.info("Remove transactionLog id from session.");
		} catch (Exception e) {
			log.error("Response exception:-", e);
		}
	}

	private void manageTransactionLogForEligibilityCheck(ContentCachingResponseWrapper responseWrapper,
			HttpServletRequest request) {
		EligibilityResponseModel eligibilityResponseModel = mapperService.mapEligibilityResponse(responseWrapper);
		String transactionLogId = sessionService.getTransactionLogIdFromSession(request);
		if (null != eligibilityResponseModel && StringUtils.isNotBlank(transactionLogId)) {
			updateTransactionLogForEligibilityCheck(eligibilityResponseModel, Long.parseLong(transactionLogId), request,
					responseWrapper);
		}
	}

	private void updateTransactionLogForEligibilityCheck(EligibilityResponseModel eligibilityResponseModel,
			long transactionLogId, HttpServletRequest request, ContentCachingResponseWrapper responseWrapper) {
		try {
			String statusDescription = eligibilityResponseModel.getStatusDescription();
			String statusCode = String.valueOf(eligibilityResponseModel.getHttpStatusCode());
			if (StringUtils.isBlank(statusCode)) {
				statusCode = String.valueOf(responseWrapper.getStatus());
				eligibilityResponseModel.setHttpStatusCode(responseWrapper.getStatus());
			}
			TransactionLog updateTransactionLog = updateTransactionLog(transactionLogId, statusCode,
					eligibilityResponseModel.getStatus(), request, statusDescription);
			if (null != updateTransactionLog) {
				EligibilityRequestModel eligibilityRequestModel = new EligibilityRequestModel(
						request.getParameter(RequestParameters.PAYER_ID.value()),
						request.getParameter(RequestParameters.PROVIDER_ID.value()),
						request.getParameter(RequestParameters.REQUEST_ID.value()));
				auditLogService.saveAuditLogInMongoDb(eligibilityRequestModel, eligibilityResponseModel,
						TransactionType.ELIGIBILITY, "", Long.valueOf("0"));
			}
		} catch (Exception e) {
			log.error("Transaction exception:-", e);
		}

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
				updatedTranLog.setStatus(status);
				updatedTranLog.setStatusDescription(statusDescription);
				updatedTranLog.setSendingResponseDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				updatedTranLog.setHttpStatus(httpStatus);
				updatedTranLog.setHttpStatusDescription(statusDescription);
				updatedTranLog.setTransactionStatus(TransactionStatusType.SENT.value());
				updatedTranLog = transactionLogRepository.save(updatedTranLog);
				sessionService.removeTransactionLogIdFromSession(request);
				log.info("Business Rules: {} check updated for TransactionLogId[{}]",
						updatedTranLog.getTransactionType(), updatedTranLog.getTransactionLogId());
				return updatedTranLog;
			}
		} catch (Exception e) {
			log.error("Transaction log Exception:-", e);
		}
		return updatedTranLog;
	}

	public TransactionLog addTransaction(EligibilityRequestModel requestModel) {
		try {
			TransactionLog transactionLog = populateCommonTransactionLogData(requestModel);
			return transactionLogRepository.save(transactionLog);
		} catch (Exception e) {
			log.error("Exception:-", e);
		}
		return null;
	}

	private TransactionLog populateCommonTransactionLogData(EligibilityRequestModel requestModel) {
		Timestamp timestamp = (new Timestamp(Calendar.getInstance().getTimeInMillis()));
		return new TransactionLog(Double.valueOf(BusinessRulesPrivilage.ELIGIBILITY_PRIVILAGE.value()),
				requestModel.getPayerId(), requestModel.getProviderId(), timestamp, timestamp,
				TransactionType.ELIGIBILITY.value(), requestModel.getRequestId(),
				TransactionStatusType.RECEIVED.value());
	}
}