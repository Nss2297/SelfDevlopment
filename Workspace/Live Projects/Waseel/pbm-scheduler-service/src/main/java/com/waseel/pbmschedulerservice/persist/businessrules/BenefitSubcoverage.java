package com.waseel.pbmschedulerservice.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BENEFIT_SUBCOVERAGE", schema = "PBM_BUSINESS_RULES")
public class BenefitSubcoverage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BENEFIT_SUBCOVERAGE_ID", nullable = false, updatable = false)
	private Long benefitSubcoverageId;

	@Column(name = "CLASS_BENEFIT_ID", nullable = false)
	private Long classBenefitId;

	@Column(name = "SUBCOV_APP_THRESHOLD_CURRENCY")
	private String subcovAppThresholdCurrency;

	@Column(name = "SUBCOV_APP_THRESHOLD_VALUE")
	private BigDecimal subcovAppThresholdValue;

	@Column(name = "SUBCOVERAGE_CODE")
	private String subcoverageCode;

	@Column(name = "SUBCOVERAGE_DESCRIPTION")
	private String subcoverageDescription;

	@Column(name = "SUBCOVERAGE_LIMIT_CURRENCY")
	private String subcoverageLimitCurrency;

	@Column(name = "SUBCOVERAGE_LIMIT_VALUE")
	private BigDecimal subcoverageLimitValue;

	public Long getBenefitSubcoverageId() {
		return benefitSubcoverageId;
	}

	public String getSubcovAppThresholdCurrency() {
		return this.subcovAppThresholdCurrency;
	}

	public void setSubcovAppThresholdCurrency(String subcovAppThresholdCurrency) {
		this.subcovAppThresholdCurrency = subcovAppThresholdCurrency;
	}

	public BigDecimal getSubcovAppThresholdValue() {
		return this.subcovAppThresholdValue;
	}

	public void setSubcovAppThresholdValue(BigDecimal subcovAppThresholdValue) {
		this.subcovAppThresholdValue = subcovAppThresholdValue;
	}

	public String getSubcoverageCode() {
		return this.subcoverageCode;
	}

	public void setSubcoverageCode(String subcoverageCode) {
		this.subcoverageCode = subcoverageCode;
	}

	public String getSubcoverageDescription() {
		return this.subcoverageDescription;
	}

	public void setSubcoverageDescription(String subcoverageDescription) {
		this.subcoverageDescription = subcoverageDescription;
	}

	public String getSubcoverageLimitCurrency() {
		return this.subcoverageLimitCurrency;
	}

	public void setSubcoverageLimitCurrency(String subcoverageLimitCurrency) {
		this.subcoverageLimitCurrency = subcoverageLimitCurrency;
	}

	public BigDecimal getSubcoverageLimitValue() {
		return this.subcoverageLimitValue;
	}

	public void setSubcoverageLimitValue(BigDecimal subcoverageLimitValue) {
		this.subcoverageLimitValue = subcoverageLimitValue;
	}

	public Long getClassBenefitId() {
		return classBenefitId;
	}

	public void setClassBenefitId(Long classBenefitId) {
		this.classBenefitId = classBenefitId;
	}

	public void setBenefitSubcoverageId(Long benefitSubcoverageId) {
		this.benefitSubcoverageId = benefitSubcoverageId;
	}
}