package com.waseel.pbm.payercustomizationservice.persist;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMIZATION_REQUESTS_AUDIT", schema = "MDSS")
public class CustomizationRequestsAudit {

	@Id
	@GeneratedValue(generator = "CustomizationRequestsAuditSeq")
	@SequenceGenerator(name = "CustomizationRequestsAuditSeq", sequenceName = "CUSTOMIZATION_REQUESTS_AUDIT_ID_SEQ", allocationSize = 0, initialValue = 1)
	@Column(name = "CUSTOMIZATION_REQUEST_AUDIT_ID")
	private Long customizationRequestAuditId;

	@Column(name = "USER_ID", nullable = false, length = 200)
	private String userId;

	@Column(name = "ENTITY_ID", nullable = false)
	private Long entityId;

	@Column(name = "ENTITY_NAME", nullable = false, length = 200)
	private String entityName;

	@Column(name = "ACTION", nullable = false, length = 200)
	private String action;

	@Column(name = "LAST_UPDATED_DATE", nullable = false)
	private Date lastUpdatedDate;

	@Column(name = "CHANGE")
	private String change;

	@Column(name = "ACCOUNT_ID", nullable = false, length = 20)
	private String accountId;

	public Long getCustomizationRequestAuditId() {
		return customizationRequestAuditId;
	}

	public String getUserId() {
		return userId;
	}

	public Long getEntityId() {
		return entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getAction() {
		return action;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public String getChange() {
		return change;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setCustomizationRequestAuditId(Long customizationRequestAuditId) {
		this.customizationRequestAuditId = customizationRequestAuditId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public CustomizationRequestsAudit() {
		super();
	}

	public CustomizationRequestsAudit(Long customizationRequestAuditId, String userId, Long entityId, String entityName,
			String action, Date lastUpdatedDate, String change, String accountId) {
		super();
		this.customizationRequestAuditId = customizationRequestAuditId;
		this.userId = userId;
		this.entityId = entityId;
		this.entityName = entityName;
		this.action = action;
		this.lastUpdatedDate = lastUpdatedDate;
		this.change = change;
		this.accountId = accountId;
	}
}
