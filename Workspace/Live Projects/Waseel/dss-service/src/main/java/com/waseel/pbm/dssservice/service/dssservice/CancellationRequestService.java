package com.waseel.pbm.dssservice.service.dssservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbm.dssservice.enums.RequestType;
import com.waseel.pbm.dssservice.model.CancelOverrideResponse;
import com.waseel.pbm.dssservice.model.CancellationOverrideRequest;
import com.waseel.pbm.dssservice.persist.mdss.TransactionLog;
import com.waseel.pbm.dssservice.service.managementservice.DssDataManipulationService;
import com.waseel.pbm.dssservice.service.managementservice.SessionService;
import com.waseel.pbm.dssservice.service.managementservice.TransactionLogService;

@Service
public class CancellationRequestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CancellationRequestService.class);

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private DssDataManipulationService dssDataManipulationService;

	@Autowired
	private SessionService sessionService;

	public CancelOverrideResponse manageCancellationRequest(CancellationOverrideRequest cancelRequest,
			ContentCachingRequestWrapper requestWrapper, RequestType requestType) {
		CancelOverrideResponse response = null;
		TransactionLog tranLog = transactionLogService.addTransactionLog(true, requestType, requestWrapper);
		// Need to set in session for avoid separate entry in transactionLog table if
		// here any failure occurs
		if (tranLog != null) {
			sessionService.setTransactionLogIdInSession(requestWrapper, tranLog.getTransactionLogId());
			response = dssDataManipulationService.validateCancellationRequest(cancelRequest);
			LOGGER.info("Data saved in TransactionLog table for Cancellation Request. TransactionLogId is "
					+ tranLog.getTransactionLogId());
			response.setTransactionLogId(tranLog.getTransactionLogId());
		}
		return response;
	}
}
