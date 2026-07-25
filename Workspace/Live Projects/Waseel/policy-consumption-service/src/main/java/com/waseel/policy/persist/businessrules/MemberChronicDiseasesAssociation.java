package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the MEMBER_CHRONIC_DISEASES_ASSOCIATION database table.
 * 
 */
@Entity
@Table(name="MEMBER_CHRONIC_DISEASES_ASSOCIATION")
@NamedQuery(name="MemberChronicDiseasesAssociation.findAll", query="SELECT m FROM MemberChronicDiseasesAssociation m")
public class MemberChronicDiseasesAssociation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MEM_CHRONIC_DISEASES_ASSO_ID")
	private long memChronicDiseasesAssoId;

	@Temporal(TemporalType.DATE)
	@Column(name="DIAGNOSIS_DATE")
	private Date diagnosisDate;

	@Column(name="IS_ENABLED")
	private String isEnabled;

	//bi-directional many-to-one association to ChronicDiseasesDetail
	@ManyToOne
	@JoinColumn(name="CHRONIC_DISEASES_DETAILS_ID")
	private ChronicDiseasesDetail chronicDiseasesDetail;

	//bi-directional many-to-one association to MemberProfile
	@ManyToOne
	@JoinColumn(name="MEMBER_PROFILE_ID")
	private MemberProfile memberProfile;

	public MemberChronicDiseasesAssociation() {
	}

	public long getMemChronicDiseasesAssoId() {
		return this.memChronicDiseasesAssoId;
	}

	public void setMemChronicDiseasesAssoId(long memChronicDiseasesAssoId) {
		this.memChronicDiseasesAssoId = memChronicDiseasesAssoId;
	}

	public Date getDiagnosisDate() {
		return this.diagnosisDate;
	}

	public void setDiagnosisDate(Date diagnosisDate) {
		this.diagnosisDate = diagnosisDate;
	}

	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	public ChronicDiseasesDetail getChronicDiseasesDetail() {
		return this.chronicDiseasesDetail;
	}

	public void setChronicDiseasesDetail(ChronicDiseasesDetail chronicDiseasesDetail) {
		this.chronicDiseasesDetail = chronicDiseasesDetail;
	}

	public MemberProfile getMemberProfile() {
		return this.memberProfile;
	}

	public void setMemberProfile(MemberProfile memberProfile) {
		this.memberProfile = memberProfile;
	}

}