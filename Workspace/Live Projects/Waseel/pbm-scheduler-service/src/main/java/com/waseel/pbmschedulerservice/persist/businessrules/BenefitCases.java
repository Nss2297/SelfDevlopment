package com.waseel.pbmschedulerservice.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BENEFIT_CASES", schema = "PBM_BUSINESS_RULES")
public class BenefitCases implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BENEFIT_CASE_ID", nullable = false, updatable = false)
	private Long benefitCaseId;

	@Column(name = "CLASS_BENEFIT_ID", nullable = false)
	private Long classBenefitId;

	@Column(name = "APPROVAL_THRESHOLD_CURRENCY")
	private String approvalThresholdCurrency;

	@Column(name = "APPROVAL_THRESHOLD_VALUE")
	private BigDecimal approvalThresholdValue;

	@Column(name = "CASE_CODE")
	private String caseCode;

	@Column(name = "MAX_CONSULTATION_FEE_CURRENCY")
	private String maxConsultationFeeCurrency;

	@Column(name = "MAX_CONSULTATION_FEE_VALUE")
	private BigDecimal maxConsultationFeeValue;

	@Column(name = "MAX_PATIENT_SHARE_CURRENCY")
	private String maxPatientShareCurrency;

	@Column(name = "MAX_PATIENT_SHARE_VALUE")
	private BigDecimal maxPatientShareValue;

	@Column(name = "PATIENT_SHARE_CURRENCY")
	private String patientShareCurrency;

	@Column(name = "PATIENT_SHARE_VALUE")
	private BigDecimal patientShareValue;

	public Long getClassBenefitId() {
		return classBenefitId;
	}

	public void setClassBenefitId(Long classBenefitId) {
		this.classBenefitId = classBenefitId;
	}

	public Long getBenefitCaseId() {
		return benefitCaseId;
	}

	public void setBenefitCaseId(Long benefitCaseId) {
		this.benefitCaseId = benefitCaseId;
	}

	public void setBenefitCaseId(long benefitCaseId) {
		this.benefitCaseId = benefitCaseId;
	}

	public String getApprovalThresholdCurrency() {
		return this.approvalThresholdCurrency;
	}

	public void setApprovalThresholdCurrency(String approvalThresholdCurrency) {
		this.approvalThresholdCurrency = approvalThresholdCurrency;
	}

	public BigDecimal getApprovalThresholdValue() {
		return this.approvalThresholdValue;
	}

	public void setApprovalThresholdValue(BigDecimal approvalThresholdValue) {
		this.approvalThresholdValue = approvalThresholdValue;
	}

	public String getCaseCode() {
		return this.caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getMaxConsultationFeeCurrency() {
		return this.maxConsultationFeeCurrency;
	}

	public void setMaxConsultationFeeCurrency(String maxConsultationFeeCurrency) {
		this.maxConsultationFeeCurrency = maxConsultationFeeCurrency;
	}

	public BigDecimal getMaxConsultationFeeValue() {
		return this.maxConsultationFeeValue;
	}

	public void setMaxConsultationFeeValue(BigDecimal maxConsultationFeeValue) {
		this.maxConsultationFeeValue = maxConsultationFeeValue;
	}

	public String getMaxPatientShareCurrency() {
		return this.maxPatientShareCurrency;
	}

	public void setMaxPatientShareCurrency(String maxPatientShareCurrency) {
		this.maxPatientShareCurrency = maxPatientShareCurrency;
	}

	public BigDecimal getMaxPatientShareValue() {
		return this.maxPatientShareValue;
	}

	public void setMaxPatientShareValue(BigDecimal maxPatientShareValue) {
		this.maxPatientShareValue = maxPatientShareValue;
	}

	public String getPatientShareCurrency() {
		return this.patientShareCurrency;
	}

	public void setPatientShareCurrency(String patientShareCurrency) {
		this.patientShareCurrency = patientShareCurrency;
	}

	public BigDecimal getPatientShareValue() {
		return this.patientShareValue;
	}

	public void setPatientShareValue(BigDecimal patientShareValue) {
		this.patientShareValue = patientShareValue;
	}
}