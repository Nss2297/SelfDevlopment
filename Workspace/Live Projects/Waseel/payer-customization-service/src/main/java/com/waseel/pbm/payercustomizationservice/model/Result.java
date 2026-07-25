package com.waseel.pbm.payercustomizationservice.model;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "ndcDrugCode", "dispensedQuantity", "amount", "daysOfSupply", "status","scientificCode", "errors"})
public class Result {

	@JsonProperty("ndcDrugCode")
	private String ndcDrugCode;
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
	
	@JsonProperty("scientificCode")
	private String scientificCode;
	
	@JsonProperty("scientificCode")
	public String getScientificCode() {
		return scientificCode;
	}

	@JsonProperty("scientificCode")
	public void setScientificCode(String scientificCode) {
		this.scientificCode = StringUtils.isNotBlank(scientificCode) ? scientificCode.trim() : scientificCode;
	}

	public Result() {
	}

	public Result(String ndcDrugCode, BigDecimal dispensedQuantity, Double amount, String daysOfSupply, String status,
			List<Error> errors) {
		this.ndcDrugCode = ndcDrugCode;
		this.dispensedQuantity = dispensedQuantity;
		this.amount = amount;
		this.daysOfSupply = daysOfSupply;
		this.status = status;
		this.errors = errors;
	}

	public Result(String ndcDrugCode, String scientificCode, BigDecimal dispensedQuantity, Double amount,
			String daysOfSupply, String status, List<Error> errors) {
		this.ndcDrugCode = ndcDrugCode;
		this.dispensedQuantity = dispensedQuantity;
		this.amount = amount;
		this.daysOfSupply = daysOfSupply;
		this.status = status;
		this.errors = errors;
		this.scientificCode = scientificCode;
	}

	public Result(String ndcDrugCode, BigDecimal dispensedQuantity, Double amount, String daysOfSupply, String status,
			List<Error> errors,String scientificCode) {
		this.ndcDrugCode = ndcDrugCode;
		this.dispensedQuantity = dispensedQuantity;
		this.amount = amount;
		this.daysOfSupply = daysOfSupply;
		this.status = status;
		this.errors = errors;
		this.scientificCode = scientificCode;
	}

	
	@JsonProperty("ndcDrugCode")
	public String getNdcDrugCode() {
		return ndcDrugCode;
	}

	@JsonProperty("ndcDrugCode")
	public void setNdcDrugCode(String ndcDrugCode) {
		this.ndcDrugCode = StringUtils.isNotBlank(ndcDrugCode) ? ndcDrugCode.trim() : ndcDrugCode;
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
