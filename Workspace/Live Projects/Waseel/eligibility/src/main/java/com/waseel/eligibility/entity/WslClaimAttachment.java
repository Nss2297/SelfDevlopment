package com.waseel.eligibility.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the WSL_CLAIM_ATTACHMENT database table.
 * 
 */
@Entity
@Table(name = "WSL_CLAIM_ATTACHMENT")
@NamedQuery(name = "WslClaimAttachment.findAll", query = "SELECT w FROM WslClaimAttachment w")
public class WslClaimAttachment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ATTACHMENTID", unique = true, nullable = false)
	private Long attachmentid;

	private String providerid;

	private String filename;

	@Lob
	private byte[] attachmentfile;

	private String filetype;

	private String usercomment;

	@ManyToOne
	@JoinColumn(name = "CLAIMID", nullable = false)
	@JsonIgnore
	private WslGeninfo geninfo;

	public WslClaimAttachment() {
	}

	public byte[] getAttachmentfile() {
		return this.attachmentfile;
	}

	public void setAttachmentfile(byte[] attachmentfile) {
		this.attachmentfile = attachmentfile;
	}

	public String getFiletype() {
		return this.filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getUsercomment() {
		return this.usercomment;
	}

	public void setUsercomment(String usercomment) {
		this.usercomment = usercomment;
	}

	public Long getAttachmentid() {
		return attachmentid;
	}

	public void setAttachmentid(Long attachmentid) {
		this.attachmentid = attachmentid;
	}

	public String getProviderid() {
		return providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public WslGeninfo getGeninfo() {
		return geninfo;
	}

	public void setGeninfo(WslGeninfo geninfo) {
		this.geninfo = geninfo;
	}

}