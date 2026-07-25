package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the MEMBER_BENEFIT_ASSOICATION database table.
 * 
 */
@Entity
@Table(name="MEMBER_BENEFIT_ASSOICATION")
@NamedQuery(name="MemberBenefitAssoication.findAll", query="SELECT m FROM MemberBenefitAssoication m")
public class MemberBenefitAssoication implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MEMBER_BENEFIT_ASSOICATION_ID")
	private long memberBenefitAssoicationId;

	@Column(name="BENEFIT_LOCKED_AMOUNT_CURRENCY")
	private String benefitLockedAmountCurrency;

	@Column(name="BENEFIT_LOCKED_AMOUNT_VALUE")
	private BigDecimal benefitLockedAmountValue;

	@Column(name="BENEFIT_REMAINING_LIMIT_CURR")
	private String benefitRemainingLimitCurr;

	@Column(name="BENEFIT_REMAINING_LIMIT_VALUE")
	private BigDecimal benefitRemainingLimitValue;

	@Column(name="IS_ENABLED")
	private String isEnabled;

	//bi-directional many-to-one association to ClassBenefit
	@ManyToOne
	@JoinColumn(name="CLASS_BENEFIT_ID")
	private ClassBenefit classBenefit;

	//bi-directional many-to-one association to MemberPolicyAssociation
	@ManyToOne
	@JoinColumn(name="MEMBER_POLICY_ASSOCIATION_ID")
	private MemberPolicyAssociation memberPolicyAssociation;

	public MemberBenefitAssoication() {
	}

	public long getMemberBenefitAssoicationId() {
		return this.memberBenefitAssoicationId;
	}

	public void setMemberBenefitAssoicationId(long memberBenefitAssoicationId) {
		this.memberBenefitAssoicationId = memberBenefitAssoicationId;
	}

	public String getBenefitLockedAmountCurrency() {
		return this.benefitLockedAmountCurrency;
	}

	public void setBenefitLockedAmountCurrency(String benefitLockedAmountCurrency) {
		this.benefitLockedAmountCurrency = benefitLockedAmountCurrency;
	}

	public BigDecimal getBenefitLockedAmountValue() {
		return this.benefitLockedAmountValue;
	}

	public void setBenefitLockedAmountValue(BigDecimal benefitLockedAmountValue) {
		this.benefitLockedAmountValue = benefitLockedAmountValue;
	}

	public String getBenefitRemainingLimitCurr() {
		return this.benefitRemainingLimitCurr;
	}

	public void setBenefitRemainingLimitCurr(String benefitRemainingLimitCurr) {
		this.benefitRemainingLimitCurr = benefitRemainingLimitCurr;
	}

	public BigDecimal getBenefitRemainingLimitValue() {
		return this.benefitRemainingLimitValue;
	}

	public void setBenefitRemainingLimitValue(BigDecimal benefitRemainingLimitValue) {
		this.benefitRemainingLimitValue = benefitRemainingLimitValue;
	}

	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	public ClassBenefit getClassBenefit() {
		return this.classBenefit;
	}

	public void setClassBenefit(ClassBenefit classBenefit) {
		this.classBenefit = classBenefit;
	}

	public MemberPolicyAssociation getMemberPolicyAssociation() {
		return this.memberPolicyAssociation;
	}

	public void setMemberPolicyAssociation(MemberPolicyAssociation memberPolicyAssociation) {
		this.memberPolicyAssociation = memberPolicyAssociation;
	}

}