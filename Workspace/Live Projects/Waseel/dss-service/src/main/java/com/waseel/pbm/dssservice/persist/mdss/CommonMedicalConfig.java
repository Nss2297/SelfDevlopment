package com.waseel.pbm.dssservice.persist.mdss;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CommonMedicalConfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "COMMON_MEDICAL_CONFIG", schema = "MDSS")

public class CommonMedicalConfig implements java.io.Serializable {

	// Fields

	private String key;
	private String value;
	private String isEnabled;
	private Timestamp lastUpdateDateAndTime;

	// Constructors

	/** default constructor */
	public CommonMedicalConfig() {
	}

	/** full constructor */
	public CommonMedicalConfig(String key, String value, String isEnabled, Timestamp lastUpdateDateAndTime) {
		this.key = key;
		this.value = value;
		this.isEnabled = isEnabled;
		this.lastUpdateDateAndTime = lastUpdateDateAndTime;
	}

	// Property accessors
	@Id

	@Column(name = "KEY", unique = true, nullable = false, length = 250)

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "VALUE", nullable = false, length = 250)

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "IS_ENABLED", nullable = false, length = 1)

	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(name = "LAST_UPDATE_DATE_AND_TIME", nullable = false, length = 7)

	public Timestamp getLastUpdateDateAndTime() {
		return this.lastUpdateDateAndTime;
	}

	public void setLastUpdateDateAndTime(Timestamp lastUpdateDateAndTime) {
		this.lastUpdateDateAndTime = lastUpdateDateAndTime;
	}

}