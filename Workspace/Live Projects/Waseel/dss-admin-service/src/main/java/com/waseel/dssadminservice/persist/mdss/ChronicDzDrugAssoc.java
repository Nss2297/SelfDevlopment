package com.waseel.dssadminservice.persist.mdss;

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
 * ChronicDzDrugAssoc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CHRONIC_DZ_DRUG_ASSOC", schema = "MDSS", uniqueConstraints = @UniqueConstraint(columnNames = {
		"CHRONIC_DISEASES_ID", "SERVICE_CODE" }))

public class ChronicDzDrugAssoc implements java.io.Serializable {

	// Fields

	private Integer chronicDzDrugAssocId;
	private ChronicDzInformation chronicDzInformation;
	private String serviceCode;
	private String isEnabled;
	private Timestamp lastUpdateDateAndTime;

	// Constructors

	/** default constructor */
	public ChronicDzDrugAssoc() {
	}

	/** full constructor */
	public ChronicDzDrugAssoc(Integer chronicDzDrugAssocId, ChronicDzInformation chronicDzInformation,
			String serviceCode, String isEnabled, Timestamp lastUpdateDateAndTime) {
		this.chronicDzDrugAssocId = chronicDzDrugAssocId;
		this.chronicDzInformation = chronicDzInformation;
		this.serviceCode = serviceCode;
		this.isEnabled = isEnabled;
		this.lastUpdateDateAndTime = lastUpdateDateAndTime;
	}

	// Property accessors
	@Id

	@Column(name = "CHRONIC_DZ_DRUG_ASSOC_ID", unique = true, nullable = false, precision = 0)

	public Integer getChronicDzDrugAssocId() {
		return this.chronicDzDrugAssocId;
	}

	public void setChronicDzDrugAssocId(Integer chronicDzDrugAssocId) {
		this.chronicDzDrugAssocId = chronicDzDrugAssocId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHRONIC_DISEASES_ID", nullable = false)

	public ChronicDzInformation getChronicDzInformation() {
		return this.chronicDzInformation;
	}

	public void setChronicDzInformation(ChronicDzInformation chronicDzInformation) {
		this.chronicDzInformation = chronicDzInformation;
	}

	@Column(name = "SERVICE_CODE", nullable = false, length = 250)

	public String getServiceCode() {
		return this.serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	@Column(name = "IS_ENABLED", nullable = false, length = 1)

	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(name = "LAST_UPDATE_DATE_AND_TIME", nullable = false, length = 7)

	public Timestamp getLastUpdateDateAndTime() {
		return this.lastUpdateDateAndTime;
	}

	public void setLastUpdateDateAndTime(Timestamp lastUpdateDateAndTime) {
		this.lastUpdateDateAndTime = lastUpdateDateAndTime;
	}

}