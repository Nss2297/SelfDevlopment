package com.waseel.pbm.rtsservice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.rtsservice.enums.ScreeningModules;
import com.waseel.pbm.rtsservice.model.RTSRequest;
import com.waseel.pbm.rtsservice.model.RTSResponse;
import com.waseel.pbm.rtsservice.persist.mdss.PayerModuleConfiguration;
import com.waseel.pbm.rtsservice.persist.mdss.ScreeningModuleAuditTrail;
import com.waseel.pbm.rtsservice.persist.mongodb.RTSAuditTrail;
import com.waseel.pbm.rtsservice.repository.mdss.PayerModuleConfigurationRepository;
import com.waseel.pbm.rtsservice.repository.mdss.ScreeningModuleAuditTrailRepository;
import com.waseel.pbm.rtsservice.repository.mongodb.RTSAuditTrailRepository;

@Service
public class AuditDmlService {
	
	@Autowired
	private RTSAuditTrailRepository rtsAuditTrailRepository;
	
	@Autowired
	private ScreeningModuleAuditTrailRepository screeningModuleAuditTrailRepository;
	
	@Autowired
	private PayerModuleConfigurationRepository modulesConfigurationRepo;
	
	public RTSAuditTrail saveAllModulesMetaDataInMongoDb(RTSRequest rtsRequest, RTSResponse rtsResponse) {
		RTSAuditTrail audit = new RTSAuditTrail();
		if(rtsRequest != null) {
			audit.setPayerId(rtsRequest.getPayerId());
			audit.setRequestId(rtsRequest.getRequestId());
			audit.setRtsRequest(rtsRequest);
		}
		audit.setSubmissionDateTime(new Date());
		audit.setRtsResponse(rtsResponse);
		return rtsAuditTrailRepository.save(audit);
	}
	
	public void saveAllModulesMetaDataInOracle(RTSAuditTrail rtsAuditTrail, Long transactionId, String payerId) {
		ScreeningModuleAuditTrail entity = new ScreeningModuleAuditTrail();
		if (rtsAuditTrail != null) {
			entity.setMongodbUniqueId(rtsAuditTrail.getDocumentId());
			entity.setPayerId(rtsAuditTrail.getPayerId());
			entity.setRequestId(rtsAuditTrail.getRequestId());
		}
		entity.setModuleId(combineModulesId(payerId));
		entity.setTransactionLogId(transactionId);
		entity.setModuleType(ScreeningModules.RTS.name());
		screeningModuleAuditTrailRepository.save(entity);
	}
	
	private String combineModulesId(String payerId) {
		PayerModuleConfiguration moduleConfiguration = modulesConfigurationRepo.findByPayerIdAndModuleId(payerId,
				ScreeningModules.RTS.value().doubleValue());
		if (moduleConfiguration != null) {
			return moduleConfiguration.getScreeningModules().getModuleId().toString();
		}
		return null;
	}
}
