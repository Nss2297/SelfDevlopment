package com.waseel.pbm.idfvalidationservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbm.idfvalidationservice.enums.ScreeningModules;
import com.waseel.pbm.idfvalidationservice.model.DssRequest;
import com.waseel.pbm.idfvalidationservice.model.DssResponse;
import com.waseel.pbm.idfvalidationservice.persist.ScreeningModuleAuditTrail;
import com.waseel.pbm.idfvalidationservice.persist.mongodb.IDFAuditTrail;
import com.waseel.pbm.idfvalidationservice.repository.PayerModuleConfigurationRepository;
import com.waseel.pbm.idfvalidationservice.repository.ScreeningModuleAuditTrailRepository;
import com.waseel.pbm.idfvalidationservice.repository.mongodb.IDFAuditTrailRepository;

@Service
public class AuditService {
	
	@Autowired
	private IDFAuditTrailRepository idfAuditTrailRepo;
	@Autowired
	ScreeningModuleAuditTrailRepository screeningModuleAuditTrailRepository;
	
	@Autowired
	private MapperService mapperService; 
	
	@Autowired
	private PayerModuleConfigurationRepository modulesConfigurationRepo;
	
	public void saveAuditLogInMongoDbAndOracle(DssRequest dssRequest, DssResponse dssResponse) {
		CompletableFuture.runAsync(() -> {
			IDFAuditTrail idfAuditTrail = saveAllModulesMetaDataInMongoDb(dssRequest, dssResponse);
			saveAllModulesMetaDataInOracle(idfAuditTrail, dssRequest.getTransactionLogId());
		});
	}

	public void saveInvalidRequestsToMongoDb( ContentCachingRequestWrapper request, DssResponse dssResponse) {
		CompletableFuture.runAsync(() -> 
			saveAllModulesMetaDataInMongoDb(mapperService.mapRTSRequest(request), dssResponse));
	}
	
	private IDFAuditTrail saveAllModulesMetaDataInMongoDb(DssRequest dssRequest, DssResponse dssResponse) {
		IDFAuditTrail audit = new IDFAuditTrail();
		if(dssRequest != null) {
			audit.setPayerId(dssRequest.getPayerId());
			audit.setRequestId(dssRequest.getRequestId());
			audit.setDssRequest(dssRequest);
		}
		audit.setSubmissionDateTime(new Date());
		audit.setDssResponse(dssResponse);
		return idfAuditTrailRepo.save(audit);
	}

	private void saveAllModulesMetaDataInOracle(IDFAuditTrail idfAuditTrail, Long transactionLogId) {
		ScreeningModuleAuditTrail entity = new ScreeningModuleAuditTrail();
		if (idfAuditTrail != null) {
			entity.setMongodbUniqueId(idfAuditTrail.getDocumentId());
			entity.setPayerId(idfAuditTrail.getPayerId());
			entity.setRequestId(idfAuditTrail.getRequestId());
			entity.setModuleId(combineModulesId(idfAuditTrail.getPayerId()));
		}
		entity.setTransactionLogId(transactionLogId);
		entity.setModuleType(ScreeningModules.IDF.name());
		screeningModuleAuditTrailRepository.save(entity);
	}

	private String combineModulesId(String payerId) {
		List<Integer> enabledConfiguredModulesIds = new ArrayList<>();
		List<Integer> configuredModulesIds = modulesConfigurationRepo.findByPayerIdAndIsEnabled(payerId);

		if (configuredModulesIds.contains(ScreeningModules.IDF_DRUG_TO_DISEASE_INTERACTION.value()))
			enabledConfiguredModulesIds.add(ScreeningModules.IDF_DRUG_TO_DISEASE_INTERACTION.value());

		if (configuredModulesIds.contains(ScreeningModules.IDF_DRUG_TO_GENDER_INTERACTION.value()))
			enabledConfiguredModulesIds.add(ScreeningModules.IDF_DRUG_TO_GENDER_INTERACTION.value());

		if (configuredModulesIds.contains(ScreeningModules.IDF_DRUG_TO_AGE_INTERACTION.value()))
			enabledConfiguredModulesIds.add(ScreeningModules.IDF_DRUG_TO_AGE_INTERACTION.value());

		if (configuredModulesIds.contains(ScreeningModules.IDF_QUANTITY_LIMIT_CHECK.value()))
			enabledConfiguredModulesIds.add(ScreeningModules.IDF_QUANTITY_LIMIT_CHECK.value());

		if (configuredModulesIds.contains(ScreeningModules.IDF_CONCURRENT_MEDICATION.value()))
			enabledConfiguredModulesIds.add(ScreeningModules.IDF_CONCURRENT_MEDICATION.value());
		
		return enabledConfiguredModulesIds.toString().replace("[", "").replace("]", "");
	}

}
