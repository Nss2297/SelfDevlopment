package com.waseel.pbm.pbmadminservice.model.drugformulary;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;

import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan256Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan50Length;

public class DrugFormularyDrugDetailsRequestModel {

	@NotBlank(message = "drugCode {notNullOrEmpty}")
	@NoMoreThan50Length(message = "drugCode {noMoreThan50LengthValidation}")
	private String drugCode;
	@NotBlank(message = "drugName {notNullOrEmpty}")
	@NoMoreThan256Length(message = "drugName {noMoreThan256LengthValidation}")
	private String drugName;
	@NotBlank(message = "genericName {notNullOrEmpty}")
	@NoMoreThan256Length(message = "genericName {noMoreThan256LengthValidation}")
	private String genericName;
	@NotNull(message = "price {notNullOrEmpty}")
	private BigDecimal price;
	private boolean isOverride;
	private BigDecimal patientShare;

	public DrugFormularyDrugDetailsRequestModel() {
	}

	public DrugFormularyDrugDetailsRequestModel(String drugCode, String drugName, String genericName,
			BigDecimal price) {
		this.drugCode = drugCode;
		this.drugName = drugName;
		this.genericName = genericName;
		this.price = price;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = !StringUtils.isBlank(drugCode) ? drugCode.trim() : drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = !StringUtils.isBlank(drugName) ? drugName.trim() : drugName;
	}

	public String getGenericName() {
		return genericName;
	}

	public void setGenericName(String genericName) {
		this.genericName = !StringUtils.isBlank(genericName) ? genericName.trim() : genericName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isOverride() {
		return isOverride;
	}

	public void setOverride(boolean isOverride) {
		this.isOverride = isOverride;
	}

	public BigDecimal getPatientShare() {
		return patientShare;
	}

	public void setPatientShare(BigDecimal patientShare) {
		this.patientShare = patientShare;
	}
}
