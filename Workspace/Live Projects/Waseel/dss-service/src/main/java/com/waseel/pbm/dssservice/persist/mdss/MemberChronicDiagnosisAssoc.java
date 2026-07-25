package com.waseel.pbm.dssservice.persist.mdss;

import java.sql.Timestamp;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * MemberChronicDiagnosisAssoc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MEMBER_CHRONIC_DIAGNOSIS_ASSOC", schema = "MDSS")

public class MemberChronicDiagnosisAssoc implements java.io.Serializable {

	// Fields

	private MemberChronicDiagnosisAssocId id;
	private MemberChronicDzAssoc memberChronicDzAssoc;
	private String isEnabled;
	private Timestamp lastUpdateDateAndTime;

	// Constructors

	/** default constructor */
	public MemberChronicDiagnosisAssoc() {
	}

	/** full constructor */
	public MemberChronicDiagnosisAssoc(MemberChronicDiagnosisAssocId id, MemberChronicDzAssoc memberChronicDzAssoc,
			String isEnabled, Timestamp lastUpdateDateAndTime) {
		this.id = id;
		this.memberChronicDzAssoc = memberChronicDzAssoc;
		this.isEnabled = isEnabled;
		this.lastUpdateDateAndTime = lastUpdateDateAndTime;
	}

	// Property accessors
	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "memberChronicDzAssocId", column = @Column(name = "MEMBER_CHRONIC_DZ_ASSOC_ID", nullable = false, precision = 0)),
			@AttributeOverride(name = "diagnosisCode", column = @Column(name = "DIAGNOSIS_CODE", nullable = false, length = 10)) })

	public MemberChronicDiagnosisAssocId getId() {
		return this.id;
	}

	public void setId(MemberChronicDiagnosisAssocId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_CHRONIC_DZ_ASSOC_ID", nullable = false, insertable = false, updatable = false)

	public MemberChronicDzAssoc getMemberChronicDzAssoc() {
		return this.memberChronicDzAssoc;
	}

	public void setMemberChronicDzAssoc(MemberChronicDzAssoc memberChronicDzAssoc) {
		this.memberChronicDzAssoc = memberChronicDzAssoc;
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