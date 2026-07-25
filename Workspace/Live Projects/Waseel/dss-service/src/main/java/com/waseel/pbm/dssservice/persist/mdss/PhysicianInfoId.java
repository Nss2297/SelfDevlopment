package com.waseel.pbm.dssservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PhyscisionInfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class PhysicianInfoId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2710092580248371316L;
	// Fields
	private String requestId;
	private String physicianId;

	// Constructors

	/** default constructor */
	public PhysicianInfoId() {
	}

	/** full constructor */
	public PhysicianInfoId(String requestId, String physicianId) {
		this.requestId = requestId;
		this.physicianId = physicianId;
	}

	// Property accessors

	@Column(name = "RequestId", precision = 0)

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Column(name = "PhysicianId", length = 20)

	public String getPhysicianId() {
		return this.physicianId;
	}

	public void setPhysicianId(String physicianId) {
		this.physicianId = physicianId;
	}
	 

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PhysicianInfoId))
			return false;
		PhysicianInfoId castOther = (PhysicianInfoId) other;

		return ((this.getRequestId() == castOther.getRequestId()) || (this.getRequestId() != null
				&& castOther.getRequestId() != null && this.getRequestId().equals(castOther.getRequestId())))
				&& ((this.getPhysicianId() == castOther.getPhysicianId())
						|| (this.getPhysicianId() != null && castOther.getPhysicianId() != null
								&& this.getPhysicianId().equals(castOther.getPhysicianId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRequestId() == null ? 0 : this.getRequestId().hashCode());
		result = 37 * result + (getPhysicianId() == null ? 0 : this.getPhysicianId().hashCode());
		return result;
	}

}