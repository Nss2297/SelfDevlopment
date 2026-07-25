package com.waseel.prescription.model.formulary;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugFormularyDetailsModel {

    private Long drugFormularyDetailsId;
    private Long formularyId;
    private Long waseelDrugId;
    private String registrationNumber;
    private String tradeName;
    private String scientificName;
    private String scientificCode;
    private BigDecimal price;
    private Boolean isOverride;
    private Date lastUpdateDate;
    private Boolean isDeleted;
    private String deletedBy;
    private BigDecimal patientShare;

    public DrugFormularyDetailsModel() {

    }

    public DrugFormularyDetailsModel(Long drugFormularyDetailsId, Long formularyId, Long waseelDrugId,
                                     String registrationNumber, String tradeName, String scientificName, String scientificCode,
                                     BigDecimal price, Boolean isOverride, Date lastUpdateDate, Boolean isDeleted, String deletedBy, BigDecimal patientShare) {
        this.drugFormularyDetailsId = drugFormularyDetailsId;
        this.formularyId = formularyId;
        this.waseelDrugId = waseelDrugId;
        this.registrationNumber = registrationNumber;
        this.tradeName = tradeName;
        this.scientificName = scientificName;
        this.scientificCode = scientificCode;
        this.price = price;
        this.isOverride = isOverride;
        this.lastUpdateDate = lastUpdateDate;
        this.isDeleted = isDeleted;
        this.deletedBy = deletedBy;
        this.patientShare = patientShare;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Boolean getOverride() {
        return isOverride;
    }

    public void setOverride(Boolean override) {
        isOverride = override;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getDrugFormularyDetailsId() {
        return drugFormularyDetailsId;
    }

    public void setDrugFormularyDetailsId(Long drugFormularyDetailsId) {
        this.drugFormularyDetailsId = drugFormularyDetailsId;
    }

    public Long getFormularyId() {
        return formularyId;
    }

    public void setFormularyId(Long formularyId) {
        this.formularyId = formularyId;
    }

    public Long getWaseelDrugId() {
        return waseelDrugId;
    }

    public void setWaseelDrugId(Long waseelDrugId) {
        this.waseelDrugId = waseelDrugId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsOverride() {
        return isOverride;
    }

    public void setIsOverride(Boolean isOverride) {
        this.isOverride = isOverride;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public BigDecimal getPatientShare() {
        return patientShare;
    }

    public void setPatientShare(BigDecimal patientShare) {
        this.patientShare = patientShare;
    }
}
