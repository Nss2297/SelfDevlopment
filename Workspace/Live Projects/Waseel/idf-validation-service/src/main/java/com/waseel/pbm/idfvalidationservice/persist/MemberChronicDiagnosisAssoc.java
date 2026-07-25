package com.waseel.pbm.idfvalidationservice.persist;

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
 * MemberChronicDiagnosisAssoc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MEMBER_CHRONIC_DIAGNOSIS_ASSOC", schema = "MDSS", uniqueConstraints = @UniqueConstraint(columnNames = {
		"MEMBER_CHRONIC_DZ_ASSOC_ID", "DIAGNOSIS_CODE" }))

public class MemberChronicDiagnosisAssoc implements java.io.Serializable {

	// Fields

	private Integer memberCroDiagnosisAssocId;
	private MemberChronicDzAssoc memberChronicDzAssoc;
	private String diagnosisCode;
	private String isEnabled;
	private Timestamp lastUpdateDateAndTime;

	// Constructors

	/** default constructor */
	public MemberChronicDiagnosisAssoc() {
	}

	/** full constructor */
	public MemberChronicDiagnosisAssoc(Integer memberCroDiagnosisAssocId, MemberChronicDzAssoc memberChronicDzAssoc,
			String diagnosisCode, String isEnabled, Timestamp lastUpdateDateAndTime) {
		this.memberCroDiagnosisAssocId = memberCroDiagnosisAssocId;
		this.memberChronicDzAssoc = memberChronicDzAssoc;
		this.diagnosisCode = diagnosisCode;
		this.isEnabled = isEnabled;
		this.lastUpdateDateAndTime = lastUpdateDateAndTime;
	}

	// Property accessors
	@Id

	@Column(name = "MEMBER_CRO_DIAGNOSIS_ASSOC_ID", unique = true, nullable = false, precision = 0)

	public Integer getMemberCroDiagnosisAssocId() {
		return this.memberCroDiagnosisAssocId;
	}

	public void setMemberCroDiagnosisAssocId(Integer memberCroDiagnosisAssocId) {
		this.memberCroDiagnosisAssocId = memberCroDiagnosisAssocId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_CHRONIC_DZ_ASSOC_ID", nullable = false)

	public MemberChronicDzAssoc getMemberChronicDzAssoc() {
		return this.memberChronicDzAssoc;
	}

	public void setMemberChronicDzAssoc(MemberChronicDzAssoc memberChronicDzAssoc) {
		this.memberChronicDzAssoc = memberChronicDzAssoc;
	}

	@Column(name = "DIAGNOSIS_CODE", nullable = false, length = 10)

	public String getDiagnosisCode() {
		return this.diagnosisCode;
	}

	public void setDiagnosisCode(String diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
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