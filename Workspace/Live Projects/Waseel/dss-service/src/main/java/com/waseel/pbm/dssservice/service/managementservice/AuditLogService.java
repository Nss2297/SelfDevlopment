package com.waseel.pbm.dssservice.service.managementservice;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.waseel.pbm.dssservice.enums.RequestType;
import com.waseel.pbm.dssservice.model.CancelOverrideResponse;
import com.waseel.pbm.dssservice.model.CancellationOverrideRequest;
import com.waseel.pbm.dssservice.model.DssRequest;
import com.waseel.pbm.dssservice.model.DssResponse;
import com.waseel.pbm.dssservice.persist.mongodb.DSSAuditTrail;

@Service
public class AuditLogService {
	
	public DSSAuditTrail populateAuditDataForNewFollowUp(DssRequest dssRequest,DssResponse dssResponse,RequestType requestType,Long transactionLogId) {
		DSSAuditTrail dss = new DSSAuditTrail();
		if(dssRequest != null) {
			dss.setRequestId(dssRequest.getRequestId());
		}
		dss.setDssRequest(dssRequest);
		if(transactionLogId != null) {
			dss.setDssTransactionLogId(transactionLogId);
		}
		dss.setDssResponse(dssResponse);
		populateCommonInfo(dss, requestType);
		return dss;
	}
	
	public DSSAuditTrail populateAuditDataForCancellationInvalidResponse(CancellationOverrideRequest cancelRequest,DssResponse dssResponse,RequestType requestType,Long transactionLogId) {
		DSSAuditTrail dss = new DSSAuditTrail();
		if(cancelRequest != null) {
			dss.setRequestId(cancelRequest.getRequestId());
		}
		dss.setCancellationOverrideRequest(cancelRequest);
		if(transactionLogId != null) {
			dss.setDssTransactionLogId(transactionLogId);
		}
		dss.setDssResponse(dssResponse);
		populateCommonInfo(dss, requestType);
		return dss;
	}
	
	public DSSAuditTrail populateAuditDataForCancellation(CancellationOverrideRequest cancelRequest,CancelOverrideResponse cancelResponse,RequestType requestType,Long transactionLogId) {
		DSSAuditTrail dss = new DSSAuditTrail();
		dss.setCancellationOverrideRequest(cancelRequest);
		if(transactionLogId != null) {
			dss.setDssTransactionLogId(transactionLogId);
		}
		if(cancelRequest != null)
			dss.setRequestId(cancelRequest.getRequestId());
		populateCommonInfo(dss, requestType);
		dss.setCancellationOverrideResponse(cancelResponse);
		return dss;
	}
	
	public void populateCommonInfo(DSSAuditTrail dss,RequestType requestType) {
		dss.setDateTime(new Date());
		dss.setRequestType(requestType.name());
	}
}
