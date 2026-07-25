package com.waseel.pbm.pbmadminservice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.pbm.pbmadminservice.enums.AuditUpdatedType;
import com.waseel.pbm.pbmadminservice.enums.EntitiesName;
import com.waseel.pbm.pbmadminservice.persist.businessrules.AuditLog;
import com.waseel.pbm.pbmadminservice.persist.mdss.CustomizationUploadAudit;
import com.waseel.pbm.pbmadminservice.repository.businessrules.AuditLogRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.CustomizationUploadAuditRepository;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;

@Service
public class AuditLogService {

	@Autowired
	private AuditLogRepository auditLogRepository;

	@Autowired
	private CustomizationUploadAuditRepository customizationUploadAuditRepository;

	public void addDataInAuditLog(AuditUpdatedType auditUpdatedType, Long entityId, EntitiesName entityName,
			Object entityData) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String jsonData = objectMapper.writeValueAsString(entityData);
			AuditLog auditLog = new AuditLog();
			auditLog.setEntityId(entityId);
			auditLog.setEntityName(entityName.value());
			auditLog.setUpdateBy("pbm-admin-service");
			auditLog.setUpdateDate(new Date());
			auditLog.setUpdateType(auditUpdatedType.name());
			auditLog.setEntityData(jsonData);
			auditLogRepository.save(auditLog);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void addDataInCustomizationUploadAudit(AuditUpdatedType auditUpdatedType, String entityId,
			EntitiesName entityName, Object entityData) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String userName = UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication());
			String accountId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
			String jsonData = objectMapper.writeValueAsString(entityData);
			CustomizationUploadAudit auditLog = new CustomizationUploadAudit(userName, entityId, entityName.value(),
					auditUpdatedType.name(), new Date(), jsonData, accountId);
			customizationUploadAuditRepository.save(auditLog);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
