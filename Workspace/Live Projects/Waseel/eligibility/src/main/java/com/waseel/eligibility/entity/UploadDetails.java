package com.waseel.eligibility.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UPLOAD_DETAILS")
public class UploadDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6941227107267815743L;

	@Id
	@Column(name = "UPLOADID", unique = true, nullable = false)
	private Long uploadid;

	@Column(name = "UPLOADNAME")
	private String uploadname;

	@Column(name = "PROVIDERID")
	private String providerid;

	public UploadDetails() {

	}

	public UploadDetails(Long uploadid, String uploadname, String providerID) {
		this.uploadid = uploadid;
		this.uploadname = uploadname;
		this.providerid = providerID;
	}

	public Long getUploadid() {
		return uploadid;
	}

	public void setUploadid(Long uploadid) {
		this.uploadid = uploadid;
	}

	public String getUploadname() {
		return uploadname;
	}

	public void setUploadname(String uploadname) {
		this.uploadname = uploadname;
	}

	public String getProviderid() {
		return providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

}
