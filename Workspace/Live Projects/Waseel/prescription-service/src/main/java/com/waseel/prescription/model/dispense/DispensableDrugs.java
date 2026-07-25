package com.waseel.prescription.model.dispense;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waseel.prescription.validator.customannotation.NoMoreThan2000Length;
import com.waseel.prescription.validator.customannotation.NoMoreThan64Length;
import com.waseel.prescription.validator.customannotation.NoMoreThanFiftyLength;
import com.waseel.prescription.validator.customannotation.NoMoreThanTenLength;

public class DispensableDrugs {
	
	@NotBlank(message = "scientificName {emptyDateValidation}")
	@NoMoreThan2000Length(message = "scientificName {noMoreThan2000LengthValidation}")
    private String scientificName;
	
	@NotBlank(message = "scientificCode {emptyDateValidation}")
	@NoMoreThan64Length(message = "scientificCode {noMoreThan64LengthValidation}")
    private String scientificCode;
	
	@NotBlank(message = "drugCode {emptyDateValidation}")
	@NoMoreThanFiftyLength(message = "drugCode {noMoreThanFiftyLengthValidation}")
    private String drugCode;
	
	@NotNull(message = "unitPrice {notEmptyValidation}")
	@Digits(integer = 12, fraction = 2, message = "unitPrice {noMoreThanTwelveWithTwoDecimalPrecisionValidation}")
    private Double unitPrice; 
	
	@NotNull(message = "quantity {notEmptyValidation}")
	@DecimalMin(value = "1", message = "quantity value should be more than or equal to 1")
	@DecimalMax(value = "999", message = "quantity value should be less than or equal to 999")
    private Integer quantity;
	
	@NotNull(message = "patientShare {notEmptyValidation}")
	@Digits(integer = 12, fraction = 2, message = "patientShare {noMoreThanTwelveWithTwoDecimalPrecisionValidation}")
    private BigDecimal patientShare;
	
	@NotNull(message = "net {notEmptyValidation}")
	@Digits(integer = 12, fraction = 2, message = "net {noMoreThanTwelveWithTwoDecimalPrecisionValidation}")
    private BigDecimal net;
	
	@NotBlank(message = "patientShareCurrency {emptyDateValidation}")
	@NoMoreThanTenLength(message = "patientShareCurrency {noMoreThanTenLengthValidation}")
    private String patientShareCurrency;
	
	@NotBlank(message = "netCurrency {emptyDateValidation}")
	@NoMoreThanTenLength(message = "netCurrency {noMoreThanTenLengthValidation}")
	private String netCurrency;
	
	@JsonProperty("isApprovalRequired")
	private boolean approvalRequired;
	
	public DispensableDrugs() {
	}

	public DispensableDrugs(String scientificName, String scientificCode, String drugCode, Double unitPrice,
			Integer quantity, BigDecimal patientShare, BigDecimal net, String patientShareCurrency, String netCurrency,
			boolean isApprovalRequired) {
		this.scientificName = scientificName;
		this.scientificCode = scientificCode;
		this.drugCode = drugCode;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.patientShare = patientShare;
		this.net = net;
		this.patientShareCurrency = patientShareCurrency;
		this.netCurrency = netCurrency;
		this.approvalRequired = isApprovalRequired;
	}
	
	public String getScientificName() {
        return scientificName;
    }

    public boolean isApprovalRequired() {
		return approvalRequired;
	}

	public void setApprovalRequired(boolean approvalRequired) {
		this.approvalRequired = approvalRequired;
	}

	public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getScientificCode() {
        return scientificCode;
    }

    public void setScientificCode(String scientificCode) {
        this.scientificCode = scientificCode;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPatientShare() {
        return patientShare;
    }

    public void setPatientShare(BigDecimal patientShare) {
        this.patientShare = patientShare;
    }

    public BigDecimal getNet() {
        return net;
    }

    public void setNet(BigDecimal net) {
        this.net = net;
    }

    public String getPatientShareCurrency() {
        return patientShareCurrency;
    }

    public void setPatientShareCurrency(String patientShareCurrency) {
        this.patientShareCurrency = patientShareCurrency;
    }

    public String getNetCurrency() {
        return netCurrency;
    }

    public void setNetCurrency(String netCurrency) {
        this.netCurrency = netCurrency;
    }
}
