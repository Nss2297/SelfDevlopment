package com.waseel.pbmpayerapisservice.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonInclude(Include.NON_NULL)
@JsonTypeName("benefitCases")
public class ClassBenefitCasesModel {

	private String caseCode;
	private String patientShareValue;
	private String patientShareCurrency;
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

	public String getPatientShareCurrency() {
		return patientShareCurrency;
	}

	public void setPatientShareCurrency(String patientShareCurrency) {
		this.patientShareCurrency = patientShareCurrency;
	}

	public ClassBenefitCasesModel() {
		super();
	}

	public ClassBenefitCasesModel(String caseCode, String patientShareValue, BigDecimal maxPatientShareValue,
			String maxPatientShareCurrency, BigDecimal maxConsultationFeeValue, String maxConsultationFeeCurrency,
			BigDecimal approvalThresholdValue, String approvalThresholdCurrency, String patientShareCurrency) {
		super();
		this.caseCode = caseCode;
		this.patientShareValue = patientShareValue;
		this.maxPatientShareValue = maxPatientShareValue;
		this.maxPatientShareCurrency = maxPatientShareCurrency;
		this.maxConsultationFeeValue = maxConsultationFeeValue;
		this.maxConsultationFeeCurrency = maxConsultationFeeCurrency;
		this.approvalThresholdValue = approvalThresholdValue;
		this.approvalThresholdCurrency = approvalThresholdCurrency;
		this.patientShareCurrency = patientShareCurrency;
	}

}
