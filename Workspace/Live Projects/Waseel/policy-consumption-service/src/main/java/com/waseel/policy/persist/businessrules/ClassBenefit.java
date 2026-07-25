package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the CLASS_BENEFITS database table.
 * 
 */
@Entity
@Table(name = "CLASS_BENEFITS")
@NamedQuery(name = "ClassBenefit.findAll", query = "SELECT c FROM ClassBenefit c")
public class ClassBenefit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CLASS_BENEFIT_ID")
	private long classBenefitId;

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

	// bi-directional many-to-one association to BenefitCas
	@OneToMany(mappedBy = "classBenefit")
	private List<BenefitCas> benefitCases;

	// bi-directional many-to-one association to BenefitSubcoverage
	@OneToMany(mappedBy = "classBenefit")
	private List<BenefitSubcoverage> benefitSubcoverages;

	// bi-directional many-to-one association to PolicyClass
	@ManyToOne
	@JoinColumn(name = "POLICY_CLASS_ID")
	private PolicyClass policyClass;

	// bi-directional many-to-one association to MemberBenefitAssoication
	@OneToMany(mappedBy = "classBenefit")
	private List<MemberBenefitAssoication> memberBenefitAssoications;

	public ClassBenefit() {
	}

	public long getClassBenefitId() {
		return this.classBenefitId;
	}

	public void setClassBenefitId(long classBenefitId) {
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

	public List<BenefitCas> getBenefitCases() {
		return this.benefitCases;
	}

	public void setBenefitCases(List<BenefitCas> benefitCases) {
		this.benefitCases = benefitCases;
	}

	public BenefitCas addBenefitCas(BenefitCas benefitCas) {
		getBenefitCases().add(benefitCas);
		benefitCas.setClassBenefit(this);

		return benefitCas;
	}

	public BenefitCas removeBenefitCas(BenefitCas benefitCas) {
		getBenefitCases().remove(benefitCas);
		benefitCas.setClassBenefit(null);

		return benefitCas;
	}

	public List<BenefitSubcoverage> getBenefitSubcoverages() {
		return this.benefitSubcoverages;
	}

	public void setBenefitSubcoverages(List<BenefitSubcoverage> benefitSubcoverages) {
		this.benefitSubcoverages = benefitSubcoverages;
	}

	public BenefitSubcoverage addBenefitSubcoverage(BenefitSubcoverage benefitSubcoverage) {
		getBenefitSubcoverages().add(benefitSubcoverage);
		benefitSubcoverage.setClassBenefit(this);

		return benefitSubcoverage;
	}

	public BenefitSubcoverage removeBenefitSubcoverage(BenefitSubcoverage benefitSubcoverage) {
		getBenefitSubcoverages().remove(benefitSubcoverage);
		benefitSubcoverage.setClassBenefit(null);

		return benefitSubcoverage;
	}

	public PolicyClass getPolicyClass() {
		return this.policyClass;
	}

	public void setPolicyClass(PolicyClass policyClass) {
		this.policyClass = policyClass;
	}

	public List<MemberBenefitAssoication> getMemberBenefitAssoications() {
		return this.memberBenefitAssoications;
	}

	public void setMemberBenefitAssoications(List<MemberBenefitAssoication> memberBenefitAssoications) {
		this.memberBenefitAssoications = memberBenefitAssoications;
	}

	public MemberBenefitAssoication addMemberBenefitAssoication(MemberBenefitAssoication memberBenefitAssoication) {
		getMemberBenefitAssoications().add(memberBenefitAssoication);
		memberBenefitAssoication.setClassBenefit(this);

		return memberBenefitAssoication;
	}

	public MemberBenefitAssoication removeMemberBenefitAssoication(MemberBenefitAssoication memberBenefitAssoication) {
		getMemberBenefitAssoications().remove(memberBenefitAssoication);
		memberBenefitAssoication.setClassBenefit(null);

		return memberBenefitAssoication;
	}

}