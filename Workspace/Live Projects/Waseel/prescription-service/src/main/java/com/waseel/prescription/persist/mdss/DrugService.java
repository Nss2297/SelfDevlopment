package com.waseel.prescription.persist.mdss;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import com.waseel.prescription.persist.businessrules.DrugFormularyDetails;

/**
 * The persistent class for the "DrugService" database table.
 */
@Entity
@Table(name = "DrugService", schema = "MDSS")
@Indexed(index = "idx_drug_service")
public class DrugService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "WASEEL_DRUG_ID", nullable = false, updatable = false)
	private Long waseelDrugId;

	@Column(name = "Code")
	private String code;

	@Column(name = "Category")
	private String category;

	@Column(name = "Discontinue_Date")
	private String discontinueDate;

	@Column(name = "Display")
	@FullTextField(analyzer = "english")
	private String display;

	@Column(name = "Dosage_Form")
	@FullTextField(analyzer = "english")
	private String dosageForm;

	@Column(name = "DrugListId")
	@GenericField
	private long drugListId;

	@Column(name = "Granular_Unit")
	private String granularUnit;

	@Column(name = "Ingredients")
	@FullTextField(analyzer = "english")
	private String ingredients;

	@Column(name = "Manufacturer")
	private String manufacturer;

	@Column(name = "Other_Codes_Type")
	private String otherCodesType;

	@Column(name = "Other_Codes_Value")
//	@KeywordField
	@FullTextField(analyzer = "english")
	private String otherCodesValue;

	@Column(name = "Package_Size")
	private String packageSize;

	@Column(name = "Package_Type")
	private String packageType;

	@Column(name = "Price")
	private String price;

	@Column(name = "Received_Date")
	private Date receivedDate;

	@Column(name = "Reg_Owner")
	private String regOwner;

	@Column(name = "Release_Date")
	private Timestamp releaseDate;

	@Column(name = "ROA_Suggested")
	@FullTextField(analyzer = "english")
	private String roaSuggested;

	@Column(name = "Strength")
	@FullTextField
	private String strength;

	@Column(name = "Strength_Unit")
	@FullTextField
	private String strengthUnit;

	@Column(name = "Unit_Type")
	private String unitType;

	@Column(name = "ScientificCode")
//	@KeywordField
	@FullTextField(analyzer = "english")
	private String scientificCode;

	@Column(name = "LastUpdatedDate")
	private Date lastUpdatedDate;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGISTRATION_NUMBER", referencedColumnName = "Other_Codes_Value")
	private List<DrugFormularyDetails> drugFormularyDetailsList;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DrugListId", referencedColumnName = "DrugListId", insertable = false, updatable = false)
	private DrugServiceMetaData drugServiceMetaData;

	public List<DrugFormularyDetails> getDrugFormularyDetailsList() {
		return drugFormularyDetailsList;
	}

	public void setDrugFormularyDetailsList(List<DrugFormularyDetails> drugFormularyDetailsList) {
		this.drugFormularyDetailsList = drugFormularyDetailsList;
	}

	public String getStrengthUnit() {
		return strengthUnit;
	}

	public void setStrengthUnit(String strengthUnit) {
		this.strengthUnit = strengthUnit;
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

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Long getWaseelDrugId() {
		return waseelDrugId;
	}

	public void setWaseelDrugId(Long waseelDrugId) {
		this.waseelDrugId = waseelDrugId;
	}

	public DrugServiceMetaData getDrugServiceMetaData() {
		return drugServiceMetaData;
	}

	public void setDrugServiceMetaData(DrugServiceMetaData drugServiceMetaData) {
		this.drugServiceMetaData = drugServiceMetaData;
	}
}