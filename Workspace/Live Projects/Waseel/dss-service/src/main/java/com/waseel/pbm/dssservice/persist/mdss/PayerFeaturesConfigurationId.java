package com.waseel.pbm.dssservice.persist.mdss;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PayerFeaturesConfigurationId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class PayerFeaturesConfigurationId implements java.io.Serializable {

	// Fields

	private String payerId;
	private String featureName;

	// Constructors

	/** default constructor */
	public PayerFeaturesConfigurationId() {
	}

	/** full constructor */
	public PayerFeaturesConfigurationId(String payerId, String featureName) {
		this.payerId = payerId;
		this.featureName = featureName;
	}

	// Property accessors

	@Column(name = "PAYER_ID", nullable = false, length = 20)

	public String getPayerId() {
		return this.payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	@Column(name = "FEATURE_NAME", nullable = false, length = 100)

	public String getFeatureName() {
		return this.featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PayerFeaturesConfigurationId))
			return false;
		PayerFeaturesConfigurationId castOther = (PayerFeaturesConfigurationId) other;

		return ((this.getPayerId() == castOther.getPayerId()) || (this.getPayerId() != null
				&& castOther.getPayerId() != null && this.getPayerId().equals(castOther.getPayerId())))
				&& ((this.getFeatureName() == castOther.getFeatureName())
						|| (this.getFeatureName() != null && castOther.getFeatureName() != null
								&& this.getFeatureName().equals(castOther.getFeatureName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPayerId() == null ? 0 : this.getPayerId().hashCode());
		result = 37 * result + (getFeatureName() == null ? 0 : this.getFeatureName().hashCode());
		return result;
	}

}