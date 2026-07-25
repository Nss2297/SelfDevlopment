package com.waseel.eligibility.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the BATCHINFO database table.
 * 
 */
@Entity
@NamedQuery(name = "Batchinfo.findAll", query = "SELECT b FROM Batchinfo b")
public class Batchinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "BATCHINFO_BATCHID_GENERATOR", sequenceName = "BATCH_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCHINFO_BATCHID_GENERATOR")
	private String batchid;

	@Temporal(TemporalType.DATE)
	private Date batchdate;

	private String batchstatus;

	private String providerid;

	private String statusdetails;
	
	private String providerbatchid;

	public Batchinfo() {
	}

	public String getBatchid() {
		return this.batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public Date getBatchdate() {
		return this.batchdate;
	}

	public void setBatchdate(Date batchdate) {
		this.batchdate = batchdate;
	}

	public String getBatchstatus() {
		return this.batchstatus;
	}

	public void setBatchstatus(String batchstatus) {
		this.batchstatus = batchstatus;
	}

	public String getProviderid() {
		return this.providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public String getStatusdetails() {
		return this.statusdetails;
	}

	public void setStatusdetails(String statusdetails) {
		this.statusdetails = statusdetails;
	}

	public String getProviderbatchid() {
		return providerbatchid;
	}

	public void setProviderbatchid(String providerbatchid) {
		this.providerbatchid = providerbatchid;
	}

	public Claimprop addClaimprop(Claimprop claimprop) {

		return claimprop;
	}

	public Claimprop removeClaimprop(Claimprop claimprop) {

		return claimprop;
	}

}