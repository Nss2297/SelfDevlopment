package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CHRONIC_DISEASES_DETAILS database table.
 * 
 */
@Entity
@Table(name="CHRONIC_DISEASES_DETAILS")
@NamedQuery(name="ChronicDiseasesDetail.findAll", query="SELECT c FROM ChronicDiseasesDetail c")
public class ChronicDiseasesDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CHRONIC_DISEASES_DETAILS_ID")
	private long chronicDiseasesDetailsId;

	@Column(name="DIAGNOSIS_CODE")
	private String diagnosisCode;

	@Column(name="DIAGNOSIS_DESCRIPTION")
	private String diagnosisDescription;

	//bi-directional many-to-one association to MemberChronicDiseasesAssociation
	@OneToMany(mappedBy="chronicDiseasesDetail")
	private List<MemberChronicDiseasesAssociation> memberChronicDiseasesAssociations;

	public ChronicDiseasesDetail() {
	}

	public long getChronicDiseasesDetailsId() {
		return this.chronicDiseasesDetailsId;
	}

	public void setChronicDiseasesDetailsId(long chronicDiseasesDetailsId) {
		this.chronicDiseasesDetailsId = chronicDiseasesDetailsId;
	}

	public String getDiagnosisCode() {
		return this.diagnosisCode;
	}

	public void setDiagnosisCode(String diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}

	public String getDiagnosisDescription() {
		return this.diagnosisDescription;
	}

	public void setDiagnosisDescription(String diagnosisDescription) {
		this.diagnosisDescription = diagnosisDescription;
	}

	public List<MemberChronicDiseasesAssociation> getMemberChronicDiseasesAssociations() {
		return this.memberChronicDiseasesAssociations;
	}

	public void setMemberChronicDiseasesAssociations(List<MemberChronicDiseasesAssociation> memberChronicDiseasesAssociations) {
		this.memberChronicDiseasesAssociations = memberChronicDiseasesAssociations;
	}

	public MemberChronicDiseasesAssociation addMemberChronicDiseasesAssociation(MemberChronicDiseasesAssociation memberChronicDiseasesAssociation) {
		getMemberChronicDiseasesAssociations().add(memberChronicDiseasesAssociation);
		memberChronicDiseasesAssociation.setChronicDiseasesDetail(this);

		return memberChronicDiseasesAssociation;
	}

	public MemberChronicDiseasesAssociation removeMemberChronicDiseasesAssociation(MemberChronicDiseasesAssociation memberChronicDiseasesAssociation) {
		getMemberChronicDiseasesAssociations().remove(memberChronicDiseasesAssociation);
		memberChronicDiseasesAssociation.setChronicDiseasesDetail(null);

		return memberChronicDiseasesAssociation;
	}

}