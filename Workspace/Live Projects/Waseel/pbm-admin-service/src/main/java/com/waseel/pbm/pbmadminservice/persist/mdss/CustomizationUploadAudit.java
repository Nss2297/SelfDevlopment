package com.waseel.pbm.pbmadminservice.persist.mdss;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMIZATION_UPLOAD_AUDIT", schema = "MDSS")
public class CustomizationUploadAudit {

	@Id
	@GeneratedValue(generator = "CustomizationUploadAuditSeq")
	@SequenceGenerator(name = "CustomizationUploadAuditSeq", sequenceName = "CUSTOMIZATION_UPLOAD_AUDIT_SEQ",
								allocationSize = 0, initialValue = 1)
	@Column(name = "CUSTOMIZATION_UPLOAD_AUDIT_ID")
	private Long customizationUploadAuditId;

	@Column(name = "USER_ID", nullable = false, length = 200)
	private String userId;

	@Column(name = "ENTITY_ID", nullable = false)
	private String entityId;

	@Column(name = "ENTITY_NAME", nullable = false, length = 200)
	private String entityName;

	@Column(name = "ACTION", nullable = false, length = 200)
	private String action;

	@Column(name = "LAST_UPDATED_DATE", nullable = false)
	private Date lastUpdatedDate;

	@Lob
	@Column(name = "CHANGE")
	private String change;

	@Column(name = "ACCOUNT_ID", nullable = false, length = 20)
	private String accountId;
	
	public CustomizationUploadAudit() {
	}

	public CustomizationUploadAudit(String userId, String entityId, String entityName, String action,
			Date lastUpdatedDate, String change, String accountId) {
		this.userId = userId;
		this.entityId = entityId;
		this.entityName = entityName;
		this.action = action;
		this.lastUpdatedDate = lastUpdatedDate;
		this.change = change;
		this.accountId = accountId;
	}

	public Long getCustomizationUploadAuditId() {
		return customizationUploadAuditId;
	}

	public void setCustomizationUploadAuditId(Long customizationUploadAuditId) {
		this.customizationUploadAuditId = customizationUploadAuditId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
