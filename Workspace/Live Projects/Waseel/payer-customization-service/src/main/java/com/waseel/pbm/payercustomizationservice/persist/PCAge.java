package com.waseel.pbm.payercustomizationservice.persist;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PCAge", schema = "MDSS")
public class PCAge implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(generator = "PCAge_SEQ")
	@SequenceGenerator(name = "PCAge_SEQ", sequenceName = "PCAge_Seq_Id", allocationSize = 0, initialValue = 1)
	private Long seqId;

	@Column(name = "ServiceCode")
	private String serviceCode;

	@Column(name = "PayerId")
	private String payerId;

	@Column(name = "ModuleName")
	private String moduleName;

	@Column(name = "FromAgeInDays")
	private Long fromAgeInDays;

	@Column(name = "ToAgeInDays")
	private Long toAgeInDays;

	@Column(name = "ServiceStatus")
	private String serviceStatus;

	@Column(name = "AdditionalRejectionReason")
	private String additionalRejectionReason;

	@Column(name = "RuleId")
	private String ruleId;

	@Column(name = "LastUpdatedDateTime")
	private Timestamp lastUpdatedDateTime;

	@Column(name = "ScientificCode")
	private String scientificCode;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "BatchId", nullable = false, insertable = false, updatable = false)
	private CustomizationBatch batch;

	public Long getFromAgeInDays() {
		return fromAgeInDays;
	}

	public Long getToAgeInDays() {
		return toAgeInDays;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public String getAdditionalRejectionReason() {
		return additionalRejectionReason;
	}

	public String getRuleId() {
		return ruleId;
	}

	public Timestamp getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
	}

	public void setFromAgeInDays(Long fromAgeInDays) {
		this.fromAgeInDays = fromAgeInDays;
	}

	public void setToAgeInDays(Long toAgeInDays) {
		this.toAgeInDays = toAgeInDays;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public void setAdditionalRejectionReason(String additionalRejectionReason) {
		this.additionalRejectionReason = additionalRejectionReason;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
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

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}
}
