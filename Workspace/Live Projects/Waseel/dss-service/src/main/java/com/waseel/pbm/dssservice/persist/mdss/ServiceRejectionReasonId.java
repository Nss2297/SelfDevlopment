package com.waseel.pbm.dssservice.persist.mdss;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ServiceRejectionReasonId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class ServiceRejectionReasonId implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6132553527415976260L;
	// Fields
	private String requestId;
	private Long serviceId;
	private String rejectionCode;
	private String rejectionReason;

	// Constructors
	/** default constructor */
	public ServiceRejectionReasonId() {
	}

	/** full constructor */
	public ServiceRejectionReasonId(String requestId, Long serviceId, String rejectionCode, String rejectionReason) {
		this.requestId = requestId;
		this.serviceId = serviceId;
		this.rejectionCode = rejectionCode;
		this.rejectionReason = rejectionReason;
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

	@Column(name = "RejectionCode", length = 30)
	public String getRejectionCode() {
		return this.rejectionCode;
	}

	public void setRejectionCode(String rejectionCode) {
		this.rejectionCode = rejectionCode;
	}

	@Column(name = "RejectionReason", length = 200)
	public String getRejectionReason() {
		return this.rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ServiceRejectionReasonId))
			return false;
		ServiceRejectionReasonId castOther = (ServiceRejectionReasonId) other;

		return ((this.getRequestId() == castOther.getRequestId()) || (this.getRequestId() != null
				&& castOther.getRequestId() != null && this.getRequestId().equals(castOther.getRequestId())))
				&& ((this.getServiceId() == castOther.getServiceId()) || (this.getServiceId() != null
						&& castOther.getServiceId() != null && this.getServiceId().equals(castOther.getServiceId())))
				&& ((this.getRejectionCode() == castOther.getRejectionCode())
						|| (this.getRejectionCode() != null && castOther.getRejectionCode() != null
								&& this.getRejectionCode().equals(castOther.getRejectionCode())))
				&& ((this.getRejectionReason() == castOther.getRejectionReason())
						|| (this.getRejectionReason() != null && castOther.getRejectionReason() != null
								&& this.getRejectionReason().equals(castOther.getRejectionReason())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRequestId() == null ? 0 : this.getRequestId().hashCode());
		result = 37 * result + (getServiceId() == null ? 0 : this.getServiceId().hashCode());
		result = 37 * result + (getRejectionCode() == null ? 0 : this.getRejectionCode().hashCode());
		result = 37 * result + (getRejectionReason() == null ? 0 : this.getRejectionReason().hashCode());
		return result;
	}

}