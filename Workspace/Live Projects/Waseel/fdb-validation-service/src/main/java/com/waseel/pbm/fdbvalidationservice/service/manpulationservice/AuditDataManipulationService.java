package com.waseel.pbm.fdbvalidationservice.service.manpulationservice;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.fdbvalidationservice.enums.ScreeningModules;
import com.waseel.pbm.fdbvalidationservice.model.DssRequest;
import com.waseel.pbm.fdbvalidationservice.model.DssResponse;
import com.waseel.pbm.fdbvalidationservice.persist.mdss.ScreeningModuleAuditTrail;
import com.waseel.pbm.fdbvalidationservice.persist.mongodb.FDBAuditTrail;
import com.waseel.pbm.fdbvalidationservice.repository.mdss.ScreeningModuleAuditTrailRepository;
import com.waseel.pbm.fdbvalidationservice.repository.mongodb.FDBAuditTrailRepository;

@Service
public class AuditDataManipulationService {

	@Autowired
	private FDBAuditTrailRepository fdbAuditTrailRepo;

	@Autowired
	ScreeningModuleAuditTrailRepository screeningModuleAuditTrailRepository;
	
	public FDBAuditTrail saveAllModulesMetaDataInMongoDb(DssRequest dssRequest, DssResponse dssResponse) {
		FDBAuditTrail audit = new FDBAuditTrail();
		if(dssRequest != null) {
			audit.setPayerId(dssRequest.getPayerId());
			audit.setRequestId(dssRequest.getRequestId());
			audit.setDssRequest(dssRequest);
		}
		audit.setSubmissionDateTime(new Date());
		audit.setDssResponse(dssResponse);
		return fdbAuditTrailRepo.save(audit);
	}
	
	public void saveAllModulesMetaDataInOracle(FDBAuditTrail fdbAuditTrail, Long transactionId,List<Integer> configuredModulesIds) {
		ScreeningModuleAuditTrail entity = new ScreeningModuleAuditTrail();
		if (fdbAuditTrail != null) {
			entity.setMongodbUniqueId(fdbAuditTrail.getDocumentId());
			entity.setPayerId(fdbAuditTrail.getPayerId());
			entity.setRequestId(fdbAuditTrail.getRequestId());
		}
		entity.setModuleId(combineModulesId(configuredModulesIds));
		entity.setTransactionLogId(transactionId);
		entity.setModuleType(ScreeningModules.FDB.name());
		screeningModuleAuditTrailRepository.save(entity);
	}
	
	private String combineModulesId(List<Integer> configuredModulesIds) {
		return configuredModulesIds.toString().replace("[","").replace("]","");
	}
}
