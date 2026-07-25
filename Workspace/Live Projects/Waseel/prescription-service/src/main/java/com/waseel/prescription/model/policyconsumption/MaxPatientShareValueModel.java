package com.waseel.prescription.model.policyconsumption;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class MaxPatientShareValueModel {

	@JsonProperty("maxPatientShare")
	private BigDecimal maxPatientShareAmount;

	@JsonProperty("maxPatientShareCurrency")
	private String maxPatientShareCurrency;

	@JsonProperty("patientShare")
	private BigDecimal patientSharePercentage;
	
	@JsonProperty("patientShareCurrency")
	private String patientShareCurrency;
	
	@JsonProperty("payerShare")
	private BigDecimal payerSharePercentage;
	
	@JsonProperty("payerShareCurrency")
	private String payerShareCurrency;
	
	public BigDecimal getPayerSharePercentage() {
		return payerSharePercentage;
	}

	public void setPayerSharePercentage(BigDecimal payerSharePercentage) {
		this.payerSharePercentage = payerSharePercentage;
	}

	public String getPatientShareCurrency() {
		return patientShareCurrency;
	}

	public void setPatientShareCurrency(String patientShareCurrency) {
		this.patientShareCurrency = patientShareCurrency;
	}

	public String getPayerShareCurrency() {
		return payerShareCurrency;
	}

	public void setPayerShareCurrency(String payerShareCurrency) {
		this.payerShareCurrency = payerShareCurrency;
	}

	public BigDecimal getMaxPatientShareAmount() {
		return maxPatientShareAmount;
	}

	public String getMaxPatientShareCurrency() {
		return maxPatientShareCurrency;
	}

	public BigDecimal getPatientSharePercentage() {
		return patientSharePercentage;
	}

	public void setMaxPatientShareAmount(BigDecimal maxPatientShareAmount) {
		this.maxPatientShareAmount = maxPatientShareAmount;
	}

	public void setMaxPatientShareCurrency(String maxPatientShareCurrency) {
		this.maxPatientShareCurrency = maxPatientShareCurrency;
	}

	public void setPatientSharePercentage(BigDecimal patientSharePercentage) {
		this.patientSharePercentage = patientSharePercentage;
	}

	public MaxPatientShareValueModel() {
		super();
	}

	public MaxPatientShareValueModel(BigDecimal maxPatientShareAmount, String maxPatientShareCurrency,
			BigDecimal patientSharePercentage) {
		super();
		this.maxPatientShareAmount = maxPatientShareAmount;
		this.maxPatientShareCurrency = maxPatientShareCurrency;
		this.patientSharePercentage = patientSharePercentage;
	}

}
