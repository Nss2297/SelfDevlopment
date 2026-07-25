package com.waseel.pbm.payercustomizationservice.persist;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "PCQuantityLimitCheck", schema = "MDSS")
public class PCQuantityLimitCheck implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverride(name = "serviceCode", column = @Column(name = "ServiceCode"))
	@AttributeOverride(name = "icdCode", column = @Column(name = "IcdCode"))
	@AttributeOverride(name = "payerId", column = @Column(name = "PayerId"))
	@AttributeOverride(name = "moduleName", column = @Column(name = "ModuleName"))
	private PCCommonId id;

	@Column(name = "FromAgeInDays")
	private Long fromAgeInDays;

	@Column(name = "ToAgeInDays")
	private Long toAgeInDays;

	@Column(name = "DrugType")
	private String drugType;

	@Column(name = "UnitType")
	private String unitType;

	@Column(name = "PayerSourceOfCustomization")
	private String payerSourceOfCustomization;

	@Column(name = "AdditionalRejectionReason")
	private String additionalRejectionReason;

	@Column(name = "RuleId")
	private String ruleId;

	@Column(name = "MaxValuePerDay")
	private Integer maxValuePerDay;

	@Column(name = "LastUpdatedDateTime")
	private Timestamp lastUpdatedDateTime;

	@Column(name = "ProductPackageSize")
	private Integer productPackageSize;

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

	public Integer getProductPackageSize() {
		return productPackageSize;
	}

	public void setProductPackageSize(Integer productPackageSize) {
		this.productPackageSize = productPackageSize;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public PCCommonId getId() {
		return id;
	}

	public Long getFromAgeInDays() {
		return fromAgeInDays;
	}

	public Long getToAgeInDays() {
		return toAgeInDays;
	}

	public String getDrugType() {
		return drugType;
	}

	public String getUnitType() {
		return unitType;
	}

	public String getPayerSourceOfCustomization() {
		return payerSourceOfCustomization;
	}

	public String getAdditionalRejectionReason() {
		return additionalRejectionReason;
	}

	public String getRuleId() {
		return ruleId;
	}

	public Integer getMaxValuePerDay() {
		return maxValuePerDay;
	}

	public Timestamp getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
	}

	public void setId(PCCommonId id) {
		this.id = id;
	}

	public void setFromAgeInDays(Long fromAgeInDays) {
		this.fromAgeInDays = fromAgeInDays;
	}

	public void setToAgeInDays(Long toAgeInDays) {
		this.toAgeInDays = toAgeInDays;
	}

	public void setDrugType(String drugType) {
		this.drugType = drugType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public void setPayerSourceOfCustomization(String payerSourceOfCustomization) {
		this.payerSourceOfCustomization = payerSourceOfCustomization;
	}

	public void setAdditionalRejectionReason(String additionalRejectionReason) {
		this.additionalRejectionReason = additionalRejectionReason;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public void setMaxValuePerDay(Integer maxValuePerDay) {
		this.maxValuePerDay = maxValuePerDay;
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
