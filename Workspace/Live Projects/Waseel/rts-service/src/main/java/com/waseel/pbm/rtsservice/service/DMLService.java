package com.waseel.pbm.rtsservice.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbm.rtsservice.model.RTSRequest;
import com.waseel.pbm.rtsservice.model.RTSResponse;
import com.waseel.pbm.rtsservice.persist.mongodb.RTSAuditTrail;

@Service
public class DMLService {
	
	@Autowired
	private AuditDmlService auditDmlService;
	
	@Autowired
	private MapperService mapperService;
	
	public void saveAuditLogInMongoDbAndOracle(RTSRequest rtsRequest, RTSResponse rtsResponse) {
		CompletableFuture.runAsync(() -> {
			//Used to save data in background -- CompletableFuture.runAsync
			RTSAuditTrail rtsAuditTrail = auditDmlService.saveAllModulesMetaDataInMongoDb(rtsRequest, rtsResponse);
			auditDmlService.saveAllModulesMetaDataInOracle(rtsAuditTrail, rtsRequest.getTransactionLogId(),rtsRequest.getPayerId());
		});
	}
	
	public void saveInvalidRequestsToMongoDb(ContentCachingRequestWrapper request, RTSResponse rtsResponse) {
		CompletableFuture.runAsync(() -> {
			//Used to save data in background -- CompletableFuture.runAsync
			auditDmlService.saveAllModulesMetaDataInMongoDb(mapperService.mapRTSRequest(request), rtsResponse);
		});
	}
}
