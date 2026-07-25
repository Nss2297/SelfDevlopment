package com.waseel.drugformulary.persist.businessrules;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "DRUG_FORMULARY_DETAILS", schema = "PBM_BUSINESS_RULES")
public class DrugFormularyDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "Seq")
	@SequenceGenerator(name = "Seq", sequenceName = "DRUG_FORMULARY_DETAILS_SEQ", allocationSize = 0)
	@Column(name = "DRUG_FORMULARY_DETAILS_ID", nullable = false, updatable = false)
	private Long drugFormularyDetailsId;

	@Column(name = "FORMULARY_ID", nullable = false)
	private Long formularyId;

	@Column(name = "WASEEL_DRUG_ID", nullable = false)
	private Long waseelDrugId;

	@Column(name = "REGISTRATION_NUMBER", nullable = false, length = 50)
	private String registrationNumber;

	@Column(name = "TRADE_NAME", nullable = false, length = 256)
	private String tradeName;

	@Column(name = "SCIENTIFIC_NAME", nullable = false, length = 256)
	private String scientificName;

	@Column(name = "SCIENTIFIC_CODE", nullable = false, length = 150)
	private String scientificCode;

	@Column(name = "PRICE", nullable = false, precision = 14, scale = 2)
	private BigDecimal price;

	@Column(name = "IS_OVERRIDE", nullable = false, columnDefinition = "CHAR(1) default ('0')")
	private Boolean isOverride = false;

	@Column(name = "LAST_UPDATE_DATE", nullable = false)
	private Date lastUpdateDate;

	@Column(name = "IS_DELETED", nullable = false, columnDefinition = "CHAR(1) default ('0')")
	private Boolean isDeleted = false;
	
	@Column(name = "PATIENT_SHARE", nullable = false, precision = 14, scale = 2)
	private BigDecimal patientShare;

	@Column(name = "DELETED_BY", length = 30)
	private String deletedBy;

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

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
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

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public BigDecimal getPatientShare() {
		return patientShare;
	}

	public void setPatientShare(BigDecimal patientShare) {
		this.patientShare = patientShare;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
