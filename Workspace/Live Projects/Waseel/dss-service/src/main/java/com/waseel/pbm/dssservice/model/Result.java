package com.waseel.pbm.dssservice.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "ndcDrugCode","scientificCode", "dispensedQuantity", "amount", "daysOfSupply", "status", "errors" })
public class Result {

	@JsonProperty("ndcDrugCode")
	private String ndcDrugCode;
	@JsonProperty("scientificCode")
	private String scientificCode;
	@JsonProperty("dispensedQuantity")
	private BigDecimal dispensedQuantity;
	@JsonProperty("amount")
	private Double amount;
	@JsonProperty("daysOfSupply")
	private String daysOfSupply;
	@JsonProperty("status")
	private String status;
	@JsonProperty("errors")
	private List<Error> errors = null;

	@JsonProperty("ndcDrugCode")
	public String getNdcDrugCode() {
		return ndcDrugCode;
	}

	@JsonProperty("ndcDrugCode")
	public void setNdcDrugCode(String ndcDrugCode) {
		this.ndcDrugCode = ndcDrugCode;
	}
	
	@JsonProperty("scientificCode")
	public String getScientificCode() {
		return scientificCode;
	}

	@JsonProperty("scientificCode")
	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	@JsonProperty("dispensedQuantity")

	public BigDecimal getDispensedQuantity() {
		return dispensedQuantity;
	}

	@JsonProperty("dispensedQuantity")
	public void setDispensedQuantity(BigDecimal dispensedQuantity) {
		this.dispensedQuantity = dispensedQuantity;
	}

	@JsonProperty("amount")
	public Double getAmount() {
		return amount;
	}

	@JsonProperty("amount")
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@JsonProperty("daysOfSupply")
	public String getDaysOfSupply() {
		return daysOfSupply;
	}

	@JsonProperty("daysOfSupply")
	public void setDaysOfSupply(String daysOfSupply) {
		this.daysOfSupply = daysOfSupply;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("errors")
	public List<Error> getErrors() {
		return errors;
	}

	@JsonProperty("errors")
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

}
