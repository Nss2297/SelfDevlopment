package com.waseel.pbmschedulerservice.model.policydetails;

import java.math.BigDecimal;

public class BenefitSubCoverageModel {

	private String subCoverageCode;
	private String subCoverageDescription;
	private BigDecimal subCoverageLimitValue;
	private String subCoverageLimitCurrency;
	private BigDecimal subCoverageThresholdValue;
	private String subCoverageThresholdCurrency;

	public String getSubCoverageCode() {
		return subCoverageCode;
	}

	public void setSubCoverageCode(String subCoverageCode) {
		this.subCoverageCode = subCoverageCode;
	}

	public String getSubCoverageDescription() {
		return subCoverageDescription;
	}

	public void setSubCoverageDescription(String subCoverageDescription) {
		this.subCoverageDescription = subCoverageDescription;
	}

	public BigDecimal getSubCoverageLimitValue() {
		return subCoverageLimitValue;
	}

	public void setSubCoverageLimitValue(BigDecimal subCoverageLimitValue) {
		this.subCoverageLimitValue = subCoverageLimitValue;
	}

	public String getSubCoverageLimitCurrency() {
		return subCoverageLimitCurrency;
	}

	public void setSubCoverageLimitCurrency(String subCoverageLimitCurrency) {
		this.subCoverageLimitCurrency = subCoverageLimitCurrency;
	}

	public BigDecimal getSubCoverageThresholdValue() {
		return subCoverageThresholdValue;
	}

	public void setSubCoverageThresholdValue(BigDecimal subCoverageThresholdValue) {
		this.subCoverageThresholdValue = subCoverageThresholdValue;
	}

	public String getSubCoverageThresholdCurrency() {
		return subCoverageThresholdCurrency;
	}

	public void setSubCoverageThresholdCurrency(String subCoverageThresholdCurrency) {
		this.subCoverageThresholdCurrency = subCoverageThresholdCurrency;
	}
}
