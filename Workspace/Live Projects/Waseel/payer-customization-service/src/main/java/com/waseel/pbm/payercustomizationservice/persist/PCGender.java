package com.waseel.pbm.payercustomizationservice.persist;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "PCGender", schema = "MDSS")
public class PCGender implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(generator = "PCGender_SEQ")
	@SequenceGenerator(name = "PCGender_SEQ", sequenceName = "PCGender_Seq_Id", allocationSize = 0, initialValue = 1)
	private Long seqId;

	@Column(name = "ServiceCode")
	private String serviceCode;

	@Column(name = "PayerId")
	private String payerId;

	@Column(name = "ModuleName")
	private String moduleName;

	@Column(name = "Gender")
	private String gender;

	@Column(name = "ServiceStatus")
	private String serviceStatus;

	@Column(name = "AdditionalRejectionReason")
	private String additionalRejectionReason;

	@Column(name = "RuleId")
	private String ruleId;

	@Column(name = "LastUpdatedDateTime")
	private Timestamp lastUpdatedDateTime;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "BatchId", nullable = false, insertable = false, updatable = false)
	private CustomizationBatch batch;

	@Column(name = "ScientificCode")
	private String scientificCode;

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getAdditionalRejectionReason() {
		return additionalRejectionReason;
	}

	public void setAdditionalRejectionReason(String additionalRejectionReason) {
		this.additionalRejectionReason = additionalRejectionReason;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public Timestamp getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
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
