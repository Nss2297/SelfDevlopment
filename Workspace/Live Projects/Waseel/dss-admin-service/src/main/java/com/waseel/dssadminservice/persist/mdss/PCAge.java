package com.waseel.dssadminservice.persist.mdss;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "`PCAge`", schema = "MDSS")
public class PCAge implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverride(name = "serviceCode", column = @Column(name = "`ServiceCode`"))
	@AttributeOverride(name = "payerId", column = @Column(name = "`PayerId`"))
	@AttributeOverride(name = "moduleName", column = @Column(name = "`ModuleName`"))
	private PCAgeGenderId id;

	@Column(name = "`FromAgeInDays`")
	private Long fromAgeInDays;

	@Column(name = "`ToAgeInDays`")
	private Long toAgeInDays;

	@Column(name = "`ServiceStatus`")
	private String serviceStatus;

	@Column(name = "`AdditionalRejectionReason`")
	private String additionalRejectionReason;

	@Column(name = "`RuleId`")
	private String ruleId;

	@Column(name = "`LastUpdatedDateTime`")
	private Timestamp lastUpdatedDateTime = Timestamp.from(Instant.now());

	@Column(name = "`Id`")
	@GeneratedValue(generator = "`PCAge_SEQ`")
	@SequenceGenerator(name = "`PCAge_SEQ`", sequenceName = "`PCAge_Seq_Id`", allocationSize = 0, initialValue = 1)
	private Long seqId;

	@Column(name = "`ScientificCode`")
	private String scientificCode;

	@Column(name = "`BatchId`")
	private Long batch;

	public PCAge() {
	}

	public PCAge(PCAgeGenderId id, Long fromAgeInDays, Long toAgeInDays, String serviceStatus,
			String additionalRejectionReason, String scientificCode) {
		super();
		this.id = id;
		this.fromAgeInDays = fromAgeInDays;
		this.toAgeInDays = toAgeInDays;
		this.serviceStatus = serviceStatus;
		this.additionalRejectionReason = additionalRejectionReason;
		this.scientificCode = scientificCode;
	}

	public PCAgeGenderId getId() {
		return id;
	}

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

	public void setId(PCAgeGenderId id) {
		this.id = id;
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

	@PreUpdate
	protected void preUpdate() {
		this.lastUpdatedDateTime = Timestamp.from(Instant.now());
	}
}
