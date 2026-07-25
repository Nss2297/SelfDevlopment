package com.waseel.pbm.payercustomizationservice.persist;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ScreeningModules entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ScreeningModules", schema = "MDSS")

public class ScreeningModules implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long moduleId;
	private String moduleName;
	private Set<PayerModuleConfiguration> payerModuleConfigurations = new HashSet<>(0);

	// Constructors
	/** default constructor */
	public ScreeningModules() {
	}

	/** minimal constructor */
	public ScreeningModules(Long moduleId) {
		this.moduleId = moduleId;
	}

	/** full constructor */
	public ScreeningModules(Long moduleId, String moduleName, Set<PayerModuleConfiguration> payerModuleConfigurations) {
		this.moduleId = moduleId;
		this.moduleName = moduleName;
		this.payerModuleConfigurations = payerModuleConfigurations;
	}

	// Property accessors
	@Id
	@Column(name = "ModuleId", unique = true, nullable = false, precision = 0)
	public Long getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name = "ModuleName", length = 50)
	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "screeningModules")
	public Set<PayerModuleConfiguration> getPayerModuleConfigurations() {
		return this.payerModuleConfigurations;
	}

	public void setPayerModuleConfigurations(Set<PayerModuleConfiguration> payerModuleConfigurations) {
		this.payerModuleConfigurations = payerModuleConfigurations;
	}

}