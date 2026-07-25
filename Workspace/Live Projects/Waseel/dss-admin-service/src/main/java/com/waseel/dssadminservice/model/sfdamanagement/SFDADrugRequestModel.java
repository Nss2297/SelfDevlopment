package com.waseel.dssadminservice.model.sfdamanagement;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.waseel.dssadminservice.validator.customannotation.NoMoreThan100Length;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan10Length;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan15Length;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan2000Length;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan50Length;

public class SFDADrugRequestModel {

	@NoMoreThan100Length(message = "GTIN Code {noMoreThan100LengthValidation}")
	private String sfdaCode;

	@NoMoreThan100Length(message = "GTIN Code {noMoreThan100LengthValidation}")
	private String gtinCode;

	@NotBlank(message = "Trade Name {emptyDataValidation}")
	@NoMoreThan100Length(message = "Trade Name {noMoreThan100LengthValidation}")
	private String tradeName;

	@NotBlank(message = "Scientific Code {emptyDataValidation}")
	@NoMoreThan50Length(message = "Scientific Code {noMoreThan50LengthValidation}")
	private String scientificCode;

	@NotBlank(message = "Scientific Name {emptyDataValidation}")
	@NoMoreThan2000Length(message = "Scientific Name {noMoreThan2000LengthValidation}")
	private String scientificName;

	@NotBlank(message = "Dosage Form {emptyDataValidation}")
	@NoMoreThan100Length(message = "Dosage Form {noMoreThan100LengthValidation}")
	private String dosageForm;

	@NotBlank(message = "Administration Route {emptyDataValidation}")
	@NoMoreThan100Length(message = "Administration Route {noMoreThan100LengthValidation}")
	private String administrationRoute;

	@NotBlank(message = "Package Size {emptyDataValidation}")
	@NoMoreThan100Length(message = "Package Size {noMoreThan100LengthValidation}")
	private String packageSize;

	@NotBlank(message = "Package Type {emptyDataValidation}")
	@NoMoreThan100Length(message = "Package Type {noMoreThan100LengthValidation}")
	private String packageType;

	@Pattern(regexp = "^[1-9]\\d{0,3}$", message = "Granular Unit should be between 1 to 9999 digits")
	private String granularUnit;

	@NotBlank(message = "Strength {emptyDataValidation}")
	@NoMoreThan2000Length(message = "Strength {noMoreThan2000LengthValidation}")
	private String strength;

	@NotBlank(message = "Strength Unit {emptyDataValidation}")
	@NoMoreThan10Length(message = "Strength Unit {noMoreThan10LengthValidation}")
	private String strengthUnit;

	@Pattern(regexp = "\\d+\\.\\d{2}", message = "Price should be a numeric value with 2 decimal places")
	@NoMoreThan15Length(message = "Price {noMoreThan15LengthValidation}")
	private String price;

	public String getSfdaCode() {
		return sfdaCode;
	}

	public void setSfdaCode(String sfdaCode) {
		this.sfdaCode = sfdaCode;
	}

	public String getStrengthUnit() {
		return strengthUnit;
	}

	public void setStrengthUnit(String strengthUnit) {
		this.strengthUnit = strengthUnit.trim();
	}

	public String getGtinCode() {
		return gtinCode;
	}

	public void setGtinCode(String gtinCode) {
		this.gtinCode = gtinCode.trim();
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName.trim();
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode.trim();
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName.trim();
	}

	public String getDosageForm() {
		return dosageForm;
	}

	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm.trim();
	}

	public String getAdministrationRoute() {
		return administrationRoute;
	}

	public void setAdministrationRoute(String administrationRoute) {
		this.administrationRoute = administrationRoute.trim();
	}

	public String getPackageSize() {
		return packageSize;
	}

	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize.trim();
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType.trim();
	}

	public String getGranularUnit() {
		return granularUnit;
	}

	public void setGranularUnit(String granularUnit) {
		this.granularUnit = granularUnit.trim();
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength.trim();
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price.trim();
	}
}