package com.waseel.pbm.dssservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ServiceRejectionReason entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ServiceRejectionReason", schema = "MDSS")

public class ServiceRejectionReason implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8554868503682191537L;
	// Fields
	private ServiceRejectionReasonId id;

	// Constructors
	/** default constructor */
	public ServiceRejectionReason() {
	}

	/** minimal constructor */
	public ServiceRejectionReason(ServiceRejectionReasonId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverride(name = "requestId", column = @Column(name = "RequestId", precision = 0))
	@AttributeOverride(name = "serviceId", column = @Column(name = "ServiceId", precision = 0))
	@AttributeOverride(name = "rejectionCode", column = @Column(name = "RejectionCode", length = 30))
	@AttributeOverride(name = "rejectionReason", column = @Column(name = "RejectionReason", length = 200))
	public ServiceRejectionReasonId getId() {
		return this.id;
	}

	public void setId(ServiceRejectionReasonId id) {
		this.id = id;
	}
}