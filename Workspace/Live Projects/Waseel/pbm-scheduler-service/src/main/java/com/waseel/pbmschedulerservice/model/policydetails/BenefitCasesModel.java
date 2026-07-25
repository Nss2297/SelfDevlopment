package com.waseel.pbmschedulerservice.model.policydetails;

import java.math.BigDecimal;

public class BenefitCasesModel {

	private String caseCode;
	private String patientShareValue;
	private BigDecimal maxPatientShareValue;
	private String maxPatientShareCurrency;
	private BigDecimal maxConsultationFeeValue;
	private String maxConsultationFeeCurrency;
	private BigDecimal approvalThresholdValue;
	private String approvalThresholdCurrency;

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
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
}
