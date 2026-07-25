package com.waseel.drugexclusionvalidationservice.persist.businessrules;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "DRUG_EXCLUSION_DETAILS", schema = "PBM_BUSINESS_RULES")
public class DrugExclusionDetails implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DRUG_EXCLUSION_DETAILS_ID", nullable = false, updatable = false)
	private Long drugExclusionDetailsId;

	@Column(name = "EXCLUSION_ID", nullable = false, updatable = false)
	private Long exclusionId;

	@Column(name = "WASEEL_DRUG_ID", nullable = false, updatable = false)
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

	@Column(name = "LAST_UPDATE_DATE", nullable = false)
	private Date lastUpdateDate;

	@Column(name = "IS_DELETED", nullable = false, columnDefinition = "CHAR(1) default ('0')")
	private Boolean isDeleted = false;

	@Column(name = "DELETED_BY", length = 30)
	private String deletedBy;

	public Long getDrugExclusionDetailsId() {
		return drugExclusionDetailsId;
	}

	public void setDrugExclusionDetailsId(Long drugExclusionDetailsId) {
		this.drugExclusionDetailsId = drugExclusionDetailsId;
	}

	public Long getExclusionId() {
		return exclusionId;
	}

	public void setExclusionId(Long exclusionId) {
		this.exclusionId = exclusionId;
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

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Boolean getDeleted() {
		return isDeleted;
	}

	public void setDeleted(Boolean deleted) {
		isDeleted = deleted;
	}

	public String getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public DrugExclusionDetails(Long drugExclusionDetailsId, Long exclusionId, Long waseelDrugId,
			String registrationNumber, String tradeName, String scientificName, String scientificCode, BigDecimal price,
			Date lastUpdateDate, Boolean isDeleted, String deletedBy) {
		this.drugExclusionDetailsId = drugExclusionDetailsId;
		this.exclusionId = exclusionId;
		this.waseelDrugId = waseelDrugId;
		this.registrationNumber = registrationNumber;
		this.tradeName = tradeName;
		this.scientificName = scientificName;
		this.scientificCode = scientificCode;
		this.price = price;
		this.lastUpdateDate = lastUpdateDate;
		this.isDeleted = isDeleted;
		this.deletedBy = deletedBy;
	}

	public DrugExclusionDetails() {
	}
}
