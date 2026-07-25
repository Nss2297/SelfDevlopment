package com.waseel.dssadminservice.persist.mdss;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "`AUDIT_LOG`", schema = "MDSS")
public class AuditLog {

	@Id
	@Column(name = "`AUDIT_LOG_ID`", nullable = false, updatable = false)
	@GeneratedValue(generator = "AuditLogSeq")
	@SequenceGenerator(name = "AuditLogSeq", sequenceName = "AUDIT_LOG_SEQ", allocationSize = 0, initialValue = 1)
	private Long auditLogId;

	@Column(name = "`USER_NAME`", nullable = false, length = 50)
	private String userName;

	@Column(name = "`ENTITY_ID`", nullable = false)
	private Long entityId;

	@Column(name = "`ENTITY_NAME`", nullable = false, length = 250)
	private String entityName;

	@Column(name = "`ACTION`", nullable = false, length = 15)
	private String action;

	@Column(name = "`DATE`", nullable = false)
	private Date date;

	@Column(name = "`ACCOUNT_ID`", nullable = false, length = 10)
	private String accountId;

	@Lob
	@Column(name = "`ENTITY_DATA`", nullable = true)
	private String entityData;

	public AuditLog() {
	}

	public AuditLog(String userName, Long entityId, String entityName, String action, Date date,
			String accountId, String entityData) {
		this.userName = userName;
		this.entityId = entityId;
		this.entityName = entityName;
		this.action = action;
		this.date = date;
		this.accountId = accountId;
		this.entityData = entityData;
	}

	public Long getAuditLogId() {
		return auditLogId;
	}

	public void setAuditLogId(Long auditLogId) {
		this.auditLogId = auditLogId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getEntityData() {
		return entityData;
	}

	public void setEntityData(String entityData) {
		this.entityData = entityData;
	}

}
