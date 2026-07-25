package com.waseel.policy.model.client;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonInclude(Include.NON_NULL)
@JsonTypeName("policyInformation")
public class PolicyDetailsModel {

	private String policyNumber;
	private String policyHolderName;
	private String memberId;
	private String memberType;
	private String policyClass;
	private String planType;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date memberSince;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date expiryDate;
	private BigDecimal remainingLimitValue;
	private String remainingLimitCurrency;
	private Boolean isChiPolicy;
	private List<PolicyClassBenefitsModel> classBenefits;

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getPolicyHolderName() {
		return policyHolderName;
	}

	public void setPolicyHolderName(String policyHolderName) {
		this.policyHolderName = policyHolderName;
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

	public String getPolicyClass() {
		return policyClass;
	}

	public void setPolicyClass(String policyClass) {
		this.policyClass = policyClass;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public Date getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public BigDecimal getRemainingLimitValue() {
		return remainingLimitValue;
	}

	public void setRemainingLimitValue(BigDecimal remainingLimitValue) {
		this.remainingLimitValue = remainingLimitValue;
	}

	public String getRemainingLimitCurrency() {
		return remainingLimitCurrency;
	}

	public void setRemainingLimitCurrency(String remainingLimitCurrency) {
		this.remainingLimitCurrency = remainingLimitCurrency;
	}

	public List<PolicyClassBenefitsModel> getClassBenefits() {
		return classBenefits;
	}

	public void setClassBenefits(List<PolicyClassBenefitsModel> classBenefits) {
		this.classBenefits = classBenefits;
	}

	public Boolean getIsChiPolicy() {
		return isChiPolicy;
	}

	public void setIsChiPolicy(Boolean isChiPolicy) {
		this.isChiPolicy = isChiPolicy;
	}

	public PolicyDetailsModel() {
		super();
	}

	public PolicyDetailsModel(String policyNumber, String policyHolderName, String memberId, String memberType,
			String policyClass, String planType, Date memberSince, Date expiryDate, BigDecimal remainingLimitValue,
			String remainingLimitCurrency, List<PolicyClassBenefitsModel> classBenefits) {
		super();
		this.policyNumber = policyNumber;
		this.policyHolderName = policyHolderName;
		this.memberId = memberId;
		this.memberType = memberType;
		this.policyClass = policyClass;
		this.planType = planType;
		this.memberSince = memberSince;
		this.expiryDate = expiryDate;
		this.remainingLimitValue = remainingLimitValue;
		this.remainingLimitCurrency = remainingLimitCurrency;
		this.classBenefits = classBenefits;
	}

}
