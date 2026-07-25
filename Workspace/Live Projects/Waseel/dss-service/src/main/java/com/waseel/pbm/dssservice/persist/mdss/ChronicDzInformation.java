package com.waseel.pbm.dssservice.persist.mdss;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * ChronicDzInformation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CHRONIC_DZ_INFORMATION", schema = "MDSS", uniqueConstraints = @UniqueConstraint(columnNames = {
		"CHRONIC_DISEASES_ID", "CHRONIC_DISEASES_NAME" }))

public class ChronicDzInformation implements java.io.Serializable {

	// Fields

	private Integer chronicDiseasesId;
	private String chronicDiseasesName;
	private Set<MemberChronicDzAssoc> memberChronicDzAssocs = new HashSet<MemberChronicDzAssoc>(0);
	private Set<ChronicDzDiagnosisAssoc> chronicDzDiagnosisAssocs = new HashSet<ChronicDzDiagnosisAssoc>(0);
	private Set<ChronicDzDrugAssoc> chronicDzDrugAssocs = new HashSet<ChronicDzDrugAssoc>(0);

	// Constructors

	/** default constructor */
	public ChronicDzInformation() {
	}

	/** minimal constructor */
	public ChronicDzInformation(Integer chronicDiseasesId, String chronicDiseasesName) {
		this.chronicDiseasesId = chronicDiseasesId;
		this.chronicDiseasesName = chronicDiseasesName;
	}

	/** full constructor */
	public ChronicDzInformation(Integer chronicDiseasesId, String chronicDiseasesName,
			Set<MemberChronicDzAssoc> memberChronicDzAssocs, Set<ChronicDzDiagnosisAssoc> chronicDzDiagnosisAssocs,
			Set<ChronicDzDrugAssoc> chronicDzDrugAssocs) {
		this.chronicDiseasesId = chronicDiseasesId;
		this.chronicDiseasesName = chronicDiseasesName;
		this.memberChronicDzAssocs = memberChronicDzAssocs;
		this.chronicDzDiagnosisAssocs = chronicDzDiagnosisAssocs;
		this.chronicDzDrugAssocs = chronicDzDrugAssocs;
	}

	// Property accessors
	@Id

	@Column(name = "CHRONIC_DISEASES_ID", unique = true, nullable = false, precision = 0)

	public Integer getChronicDiseasesId() {
		return this.chronicDiseasesId;
	}

	public void setChronicDiseasesId(Integer chronicDiseasesId) {
		this.chronicDiseasesId = chronicDiseasesId;
	}

	@Column(name = "CHRONIC_DISEASES_NAME", nullable = false, length = 250)

	public String getChronicDiseasesName() {
		return this.chronicDiseasesName;
	}

	public void setChronicDiseasesName(String chronicDiseasesName) {
		this.chronicDiseasesName = chronicDiseasesName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chronicDzInformation")

	public Set<MemberChronicDzAssoc> getMemberChronicDzAssocs() {
		return this.memberChronicDzAssocs;
	}

	public void setMemberChronicDzAssocs(Set<MemberChronicDzAssoc> memberChronicDzAssocs) {
		this.memberChronicDzAssocs = memberChronicDzAssocs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chronicDzInformation")

	public Set<ChronicDzDiagnosisAssoc> getChronicDzDiagnosisAssocs() {
		return this.chronicDzDiagnosisAssocs;
	}

	public void setChronicDzDiagnosisAssocs(Set<ChronicDzDiagnosisAssoc> chronicDzDiagnosisAssocs) {
		this.chronicDzDiagnosisAssocs = chronicDzDiagnosisAssocs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chronicDzInformation")

	public Set<ChronicDzDrugAssoc> getChronicDzDrugAssocs() {
		return this.chronicDzDrugAssocs;
	}

	public void setChronicDzDrugAssocs(Set<ChronicDzDrugAssoc> chronicDzDrugAssocs) {
		this.chronicDzDrugAssocs = chronicDzDrugAssocs;
	}

}