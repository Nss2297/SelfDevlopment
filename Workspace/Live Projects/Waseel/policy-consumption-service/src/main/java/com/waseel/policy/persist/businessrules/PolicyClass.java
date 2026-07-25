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

import org.hibernate.annotations.Where;

/**
 * The persistent class for the POLICY_CLASSES database table.
 * 
 */
@Entity
@Table(name = "POLICY_CLASSES")
@NamedQuery(name = "PolicyClass.findAll", query = "SELECT p FROM PolicyClass p")
@Where(clause = "IS_ENABLED='1'")
public class PolicyClass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "POLICY_CLASS_ID")
	private long policyClassId;

	@Column(name = "CLASS_CODE")
	private String classCode;

	@Column(name = "CLASS_LIMIT_CURRENCY")
	private String classLimitCurrency;

	@Column(name = "CLASS_LIMIT_VALUE")
	private BigDecimal classLimitValue;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "COVERAGE")
	private String coverage;

	@Column(name = "EXCLUSION")
	private String exclusion;

	@Column(name = "IS_ENABLED", columnDefinition = "CHAR(1) default ('1')", nullable = false)
	private Boolean isEnabled = true;

	// bi-directional many-to-one association to ClassBenefit
	@OneToMany(mappedBy = "policyClass")
	private List<ClassBenefit> classBenefits;

	// bi-directional many-to-one association to DrugFormularyPolicyAssociation
	@OneToMany(mappedBy = "policyClass")
	private List<DrugFormularyPolicyAssociation> drugFormularyPolicyAssociations;

	// bi-directional many-to-one association to MemberPolicyAssociation
	@OneToMany(mappedBy = "policyClass")
	private List<MemberPolicyAssociation> memberPolicyAssociations;

	// bi-directional many-to-one association to PolicyInformation
	@ManyToOne
	@JoinColumn(name = "POLICY_INFORMATION_ID")
	private PolicyInformation policyInformation;

	public PolicyClass() {
	}

	public long getPolicyClassId() {
		return this.policyClassId;
	}

	public void setPolicyClassId(long policyClassId) {
		this.policyClassId = policyClassId;
	}

	public String getClassCode() {
		return this.classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getClassLimitCurrency() {
		return this.classLimitCurrency;
	}

	public void setClassLimitCurrency(String classLimitCurrency) {
		this.classLimitCurrency = classLimitCurrency;
	}

	public BigDecimal getClassLimitValue() {
		return this.classLimitValue;
	}

	public void setClassLimitValue(BigDecimal classLimitValue) {
		this.classLimitValue = classLimitValue;
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

	public List<ClassBenefit> getClassBenefits() {
		return this.classBenefits;
	}

	public void setClassBenefits(List<ClassBenefit> classBenefits) {
		this.classBenefits = classBenefits;
	}

	public ClassBenefit addClassBenefit(ClassBenefit classBenefit) {
		getClassBenefits().add(classBenefit);
		classBenefit.setPolicyClass(this);

		return classBenefit;
	}

	public ClassBenefit removeClassBenefit(ClassBenefit classBenefit) {
		getClassBenefits().remove(classBenefit);
		classBenefit.setPolicyClass(null);

		return classBenefit;
	}

	public List<DrugFormularyPolicyAssociation> getDrugFormularyPolicyAssociations() {
		return this.drugFormularyPolicyAssociations;
	}

	public void setDrugFormularyPolicyAssociations(
			List<DrugFormularyPolicyAssociation> drugFormularyPolicyAssociations) {
		this.drugFormularyPolicyAssociations = drugFormularyPolicyAssociations;
	}

	public DrugFormularyPolicyAssociation addDrugFormularyPolicyAssociation(
			DrugFormularyPolicyAssociation drugFormularyPolicyAssociation) {
		getDrugFormularyPolicyAssociations().add(drugFormularyPolicyAssociation);
		drugFormularyPolicyAssociation.setPolicyClass(this);

		return drugFormularyPolicyAssociation;
	}

	public DrugFormularyPolicyAssociation removeDrugFormularyPolicyAssociation(
			DrugFormularyPolicyAssociation drugFormularyPolicyAssociation) {
		getDrugFormularyPolicyAssociations().remove(drugFormularyPolicyAssociation);
		drugFormularyPolicyAssociation.setPolicyClass(null);

		return drugFormularyPolicyAssociation;
	}

	public List<MemberPolicyAssociation> getMemberPolicyAssociations() {
		return this.memberPolicyAssociations;
	}

	public void setMemberPolicyAssociations(List<MemberPolicyAssociation> memberPolicyAssociations) {
		this.memberPolicyAssociations = memberPolicyAssociations;
	}

	public MemberPolicyAssociation addMemberPolicyAssociation(MemberPolicyAssociation memberPolicyAssociation) {
		getMemberPolicyAssociations().add(memberPolicyAssociation);
		memberPolicyAssociation.setPolicyClass(this);

		return memberPolicyAssociation;
	}

	public MemberPolicyAssociation removeMemberPolicyAssociation(MemberPolicyAssociation memberPolicyAssociation) {
		getMemberPolicyAssociations().remove(memberPolicyAssociation);
		memberPolicyAssociation.setPolicyClass(null);

		return memberPolicyAssociation;
	}

	public PolicyInformation getPolicyInformation() {
		return this.policyInformation;
	}

	public void setPolicyInformation(PolicyInformation policyInformation) {
		this.policyInformation = policyInformation;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}