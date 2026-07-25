package com.waseel.dssadminservice.persist.mdss;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "`PCDuplicateTherapy`", schema = "`MDSS`")
public class PCDuplicateTherapy implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverride(name = "serviceCode", column = @Column(name = "`ServiceCode`"))
	@AttributeOverride(name = "interactedServiceCode", column = @Column(name = "`InteractedServiceCode`"))
	@AttributeOverride(name = "payerId", column = @Column(name = "`PayerId`"))
	@AttributeOverride(name = "moduleName", column = @Column(name = "`ModuleName`"))
	private PCDrugCommonId id;

	@Column(name = "`RuleId`")
	private String ruleId;

	@Column(name = "`ServiceStatus`")
	private String serviceStatus;

	@Column(name = "`AdditionalRejectionReason`")
	private String additionalRejectionReason;

	@Column(name = "`LastUpdatedDateTime`")
	private Timestamp lastUpdatedDateTime;

	@Column(name = "`Id`")
	private Long seqId;

	@Column(name = "`BatchId`")
	private Long batch;

	@Column(name = "`ScientificCode`")
	private String scientificCode;

	public PCDuplicateTherapy() {
	}

	public PCDuplicateTherapy(PCDrugCommonId id, String serviceStatus, String additionalRejectionReason) {
		super();
		this.id = id;
		this.serviceStatus = serviceStatus;
		this.additionalRejectionReason = additionalRejectionReason;
	}

	public PCDrugCommonId getId() {
		return id;
	}

	public String getRuleId() {
		return ruleId;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public String getAdditionalRejectionReason() {
		return additionalRejectionReason;
	}

	public Timestamp getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
	}

	public void setId(PCDrugCommonId id) {
		this.id = id;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public void setAdditionalRejectionReason(String additionalRejectionReason) {
		this.additionalRejectionReason = additionalRejectionReason;
	}

	public void setLastUpdatedDateTime(Timestamp lastUpdatedDateTime) {
		this.lastUpdatedDateTime = lastUpdatedDateTime;
	}

	public Long getBatch() {
		return batch;
	}

	public void setBatch(Long batch) {
		this.batch = batch;
	}

	public Long getSeqId() {
		return seqId;
	}

	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}
	
	@PrePersist
	@PreUpdate
	private void updateLastUpdateTime() {
		this.lastUpdatedDateTime = Timestamp.from(Instant.now());
	}
}
