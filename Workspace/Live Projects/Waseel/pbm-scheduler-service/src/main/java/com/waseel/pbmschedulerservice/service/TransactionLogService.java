package com.waseel.pbmschedulerservice.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbmschedulerservice.model.enums.Privileges;
import com.waseel.pbmschedulerservice.model.enums.RequestType;
import com.waseel.pbmschedulerservice.model.enums.TransactionStatusType;
import com.waseel.pbmschedulerservice.persist.businessrules.TransactionLog;
import com.waseel.pbmschedulerservice.repository.businessrules.TransactionLogRepository;

@Service
public class TransactionLogService {

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	public TransactionLog addDataInTransactionLog(String payerId, RequestType requestType) {
		try {
			TransactionLog transactionLog = new TransactionLog();
			transactionLog.setTransactionId(getTransactionId(requestType));
			transactionLog.setTransactionType(requestType.value());
			transactionLog.setPayerId(payerId);
			transactionLog.setTransactionStatus(TransactionStatusType.RECEIVED.value());
			transactionLog.setReceivingRequestDateTime((new Timestamp(Calendar.getInstance().getTimeInMillis())));
			transactionLog.setSendingResponseDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			return transactionLogRepository.save(transactionLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public TransactionLog updateDataInTransactionLog(Long transactionLogId, String status, String statusDescription,
			String httpStatus, String httpStatusDescription, String transactionURL) {
		try {
			if (transactionLogId != null) {
				Optional<TransactionLog> transactionLog = transactionLogRepository
						.findByTransactionLogId(transactionLogId);
				if (transactionLog.isPresent()) {
					TransactionLog tLog = transactionLog.get();
					tLog.setStatus(status);
					tLog.setStatusDescription(statusDescription);
					tLog.setHttpStatus(httpStatus);
					tLog.setHttpStatusDescription(httpStatusDescription);
					tLog.setTransactionUrl(transactionURL);
					tLog.setSendingResponseDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					tLog.setTransactionStatus(TransactionStatusType.SENT.value());
					return transactionLogRepository.save(tLog);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Double getTransactionId(RequestType type) {
		switch (type) {
		case POLICY_DETAILS:
			return Privileges.POLICY_DETAILS.value();
		case MEMBER_DETAILS:
			return Privileges.MEMBER_DETAILS.value();
		case PROVIDER_NETWORKS:
			return Privileges.PROVIDER_NETWORKS.value();
		default:
			return null;
		}
	}
}
