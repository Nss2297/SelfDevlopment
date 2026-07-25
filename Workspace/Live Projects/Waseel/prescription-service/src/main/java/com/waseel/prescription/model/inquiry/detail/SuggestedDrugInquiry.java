package com.waseel.prescription.model.inquiry.detail;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuggestedDrugInquiry {

	@JsonProperty("unitPrice")
	private String unitPrice;
	@JsonProperty("totalPrice")
	private String totalPrice;
	@JsonProperty("sfdaCode")
	private String sfdaCode;
	@JsonProperty("sfdaDescription")
	private String sfdaDescription;
	@JsonProperty("benefitCase")
	private String benefitCase;
	@JsonProperty("isApprovalRequired")
	private Boolean isApprovalRequired;

	// ADDED AFTER CHI IMPLEMENTATION
	@JsonProperty("maxPatientShare")
	private BigDecimal maxPatientShareAmount;
	@JsonProperty("maxPatientShareCurrency")
	private String maxPatientShareCurrency;
	@JsonProperty("patientShare")
	private BigDecimal patientShare;
	@JsonProperty("patientShareCurrency")
	private String patientShareCurrency;

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getSfdaCode() {
		return sfdaCode;
	}

	public void setSfdaCode(String sfdaCode) {
		this.sfdaCode = sfdaCode;
	}

	public String getSfdaDescription() {
		return sfdaDescription;
	}

	public void setSfdaDescription(String sfdaDescription) {
		this.sfdaDescription = sfdaDescription;
	}

	public String getBenefitCase() {
		return benefitCase;
	}

	public void setBenefitCase(String benefitCase) {
		this.benefitCase = benefitCase;
	}

	public Boolean getIsApprovalRequired() {
		return isApprovalRequired;
	}

	public void setIsApprovalRequired(Boolean isApprovalRequired) {
		this.isApprovalRequired = isApprovalRequired;
	}

	public BigDecimal getMaxPatientShareAmount() {
		return maxPatientShareAmount;
	}

	public void setMaxPatientShareAmount(BigDecimal maxPatientShareAmount) {
		this.maxPatientShareAmount = maxPatientShareAmount;
	}

	public String getMaxPatientShareCurrency() {
		return maxPatientShareCurrency;
	}

	public void setMaxPatientShareCurrency(String maxPatientShareCurrency) {
		this.maxPatientShareCurrency = maxPatientShareCurrency;
	}

	public BigDecimal getPatientShare() {
		return patientShare;
	}

	public void setPatientShare(BigDecimal patientShare) {
		this.patientShare = patientShare;
	}

	public String getPatientShareCurrency() {
		return patientShareCurrency;
	}

	public void setPatientShareCurrency(String patientShareCurrency) {
		this.patientShareCurrency = patientShareCurrency;
	}

}
