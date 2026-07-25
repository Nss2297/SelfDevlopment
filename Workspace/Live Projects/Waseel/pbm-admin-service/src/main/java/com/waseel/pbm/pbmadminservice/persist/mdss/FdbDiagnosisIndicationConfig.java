package com.waseel.pbm.pbmadminservice.persist.mdss;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 * FdbdiagnosisIndicationConfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FDBDiagnosisIndicationConfig", schema = "MDSS")
public class FdbDiagnosisIndicationConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String icdCode;
	private String validateSubChapters;
	private Character isEnabled = '0';
	private Character isDeleted = '0';
	private Timestamp lastUpdatedDateTime = Timestamp.from(Instant.now());
	private Long id;

	/** default constructor */
	public FdbDiagnosisIndicationConfig() {
	}

	/** minimal constructor */
	public FdbDiagnosisIndicationConfig(String icdcode) {
		this.icdCode = icdcode;
	}

	/** full constructor */
	public FdbDiagnosisIndicationConfig(String icdcode, String validateSubChapters) {
		this.icdCode = icdcode;
		this.validateSubChapters = validateSubChapters;
	}

	@Id
	@Column(name = "ICDCode", unique = true, nullable = false, length = 20)
	public String getIcdCode() {
		return icdCode;
	}

	public void setIcdCode(String icdCode) {
		this.icdCode = icdCode;
	}

	@Column(name = "ValidateSubChapters", length = 1)
	public String getValidateSubChapters() {
		return this.validateSubChapters;
	}

	public void setValidateSubChapters(String validateSubChapters) {
		this.validateSubChapters = validateSubChapters;
	}

	@Column(name = "IsEnabled", length = 1)
	public Character getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Character isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(name = "IsDeleted", length = 1)
	public Character getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Character isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "LastUpdatedDateTime")
	public Timestamp getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
	}

	public void setLastUpdatedDateTime(Timestamp lastUpdatedDateTime) {
		this.lastUpdatedDateTime = lastUpdatedDateTime;
	}

	@PreUpdate
	protected void preUpdate() {
		this.lastUpdatedDateTime = Timestamp.from(Instant.now());
	}

	@Column(name = "Id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}