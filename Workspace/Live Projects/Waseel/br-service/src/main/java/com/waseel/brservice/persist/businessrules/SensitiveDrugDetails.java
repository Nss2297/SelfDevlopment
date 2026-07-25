package com.waseel.brservice.persist.businessrules;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SENSITIVE_DRUG_DETAILS", schema = "PBM_BUSINESS_RULES")
public class SensitiveDrugDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "Seq")
	@SequenceGenerator(name = "Seq", sequenceName = "SENSITIVE_DRUG_SEQ", allocationSize = 0)
	@Column(name = "SENSITIVE_DRUG_ID", nullable = false, updatable = false)
	private Long sensitiveDrugId;

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

	@Column(name = "PRICE", nullable = false, scale = 2)
	private BigDecimal price;

	@Column(name = "LAST_UPDATE_DATE", nullable = false)
	private Date lastUpdatedDate;

	@Column(name = "IS_DELETED", nullable = false, columnDefinition = "CHAR(1) default ('0')")
	private Boolean isDeleted = false;

	@Column(name = "DELETED_BY", nullable = true, length = 30)
	private String deletedBy;
	
	public Long getSensitiveDrugId() {
		return sensitiveDrugId;
	}

	public void setSensitiveDrugId(Long sensitiveDrugId) {
		this.sensitiveDrugId = sensitiveDrugId;
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

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
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
}
