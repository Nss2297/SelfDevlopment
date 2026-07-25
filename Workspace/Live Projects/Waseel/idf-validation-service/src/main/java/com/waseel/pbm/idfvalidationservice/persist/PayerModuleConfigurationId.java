package com.waseel.pbm.idfvalidationservice.persist;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PayerModuleConfigurationId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class PayerModuleConfigurationId implements java.io.Serializable {

	// Fields

	private Double moduleId;
	private String payerId;

	// Constructors

	/** default constructor */
	public PayerModuleConfigurationId() {
	}

	/** full constructor */
	public PayerModuleConfigurationId(Double moduleId, String payerId) {
		this.moduleId = moduleId;
		this.payerId = payerId;
	}

	// Property accessors

	@Column(name = "ModuleId", nullable = false, precision = 0)

	public Double getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Double moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name = "PayerId", nullable = false, length = 20)

	public String getPayerId() {
		return this.payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PayerModuleConfigurationId))
			return false;
		PayerModuleConfigurationId castOther = (PayerModuleConfigurationId) other;

		return ((this.getModuleId() == castOther.getModuleId()) || (this.getModuleId() != null
				&& castOther.getModuleId() != null && this.getModuleId().equals(castOther.getModuleId())))
				&& ((this.getPayerId() == castOther.getPayerId()) || (this.getPayerId() != null
						&& castOther.getPayerId() != null && this.getPayerId().equals(castOther.getPayerId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getModuleId() == null ? 0 : this.getModuleId().hashCode());
		result = 37 * result + (getPayerId() == null ? 0 : this.getPayerId().hashCode());
		return result;
	}

}