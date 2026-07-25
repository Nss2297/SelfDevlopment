package com.waseel.pbm.idfvalidationservice.persist;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ScreeningModuleAuditTrail")
public class ScreeningModuleAuditTrail {

	@Id
	@GeneratedValue(generator = "FdbScreeningModuleAuditTrail_Seq")
	@SequenceGenerator(name="FdbScreeningModuleAuditTrail_Seq",sequenceName="DSS_ScreeningModuleAuditTrail_SEQ", allocationSize=0,initialValue = 1)
	@Column(name = "AuditId")
	private Long auditId;
	
	@Column(name = "MongoDBUniqueId")
	private String mongodbUniqueId;

	@Column(name = "RequestId")
	private String requestId;

	@Column(name = "PayerId")
	private String payerId;

	@Column(name = "TransactionLogId")
	private Long transactionLogId;

	@Column(name = "ModuleId")
	private String moduleId;

	@Column(name = "ModuleType")
	private String moduleType;
	
	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public Long getTransactionLogId() {
		return transactionLogId;
	}

	public void setTransactionLogId(Long transactionLogId) {
		this.transactionLogId = transactionLogId;
	}

	public String getMongodbUniqueId() {
		return mongodbUniqueId;
	}

	public void setMongodbUniqueId(String mongodbUniqueId) {
		this.mongodbUniqueId = mongodbUniqueId;
	}

	public Long getAuditId() {
		return auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	
}
