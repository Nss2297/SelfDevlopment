package com.waseel.policy.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DrugListModel {

	@NotEmpty(message = "drugCode {notEmptyValidation}")
	private String drugCode;
	@NotNull(message = "amount {notEmptyValidation}")
	private BigDecimal amount;
	private BigDecimal payerShare;
	private BigDecimal patientShare;
	private String patientShareCurrency;
	private String payerShareCurrency;
	private BigDecimal maxPatientShareValue;
	private String maxPatientShareCurrency;
	private String benefitCase;

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPayerShare() {
		return payerShare;
	}

	public void setPayerShare(BigDecimal payerShare) {
		this.payerShare = payerShare;
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

	public String getPayerShareCurrency() {
		return payerShareCurrency;
	}

	public void setPatientShareCurrency(String patientShareCurrency) {
		this.patientShareCurrency = patientShareCurrency;
	}

	public void setPayerShareCurrency(String payerShareCurrency) {
		this.payerShareCurrency = payerShareCurrency;
	}

	public DrugListModel() {
		super();
	}

	public BigDecimal getMaxPatientShareValue() {
		return maxPatientShareValue;
	}

	public void setMaxPatientShareValue(BigDecimal maxPatientShareValue) {
		this.maxPatientShareValue = maxPatientShareValue;
	}

	public String getMaxPatientShareCurrency() {
		return maxPatientShareCurrency;
	}

	public void setMaxPatientShareCurrency(String maxPatientShareCurrency) {
		this.maxPatientShareCurrency = maxPatientShareCurrency;
	}

	public String getBenefitCase() {
		return benefitCase;
	}

	public void setBenefitCase(String benefitCase) {
		this.benefitCase = benefitCase;
	}

	public DrugListModel(String drugCode, BigDecimal patientShare, String patientShareCurrency) {
		super();
		this.drugCode = drugCode;
		this.patientShare = patientShare;
		this.patientShareCurrency = patientShareCurrency;
	}

	public DrugListModel(String drugCode, BigDecimal patientShare, String patientShareCurrency,
			BigDecimal maxPatientShareValue, String maxPatientShareCurrency) {
		super();
		this.drugCode = drugCode;
		this.patientShare = patientShare;
		this.patientShareCurrency = patientShareCurrency;
		this.maxPatientShareValue = maxPatientShareValue;
		this.maxPatientShareCurrency = maxPatientShareCurrency;
	}

	public DrugListModel(String drugCode, BigDecimal patientShare, String patientShareCurrency,
			BigDecimal maxPatientShareValue, String maxPatientShareCurrency, String benefitCase) {
		super();
		this.drugCode = drugCode;
		this.patientShare = patientShare;
		this.patientShareCurrency = patientShareCurrency;
		this.maxPatientShareValue = maxPatientShareValue;
		this.maxPatientShareCurrency = maxPatientShareCurrency;
		this.benefitCase = benefitCase;
	}

	public DrugListModel(String drugCode) {
		super();
		this.drugCode = drugCode;
	}

	public DrugListModel(String drugCode, String benefitCase) {
		super();
		this.drugCode = drugCode;
		this.benefitCase = benefitCase;
	}
}
