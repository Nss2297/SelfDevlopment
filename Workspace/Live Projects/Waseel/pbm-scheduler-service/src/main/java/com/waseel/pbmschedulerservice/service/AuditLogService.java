package com.waseel.pbmschedulerservice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.waseel.pbmschedulerservice.model.enums.AuditUpdatedType;
import com.waseel.pbmschedulerservice.model.enums.EntitiesName;
import com.waseel.pbmschedulerservice.persist.businessrules.AuditLog;
import com.waseel.pbmschedulerservice.repository.businessrules.AuditLogRepository;

@Service
public class AuditLogService {

	@Autowired
	private AuditLogRepository auditLogRepository;

	public void addDataInAuditLog(AuditUpdatedType auditUpdatedType, Long entityId, EntitiesName entityName,
			Object entityData) {
		AuditLog auditLog = new AuditLog();
		auditLog.setEntityId(entityId);
		auditLog.setEntityName(entityName.value());
		auditLog.setUpdateBy("pbm-scheduler-service");
		auditLog.setUpdateDate(new Date());
		auditLog.setUpdateType(auditUpdatedType.name());
		auditLog.setEntityData(new Gson().toJson(entityData));
		auditLogRepository.save(auditLog);
	}
}
