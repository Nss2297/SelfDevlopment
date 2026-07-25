package com.waseel.eligibility.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the WSL_CLAIM_INVESTIGATION database table.
 * 
 */
@Entity
@Table(name = "WSL_CLAIM_INVESTIGATION")
@NamedQuery(name = "WslClaimInvestigation.findAll", query = "SELECT w FROM WslClaimInvestigation w")
public class WslClaimInvestigation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INVESTIGATIONID", unique = true, nullable = false)
	private Long investigationid;

	private String providerid;

	private String investigationcode;

	private String investigationserial;

	private String investigationcomments;

	@Temporal(TemporalType.DATE)
	private Date investigationdate;

	private String investigationdescription;

	private String investigationtype;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "investigation", fetch = FetchType.LAZY)
	private List<WslClaimObservation> wslClaimObservations;

	@ManyToOne
	@JoinColumn(name = "CLAIMID", nullable = false)
	@JsonIgnore
	private WslGeninfo geninfo;

	public WslClaimInvestigation() {
	}

	public String getInvestigationcomments() {
		return this.investigationcomments;
	}

	public void setInvestigationcomments(String investigationcomments) {
		this.investigationcomments = investigationcomments;
	}

	public Date getInvestigationdate() {
		return this.investigationdate;
	}

	public void setInvestigationdate(Date investigationdate) {
		this.investigationdate = investigationdate;
	}

	public String getInvestigationdescription() {
		return this.investigationdescription;
	}

	public void setInvestigationdescription(String investigationdescription) {
		this.investigationdescription = investigationdescription;
	}

	public String getInvestigationtype() {
		return this.investigationtype;
	}

	public void setInvestigationtype(String investigationtype) {
		this.investigationtype = investigationtype;
	}

	public List<WslClaimObservation> getWslClaimObservations() {
		return this.wslClaimObservations;
	}

	public void setWslClaimObservations(List<WslClaimObservation> wslClaimObservations) {
		this.wslClaimObservations = wslClaimObservations;
	}

	public Long getInvestigationid() {
		return investigationid;
	}

	public void setInvestigationid(Long investigationid) {
		this.investigationid = investigationid;
	}

	public String getProviderid() {
		return providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public String getInvestigationcode() {
		return investigationcode;
	}

	public void setInvestigationcode(String investigationcode) {
		this.investigationcode = investigationcode;
	}

	public String getInvestigationserial() {
		return investigationserial;
	}

	public void setInvestigationserial(String investigationserial) {
		this.investigationserial = investigationserial;
	}

	public WslGeninfo getGeninfo() {
		return geninfo;
	}

	public void setGeninfo(WslGeninfo geninfo) {
		this.geninfo = geninfo;
	}

}