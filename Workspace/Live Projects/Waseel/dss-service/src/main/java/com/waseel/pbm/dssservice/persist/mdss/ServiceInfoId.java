package com.waseel.pbm.dssservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ServiceInfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class ServiceInfoId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3283684620331783120L;
	// Fields
	private String requestId;
	private Long serviceId;

	// Constructors
	/** default constructor */
	public ServiceInfoId() {
	}

	/** full constructor */
	public ServiceInfoId(String requestId, Long serviceId) {
		this.requestId = requestId;
		this.serviceId = serviceId;
	}

	// Property accessors
	@Column(name = "RequestId", nullable = false, precision = 0)
	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Column(name = "ServiceId", nullable = false, precision = 0)
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
		if (!(other instanceof ServiceInfoId))
			return false;
		ServiceInfoId castOther = (ServiceInfoId) other;

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