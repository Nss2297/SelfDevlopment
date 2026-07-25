package com.waseel.prescription.model.dss;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "ndcDrugCode", "dispensedQuantity", "amount", "daysOfSupply", "status", "errors" })
public class Result {

	@JsonProperty("ndcDrugCode")
	private String ndcDrugCode;
	@JsonProperty("dispensedQuantity")
	private BigDecimal dispensedQuantity;
	@JsonProperty("amount")
	private BigDecimal amount;
	@JsonProperty("daysOfSupply")
	private String daysOfSupply;
	@JsonProperty("status")
	private String status;
	@JsonProperty("errors")
	private List<Error> errors = null;
	@JsonProperty("scientificCode")
	private String scientificCode;

	@JsonProperty("ndcDrugCode")
	public String getNdcDrugCode() {
		return ndcDrugCode;
	}

	@JsonProperty("ndcDrugCode")
	public void setNdcDrugCode(String ndcDrugCode) {
		this.ndcDrugCode = ndcDrugCode;
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

	public BigDecimal getDispensedQuantity() {
		return dispensedQuantity;
	}

	public void setDispensedQuantity(BigDecimal dispensedQuantity) {
		this.dispensedQuantity = dispensedQuantity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@JsonProperty("errors")
	public List<Error> getErrors() {
		return errors;
	}

	@JsonProperty("errors")
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public Result(String ndcDrugCode, BigDecimal dispensedQuantity, BigDecimal amount, String daysOfSupply,
				  String status, List<Error> errors) {
		super();
		this.ndcDrugCode = ndcDrugCode;
		this.dispensedQuantity = dispensedQuantity;
		this.amount = amount;
		this.daysOfSupply = daysOfSupply;
		this.status = status;
		this.errors = errors;
	}

	public Result() {
		super();
	}
}
