package com.waseel.prescription.model.pbmpayerapis;

import java.math.BigDecimal;

public class EPrescriptionDrugList {

	private String drugCode;
	private String drugDescription;
	private String scientificName;
	private String scientificCode;
	private String unitType;
	private BigDecimal quantity;
	private Double unitPrice;
	private String orderingClinician;
	private BigDecimal duration;
	private BigDecimal useUnitValue;
	private String frequency;
	private String frequencyOthersDescription;
	private String serviceStartDate;
	private String serviceEndDate;
	private BigDecimal patientShareValue;
	private String patientShareCurrency;
	private BigDecimal patientShareVatAmountValue = BigDecimal.ZERO;
	private String patientShareVatAmountCurrency = "SAR";
	private BigDecimal payerShareValue;
	private String payerShareCurrency;
	private PbmValidationResult pbmValidationResult;

	public PbmValidationResult getPbmValidationResult() {
		return pbmValidationResult;
	}

	public void setPbmValidationResult(PbmValidationResult pbmValidationResult) {
		this.pbmValidationResult = pbmValidationResult;
	}

	public String getFrequencyOthersDescription() {
		return frequencyOthersDescription;
	}

	public void setFrequencyOthersDescription(String frequencyOthersDescription) {
		this.frequencyOthersDescription = frequencyOthersDescription;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getOrderingClinician() {
		return orderingClinician;
	}

	public void setOrderingClinician(String orderingClinician) {
		this.orderingClinician = orderingClinician;
	}

	public BigDecimal getDuration() {
		return duration;
	}

	public void setDuration(BigDecimal duration) {
		this.duration = duration;
	}

	public BigDecimal getUseUnitValue() {
		return useUnitValue;
	}

	public void setUseUnitValue(BigDecimal useUnitValue) {
		this.useUnitValue = useUnitValue;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(String serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public String getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(String serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
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

	public BigDecimal getPayerShareValue() {
		return payerShareValue;
	}

	public void setPayerShareValue(BigDecimal payerShareValue) {
		this.payerShareValue = payerShareValue;
	}

	public String getPayerShareCurrency() {
		return payerShareCurrency;
	}

	public void setPayerShareCurrency(String payerShareCurrency) {
		this.payerShareCurrency = payerShareCurrency;
	}

	public String getDrugDescription() {
		return drugDescription;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setDrugDescription(String drugDescription) {
		this.drugDescription = drugDescription;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public BigDecimal getPatientShareVatAmountValue() {
		return patientShareVatAmountValue;
	}

	public void setPatientShareVatAmountValue(BigDecimal patientShareVatAmountValue) {
		this.patientShareVatAmountValue = patientShareVatAmountValue;
	}

	public String getPatientShareVatAmountCurrency() {
		return patientShareVatAmountCurrency;
	}

	public void setPatientShareVatAmountCurrency(String patientShareVatAmountCurrency) {
		this.patientShareVatAmountCurrency = patientShareVatAmountCurrency;
	}
}