package com.waseel.prescription.model.dss;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"ndcDrugCode", "dispensedQuantity", "amount", "daysOfSupply"})
public class DssDrugList {

    @JsonProperty("ndcDrugCode")
    private String ndcDrugCode;

    @JsonProperty("dispensedQuantity")
    private BigDecimal dispensedQuantity;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("daysOfSupply")
    private String daysOfSupply;

    @JsonProperty("scientificCode")
    private String scientificCode;

    @JsonProperty("ndcDrugCode")
    public String getNdcDrugCode() {
        return ndcDrugCode;
    }

    @JsonProperty("ndcDrugCode")
    public void setNdcDrugCode(String ndcDrugCode) {
        this.ndcDrugCode = ndcDrugCode != null ? ndcDrugCode.trim() : ndcDrugCode;
    }

    @JsonProperty("daysOfSupply")
    public String getDaysOfSupply() {
        return daysOfSupply;
    }

    @JsonProperty("daysOfSupply")
    public void setDaysOfSupply(String daysOfSupply) {
        this.daysOfSupply = daysOfSupply.trim();
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

    public String getScientificCode() {
        return scientificCode;
    }

    public void setScientificCode(String scientificCode) {
        this.scientificCode = scientificCode;
    }
}