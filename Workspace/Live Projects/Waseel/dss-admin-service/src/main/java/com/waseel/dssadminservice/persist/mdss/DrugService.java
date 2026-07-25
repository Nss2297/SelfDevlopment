package com.waseel.dssadminservice.persist.mdss;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

/**
 * The persistent class for the "DrugService" database table.
 */
@Entity
@Table(name = "`DrugService`", schema = "MDSS")
@Indexed(index = "idx_drug_service")
public class DrugService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "WASEEL_DRUG_ID", nullable = false, updatable = false)
	private Long waseelDrugId;

	@Column(name = "`Code`")
	private String code;

	@Column(name = "`Category`")
	private String category;

	@Column(name = "`Discontinue_Date`")
	private String discontinueDate;

	@Column(name = "`Display`")
	@FullTextField(analyzer = "english")
	private String display;

	@Column(name = "`Dosage_Form`")
	@FullTextField(analyzer = "english")
	private String dosageForm;

	@Column(name = "`DrugListId`")
	@GenericField
	private Long drugListId;

	@Column(name = "`Granular_Unit`")
	private String granularUnit;

	@Column(name = "`Ingredients`")
	@FullTextField(analyzer = "english")
	private String ingredients;

	@Column(name = "`Manufacturer`")
	private String manufacturer;

	@Column(name = "`Other_Codes_Type`")
	private String otherCodesType;

	@Column(name = "`Other_Codes_Value`")
	@FullTextField(analyzer = "english")
	private String otherCodesValue;

	@Column(name = "`Package_Size`")
	private String packageSize;

	@Column(name = "`Package_Type`")
	private String packageType;

	@Column(name = "`Price`")
	private String price;

	@Column(name = "`Received_Date`")
	private Date receivedDate;

	@Column(name = "`Reg_Owner`")
	private String regOwner;

	@Column(name = "`Release_Date`")
	private Timestamp releaseDate;

	@Column(name = "`ROA_Suggested`")
	@FullTextField(analyzer = "english")
	private String roaSuggested;

	@Column(name = "`Strength`")
	@FullTextField
	private String strength;

	@Column(name = "`Unit_Type`")
	private String unitType;

	@Column(name = "`ScientificCode`")
	@FullTextField(analyzer = "english")
	private String scientificCode;

	@Column(name = "`LastUpdatedDate`", nullable = false)
	private Date lastUpdatedDate;

	@Column(name = "`Strength_Unit`")
	@FullTextField
	private String strengthUnit;

	public String getStrengthUnit() {
		return strengthUnit;
	}

	public void setStrengthUnit(String strengthUnit) {
		this.strengthUnit = strengthUnit;
	}

	public void setDrugListId(Long drugListId) {
		this.drugListId = drugListId;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getDrugListId() {
		return this.drugListId;
	}

	public void setDrugListId(long drugListId) {
		this.drugListId = drugListId;
	}

	public String getGranularUnit() {
		return granularUnit;
	}

	public void setGranularUnit(String granularUnit) {
		this.granularUnit = granularUnit;
	}

	public String getRoaSuggested() {
		return roaSuggested;
	}

	public void setRoaSuggested(String roaSuggested) {
		this.roaSuggested = roaSuggested;
	}

	public String getIngredients() {
		return this.ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStrength() {
		return this.strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getDiscontinueDate() {
		return discontinueDate;
	}

	public void setDiscontinueDate(String discontinueDate) {
		this.discontinueDate = discontinueDate;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getDosageForm() {
		return dosageForm;
	}

	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}

	public String getOtherCodesType() {
		return otherCodesType;
	}

	public void setOtherCodesType(String otherCodesType) {
		this.otherCodesType = otherCodesType;
	}

	public String getOtherCodesValue() {
		return otherCodesValue;
	}

	public void setOtherCodesValue(String otherCodesValue) {
		this.otherCodesValue = otherCodesValue;
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

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getRegOwner() {
		return regOwner;
	}

	public void setRegOwner(String regOwner) {
		this.regOwner = regOwner;
	}

	public Timestamp getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Timestamp releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getROASuggested() {
		return roaSuggested;
	}

	public void setROASuggested(String rOASuggested) {
		roaSuggested = rOASuggested;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public Long getWaseelDrugId() {
		return waseelDrugId;
	}

	public void setWaseelDrugId(Long waseelDrugId) {
		this.waseelDrugId = waseelDrugId;
	}

	public DrugService(String code, String category, String discontinueDate, String display, String dosageForm,
			long drugListId, String granularUnit, String ingredients, String manufacturer, String otherCodesType,
			String otherCodesValue, String packageSize, String packageType, String price, Date receivedDate,
			String regOwner, Timestamp releaseDate, String roaSuggested, String strength, String unitType,
			String scientificCode, String scientificName, Date lastUpdatedDate) {
		super();
		this.code = code;
		this.category = category;
		this.discontinueDate = discontinueDate;
		this.display = display;
		this.dosageForm = dosageForm;
		this.drugListId = drugListId;
		this.granularUnit = granularUnit;
		this.manufacturer = manufacturer;
		this.otherCodesType = otherCodesType;
		this.otherCodesValue = otherCodesValue;
		this.packageSize = packageSize;
		this.packageType = packageType;
		this.price = price;
		this.receivedDate = receivedDate;
		this.regOwner = regOwner;
		this.releaseDate = releaseDate;
		this.roaSuggested = roaSuggested;
		this.strength = strength;
		this.unitType = unitType;
		this.scientificCode = scientificCode;
		this.ingredients = scientificName;
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public DrugService() {
		super();
	}

}