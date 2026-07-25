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
 * The persistent class for the WSL_CLAIM_OBSERVATION database table.
 * 
 */
@Entity
@Table(name = "WSL_CLAIM_OBSERVATION")
@NamedQuery(name = "WslClaimObservation.findAll", query = "SELECT w FROM WslClaimObservation w")
public class WslClaimObservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OBSERVATIONID", unique = true, nullable = false)
	private Long observationid;

	private String providerid;

	private String observationcode;

	private String observationserial;

	private String observationcomment;

	private String observationdescription;

	private String observationunit;

	private String observationvalue;

	@ManyToOne
	@JoinColumn(name = "INVESTIGATIONID", nullable = false)
	@JsonIgnore
	private WslClaimInvestigation investigation;

	public WslClaimObservation() {
	}

	public String getObservationcomment() {
		return this.observationcomment;
	}

	public void setObservationcomment(String observationcomment) {
		this.observationcomment = observationcomment;
	}

	public String getObservationdescription() {
		return this.observationdescription;
	}

	public void setObservationdescription(String observationdescription) {
		this.observationdescription = observationdescription;
	}

	public String getObservationunit() {
		return this.observationunit;
	}

	public void setObservationunit(String observationunit) {
		this.observationunit = observationunit;
	}

	public String getObservationvalue() {
		return this.observationvalue;
	}

	public void setObservationvalue(String observationvalue) {
		this.observationvalue = observationvalue;
	}

	public Long getObservationid() {
		return observationid;
	}

	public void setObservationid(Long observationid) {
		this.observationid = observationid;
	}

	public String getProviderid() {
		return providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public String getObservationcode() {
		return observationcode;
	}

	public void setObservationcode(String observationcode) {
		this.observationcode = observationcode;
	}

	public String getObservationserial() {
		return observationserial;
	}

	public void setObservationserial(String observationserial) {
		this.observationserial = observationserial;
	}

	public WslClaimInvestigation getInvestigation() {
		return investigation;
	}

	public void setInvestigation(WslClaimInvestigation investigation) {
		this.investigation = investigation;
	}

}