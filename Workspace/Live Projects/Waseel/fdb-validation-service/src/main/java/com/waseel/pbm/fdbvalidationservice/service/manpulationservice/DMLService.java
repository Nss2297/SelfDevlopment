package com.waseel.pbm.fdbvalidationservice.service.manpulationservice;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.pbm.fdbvalidationservice.model.DssRequest;
import com.waseel.pbm.fdbvalidationservice.model.DssResponse;
import com.waseel.pbm.fdbvalidationservice.persist.mongodb.FDBAuditTrail;

@Service
public class DMLService {

	@Autowired
	AuditDataManipulationService dataManipulationAuditService;

	public void saveAuditLogInMongoDbAndOracle(DssRequest dssRequest, DssResponse dssResponse,
			List<Integer> configuredModulesIds) {
		CompletableFuture.runAsync(() -> {
			//Used to save data in background -- CompletableFuture.runAsync
			FDBAuditTrail fdbAuditTrail = dataManipulationAuditService.saveAllModulesMetaDataInMongoDb(dssRequest, dssResponse);
			dataManipulationAuditService.saveAllModulesMetaDataInOracle(fdbAuditTrail, 
					dssRequest.getTransactionLogId(),configuredModulesIds);
		});
	}
	
	public void saveInvalidRequestsToMongoDb(ContentCachingRequestWrapper request, DssResponse dssResponse) {
		CompletableFuture.runAsync(() -> {
			ObjectMapper mapper = new ObjectMapper();
			DssRequest dssReq = null;
			try {
				dssReq = mapper.readValue(new String(request.getContentAsByteArray()), DssRequest.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			dataManipulationAuditService.saveAllModulesMetaDataInMongoDb(dssReq, dssResponse);
		});
	}
}