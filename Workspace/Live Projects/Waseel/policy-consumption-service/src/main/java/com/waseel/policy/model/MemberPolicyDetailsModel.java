package com.waseel.policy.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.waseel.policy.enums.CurrencyType;

@JsonInclude(Include.NON_NULL)
public class MemberPolicyDetailsModel {

	private BigDecimal benefitLimitValue;
	private BigDecimal remainingLimitValue;
	private BigDecimal patientShareValue;
	private String patientShareCurrency;
	private BigDecimal maxPatientShareValue;
	private String maxPatientShareCurrency = CurrencyType.SAR.value();;
	private String policyNumber;
	private String policyClass;
	private String memberId;
	private String benefitLimitCurrency;
	private BigDecimal totalRemainingLimitAmount;
	private String totalRemainingLimitCurrency;
	@JsonIgnore
	private BrandAndGenericModel brandAndGenericModel;

	public BigDecimal getBenefitLimitValue() {
		return benefitLimitValue;
	}

	public void setBenefitLimitValue(BigDecimal benefitLimitValue) {
		this.benefitLimitValue = benefitLimitValue;
	}

	public BigDecimal getRemainingLimitValue() {
		return remainingLimitValue;
	}

	public void setRemainingLimitValue(BigDecimal remainingLimitValue) {
		this.remainingLimitValue = remainingLimitValue;
	}

	public BigDecimal getPatientShareValue() {
		return patientShareValue;
	}

	public void setPatientShareValue(BigDecimal patientShareValue) {
		this.patientShareValue = patientShareValue;
	}

	public String getPatientShareCurrency() {
		return patientShareCurrency;
	}

	public void setPatientShareCurrency(String patientShareCurrency) {
		this.patientShareCurrency = patientShareCurrency;
	}

	public BigDecimal getMaxPatientShareValue() {
		return maxPatientShareValue;
	}

	public void setMaxPatientShareValue(BigDecimal maxPatientShareValue) {
		this.maxPatientShareValue = maxPatientShareValue;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getPolicyClass() {
		return policyClass;
	}

	public void setPolicyClass(String policyClass) {
		this.policyClass = policyClass;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getBenefitLimitCurrency() {
		return benefitLimitCurrency;
	}

	public void setBenefitLimitCurrency(String benefitLimitCurrency) {
		this.benefitLimitCurrency = benefitLimitCurrency;
	}

	public BigDecimal getTotalRemainingLimitAmount() {
		return totalRemainingLimitAmount;
	}

	public void setTotalRemainingLimitAmount(BigDecimal totalRemainingLimitAmount) {
		this.totalRemainingLimitAmount = totalRemainingLimitAmount;
	}

	public BrandAndGenericModel getBrandAndGenericModel() {
		return brandAndGenericModel;
	}

	public void setBrandAndGenericModel(BrandAndGenericModel brandAndGenericModel) {
		this.brandAndGenericModel = brandAndGenericModel;
	}

	public String getTotalRemainingLimitCurrency() {
		return totalRemainingLimitCurrency;
	}

	public void setTotalRemainingLimitCurrency(String totalRemainingLimitCurrency) {
		this.totalRemainingLimitCurrency = totalRemainingLimitCurrency;
	}

	public String getMaxPatientShareCurrency() {
		return maxPatientShareCurrency;
	}

	public void setMaxPatientShareCurrency(String maxPatientShareCurrency) {
		this.maxPatientShareCurrency = maxPatientShareCurrency;
	}

	public MemberPolicyDetailsModel() {
		super();
	}

	public MemberPolicyDetailsModel(BigDecimal benefitLimitValue, BigDecimal remainingLimitValue,
			BigDecimal patientShareValue, String patientShareCurrency, BigDecimal maxPatientShareValue,
			String policyNumber, String policyClass, String memberId, String benefitLimitCurrency,
			BigDecimal totalRemainingLimitAmount) {
		super();
		this.benefitLimitValue = benefitLimitValue;
		this.remainingLimitValue = remainingLimitValue;
		this.patientShareValue = patientShareValue;
		this.patientShareCurrency = patientShareCurrency;
		this.maxPatientShareValue = maxPatientShareValue;
		this.policyNumber = policyNumber;
		this.policyClass = policyClass;
		this.memberId = memberId;
		this.benefitLimitCurrency = benefitLimitCurrency;
		this.totalRemainingLimitAmount = totalRemainingLimitAmount;
	}

	public MemberPolicyDetailsModel(BigDecimal benefitLimitValue, BigDecimal remainingLimitValue,
			BigDecimal patientShareValue, String patientShareCurrency, BigDecimal maxPatientShareValue,
			String policyNumber, String policyClass, String memberId, String benefitLimitCurrency,
			BigDecimal totalRemainingLimitAmount, BrandAndGenericModel brandAndGenericModel,
			String totalRemainingLimitCurrency) {
		super();
		this.benefitLimitValue = benefitLimitValue;
		this.remainingLimitValue = remainingLimitValue;
		this.patientShareValue = patientShareValue;
		this.patientShareCurrency = patientShareCurrency;
		this.maxPatientShareValue = maxPatientShareValue;
		this.policyNumber = policyNumber;
		this.policyClass = policyClass;
		this.memberId = memberId;
		this.benefitLimitCurrency = benefitLimitCurrency;
		this.totalRemainingLimitAmount = totalRemainingLimitAmount;
		this.brandAndGenericModel = brandAndGenericModel;
		this.totalRemainingLimitCurrency = totalRemainingLimitCurrency;
	}

}
