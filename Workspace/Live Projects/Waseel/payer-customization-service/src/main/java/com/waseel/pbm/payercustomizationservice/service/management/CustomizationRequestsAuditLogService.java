package com.waseel.pbm.payercustomizationservice.service.management;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestsAudit;
import com.waseel.pbm.payercustomizationservice.repository.CustomizationRequestsAuditRepository;
import com.waseel.pbm.payercustomizationservice.util.UserInfoUtil;

@Service
public class CustomizationRequestsAuditLogService {

	@Autowired
	private CustomizationRequestsAuditRepository customizationRequestsAuditRepository;

	public void populateCustomizationRequestsAudit(long entityId, String entityName, String action, String change) {
		String userName = UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication());
		String accountId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		CustomizationRequestsAudit customizationRequestsAudit = new CustomizationRequestsAudit();
		customizationRequestsAudit.setAccountId(accountId);
		customizationRequestsAudit.setAction(action);
		customizationRequestsAudit.setChange(change);
		customizationRequestsAudit.setEntityId(entityId);
		customizationRequestsAudit.setEntityName(entityName);
		customizationRequestsAudit.setLastUpdatedDate(new Date());
		customizationRequestsAudit.setUserId(userName);
		customizationRequestsAuditRepository.save(customizationRequestsAudit);
	}
}
