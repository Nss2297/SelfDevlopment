package com.waseel.prescription.model.inquiry.eprescription;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EPrescriptionInquiryDrugList {

	private String drugCode;
	private String drugDescription;
	private String scientificName;
	private String unitType;
	private BigDecimal quantity;
	private Double unitPrice;
	private String orderingClinician;
	private BigDecimal duration;
	private BigDecimal useUnitValue;
	private String frequency;
	private String frequencyOthersDescription;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date serviceStartDate;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date serviceEndDate;
	private BigDecimal patientShareValue;
	private String patientShareCurrency;
	private BigDecimal payerShareValue;
	private String payerShareCurrency;
	private PbmValidationResult pbmValidationResult;
	private InsuranceCompanyDecision insuranceCompanyDecision;
	private BigDecimal patientShare;
	private BigDecimal payerShare;

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

	public InsuranceCompanyDecision getInsuranceCompanyDecision() {
		return insuranceCompanyDecision;
	}

	public void setInsuranceCompanyDecision(InsuranceCompanyDecision insuranceCompanyDecision) {
		this.insuranceCompanyDecision = insuranceCompanyDecision;
	}

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

	public Date getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public Date getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(Date serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}

	public BigDecimal getPatientShare() {
		return patientShare;
	}

	public BigDecimal getPayerShare() {
		return payerShare;
	}

	public void setPatientShare(BigDecimal patientShare) {
		this.patientShare = patientShare;
	}

	public void setPayerShare(BigDecimal payerShare) {
		this.payerShare = payerShare;
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

	public EPrescriptionInquiryDrugList() {
		super();
	}

	public EPrescriptionInquiryDrugList(String drugCode, String unitType, BigDecimal quantity, Double unitPrice,
			String orderingClinician, BigDecimal duration, BigDecimal useUnitValue, String frequency,
			String frequencyOthersDescription, Date serviceStartDate, Date serviceEndDate, BigDecimal patientShareValue,
			String patientShareCurrency, BigDecimal payerShareValue, String payerShareCurrency,
			PbmValidationResult pbmValidationResult, InsuranceCompanyDecision insuranceCompanyDecision,
			BigDecimal patientShare, BigDecimal payerShare) {
		super();
		this.drugCode = drugCode;
		this.unitType = unitType;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.orderingClinician = orderingClinician;
		this.duration = duration;
		this.useUnitValue = useUnitValue;
		this.frequency = frequency;
		this.frequencyOthersDescription = frequencyOthersDescription;
		this.serviceStartDate = serviceStartDate;
		this.serviceEndDate = serviceEndDate;
		this.patientShareValue = patientShareValue;
		this.patientShareCurrency = patientShareCurrency;
		this.payerShareValue = payerShareValue;
		this.payerShareCurrency = payerShareCurrency;
		this.pbmValidationResult = pbmValidationResult;
		this.insuranceCompanyDecision = insuranceCompanyDecision;
		this.patientShare = patientShare;
		this.payerShare = payerShare;
	}
}