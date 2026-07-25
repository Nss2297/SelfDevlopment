package com.waseel.pbmschedulerservice.persist.businessrules;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLASS_BENEFITS", schema = "PBM_BUSINESS_RULES")
public class ClassBenefits implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLASS_BENEFIT_ID", nullable = false, updatable = false)
	private Long classBenefitId;

	@Column(name = "POLICY_CLASS_ID", nullable = false)
	private Long policyClassId;

	@Column(name = "APPROVAL_THRESHOLD_CURRENCY")
	private String approvalThresholdCurrency;

	@Column(name = "APPROVAL_THRESHOLD_VALUE")
	private BigDecimal approvalThresholdValue;

	@Column(name = "BENEFIT_CODE")
	private String benefitCode;

	@Column(name = "BENEFIT_DESCRIPTION")
	private String benefitDescription;

	@Column(name = "BENEFIT_LIMIT_CURRENCY")
	private String benefitLimitCurrency;

	@Column(name = "BENEFIT_LIMIT_VALUE")
	private BigDecimal benefitLimitValue;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "COVERAGE")
	private String coverage;

	@Column(name = "EXCLUSION")
	private String exclusion;

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

	public Long getPolicyClassId() {
		return policyClassId;
	}

	public void setPolicyClassId(Long policyClassId) {
		this.policyClassId = policyClassId;
	}

	public void setClassBenefitId(Long classBenefitId) {
		this.classBenefitId = classBenefitId;
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

	public String getBenefitCode() {
		return this.benefitCode;
	}

	public void setBenefitCode(String benefitCode) {
		this.benefitCode = benefitCode;
	}

	public String getBenefitDescription() {
		return this.benefitDescription;
	}

	public void setBenefitDescription(String benefitDescription) {
		this.benefitDescription = benefitDescription;
	}

	public String getBenefitLimitCurrency() {
		return this.benefitLimitCurrency;
	}

	public void setBenefitLimitCurrency(String benefitLimitCurrency) {
		this.benefitLimitCurrency = benefitLimitCurrency;
	}

	public BigDecimal getBenefitLimitValue() {
		return this.benefitLimitValue;
	}

	public void setBenefitLimitValue(BigDecimal benefitLimitValue) {
		this.benefitLimitValue = benefitLimitValue;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCoverage() {
		return this.coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public String getExclusion() {
		return this.exclusion;
	}

	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
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

	public Long getClassBenefitId() {
		return classBenefitId;
	}
}