package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the MEMBER_PROFILE database table.
 * 
 */
@Entity
@Table(name = "MEMBER_PROFILE")
@NamedQuery(name = "MemberProfile.findAll", query = "SELECT m FROM MemberProfile m")
public class MemberProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MEMBER_PROFILE_ID")
	private long memberProfileId;

	@Temporal(TemporalType.DATE)
	@Column(name = "DOB")
	private Date dob;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "HAS_CHRONIC_DISEASES")
	private String hasChronicDiseases;

	@Column(name = "ID_NUMBER")
	private BigDecimal idNumber;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name = "MEMBER_NAME")
	private String memberName;

	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;

	@Column(name = "NATIONALITY")
	private String nationality;

	// bi-directional many-to-one association to MemberChronicDiseasesAssociation
	@OneToMany(mappedBy = "memberProfile")
	private List<MemberChronicDiseasesAssociation> memberChronicDiseasesAssociations;

	// bi-directional many-to-one association to MemberPolicyAssociation
	@OneToMany(mappedBy = "memberProfile")
	private List<MemberPolicyAssociation> memberPolicyAssociations;

	public MemberProfile() {
	}

	public long getMemberProfileId() {
		return this.memberProfileId;
	}

	public void setMemberProfileId(long memberProfileId) {
		this.memberProfileId = memberProfileId;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHasChronicDiseases() {
		return this.hasChronicDiseases;
	}

	public void setHasChronicDiseases(String hasChronicDiseases) {
		this.hasChronicDiseases = hasChronicDiseases;
	}

	public BigDecimal getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(BigDecimal idNumber) {
		this.idNumber = idNumber;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public List<MemberChronicDiseasesAssociation> getMemberChronicDiseasesAssociations() {
		return this.memberChronicDiseasesAssociations;
	}

	public void setMemberChronicDiseasesAssociations(
			List<MemberChronicDiseasesAssociation> memberChronicDiseasesAssociations) {
		this.memberChronicDiseasesAssociations = memberChronicDiseasesAssociations;
	}

	public MemberChronicDiseasesAssociation addMemberChronicDiseasesAssociation(
			MemberChronicDiseasesAssociation memberChronicDiseasesAssociation) {
		getMemberChronicDiseasesAssociations().add(memberChronicDiseasesAssociation);
		memberChronicDiseasesAssociation.setMemberProfile(this);

		return memberChronicDiseasesAssociation;
	}

	public MemberChronicDiseasesAssociation removeMemberChronicDiseasesAssociation(
			MemberChronicDiseasesAssociation memberChronicDiseasesAssociation) {
		getMemberChronicDiseasesAssociations().remove(memberChronicDiseasesAssociation);
		memberChronicDiseasesAssociation.setMemberProfile(null);

		return memberChronicDiseasesAssociation;
	}

	public List<MemberPolicyAssociation> getMemberPolicyAssociations() {
		return this.memberPolicyAssociations;
	}

	public void setMemberPolicyAssociations(List<MemberPolicyAssociation> memberPolicyAssociations) {
		this.memberPolicyAssociations = memberPolicyAssociations;
	}

	public MemberPolicyAssociation addMemberPolicyAssociation(MemberPolicyAssociation memberPolicyAssociation) {
		getMemberPolicyAssociations().add(memberPolicyAssociation);
		memberPolicyAssociation.setMemberProfile(this);

		return memberPolicyAssociation;
	}

	public MemberPolicyAssociation removeMemberPolicyAssociation(MemberPolicyAssociation memberPolicyAssociation) {
		getMemberPolicyAssociations().remove(memberPolicyAssociation);
		memberPolicyAssociation.setMemberProfile(null);

		return memberPolicyAssociation;
	}

}