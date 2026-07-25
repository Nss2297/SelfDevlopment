package com.waseel.pbm.pbmadminservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * PayerConfigId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class PayerConfigId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private String payerId;
	private String pbmPayerType;
	@JsonIgnore
	private Character isEnabled;

	// Constructors

	/** default constructor */
	public PayerConfigId() {
	}

	/** full constructor */
	public PayerConfigId(String payerId, Character isEnabled) {
		this.payerId = payerId;
		this.isEnabled = isEnabled;
	}

	// Property accessors

	@Column(name = "PayerId", length = 100)
	public String getPayerId() {
		return this.payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	@Column(name = "PbmPayerType", length = 1)
	public String getPbmPayerType() {
		return pbmPayerType;
	}

	public void setPbmPayerType(String pbmPayerType) {
		this.pbmPayerType = pbmPayerType;
	}
	
	@Column(name = "isEnabled", length = 1)
	public Character getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(Character isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PayerConfigId))
			return false;
		PayerConfigId castOther = (PayerConfigId) other;

		return ((this.getPayerId() == castOther.getPayerId()) || (this.getPayerId() != null
				&& castOther.getPayerId() != null && this.getPayerId().equals(castOther.getPayerId())))
				&& ((this.getIsEnabled() == castOther.getIsEnabled()) || (this.getIsEnabled() != null
						&& castOther.getIsEnabled() != null && this.getIsEnabled().equals(castOther.getIsEnabled())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPayerId() == null ? 0 : this.getPayerId().hashCode());
		result = 37 * result + (getIsEnabled() == null ? 0 : this.getIsEnabled().hashCode());
		return result;
	}

}