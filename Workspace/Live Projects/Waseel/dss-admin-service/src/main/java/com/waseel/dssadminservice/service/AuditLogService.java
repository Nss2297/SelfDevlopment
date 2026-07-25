package com.waseel.dssadminservice.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.dssadminservice.enums.AuditLogAction;
import com.waseel.dssadminservice.enums.EntitiesName;
import com.waseel.dssadminservice.persist.mdss.AuditLog;
import com.waseel.dssadminservice.persist.mdss.CustomizationUploadAudit;
import com.waseel.dssadminservice.repository.mdss.AuditLogRepository;
import com.waseel.dssadminservice.repository.mdss.CustomizationUploadAuditRepository;
import com.waseel.dssadminservice.util.UserInfoUtil;

@Service
public class AuditLogService {

	private final Logger logger = LoggerFactory.getLogger(AuditLogService.class);

	@Autowired
	private AuditLogRepository auditLogRepository;
	@Autowired
	private CustomizationUploadAuditRepository customizationUploadAuditRepository;

	public void addDataInAuditLog(Long entityId, EntitiesName entityName, AuditLogAction action, Object entityData) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String entityDataStr = objectMapper.writeValueAsString(entityData);
			String userName = UserInfoUtil.getAccName(SecurityContextHolder.getContext().getAuthentication());
			String accId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
			AuditLog auditLog = new AuditLog(userName, entityId, entityName.value(), action.name(), new Date(), accId,
					entityDataStr);
			auditLogRepository.save(auditLog);
			logger.info("Data saved in auditLog for EntityId: [{}], EntityName: [{}]", entityId, entityName.value());
		} catch (JsonProcessingException e) {
			logger.error("Error occur while saving data in auditLog table: ", e);
			e.printStackTrace();
		}
	}

	public void addDataInCustomizationUploadAudit(AuditLogAction action, String entityId, EntitiesName entityName,
			Object entityData) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String userName = UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication());
			String accountId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
			String jsonData = objectMapper.writeValueAsString(entityData);
			CustomizationUploadAudit auditLog = new CustomizationUploadAudit(userName, entityId, entityName.value(),
					action.name(), new Date(), jsonData, accountId);
			customizationUploadAuditRepository.save(auditLog);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
