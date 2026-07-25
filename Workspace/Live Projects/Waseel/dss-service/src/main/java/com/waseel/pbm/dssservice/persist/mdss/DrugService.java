package com.waseel.pbm.dssservice.persist.mdss;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * DrugService entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DrugService", schema = "MDSS", uniqueConstraints = @UniqueConstraint(columnNames = { "Other_Codes_Value",
		"DrugListId" }))

public class DrugService implements java.io.Serializable {

	// Fields

	private Double waseelDrugId;
	private DrugServiceMetaData drugServiceMetaData;
	private String code;
	private String category;
	private String display;
	private String discontinueDate;
	private String price;
	private String granularUnit;
	private String unitType;
	private String manufacturer;
	private String regOwner;
	private String dosageForm;
	private String roaSuggested;
	private String packageType;
	private String packageSize;
	private String ingredients;
	private String strength;
	private Timestamp releaseDate;
	private Timestamp receivedDate;
	private String otherCodesType;
	private String otherCodesValue;
	private String scientificCode;
	private Timestamp lastUpdatedDate;
	private String registrationYear;
	private String strengthUnit;

	// Constructors

	/** default constructor */
	public DrugService() {
	}

	/** minimal constructor */
	public DrugService(Double waseelDrugId, DrugServiceMetaData drugServiceMetaData, String otherCodesValue) {
		this.waseelDrugId = waseelDrugId;
		this.drugServiceMetaData = drugServiceMetaData;
		this.otherCodesValue = otherCodesValue;
	}

	/** full constructor */
	public DrugService(Double waseelDrugId, DrugServiceMetaData drugServiceMetaData, String code, String category,
			String display, String discontinueDate, String price, String granularUnit, String unitType,
			String manufacturer, String regOwner, String dosageForm, String roaSuggested, String packageType,
			String packageSize, String ingredients, String strength, Timestamp releaseDate, Timestamp receivedDate,
			String otherCodesType, String otherCodesValue, String scientificCode, Timestamp lastUpdatedDate,
			String registrationYear, String strengthUnit) {
		this.waseelDrugId = waseelDrugId;
		this.drugServiceMetaData = drugServiceMetaData;
		this.code = code;
		this.category = category;
		this.display = display;
		this.discontinueDate = discontinueDate;
		this.price = price;
		this.granularUnit = granularUnit;
		this.unitType = unitType;
		this.manufacturer = manufacturer;
		this.regOwner = regOwner;
		this.dosageForm = dosageForm;
		this.roaSuggested = roaSuggested;
		this.packageType = packageType;
		this.packageSize = packageSize;
		this.ingredients = ingredients;
		this.strength = strength;
		this.releaseDate = releaseDate;
		this.receivedDate = receivedDate;
		this.otherCodesType = otherCodesType;
		this.otherCodesValue = otherCodesValue;
		this.scientificCode = scientificCode;
		this.lastUpdatedDate = lastUpdatedDate;
		this.registrationYear = registrationYear;
		this.strengthUnit = strengthUnit;
	}

	// Property accessors
	@Id

	@Column(name = "WASEEL_DRUG_ID", unique = true, nullable = false, precision = 0)

	public Double getWaseelDrugId() {
		return this.waseelDrugId;
	}

	public void setWaseelDrugId(Double waseelDrugId) {
		this.waseelDrugId = waseelDrugId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DrugListId", nullable = false)

	public DrugServiceMetaData getDrugServiceMetaData() {
		return this.drugServiceMetaData;
	}

	public void setDrugServiceMetaData(DrugServiceMetaData drugServiceMetaData) {
		this.drugServiceMetaData = drugServiceMetaData;
	}

	@Column(name = "Code", length = 100)

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "Category", length = 100)

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "Display", length = 100)

	public String getDisplay() {
		return this.display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	@Column(name = "Discontinue_Date", length = 100)

	public String getDiscontinueDate() {
		return this.discontinueDate;
	}

	public void setDiscontinueDate(String discontinueDate) {
		this.discontinueDate = discontinueDate;
	}

	@Column(name = "Price", length = 100)

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Column(name = "Granular_Unit", length = 100)

	public String getGranularUnit() {
		return this.granularUnit;
	}

	public void setGranularUnit(String granularUnit) {
		this.granularUnit = granularUnit;
	}

	@Column(name = "Unit_Type", length = 100)

	public String getUnitType() {
		return this.unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	@Column(name = "Manufacturer", length = 300)

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Column(name = "Reg_Owner", length = 300)

	public String getRegOwner() {
		return this.regOwner;
	}

	public void setRegOwner(String regOwner) {
		this.regOwner = regOwner;
	}

	@Column(name = "Dosage_Form", length = 100)

	public String getDosageForm() {
		return this.dosageForm;
	}

	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}

	@Column(name = "ROA_Suggested", length = 100)

	public String getRoaSuggested() {
		return this.roaSuggested;
	}

	public void setRoaSuggested(String roaSuggested) {
		this.roaSuggested = roaSuggested;
	}

	@Column(name = "Package_Type", length = 100)

	public String getPackageType() {
		return this.packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	@Column(name = "Package_Size", length = 100)

	public String getPackageSize() {
		return this.packageSize;
	}

	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}

	@Column(name = "Ingredients", length = 2000)

	public String getIngredients() {
		return this.ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	@Column(name = "Strength", length = 2000)

	public String getStrength() {
		return this.strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	@Column(name = "Release_Date", length = 11)

	public Timestamp getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(Timestamp releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Column(name = "Received_Date", length = 7)

	public Timestamp getReceivedDate() {
		return this.receivedDate;
	}

	public void setReceivedDate(Timestamp receivedDate) {
		this.receivedDate = receivedDate;
	}

	@Column(name = "Other_Codes_Type", length = 100)

	public String getOtherCodesType() {
		return this.otherCodesType;
	}

	public void setOtherCodesType(String otherCodesType) {
		this.otherCodesType = otherCodesType;
	}

	@Column(name = "Other_Codes_Value", nullable = false, length = 150)

	public String getOtherCodesValue() {
		return this.otherCodesValue;
	}

	public void setOtherCodesValue(String otherCodesValue) {
		this.otherCodesValue = otherCodesValue;
	}

	@Column(name = "ScientificCode", length = 64)

	public String getScientificCode() {
		return this.scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	@Column(name = "LastUpdatedDate", length = 7)

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@Column(name = "Registration_Year", length = 4)

	public String getRegistrationYear() {
		return this.registrationYear;
	}

	public void setRegistrationYear(String registrationYear) {
		this.registrationYear = registrationYear;
	}

	@Column(name = "Strength_Unit", length = 10)

	public String getStrengthUnit() {
		return this.strengthUnit;
	}

	public void setStrengthUnit(String strengthUnit) {
		this.strengthUnit = strengthUnit;
	}

}