package com.waseel.prescription.model.prescription;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponse {

	private String drugCode;
	
	private String scientificCode;

	private String unitType;

	private Double unitPrice;

	private BigDecimal quantity;

	private BigDecimal requestedAmount;

	private BigDecimal approvedAmount;

	private Double discount;

	private BigDecimal patientShare;

	private BigDecimal net;

	private String status;

	@Schema(hidden = true)
	private String statusDescription;

	private List<MedicalValidations> errors = null;

	@Hidden
	private BusinessRuleValidations businessRuleError = null;

	private String patientShareCurrency;

	private String netCurrency;
	
	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<MedicalValidations> getErrors() {
		return errors;
	}

	public void setErrors(List<MedicalValidations> errors) {
		this.errors = errors;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public BigDecimal getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(BigDecimal requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public BigDecimal getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(BigDecimal approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BusinessRuleValidations getBusinessRuleError() {
		return businessRuleError;
	}

	public void setBusinessRuleError(BusinessRuleValidations businessRuleError) {
		this.businessRuleError = businessRuleError;
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

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getPatientShareCurrency() {
		return patientShareCurrency;
	}

	public String getNetCurrency() {
		return netCurrency;
	}

	public void setPatientShareCurrency(String patientShareCurrency) {
		this.patientShareCurrency = patientShareCurrency;
	}

	public void setNetCurrency(String netCurrency) {
		this.netCurrency = netCurrency;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public ServiceResponse() {
		super();
	}

	public ServiceResponse(String drugCode, String unitType, Double unitPrice, BigDecimal quantity,
			BigDecimal requestedAmount, BigDecimal approvedAmount, Double discount, BigDecimal patientShare,
			BigDecimal net, String status, String statusDescription) {
		super();
		this.drugCode = drugCode;
		this.unitType = unitType;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.requestedAmount = requestedAmount;
		this.approvedAmount = approvedAmount;
		this.discount = discount;
		this.patientShare = patientShare;
		this.net = net;
		this.status = status;
		this.statusDescription = statusDescription;
	}
	
	public ServiceResponse(String scientificCode,String drugCode, String unitType, Double unitPrice, BigDecimal quantity,
			BigDecimal requestedAmount, BigDecimal approvedAmount, Double discount, BigDecimal patientShare,
			BigDecimal net, String status, String statusDescription) {
		this.scientificCode = scientificCode;
		this.drugCode = drugCode;
		this.unitType = unitType;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.requestedAmount = requestedAmount;
		this.approvedAmount = approvedAmount;
		this.discount = discount;
		this.patientShare = patientShare;
		this.net = net;
		this.status = status;
		this.statusDescription = statusDescription;
	}

}
