package com.waseel.eligibility.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the WSL_CLAIM_DIAGNOSIS database table.
 * 
 */
@Entity
@Table(name = "WSL_CLAIM_DIAGNOSIS")
@NamedQuery(name = "WslClaimDiagnosis.findAll", query = "SELECT w FROM WslClaimDiagnosis w")
public class WslClaimDiagnosis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DIAGNOSISID", unique = true, nullable = false)
	private Long diagnosisid;

	private String providerid;

	private String diagnosiscode;

	private String diagnosisdesc;

	private String diagnosisnumber;

	private String diagnosistype;

	@ManyToOne
	@JoinColumn(name = "CLAIMID", nullable = false)
	@JsonIgnore
	private WslGeninfo geninfo;

	public WslClaimDiagnosis() {
	}

	public String getDiagnosisdesc() {
		return this.diagnosisdesc;
	}

	public void setDiagnosisdesc(String diagnosisdesc) {
		this.diagnosisdesc = diagnosisdesc;
	}

	public String getDiagnosisnumber() {
		return this.diagnosisnumber;
	}

	public void setDiagnosisnumber(String diagnosisnumber) {
		this.diagnosisnumber = diagnosisnumber;
	}

	public String getDiagnosistype() {
		return this.diagnosistype;
	}

	public void setDiagnosistype(String diagnosistype) {
		this.diagnosistype = diagnosistype;
	}

	public Long getDiagnosisid() {
		return diagnosisid;
	}

	public void setDiagnosisid(Long diagnosisid) {
		this.diagnosisid = diagnosisid;
	}

	public String getProviderid() {
		return providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public String getDiagnosiscode() {
		return diagnosiscode;
	}

	public void setDiagnosiscode(String diagnosiscode) {
		this.diagnosiscode = diagnosiscode;
	}

	public WslGeninfo getGeninfo() {
		return geninfo;
	}

	public void setGeninfo(WslGeninfo geninfo) {
		this.geninfo = geninfo;
	}

}