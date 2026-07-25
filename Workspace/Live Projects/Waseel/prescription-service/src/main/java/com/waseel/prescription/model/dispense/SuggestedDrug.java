package com.waseel.prescription.model.dispense;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuggestedDrug {

	@JsonProperty("unitPrice")
	private String unitPrice;
	@JsonProperty("quantity")
	private Integer quantity;
	@JsonProperty("totalPrice")
	private String totalPrice;

	@JsonProperty("net")
	private String net;
	@JsonProperty("sfdaCode")
	private String sfdaCode;
	@JsonProperty("sfdaDescription")
	private String sfdaDescription;
	@JsonProperty("dosageForm")
	private String dosageForm;
	@JsonProperty("strengthUnit")
	private String strengthUnit;
	@JsonProperty("strength")
	private String strength;
	@JsonProperty("roaSuggested")
	private String roaSuggested;
	@JsonProperty("inExclusionList")
	private Boolean inExclusionList;
	@JsonProperty("drugFormulary")
	private Boolean drugFormulary;
	@JsonProperty("isApproved")
	private Boolean isApproved;
	@JsonProperty("benefitCase")
	private String benefitCase;
	@JsonProperty("isApprovalRequired")
	private Boolean isApprovalRequired;
	@JsonProperty("patientShare")
	private String patientShare;
	@JsonProperty("maxPatientShare")
	private BigDecimal maxPatientShareAmount;
	@JsonProperty("maxPatientShareCurrency")
	private String maxPatientShareCurrency;
	@JsonProperty("patientShareCurrency")
	private String patientShareCurrency;
	private BigDecimal patientShareVatAmount = BigDecimal.ZERO;
	private String patientShareVatCurrency = "SAR";

	public SuggestedDrug(String unitPrice, String sfdaCode, String sfdaDescription, String dosageForm,
			String strengthUnit, String strength, String roaSuggested) {
		super();
		this.unitPrice = unitPrice;
		this.sfdaCode = sfdaCode;
		this.sfdaDescription = sfdaDescription;
		this.dosageForm = dosageForm;
		this.strengthUnit = strengthUnit;
		this.strength = strength;
		this.roaSuggested = roaSuggested;
	}

	public SuggestedDrug(String unitPrice, String sfdaCode, String sfdaDescription, String dosageForm,
			String strengthUnit, String strength, String roaSuggested, String totalPrice) {
		super();
		this.unitPrice = unitPrice;
		this.sfdaCode = sfdaCode;
		this.sfdaDescription = sfdaDescription;
		this.dosageForm = dosageForm;
		this.strengthUnit = strengthUnit;
		this.strength = strength;
		this.roaSuggested = roaSuggested;
		this.totalPrice = totalPrice;
	}

	public SuggestedDrug(String unitPrice, String totalPrice, String sfdaCode, String sfdaDescription,
			String dosageForm, String strengthUnit, String strength, String roaSuggested,
			BigDecimal maxPatientShareAmount, String maxPatientShareCurrency, String patientShare,
			String patientShareCurrency) {
		super();
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
		this.sfdaCode = sfdaCode;
		this.sfdaDescription = sfdaDescription;
		this.dosageForm = dosageForm;
		this.strengthUnit = strengthUnit;
		this.strength = strength;
		this.roaSuggested = roaSuggested;
		this.maxPatientShareAmount = maxPatientShareAmount;
		this.maxPatientShareCurrency = maxPatientShareCurrency;
		this.patientShare = patientShare;
		this.patientShareCurrency = patientShareCurrency;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public String getDosageForm() {
		return dosageForm;
	}

	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}

	public String getStrengthUnit() {
		return strengthUnit;
	}

	public void setStrengthUnit(String strengthUnit) {
		this.strengthUnit = strengthUnit;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getRoaSuggested() {
		return roaSuggested;
	}

	public void setRoaSuggested(String roaSuggested) {
		this.roaSuggested = roaSuggested;
	}

	public Boolean getInExclusionList() {
		return inExclusionList;
	}

	public void setInExclusionList(Boolean inExclusionList) {
		this.inExclusionList = inExclusionList;
	}

	public Boolean getDrugFormulary() {
		return drugFormulary;
	}

	public void setDrugFormulary(Boolean drugFormulary) {
		this.drugFormulary = drugFormulary;
	}

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

	public String getPatientShare() {
		return patientShare;
	}

	public void setPatientShare(String patientShare) {
		this.patientShare = patientShare;
	}

	public String getNet() {
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
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

	public BigDecimal getPatientShareVatAmount() {
		return patientShareVatAmount;
	}

	public void setPatientShareVatAmount(BigDecimal patientShareVatAmount) {
		this.patientShareVatAmount = patientShareVatAmount;
	}

	public String getPatientShareVatCurrency() {
		return patientShareVatCurrency;
	}

	public void setPatientShareVatCurrency(String patientShareVatCurrency) {
		this.patientShareVatCurrency = patientShareVatCurrency;
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

	public String getPatientShareCurrency() {
		return patientShareCurrency;
	}

	public void setPatientShareCurrency(String patientShareCurrency) {
		this.patientShareCurrency = patientShareCurrency;
	}
}
