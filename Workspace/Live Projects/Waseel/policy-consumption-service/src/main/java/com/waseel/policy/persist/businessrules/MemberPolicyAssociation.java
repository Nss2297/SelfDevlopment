package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;

/**
 * The persistent class for the MEMBER_POLICY_ASSOCIATION database table.
 * 
 */
@Entity
@Table(name = "MEMBER_POLICY_ASSOCIATION")
@NamedQuery(name = "MemberPolicyAssociation.findAll", query = "SELECT m FROM MemberPolicyAssociation m")
@Where(clause = "IS_ENABLED='1'")
public class MemberPolicyAssociation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MEMBER_POLICY_ASSOCIATION_ID")
	private long memberPolicyAssociationId;

	@Temporal(TemporalType.DATE)
	@Column(name = "CANCELLATION_DATE")
	private Date cancellationDate;

	@Column(name = "CLASS_LIMIT_CURRENCY")
	private String classLimitCurrency;

	@Column(name = "CLASS_LIMIT_VALUE")
	private BigDecimal classLimitValue;

	@Column(name = "CLASS_REMAINING_LIMIT_CURRENCY")
	private String classRemainingLimitCurrency;

	@Column(name = "CLASS_REMAINING_LIMIT_VALUE")
	private BigDecimal classRemainingLimitValue;

	@Column(name = "IS_CANCELLED")
	private String isCancelled;

	@Column(name = "MEMBER_ID")
	private String memberId;

	@Temporal(TemporalType.DATE)
	@Column(name = "MEMBER_SINCE")
	private Date memberSince;

	@Column(name = "MEMBER_TYPE")
	private String memberType;

	@Column(name = "IS_ENABLED", columnDefinition = "CHAR(1) default ('1')", nullable = false)
	private Boolean isEnabled = true;

	// bi-directional many-to-one association to DrugFormularyPolicyAssociation
	@OneToMany(mappedBy = "memberPolicyAssociation", fetch = FetchType.LAZY)
	private List<DrugFormularyPolicyAssociation> drugFormularyPolicyAssociations;

	// bi-directional many-to-one association to MemberBenefitAssoication
	@OneToMany(mappedBy = "memberPolicyAssociation", fetch = FetchType.LAZY)
	private List<MemberBenefitAssoication> memberBenefitAssoications;

	// bi-directional many-to-one association to MemberProfile
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_PROFILE_ID")
	private MemberProfile memberProfile;

	// bi-directional many-to-one association to PolicyClass
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POLICY_CLASS_ID")
	private PolicyClass policyClass;

	// bi-directional many-to-one association to PolicyInformation
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POLICY_INFORMATION_ID")
	private PolicyInformation policyInformation;

	public MemberPolicyAssociation() {
	}

	public long getMemberPolicyAssociationId() {
		return this.memberPolicyAssociationId;
	}

	public void setMemberPolicyAssociationId(long memberPolicyAssociationId) {
		this.memberPolicyAssociationId = memberPolicyAssociationId;
	}

	public Date getCancellationDate() {
		return this.cancellationDate;
	}

	public void setCancellationDate(Date cancellationDate) {
		this.cancellationDate = cancellationDate;
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

	public String getClassRemainingLimitCurrency() {
		return this.classRemainingLimitCurrency;
	}

	public void setClassRemainingLimitCurrency(String classRemainingLimitCurrency) {
		this.classRemainingLimitCurrency = classRemainingLimitCurrency;
	}

	public BigDecimal getClassRemainingLimitValue() {
		return this.classRemainingLimitValue;
	}

	public void setClassRemainingLimitValue(BigDecimal classRemainingLimitValue) {
		this.classRemainingLimitValue = classRemainingLimitValue;
	}

	public String getIsCancelled() {
		return this.isCancelled;
	}

	public void setIsCancelled(String isCancelled) {
		this.isCancelled = isCancelled;
	}

	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getMemberSince() {
		return this.memberSince;
	}

	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}

	public String getMemberType() {
		return this.memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
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
		drugFormularyPolicyAssociation.setMemberPolicyAssociation(this);

		return drugFormularyPolicyAssociation;
	}

	public DrugFormularyPolicyAssociation removeDrugFormularyPolicyAssociation(
			DrugFormularyPolicyAssociation drugFormularyPolicyAssociation) {
		getDrugFormularyPolicyAssociations().remove(drugFormularyPolicyAssociation);
		drugFormularyPolicyAssociation.setMemberPolicyAssociation(null);

		return drugFormularyPolicyAssociation;
	}

	public List<MemberBenefitAssoication> getMemberBenefitAssoications() {
		return this.memberBenefitAssoications;
	}

	public void setMemberBenefitAssoications(List<MemberBenefitAssoication> memberBenefitAssoications) {
		this.memberBenefitAssoications = memberBenefitAssoications;
	}

	public MemberBenefitAssoication addMemberBenefitAssoication(MemberBenefitAssoication memberBenefitAssoication) {
		getMemberBenefitAssoications().add(memberBenefitAssoication);
		memberBenefitAssoication.setMemberPolicyAssociation(this);

		return memberBenefitAssoication;
	}

	public MemberBenefitAssoication removeMemberBenefitAssoication(MemberBenefitAssoication memberBenefitAssoication) {
		getMemberBenefitAssoications().remove(memberBenefitAssoication);
		memberBenefitAssoication.setMemberPolicyAssociation(null);

		return memberBenefitAssoication;
	}

	public MemberProfile getMemberProfile() {
		return this.memberProfile;
	}

	public void setMemberProfile(MemberProfile memberProfile) {
		this.memberProfile = memberProfile;
	}

	public PolicyClass getPolicyClass() {
		return this.policyClass;
	}

	public void setPolicyClass(PolicyClass policyClass) {
		this.policyClass = policyClass;
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