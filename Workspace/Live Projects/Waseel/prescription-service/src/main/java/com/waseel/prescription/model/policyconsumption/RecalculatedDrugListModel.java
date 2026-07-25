package com.waseel.prescription.model.policyconsumption;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecalculatedDrugListModel {

	private String drugCode;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private String status;
	private BigDecimal patientShare;
	private BigDecimal payerShare;

	public String getDrugCode() {
		return drugCode;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public String getStatus() {
		return status;
	}

	public BigDecimal getPatientShare() {
		return patientShare;
	}

	public BigDecimal getPayerShare() {
		return payerShare;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPatientShare(BigDecimal patientShare) {
		this.patientShare = patientShare;
	}

	public void setPayerShare(BigDecimal payerShare) {
		this.payerShare = payerShare;
	}
}
