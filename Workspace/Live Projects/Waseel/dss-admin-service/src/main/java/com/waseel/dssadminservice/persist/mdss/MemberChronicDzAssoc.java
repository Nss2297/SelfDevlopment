package com.waseel.dssadminservice.persist.mdss;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * MemberChronicDzAssoc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MEMBER_CHRONIC_DZ_ASSOC", schema = "MDSS", uniqueConstraints = @UniqueConstraint(columnNames = {
		"MEMBER_ID", "CHRONIC_DISEASES_ID" }))

public class MemberChronicDzAssoc implements java.io.Serializable {

	// Fields

	private Integer memberChronicDzAssocId;
	private ChronicDzInformation chronicDzInformation;
	private String memberId;
	private String isEnabled;
	private Timestamp lastUpdateDateAndTime;
	private String payerId;

	// Constructors

	/** default constructor */
	public MemberChronicDzAssoc() {
	}

	/** minimal constructor */
	public MemberChronicDzAssoc(Integer memberChronicDzAssocId, ChronicDzInformation chronicDzInformation,
			String memberId, String isEnabled, Timestamp lastUpdateDateAndTime) {
		this.memberChronicDzAssocId = memberChronicDzAssocId;
		this.chronicDzInformation = chronicDzInformation;
		this.memberId = memberId;
		this.isEnabled = isEnabled;
		this.lastUpdateDateAndTime = lastUpdateDateAndTime;
	}

	/** full constructor */
	public MemberChronicDzAssoc(Integer memberChronicDzAssocId, ChronicDzInformation chronicDzInformation,
			String memberId, String isEnabled, Timestamp lastUpdateDateAndTime, String payerId) {
		this.memberChronicDzAssocId = memberChronicDzAssocId;
		this.chronicDzInformation = chronicDzInformation;
		this.memberId = memberId;
		this.isEnabled = isEnabled;
		this.lastUpdateDateAndTime = lastUpdateDateAndTime;
		this.payerId = payerId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Member_Chronic_Dz_Seq")
	@SequenceGenerator(name = "Member_Chronic_Dz_Seq", sequenceName = "MEMBER_CHRONIC_DZ_SEQ", allocationSize = 1, initialValue = 1)

	@Column(name = "MEMBER_CHRONIC_DZ_ASSOC_ID", unique = true, nullable = false, precision = 0)

	public Integer getMemberChronicDzAssocId() {
		return this.memberChronicDzAssocId;
	}

	public void setMemberChronicDzAssocId(Integer memberChronicDzAssocId) {
		this.memberChronicDzAssocId = memberChronicDzAssocId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHRONIC_DISEASES_ID", nullable = false)

	public ChronicDzInformation getChronicDzInformation() {
		return this.chronicDzInformation;
	}

	public void setChronicDzInformation(ChronicDzInformation chronicDzInformation) {
		this.chronicDzInformation = chronicDzInformation;
	}

	@Column(name = "MEMBER_ID", nullable = false, length = 30)

	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
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

	@Column(name = "PAYER_ID", length = 20)

	public String getPayerId() {
		return this.payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}
}