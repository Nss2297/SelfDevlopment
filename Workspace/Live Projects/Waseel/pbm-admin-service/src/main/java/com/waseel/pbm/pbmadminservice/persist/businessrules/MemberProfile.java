package com.waseel.pbm.pbmadminservice.persist.businessrules;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER_PROFILE", schema = "PBM_BUSINESS_RULES")
public class MemberProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MEMBER_PROFILE_ID", nullable = false, updatable = false)
	private Long memberProfileId;

	@Column(name = "MEMBER_NAME", nullable = false, length = 200)
	private String memberName;

	@Column(name = "ID_NUMBER", nullable = false)
	private Long idNumber;

	@Column(name = "DOB", nullable = false)
	private LocalDate dob;

	@Column(name = "GENDER", nullable = false, length = 10)
	private String gender;

	@Column(name = "NATIONALITY", nullable = false, length = 56)
	private String nationality;

	@Column(name = "MOBILE_NUMBER", length = 15)
	private String mobileNumber;

	@Column(name = "EMAIL", length = 64)
	private String email;

	@Column(name = "HAS_CHRONIC_DISEASES", columnDefinition = "CHAR(1) default ('0')")
	private Boolean hasChronicDiseases = false;

	@Column(name = "LAST_UPDATE_DATE", nullable = false)
	private Date lastUpdateDate;

	@Column(name = "MARITAL_STATUS", length = 30)
	private String maritalStatus;

	// bi-directional one-to-one association to MemberPolicyAssociation
	@OneToMany(mappedBy = "memberProfile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<MemberPolicyAssociation> memberPolicyAssociation;

	public List<MemberPolicyAssociation> getMemberPolicyAssociation() {
		return memberPolicyAssociation;
	}

	public void setMemberPolicyAssociation(List<MemberPolicyAssociation> memberPolicyAssociation) {
		this.memberPolicyAssociation = memberPolicyAssociation;
	}

	public Long getMemberProfileId() {
		return memberProfileId;
	}

	public void setMemberProfileId(Long memberProfileId) {
		this.memberProfileId = memberProfileId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Long getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(Long idNumber) {
		this.idNumber = idNumber;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getHasChronicDiseases() {
		return hasChronicDiseases;
	}

	public void setHasChronicDiseases(Boolean hasChronicDiseases) {
		this.hasChronicDiseases = hasChronicDiseases;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

}
