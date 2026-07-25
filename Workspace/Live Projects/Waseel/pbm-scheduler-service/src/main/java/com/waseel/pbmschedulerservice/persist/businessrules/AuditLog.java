package com.waseel.pbmschedulerservice.persist.businessrules;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "AUDIT_LOG", schema = "PBM_BUSINESS_RULES")
public class AuditLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "AUDIT_LOG_ID", nullable = false, updatable = false)
	@GeneratedValue(generator = "AuditLogSeq")
	@SequenceGenerator(name = "AuditLogSeq", sequenceName = "AUDIT_LOG_SEQ", allocationSize = 0, initialValue = 1)
	private Long auditLogId;

	@Column(name = "UPDATE_BY", nullable = false, length = 250)
	private String updateBy;

	@Column(name = "UPDATE_DATE", nullable = false)
	private Date updateDate;

	@Column(name = "ENTITY_ID", nullable = false)
	private Long entityId;

	@Column(name = "ENTITY_NAME", nullable = false, length = 250)
	private String entityName;

	@Column(name = "UPDATE_TYPE", nullable = false, length = 10)
	private String updateType;

	@Lob
	@Column(name = "ENTITY_DATA", length = 5000)
	private String entityData;

	public Long getAuditLogId() {
		return auditLogId;
	}

	public void setAuditLogId(Long auditLogId) {
		this.auditLogId = auditLogId;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public String getEntityData() {
		return entityData;
	}

	public void setEntityData(String entityData) {
		this.entityData = entityData;
	}
}
