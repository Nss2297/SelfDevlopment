package com.waseel.policy.service.management;

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

import com.waseel.policy.enums.TransactionStatusType;
import com.waseel.policy.model.PolicyResponseModel;
import com.waseel.policy.persist.businessrules.TransactionLog;
import com.waseel.policy.repository.businessrules.TransactionLogRepository;
import com.waseel.policy.service.mapper.MapperService;

@Service
public class TransactionLogService {

	private static final Logger log = LoggerFactory.getLogger(TransactionLogService.class);

	@Autowired
	private MapperService mapperService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	public void manageTransactionLogFromResponse(HttpServletRequest request,
			ContentCachingResponseWrapper responseWrapper, ContentCachingRequestWrapper requestWrapper,
			Timestamp sendingTime) {
		try {
			PolicyResponseModel policyResponseModel = mapperService.mapPolicyResponseModel(responseWrapper);
			String transactionLogId = sessionService.getTransactionLogIdFromSession(request);
			if (null != policyResponseModel && StringUtils.isNotBlank(transactionLogId)) {
				String status = policyResponseModel.getStatus();
				String statusDescription = policyResponseModel.getStatusDescription();
				String httpStatus = policyResponseModel.getHttpStatusCode();
				String httpStatusDescription = policyResponseModel.getHttpStatusDescription();
				updateTransactionLog(Long.parseLong(transactionLogId), status, statusDescription, httpStatus,
						httpStatusDescription, request);
			}
			log.info("Remove transactionLog id from session.");
		} catch (Exception e) {
			log.error("Response exception:-", e);
		}
	}

	private void updateTransactionLog(long transactionLogId, String status, String statusDescription, String httpStatus,
			String httpStatusDescription, HttpServletRequest httpServletRequest) {
		TransactionLog updatedTranLog = null;
		try {
			Optional<TransactionLog> transactionLogOpt = transactionLogRepository.findById(transactionLogId);
			if (transactionLogOpt.isPresent()) {
				updatedTranLog = transactionLogOpt.get();
			} else {
				updatedTranLog = new TransactionLog();
			}
			updatedTranLog.setStatus(status);
			updatedTranLog.setStatusDescription(statusDescription);
			updatedTranLog.setSendingResponseDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			updatedTranLog.setHttpStatus(httpStatus);
			updatedTranLog.setHttpStatusDescription(httpStatusDescription);
			updatedTranLog.setTransactionStatus(TransactionStatusType.SENT.value());
			transactionLogRepository.save(updatedTranLog);
			sessionService.removeTransactionLogIdFromSession(httpServletRequest);
			log.info("Policy consumption Transaction updated for TransactionLogId[{}]",
					updatedTranLog.getTransactionLogId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Transaction log Exception:-", e);
		}
	}
}
