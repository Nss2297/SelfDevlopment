package com.waseel.eligibility.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the CLAIMERRORS database table.
 * 
 */
@Entity
@Table(name = "CLAIMERRORS")
@NamedQuery(name = "Claimerror.findAll", query = "SELECT c FROM Claimerror c")
public class Claimerror implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long errornum;

	private String errormessage;

	private String providerid;

	private String errorcode;

	private String fieldcode;

	@OneToOne
	@JoinColumn(name = "CLAIMID", nullable = false)
	private WslGeninfo geninfo;

	public Claimerror() {
	}

	public long getErrornum() {
		return this.errornum;
	}

	public void setErrornum(long errornum) {
		this.errornum = errornum;
	}

	public String getErrormessage() {
		return this.errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	public String getProviderid() {
		return this.providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getFieldcode() {
		return fieldcode;
	}

	public void setFieldcode(String fieldcode) {
		this.fieldcode = fieldcode;
	}

	public WslGeninfo getGeninfo() {
		return geninfo;
	}

	public void setGeninfo(WslGeninfo geninfo) {
		this.geninfo = geninfo;
	}

}