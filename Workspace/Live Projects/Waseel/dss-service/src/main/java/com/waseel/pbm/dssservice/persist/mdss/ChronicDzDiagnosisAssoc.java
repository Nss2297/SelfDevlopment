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
 * ChronicDzDiagnosisAssoc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CHRONIC_DZ_DIAGNOSIS_ASSOC", schema = "MDSS", uniqueConstraints = @UniqueConstraint(columnNames = {
		"CHRONIC_DISEASES_ID", "DIAGNOSIS_CODE" }))

public class ChronicDzDiagnosisAssoc implements java.io.Serializable {

	// Fields

	private Integer chronicDzDiagnosisAssocId;
	private ChronicDzInformation chronicDzInformation;
	private String diagnosisCode;
	private String diagnosisDescription;
	private String isEnabled;
	private Timestamp lastUpdateDateAndTime;

	// Constructors

	/** default constructor */
	public ChronicDzDiagnosisAssoc() {
	}

	/** minimal constructor */
	public ChronicDzDiagnosisAssoc(Integer chronicDzDiagnosisAssocId, ChronicDzInformation chronicDzInformation,
			String diagnosisCode, String isEnabled, Timestamp lastUpdateDateAndTime) {
		this.chronicDzDiagnosisAssocId = chronicDzDiagnosisAssocId;
		this.chronicDzInformation = chronicDzInformation;
		this.diagnosisCode = diagnosisCode;
		this.isEnabled = isEnabled;
		this.lastUpdateDateAndTime = lastUpdateDateAndTime;
	}

	/** full constructor */
	public ChronicDzDiagnosisAssoc(Integer chronicDzDiagnosisAssocId, ChronicDzInformation chronicDzInformation,
			String diagnosisCode, String diagnosisDescription, String isEnabled, Timestamp lastUpdateDateAndTime) {
		this.chronicDzDiagnosisAssocId = chronicDzDiagnosisAssocId;
		this.chronicDzInformation = chronicDzInformation;
		this.diagnosisCode = diagnosisCode;
		this.diagnosisDescription = diagnosisDescription;
		this.isEnabled = isEnabled;
		this.lastUpdateDateAndTime = lastUpdateDateAndTime;
	}

	// Property accessors
	@Id

	@Column(name = "CHRONIC_DZ_DIAGNOSIS_ASSOC_ID", unique = true, nullable = false, precision = 0)

	public Integer getChronicDzDiagnosisAssocId() {
		return this.chronicDzDiagnosisAssocId;
	}

	public void setChronicDzDiagnosisAssocId(Integer chronicDzDiagnosisAssocId) {
		this.chronicDzDiagnosisAssocId = chronicDzDiagnosisAssocId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHRONIC_DISEASES_ID", nullable = false)

	public ChronicDzInformation getChronicDzInformation() {
		return this.chronicDzInformation;
	}

	public void setChronicDzInformation(ChronicDzInformation chronicDzInformation) {
		this.chronicDzInformation = chronicDzInformation;
	}

	@Column(name = "DIAGNOSIS_CODE", nullable = false, length = 10)

	public String getDiagnosisCode() {
		return this.diagnosisCode;
	}

	public void setDiagnosisCode(String diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}

	@Column(name = "DIAGNOSIS_DESCRIPTION", length = 256)

	public String getDiagnosisDescription() {
		return this.diagnosisDescription;
	}

	public void setDiagnosisDescription(String diagnosisDescription) {
		this.diagnosisDescription = diagnosisDescription;
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