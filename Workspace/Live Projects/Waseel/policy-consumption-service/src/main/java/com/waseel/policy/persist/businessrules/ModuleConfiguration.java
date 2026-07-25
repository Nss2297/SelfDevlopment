package com.waseel.policy.persist.businessrules;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MODULE_CONFIGURATION", schema = "PBM_BUSINESS_RULES")
public class ModuleConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@AttributeOverride(name = "providerId", column = @Column(name = "PROVIDER_ID", nullable = false, length = 20))
	@AttributeOverride(name = "moduleId", column = @Column(name = "MODULE_ID", nullable = false, precision = 0))
	@AttributeOverride(name = "payerId", column = @Column(name = "PAYER_ID", nullable = false, length = 20))
	private ModuleConfigurationId moduleConfigurationId;
	
	@Column(name = "IS_ENABLED", nullable = false)
	private Boolean isEnabled = true;

	public ModuleConfigurationId getModuleConfigurationId() {
		return moduleConfigurationId;
	}

	public void setModuleConfigurationId(ModuleConfigurationId moduleConfigurationId) {
		this.moduleConfigurationId = moduleConfigurationId;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
