package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DRUG_FORMULARY_DETAILS database table.
 * 
 */
@Entity
@Table(name="DRUG_FORMULARY_DETAILS")
@NamedQuery(name="DrugFormularyDetail.findAll", query="SELECT d FROM DrugFormularyDetail d")
public class DrugFormularyDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DRUG_FORMULARY_DETAILS_ID")
	private long drugFormularyDetailsId;

	@Column(name="DELETED_BY")
	private String deletedBy;

	@Column(name="IS_DELETED")
	private String isDeleted;

	@Column(name="IS_OVERRIDE")
	private String isOverride;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	private BigDecimal price;

	@Column(name="REGISTRATION_NUMBER")
	private String registrationNumber;

	@Column(name="SCIENTIFIC_CODE")
	private String scientificCode;

	@Column(name="SCIENTIFIC_NAME")
	private String scientificName;

	@Column(name="TRADE_NAME")
	private String tradeName;

	@Column(name="WASEEL_DRUG_ID")
	private BigDecimal waseelDrugId;
	
	@Column(name = "PATIENT_SHARE", nullable = false, precision = 14, scale = 2)
	private BigDecimal patientShare;

	//bi-directional many-to-one association to DrugFormularyMetadata
	@ManyToOne
	@JoinColumn(name="FORMULARY_ID")
	private DrugFormularyMetadata drugFormularyMetadata;

	public DrugFormularyDetail() {
	}

	public long getDrugFormularyDetailsId() {
		return this.drugFormularyDetailsId;
	}

	public void setDrugFormularyDetailsId(long drugFormularyDetailsId) {
		this.drugFormularyDetailsId = drugFormularyDetailsId;
	}

	public String getDeletedBy() {
		return this.deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public String getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getIsOverride() {
		return this.isOverride;
	}

	public void setIsOverride(String isOverride) {
		this.isOverride = isOverride;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getRegistrationNumber() {
		return this.registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getScientificCode() {
		return this.scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public String getScientificName() {
		return this.scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getTradeName() {
		return this.tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public BigDecimal getWaseelDrugId() {
		return this.waseelDrugId;
	}

	public void setWaseelDrugId(BigDecimal waseelDrugId) {
		this.waseelDrugId = waseelDrugId;
	}

	public DrugFormularyMetadata getDrugFormularyMetadata() {
		return this.drugFormularyMetadata;
	}

	public void setDrugFormularyMetadata(DrugFormularyMetadata drugFormularyMetadata) {
		this.drugFormularyMetadata = drugFormularyMetadata;
	}

	public BigDecimal getPatientShare() {
		return patientShare;
	}

	public void setPatientShare(BigDecimal patientShare) {
		this.patientShare = patientShare;
	}

}