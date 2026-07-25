package com.waseel.pbmschedulerservice.model.policydetails;

import java.math.BigDecimal;

public class ClassBenefitsModel {

	private String benefitCode;
	private String benefitDescription;
	private BigDecimal benefitLimitValue;
	private String benefitLimitCurrency;
	private String patientShareValue;
	private BigDecimal maxPatientShareValue;
	private String maxPatientShareCurrency;
	private BigDecimal maxConsultationFeeValue;
	private String maxConsultationFeeCurrency;
	private BigDecimal approvalThresholdValue;
	private String approvalThresholdCurrency;
	private String coverage;
	private String exclusions;
	private String comments;
	
	public String getBenefitCode() {
		return benefitCode;
	}

	public void setBenefitCode(String benefitCode) {
		this.benefitCode = benefitCode;
	}

	public String getBenefitDescription() {
		return benefitDescription;
	}

	public void setBenefitDescription(String benefitDescription) {
		this.benefitDescription = benefitDescription;
	}

	public BigDecimal getBenefitLimitValue() {
		return benefitLimitValue;
	}

	public void setBenefitLimitValue(BigDecimal benefitLimitValue) {
		this.benefitLimitValue = benefitLimitValue;
	}

	public String getBenefitLimitCurrency() {
		return benefitLimitCurrency;
	}

	public void setBenefitLimitCurrency(String benefitLimitCurrency) {
		this.benefitLimitCurrency = benefitLimitCurrency;
	}

	public String getPatientShareValue() {
		return patientShareValue;
	}

	public void setPatientShareValue(String patientShareValue) {
		this.patientShareValue = patientShareValue;
	}

	public BigDecimal getMaxPatientShareValue() {
		return maxPatientShareValue;
	}

	public void setMaxPatientShareValue(BigDecimal maxPatientShareValue) {
		this.maxPatientShareValue = maxPatientShareValue;
	}

	public String getMaxPatientShareCurrency() {
		return maxPatientShareCurrency;
	}

	public void setMaxPatientShareCurrency(String maxPatientShareCurrency) {
		this.maxPatientShareCurrency = maxPatientShareCurrency;
	}

	public BigDecimal getMaxConsultationFeeValue() {
		return maxConsultationFeeValue;
	}

	public void setMaxConsultationFeeValue(BigDecimal maxConsultationFeeValue) {
		this.maxConsultationFeeValue = maxConsultationFeeValue;
	}

	public String getMaxConsultationFeeCurrency() {
		return maxConsultationFeeCurrency;
	}

	public void setMaxConsultationFeeCurrency(String maxConsultationFeeCurrency) {
		this.maxConsultationFeeCurrency = maxConsultationFeeCurrency;
	}

	public BigDecimal getApprovalThresholdValue() {
		return approvalThresholdValue;
	}

	public void setApprovalThresholdValue(BigDecimal approvalThresholdValue) {
		this.approvalThresholdValue = approvalThresholdValue;
	}

	public String getApprovalThresholdCurrency() {
		return approvalThresholdCurrency;
	}

	public void setApprovalThresholdCurrency(String approvalThresholdCurrency) {
		this.approvalThresholdCurrency = approvalThresholdCurrency;
	}

	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public String getExclusions() {
		return exclusions;
	}

	public void setExclusions(String exclusions) {
		this.exclusions = exclusions;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
