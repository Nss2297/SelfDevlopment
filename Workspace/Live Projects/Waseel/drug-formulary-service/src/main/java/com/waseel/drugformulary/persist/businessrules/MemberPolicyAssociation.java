package com.waseel.drugformulary.persist.businessrules;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "MEMBER_POLICY_ASSOCIATION", schema = "PBM_BUSINESS_RULES")
@Where(clause = "IS_ENABLED='1'")
public class MemberPolicyAssociation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_POLICY_ASSOCIATION_ID", nullable = false, updatable = false)
	private Long memberPolicyAssociationId;

	@Column(name = "MEMBER_PROFILE_ID", unique = true)
	private Long memberProfileId;

	@Column(name = "POLICY_INFORMATION_ID", unique = true)
	private Long policyInformationId;

	@Column(name = "POLICY_CLASS_ID")
	private Long policyClassId;

	@Column(name = "MEMBER_ID", nullable = false, length = 50, unique = true)
	private String memberId;

	@Column(name = "MEMBER_TYPE", length = 50)
	private String memberType;

	@Column(name = "MEMBER_SINCE")
	private Date memberSince;

	@Column(name = "IS_CANCELLED", nullable = false, columnDefinition = "CHAR(1) default ('0')")
	private Boolean isCancelled = false;

	@Column(name = "CANCELLATION_DATE")
	private Date cancellationDate;

	@Column(name = "CLASS_LIMIT_VALUE")
	private Long classLimitValue;

	@Column(name = "CLASS_LIMIT_CURRENCY", length = 10)
	private String classLimitCurrency;

	@Column(name = "CLASS_REMAINING_LIMIT_VALUE")
	private Long classRemainingLimitValue;

	@Column(name = "CLASS_REMAINING_LIMIT_CURRENCY", length = 10)
	private String classRemainingLimitCurrency;

	@Column(name = "IS_ENABLED", columnDefinition = "CHAR(1) default ('1')", nullable = false)
	private Boolean isEnabled = true;

	public Long getMemberPolicyAssociationId() {
		return memberPolicyAssociationId;
	}

	public void setMemberPolicyAssociationId(Long memberPolicyAssociationId) {
		this.memberPolicyAssociationId = memberPolicyAssociationId;
	}

	public Long getMemberProfileId() {
		return memberProfileId;
	}

	public void setMemberProfileId(Long memberProfileId) {
		this.memberProfileId = memberProfileId;
	}

	public Long getPolicyInformationId() {
		return policyInformationId;
	}

	public void setPolicyInformationId(Long policyInformationId) {
		this.policyInformationId = policyInformationId;
	}

	public Long getPolicyClassId() {
		return policyClassId;
	}

	public void setPolicyClassId(Long policyClassId) {
		this.policyClassId = policyClassId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public Date getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}

	public Boolean getIsCancelled() {
		return isCancelled;
	}

	public void setIsCancelled(Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public Date getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(Date cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

	public Long getClassLimitValue() {
		return classLimitValue;
	}

	public void setClassLimitValue(Long classLimitValue) {
		this.classLimitValue = classLimitValue;
	}

	public String getClassLimitCurrency() {
		return classLimitCurrency;
	}

	public void setClassLimitCurrency(String classLimitCurrency) {
		this.classLimitCurrency = classLimitCurrency;
	}

	public Long getClassRemainingLimitValue() {
		return classRemainingLimitValue;
	}

	public void setClassRemainingLimitValue(Long classRemainingLimitValue) {
		this.classRemainingLimitValue = classRemainingLimitValue;
	}

	public String getClassRemainingLimitCurrency() {
		return classRemainingLimitCurrency;
	}

	public void setClassRemainingLimitCurrency(String classRemainingLimitCurrency) {
		this.classRemainingLimitCurrency = classRemainingLimitCurrency;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}
