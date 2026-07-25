package com.waseel.policy.model.client;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonInclude(Include.NON_NULL)
@JsonTypeName("classBenefits")
public class PolicyClassBenefitsModel {

	private String benefitCode;
	private String benefitDescription;
	private BigDecimal benefitLimitValue;
	private String benefitLimitCurrency;
	private String patientShareValue;
	private String patientShareCurrency;
	private BigDecimal maxPatientShareValue;
	private String maxPatientShareCurrency;
	private BigDecimal maxConsultationFeeValue;
	private String maxConsultationFeeCurrency;
	private BigDecimal approvalThresholdValue;
	private String approvalThresholdCurrency;
	private BigDecimal remainingLimitValue;
	private String remainingLimitCurrency;
	private List<ClassBenefitCasesModel> benefitCases;

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

	public String getPatientShareCurrency() {
		return patientShareCurrency;
	}

	public void setPatientShareCurrency(String patientShareCurrency) {
		this.patientShareCurrency = patientShareCurrency;
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

	public BigDecimal getRemainingLimitValue() {
		return remainingLimitValue;
	}

	public void setRemainingLimitValue(BigDecimal remainingLimitValue) {
		this.remainingLimitValue = remainingLimitValue;
	}

	public String getRemainingLimitCurrency() {
		return remainingLimitCurrency;
	}

	public void setRemainingLimitCurrency(String remainingLimitCurrency) {
		this.remainingLimitCurrency = remainingLimitCurrency;
	}

	public List<ClassBenefitCasesModel> getBenefitCases() {
		return benefitCases;
	}

	public void setBenefitCases(List<ClassBenefitCasesModel> benefitCases) {
		this.benefitCases = benefitCases;
	}

	public PolicyClassBenefitsModel() {
		super();
	}

	public PolicyClassBenefitsModel(String benefitCode, String benefitDescription, BigDecimal benefitLimitValue,
			String benefitLimitCurrency, String patientShareValue, String patientShareCurrency,
			BigDecimal maxPatientShareValue, String maxPatientShareCurrency, BigDecimal maxConsultationFeeValue,
			String maxConsultationFeeCurrency, BigDecimal approvalThresholdValue, String approvalThresholdCurrency,
			BigDecimal remainingLimitValue, String remainingLimitCurrency, List<ClassBenefitCasesModel> benefitCases) {
		super();
		this.benefitCode = benefitCode;
		this.benefitDescription = benefitDescription;
		this.benefitLimitValue = benefitLimitValue;
		this.benefitLimitCurrency = benefitLimitCurrency;
		this.patientShareValue = patientShareValue;
		this.patientShareCurrency = patientShareCurrency;
		this.maxPatientShareValue = maxPatientShareValue;
		this.maxPatientShareCurrency = maxPatientShareCurrency;
		this.maxConsultationFeeValue = maxConsultationFeeValue;
		this.maxConsultationFeeCurrency = maxConsultationFeeCurrency;
		this.approvalThresholdValue = approvalThresholdValue;
		this.approvalThresholdCurrency = approvalThresholdCurrency;
		this.remainingLimitValue = remainingLimitValue;
		this.remainingLimitCurrency = remainingLimitCurrency;
		this.benefitCases = benefitCases;
	}

}
