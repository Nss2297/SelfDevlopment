package com.waseel.pbm.rtsservice.persist.mdss;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * PayerModuleConfiguration entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PayerModuleConfiguration", schema = "MDSS")

public class PayerModuleConfiguration implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8665623725509642473L;
	private PayerModuleConfigurationId id;
	private ScreeningModules screeningModules;
	private String isEnabled;

	// Constructors

	/** default constructor */
	public PayerModuleConfiguration() {
	}

	/** minimal constructor */
	public PayerModuleConfiguration(PayerModuleConfigurationId id, ScreeningModules screeningModules) {
		this.id = id;
		this.screeningModules = screeningModules;
	}

	/** full constructor */
	public PayerModuleConfiguration(PayerModuleConfigurationId id, ScreeningModules screeningModules,
			String isEnabled) {
		this.id = id;
		this.screeningModules = screeningModules;
		this.isEnabled = isEnabled;
	}

	// Property accessors
	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "moduleId", column = @Column(name = "ModuleId", nullable = false, precision = 0)),
			@AttributeOverride(name = "payerId", column = @Column(name = "PayerId", nullable = false, length = 20)) })

	public PayerModuleConfigurationId getId() {
		return this.id;
	}

	public void setId(PayerModuleConfigurationId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ModuleId", nullable = false, insertable = false, updatable = false)

	public ScreeningModules getScreeningModules() {
		return this.screeningModules;
	}

	public void setScreeningModules(ScreeningModules screeningModules) {
		this.screeningModules = screeningModules;
	}

	@Column(name = "IsEnabled", length = 1)

	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

}