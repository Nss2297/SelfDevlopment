package com.waseel.pbm.pbmadminservice.model.drugexclusion;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan150Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan256Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan50Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NumericValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExclusionListDrugDetailsRequestModel {

	@NotEmpty(message = "drugCode {notNullOrEmpty}")
	@NoMoreThan50Length(message = "drugCode {noMoreThan50LengthValidation}")
	private String drugCode;

	@NotEmpty(message = "drugName {notNullOrEmpty}")
	@NoMoreThan256Length(message = "drugName {noMoreThan256LengthValidation}")
	private String drugName;

	@NotEmpty(message = "scientificName {notNullOrEmpty}")
	@NoMoreThan256Length(message = "scientificName {noMoreThan256LengthValidation}")
	private String scientificName;

	@NotEmpty(message = "scientificCode {notNullOrEmpty}")
	@NoMoreThan150Length(message = "scientificName {noMoreThan150LengthValidation}")
	private String scientificCode;

	@NotEmpty(message = "lastUpdateDate {notNullOrEmpty}")
	private String lastUpdateDate;

	@NotNull(message = "price {notNullOrEmpty}")
	private BigDecimal price;

	@NotEmpty(message = "waseelDrugId {notNullOrEmpty}")
	@NumericValue(message = "waseelDrugId {onlyNumericValue}", value = "waseelDrugId")
	private String waseelDrugId;

	public ExclusionListDrugDetailsRequestModel() {
		super();
	}

	public String getDrugCode() {
		return drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public String getScientificName() {
		return scientificName;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getWaseelDrugId() {
		return waseelDrugId;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setWaseelDrugId(String waseelDrugId) {
		this.waseelDrugId = waseelDrugId;
	}

	public ExclusionListDrugDetailsRequestModel(String drugCode, String drugName, String scientificName,
			String scientificCode, String lastUpdateDate, BigDecimal price, String waseelDrugId) {
		super();
		this.drugCode = drugCode;
		this.drugName = drugName;
		this.scientificName = scientificName;
		this.scientificCode = scientificCode;
		this.lastUpdateDate = lastUpdateDate;
		this.price = price;
		this.waseelDrugId = waseelDrugId;
	}

}
