package com.waseel.pbm.fdbvalidationservice.model;

import java.math.BigDecimal;

import com.fdb.mkfi.core.DispensableGeneric;

public class FdbDrugList {

	// Form SFDA -- Need To Check If we Can Modify Dss Request and Pass all these
	// info at once
	private String scientificName;
	private String dosageForm;
	private String strength;
	private String strengthUnit;
	private String display;

	// Form FDB
	private Integer gcnSeqNo;
	private String productPackageUnit; // refers to solid , cream , liquied
	private Double productPackageSize; // size of box , tube or bottle
	private DispensableGeneric dispensableGeneric;

	// From Dss Request
	private String drugCode;
	private String scientificCode;
	private BigDecimal dispensedQuantity;
	private Double amount;
	private String daysOfSupply;
	private Boolean isDrugCodeMappedFromScientificCode;

	public FdbDrugList() {
		super();
	}

	public FdbDrugList(String drugCode, String scientificCode, String scientificName, String display, Integer gcnSeqNo,
			String productPackageUnit, Double productPackageSize, String dosageForm, String strength,
			String strengthUnit, DispensableGeneric dispensableGeneric, BigDecimal dispensedQuantity, Double amount,
			String daysOfSupply, Boolean isDrugCodeMappedFromScientificCode) {
		super();
		this.drugCode = drugCode;
		this.scientificCode = scientificCode;
		this.scientificName = scientificName;
		this.gcnSeqNo = gcnSeqNo;
		this.productPackageUnit = productPackageUnit;
		this.productPackageSize = productPackageSize;
		this.dispensableGeneric = dispensableGeneric;
		this.dispensedQuantity = dispensedQuantity;
		this.amount = amount;
		this.daysOfSupply = daysOfSupply;
		this.isDrugCodeMappedFromScientificCode = isDrugCodeMappedFromScientificCode;
		this.dosageForm=dosageForm;
		this.strength = strength;
		this.strengthUnit = strengthUnit;
		this.display = display;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public Integer getGcnSeqNo() {
		return gcnSeqNo;
	}

	public void setGcnSeqNo(Integer gcnSeqNo) {
		this.gcnSeqNo = gcnSeqNo;
	}

	public BigDecimal getDispensedQuantity() {
		return dispensedQuantity;
	}

	public void setDispensedQuantity(BigDecimal dispensedQuantity) {
		this.dispensedQuantity = dispensedQuantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDaysOfSupply() {
		return daysOfSupply;
	}

	public void setDaysOfSupply(String daysOfSupply) {
		this.daysOfSupply = daysOfSupply;
	}

	public String getProductPackageUnit() {
		return productPackageUnit;
	}

	public void setProductPackageUnit(String productPackageUnit) {
		this.productPackageUnit = productPackageUnit;
	}

	public Double getProductPackageSize() {
		return productPackageSize;
	}

	public void setProductPackageSize(Double productPackageSize) {
		this.productPackageSize = productPackageSize;
	}

	public DispensableGeneric getDispensableGeneric() {
		return dispensableGeneric;
	}

	public void setDispensableGeneric(DispensableGeneric dispensableGeneric) {
		this.dispensableGeneric = dispensableGeneric;
	}

	public Boolean getIsDrugCodeMappedFromScientificCode() {
		return isDrugCodeMappedFromScientificCode;
	}

	public void setIsDrugCodeMappedFromScientificCode(Boolean isDrugCodeMappedFromScientificCode) {
		this.isDrugCodeMappedFromScientificCode = isDrugCodeMappedFromScientificCode;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getDosageForm() {
		return dosageForm;
	}

	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getStrengthUnit() {
		return strengthUnit;
	}

	public void setStrengthUnit(String strengthUnit) {
		this.strengthUnit = strengthUnit;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	
	

}
