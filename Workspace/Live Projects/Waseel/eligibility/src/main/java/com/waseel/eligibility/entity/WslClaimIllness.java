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
 * The persistent class for the WSL_CLAIM_ILLNESS database table.
 * 
 */
@Entity
@Table(name = "WSL_CLAIM_ILLNESS")
@NamedQuery(name = "WslClaimIllness.findAll", query = "SELECT w FROM WslClaimIllness w")
public class WslClaimIllness implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ILLNESSID", unique = true, nullable = false)
	private Long illnessid;

	private String providerid;

	private String illnesscode;

	@ManyToOne
	@JoinColumn(name = "CLAIMID", nullable = false)
	@JsonIgnore
	private WslGeninfo geninfo;

	public WslClaimIllness() {
	}

	public Long getIllnessid() {
		return illnessid;
	}

	public void setIllnessid(Long illnessid) {
		this.illnessid = illnessid;
	}

	public String getProviderid() {
		return providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public String getIllnesscode() {
		return illnesscode;
	}

	public void setIllnesscode(String illnesscode) {
		this.illnesscode = illnesscode;
	}

	public WslGeninfo getGeninfo() {
		return geninfo;
	}

	public void setGeninfo(WslGeninfo geninfo) {
		this.geninfo = geninfo;
	}

}