package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BENEFIT_SUBCOVERAGE database table.
 * 
 */
@Entity
@Table(name="BENEFIT_SUBCOVERAGE")
@NamedQuery(name="BenefitSubcoverage.findAll", query="SELECT b FROM BenefitSubcoverage b")
public class BenefitSubcoverage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BENEFIT_SUBCOVERAGE_ID")
	private long benefitSubcoverageId;

	@Column(name="SUBCOV_APP_THRESHOLD_CURRENCY")
	private String subcovAppThresholdCurrency;

	@Column(name="SUBCOV_APP_THRESHOLD_VALUE")
	private BigDecimal subcovAppThresholdValue;

	@Column(name="SUBCOVERAGE_CODE")
	private String subcoverageCode;

	@Column(name="SUBCOVERAGE_DESCRIPTION")
	private String subcoverageDescription;

	@Column(name="SUBCOVERAGE_LIMIT_CURRENCY")
	private String subcoverageLimitCurrency;

	@Column(name="SUBCOVERAGE_LIMIT_VALUE")
	private BigDecimal subcoverageLimitValue;

	//bi-directional many-to-one association to ClassBenefit
	@ManyToOne
	@JoinColumn(name="CLASS_BENEFIT_ID")
	private ClassBenefit classBenefit;

	public BenefitSubcoverage() {
	}

	public long getBenefitSubcoverageId() {
		return this.benefitSubcoverageId;
	}

	public void setBenefitSubcoverageId(long benefitSubcoverageId) {
		this.benefitSubcoverageId = benefitSubcoverageId;
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

	public ClassBenefit getClassBenefit() {
		return this.classBenefit;
	}

	public void setClassBenefit(ClassBenefit classBenefit) {
		this.classBenefit = classBenefit;
	}

}