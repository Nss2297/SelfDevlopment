package com.waseel.pbm.dssservice.service.dssservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbm.dssservice.enums.RequestType;
import com.waseel.pbm.dssservice.model.DssRequest;
import com.waseel.pbm.dssservice.model.DssResponse;
import com.waseel.pbm.dssservice.persist.mdss.TransactionLog;
import com.waseel.pbm.dssservice.service.managementservice.DssDataManipulationService;
import com.waseel.pbm.dssservice.service.managementservice.SessionService;
import com.waseel.pbm.dssservice.service.managementservice.TransactionLogService;
import com.waseel.pbm.dssservice.service.validationservice.DssDrugValidationService;


@Service
public class FollowupRequestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FollowupRequestService.class);

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private DssDataManipulationService dssDataManipulationService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private DssDrugValidationService drugValidationService;

	public DssResponse manageFollowUpDssRequest(DssRequest dssRequest, ContentCachingRequestWrapper requestWrapper,
			RequestType requestType) {
		DssResponse dssResponse = null;
		TransactionLog tranLog = transactionLogService.addTransactionLog(true, requestType, requestWrapper);
		if (tranLog != null) {
			/*
			 * Need to set in session for avoid separate entry in transactionLog table if
			 * here any failure occurs
			 */
			sessionService.setTransactionLogIdInSession(requestWrapper, tranLog.getTransactionLogId());
			if (dssDataManipulationService.getRequestInfo(dssRequest.getRequestId()) != null) {
				dssResponse = drugValidationService.validate(dssRequest, tranLog.getTransactionLogId());
			}
			if (dssResponse != null && dssResponse.getHttpStatusCode() == 200) {
				dssDataManipulationService.updateDssRequest(dssRequest, requestType, dssResponse.getResults());
			}
			if (dssResponse != null) {
				LOGGER.info("Data saved in TransactionLog table for FollowUp Request. TransactionLogId is "
						+ tranLog.getTransactionLogId());
				dssResponse.setTransactionLogId(tranLog.getTransactionLogId());
			}
		}
		return dssResponse;
	}
}
