package com.waseel.dssadminservice.model.sfdamanagement;

import javax.validation.constraints.NotEmpty;

import com.waseel.dssadminservice.validator.customannotation.IsNumber;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan100Length;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan10Length;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan15Length;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan2000Length;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan50Length;
import com.waseel.dssadminservice.validator.customannotation.PriceAmount;
import com.waseel.dssadminservice.validator.customannotation.ValidGranularUnit;

public class SFDAExcelRequestModel {

	private Integer rowNumber;

	@NoMoreThan50Length(message = "SFDA Code {noMoreThan50LengthValidation}")
	@NotEmpty(message = "SFDA Code {emptyDataValidation}")
	private String sfdaCode;

	@NoMoreThan100Length(message = "GTIN Code {noMoreThan100LengthValidation}")
	private String gtinCode;

	@NoMoreThan100Length(message = "Trade Name {noMoreThan100LengthValidation}")
	@NotEmpty(message = "Trade Name {emptyDataValidation}")
	private String tradeName;

	@NoMoreThan2000Length(message = "Scientific Name {noMoreThan2000LengthValidation}")
	@NotEmpty(message = "Scientific Name {emptyDataValidation}")
	private String scientificName;

	@NoMoreThan50Length(message = "Scientific Code {noMoreThan50LengthValidation}")
	@NotEmpty(message = "Scientific Code {emptyDataValidation}")
	private String scientificCode;

	@NoMoreThan100Length(message = "Dosage Form {noMoreThan100LengthValidation}")
	@NotEmpty(message = "Dosage Form {emptyDataValidation}")
	private String dosageForm;

	@NoMoreThan100Length(message = "Administration Route {noMoreThan100LengthValidation}")
	@NotEmpty(message = "Administration Route {emptyDataValidation}")
	private String administrationRoute;

	@NoMoreThan100Length(message = "Package size {noMoreThan100LengthValidation}")
	@NotEmpty(message = "Package size {emptyDataValidation}")
	private String packageSize;

	@NoMoreThan100Length(message = "Package Type {noMoreThan100LengthValidation}")
	@NotEmpty(message = "Package Type {emptyDataValidation}")
	private String packageType;

	@ValidGranularUnit(message = "Granular Unit {GranularUnitValidation}")
	@IsNumber(message = "Granular Unit {notANumberValidation}")
	@NotEmpty(message = "Granular Unit {emptyDataValidation}")
	private String granularUnit;

	@NoMoreThan2000Length(message = "Strength {noMoreThan2000LengthValidation}")
	@NotEmpty(message = "Strength {emptyDataValidation}")
	private String strength;

	@NoMoreThan10Length(message = "Strength Unit {noMoreThan10LengthValidation}")
	@NotEmpty(message = "Strength Unit {emptyDataValidation}")
	private String strengthUnit;

	@PriceAmount(message = "Price {PriceAmountValidation}")
	@NoMoreThan15Length(message = "Price {noMoreThan15LengthValidation}")
	@IsNumber(message = "Price {notANumberValidation}")
	@NotEmpty(message = "Price {emptyDataValidation}")
	private String price;

	public String getSfdaCode() {
		return sfdaCode;
	}

	public void setSfdaCode(String sfdaCode) {
		this.sfdaCode = sfdaCode;
	}

	public String getGtinCode() {
		return gtinCode;
	}

	public void setGtinCode(String gtinCode) {
		this.gtinCode = gtinCode;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getScientificName() {
		return scientificName;
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

	public String getDosageForm() {
		return dosageForm;
	}

	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}

	public String getAdministrationRoute() {
		return administrationRoute;
	}

	public void setAdministrationRoute(String administrationRoute) {
		this.administrationRoute = administrationRoute;
	}

	public String getPackageSize() {
		return packageSize;
	}

	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getStrengthUnit() {
		return strengthUnit;
	}

	public void setStrengthUnit(String strengthUnit) {
		this.strengthUnit = strengthUnit;
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getGranularUnit() {
		return granularUnit;
	}

	public void setGranularUnit(String granularUnit) {
		this.granularUnit = granularUnit;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
