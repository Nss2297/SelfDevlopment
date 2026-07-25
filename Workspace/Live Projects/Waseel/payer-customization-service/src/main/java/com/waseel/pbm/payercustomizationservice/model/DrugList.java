package com.waseel.pbm.payercustomizationservice.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "ndcDrugCode", "dispensedQuantity", "amount", "daysOfSupply","scientificCode" })
public class DrugList {

	@JsonProperty("ndcDrugCode")
	private String ndcDrugCode;

	@JsonProperty("dispensedQuantity")
	private BigDecimal dispensedQuantity;

	@JsonProperty("amount")
	private Double amount;

	@JsonProperty("daysOfSupply")
	private String daysOfSupply;
	
	@JsonProperty("scientificCode")
	private String scientificCode;
	
	@JsonProperty("scientificCode")
	public String getScientificCode() {
		return scientificCode;
	}

	@JsonProperty("scientificCode")
	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode.trim();
	}

	@JsonProperty("ndcDrugCode")
	public String getNdcDrugCode() {
		return ndcDrugCode;
	}

	@JsonProperty("ndcDrugCode")
	public void setNdcDrugCode(String ndcDrugCode) {
		this.ndcDrugCode = ndcDrugCode.trim();
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
		this.daysOfSupply = daysOfSupply.trim();
	}

}