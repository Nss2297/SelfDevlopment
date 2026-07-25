package com.waseel.pbm.payercustomizationservice.persist;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "PCDuplicateTherapy", schema = "MDSS")
public class PCDuplicateTherapy implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverride(name = "serviceCode", column = @Column(name = "ServiceCode"))
	@AttributeOverride(name = "interactedServiceCode", column = @Column(name = "InteractedServiceCode"))
	@AttributeOverride(name = "payerId", column = @Column(name = "PayerId"))
	@AttributeOverride(name = "moduleName", column = @Column(name = "ModuleName"))
	private PCDrugCommonId id;

	@Column(name = "RuleId")
	private String ruleId;

	@Column(name = "ServiceStatus")
	private String serviceStatus;

	@Column(name = "AdditionalRejectionReason")
	private String additionalRejectionReason;

	@Column(name = "LastUpdatedDateTime")
	private Timestamp lastUpdatedDateTime;

	@Column(name = "Id")
	private Long seqId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "BatchId", nullable = false, insertable = false, updatable = false)
	private CustomizationBatch batch;

	@Column(name = "ScientificCode")
	private String ScientificCode;

	public String getScientificCode() {
		return ScientificCode;
	}

	public void setScientificCode(String scientificCode) {
		ScientificCode = scientificCode;
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

	public CustomizationBatch getBatch() {
		return batch;
	}

	public void setBatch(CustomizationBatch batch) {
		this.batch = batch;
	}

	public Long getSeqId() {
		return seqId;
	}

	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}
}
