package com.waseel.pbm.dssservice.persist.mdss;

import java.sql.Timestamp;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PayerFeaturesConfiguration entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PAYER_FEATURES_CONFIGURATION", schema = "MDSS")

public class PayerFeaturesConfiguration implements java.io.Serializable {

	// Fields

	private PayerFeaturesConfigurationId id;
	private String isEnabled;
	private Timestamp lastUpdatedDateAndTime;

	// Constructors

	/** default constructor */
	public PayerFeaturesConfiguration() {
	}

	/** full constructor */
	public PayerFeaturesConfiguration(PayerFeaturesConfigurationId id, String isEnabled,
			Timestamp lastUpdatedDateAndTime) {
		this.id = id;
		this.isEnabled = isEnabled;
		this.lastUpdatedDateAndTime = lastUpdatedDateAndTime;
	}

	// Property accessors
	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "payerId", column = @Column(name = "PAYER_ID", nullable = false, length = 20)),
			@AttributeOverride(name = "featureName", column = @Column(name = "FEATURE_NAME", nullable = false, length = 100)) })

	public PayerFeaturesConfigurationId getId() {
		return this.id;
	}

	public void setId(PayerFeaturesConfigurationId id) {
		this.id = id;
	}

	@Column(name = "IS_ENABLED", nullable = false, length = 1)

	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(name = "LAST_UPDATED_DATE_AND_TIME", nullable = false, length = 7)

	public Timestamp getLastUpdatedDateAndTime() {
		return this.lastUpdatedDateAndTime;
	}

	public void setLastUpdatedDateAndTime(Timestamp lastUpdatedDateAndTime) {
		this.lastUpdatedDateAndTime = lastUpdatedDateAndTime;
	}

}