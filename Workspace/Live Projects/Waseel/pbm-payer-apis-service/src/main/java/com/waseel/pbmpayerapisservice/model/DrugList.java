package com.waseel.pbmpayerapisservice.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.waseel.pbmpayerapisservice.validator.customannotation.IsValidUnitType;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan2000Length;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan200Length;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan20Length;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan256Length;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan30Length;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan50Length;

public class DrugList {

	@NoMoreThan50Length(message = "drugCode {noMoreThan50LengthValidation}")
	private String drugCode;

	@NoMoreThan256Length(message = "drugDescription {noMoreThan256LengthValidation}")
	private String drugDescription;

	@NotEmpty(message = "scientificName {notEmptyValidation}")
	@NoMoreThan2000Length(message = "scientificName {noMoreThan2000LengthValidation}")
	private String scientificName;

	@NotEmpty(message = "unitType {notEmptyValidation}")
	@NoMoreThan30Length(message = "unitType {noMoreThan30LengthValidation}")
	@IsValidUnitType(message = "{unitTypeValidation}")
	private String unitType;

	@NotNull(message = "quantity {notEmptyValidation}")
	@DecimalMin(value = "1", message = "quantity {valueMoreThanOrEqualTo1}")
	@DecimalMax(value = "999", message = "quantity {valueLessThanOrEqualTo999}")
	private BigDecimal quantity;

	@Digits(integer = 14, fraction = 2, message = "unitPrice {noMoreThan14With2DecimalPrecisionValidation}")
	private Double unitPrice;

	@NoMoreThan20Length(message = "orderingClinician {noMoreThan20LengthValidation}")
	private String orderingClinician;

	@NotNull(message = "duration {notEmptyValidation}")
	@Min(value = 1, message = "duration {valueMoreThanOrEqualTo1}")
	private BigDecimal duration;

	@NotNull(message = "useUnitValue {notEmptyValidation}")
	@DecimalMin(value = "1", message = "UseUnitValue {valueMoreThanOrEqualTo1}")
	@DecimalMax(value = "99", message = "UseUnitValue {valueLessThanOrEqualTo99}")
	private BigDecimal useUnitValue;

	@NotEmpty(message = "frequency {notEmptyValidation}")
	@NoMoreThan30Length(message = "frequency {noMoreThan30LengthValidation}")
	private String frequency;

	@NoMoreThan200Length(message = "frequencyOthersDescription {noMoreThan200LengthValidation}")
	private String frequencyOthersDescription;

	@NotNull(message = "serviceStartDate {notEmptyValidation}")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date serviceStartDate;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date serviceEndDate;

	@NotNull(message = "patientShareValue {notEmptyValidation}")
	private BigDecimal patientShareValue;

	@NotNull(message = "patientShareCurrency {notEmptyValidation}")
	private String patientShareCurrency;

	@NotNull(message = "payerShareValue {notEmptyValidation}")
	private BigDecimal payerShareValue;

	@NotNull(message = "payerShareCurrency {notEmptyValidation}")
	private String payerShareCurrency;

	@NotNull(message = "pbmValidationResult {notEmptyValidation}")
	@Valid
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

}