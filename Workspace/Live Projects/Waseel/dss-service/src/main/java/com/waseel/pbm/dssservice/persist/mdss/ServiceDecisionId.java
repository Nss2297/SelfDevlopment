package com.waseel.pbm.dssservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ServiceDecisionId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class ServiceDecisionId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4973179204490225070L;
	// Fields
	private String requestId;
	private Long serviceId;

	// Constructors
	/** default constructor */
	public ServiceDecisionId() {
	}

	/** full constructor */
	public ServiceDecisionId(String requestId, Long serviceId) {
		this.requestId = requestId;
		this.serviceId = serviceId;
	}

	// Property accessors
	@Column(name = "RequestId", precision = 0)
	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Column(name = "ServiceId", precision = 0)
	public Long getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ServiceDecisionId))
			return false;
		ServiceDecisionId castOther = (ServiceDecisionId) other;

		return ((this.getRequestId() == castOther.getRequestId()) || (this.getRequestId() != null
				&& castOther.getRequestId() != null && this.getRequestId().equals(castOther.getRequestId())))
				&& ((this.getServiceId() == castOther.getServiceId()) || (this.getServiceId() != null
						&& castOther.getServiceId() != null && this.getServiceId().equals(castOther.getServiceId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRequestId() == null ? 0 : this.getRequestId().hashCode());
		result = 37 * result + (getServiceId() == null ? 0 : this.getServiceId().hashCode());
		return result;
	}

}