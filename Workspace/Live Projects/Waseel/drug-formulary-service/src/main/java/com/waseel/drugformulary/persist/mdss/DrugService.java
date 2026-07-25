package com.waseel.drugformulary.persist.mdss;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the "DrugService" database table.
 */
@Entity
@Table(name = "`DrugService`", schema = "MDSS")
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
    private String display;

    @Column(name = "`Dosage_Form`")
    private String dosageForm;

    @Column(name = "`DrugListId`")
    private long drugListId;

    @Column(name = "`Granular_Unit`")
    private String granularUnit;

    @Column(name = "`Ingredients`")
    private String ingredients;

    @Column(name = "`Manufacturer`")
    private String manufacturer;

    @Column(name = "`Other_Codes_Type`")
    private String otherCodesType;

    @Column(name = "`Other_Codes_Value`")
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
    private String roaSuggested;

    @Column(name = "`Strength`")
    private String strength;

    @Column(name = "`Unit_Type`")
    private String unitType;

    public Long getWaseelDrugId() {
        return waseelDrugId;
    }

    public void setWaseelDrugId(Long waseelDrugId) {
        this.waseelDrugId = waseelDrugId;
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
}