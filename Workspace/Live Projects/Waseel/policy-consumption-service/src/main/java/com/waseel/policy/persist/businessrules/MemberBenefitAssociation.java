package com.waseel.policy.persist.businessrules;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER_BENEFIT_ASSOICATION", schema = "PBM_BUSINESS_RULES")
public class MemberBenefitAssociation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_BENEFIT_ASSOICATION_ID", nullable = false, updatable = false)
	private Long memberBenefitAssociationId;

	@Column(name = "MEMBER_POLICY_ASSOCIATION_ID", unique = true)
	private Long memberPolicyAssociationId;

	@Column(name = "CLASS_BENEFIT_ID", unique = true)
	private Long classBenefitId;

	@Column(name = "BENEFIT_LOCKED_AMOUNT_VALUE")
	private Long benefitLockedAmountValue;

	@Column(name = "BENEFIT_LOCKED_AMOUNT_CURRENCY", length = 30)
	private String benefitLockedAmountCurrency;

	@Column(name = "BENEFIT_REMAINING_LIMIT_VALUE")
	private Long benefitRemainingLimitValue;

	@Column(name = "BENEFIT_REMAINING_LIMIT_CURR", length = 30)
	private String benefitRemainingLimitCurrency;

	@Column(name = "IS_ENABLED", nullable = false, columnDefinition = "CHAR(1) default ('0')")
	private Boolean isEnabled = false;

	public Long getMemberBenefitAssociationId() {
		return memberBenefitAssociationId;
	}

	public void setMemberBenefitAssociationId(Long memberBenefitAssociationId) {
		this.memberBenefitAssociationId = memberBenefitAssociationId;
	}

	public Long getMemberPolicyAssociationId() {
		return memberPolicyAssociationId;
	}

	public void setMemberPolicyAssociationId(Long memberPolicyAssociationId) {
		this.memberPolicyAssociationId = memberPolicyAssociationId;
	}

	public Long getClassBenefitId() {
		return classBenefitId;
	}

	public void setClassBenefitId(Long classBenefitId) {
		this.classBenefitId = classBenefitId;
	}

	public Long getBenefitLockedAmountValue() {
		return benefitLockedAmountValue;
	}

	public void setBenefitLockedAmountValue(Long benefitLockedAmountValue) {
		this.benefitLockedAmountValue = benefitLockedAmountValue;
	}

	public String getBenefitLockedAmountCurrency() {
		return benefitLockedAmountCurrency;
	}

	public void setBenefitLockedAmountCurrency(String benefitLockedAmountCurrency) {
		this.benefitLockedAmountCurrency = benefitLockedAmountCurrency;
	}

	public Long getBenefitRemainingLimitValue() {
		return benefitRemainingLimitValue;
	}

	public void setBenefitRemainingLimitValue(Long benefitRemainingLimitValue) {
		this.benefitRemainingLimitValue = benefitRemainingLimitValue;
	}

	public String getBenefitRemainingLimitCurrency() {
		return benefitRemainingLimitCurrency;
	}

	public void setBenefitRemainingLimitCurrency(String benefitRemainingLimitCurrency) {
		this.benefitRemainingLimitCurrency = benefitRemainingLimitCurrency;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
