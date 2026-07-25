package com.waseel.pbm.dssservice.service.managementservice;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.dssservice.enums.RequestType;
import com.waseel.pbm.dssservice.model.CancelOverrideResponse;
import com.waseel.pbm.dssservice.model.CancellationOverrideRequest;
import com.waseel.pbm.dssservice.model.DssRequest;
import com.waseel.pbm.dssservice.model.DssResponse;
import com.waseel.pbm.dssservice.repository.mongodb.DSSAuditTrailRepository;

@Service
public class DataManipulationAuditService {

	@Autowired
	private DSSAuditTrailRepository dssAuditTrailRepository;
	
	@Autowired
	private AuditLogService auditLogService;
	
	public void saveAuditLogInMongoDbForNewFollowup(DssRequest dssRequest,DssResponse dssResponse,
			RequestType requestType,Long transactionLogId) {
		try {
			CompletableFuture.runAsync(() ->
			// Used to save data in background -- CompletableFuture.runAsync
			dssAuditTrailRepository.save(auditLogService.populateAuditDataForNewFollowUp(
					dssRequest, dssResponse,requestType, transactionLogId)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveAuditLogInMongoDbForCancellationOverrideInvalidRes(CancellationOverrideRequest cancelRequest,DssResponse dssResponse,RequestType requestType,Long transactionLogId) {
		try {
			CompletableFuture.runAsync(() ->
			// Used to save data in background -- CompletableFuture.runAsync
			dssAuditTrailRepository.save(auditLogService.populateAuditDataForCancellationInvalidResponse(cancelRequest,
					dssResponse, requestType, transactionLogId)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveAuditLogInMongoDbForCancellationOverride(CancellationOverrideRequest cancelRequest,CancelOverrideResponse cancelResponse,RequestType requestType,Long transactionLogId) {
		try {
			CompletableFuture.runAsync(() ->
			// Used to save data in background -- CompletableFuture.runAsync
			dssAuditTrailRepository.save(auditLogService.populateAuditDataForCancellation(cancelRequest, cancelResponse,
					requestType, transactionLogId)));	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
