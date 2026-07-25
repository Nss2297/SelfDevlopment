package com.waseel.pbm.dssservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ServiceDecision entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ServiceDecision", schema = "MDSS")

public class ServiceDecision implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1006673809585810892L;
	// Fields
	private ServiceDecisionId id;
	private String status;
	private Serviceinfo serviceInfo;

	// Constructors
	/** default constructor */
	public ServiceDecision() {
	}

	/** minimal constructor */
	public ServiceDecision(ServiceDecisionId id) {
		this.id = id;
	}

	/** full constructor */
	public ServiceDecision(ServiceDecisionId id, Serviceinfo serviceInfo) {
		this.id = id;
		this.serviceInfo = serviceInfo;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverride(name = "requestId", column = @Column(name = "RequestId", precision = 0))
	@AttributeOverride(name = "serviceId", column = @Column(name = "ServiceId", precision = 0))
	@AttributeOverride(name = "status", column = @Column(name = "Status", length = 50))
	public ServiceDecisionId getId() {
		return this.id;
	}

	public void setId(ServiceDecisionId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RequestId", referencedColumnName = "RequestId", insertable = false, updatable = false)
	@JoinColumn(name = "ServiceId", referencedColumnName = "ServiceId", insertable = false, updatable = false)
	public Serviceinfo getServiceInfo() {
		return this.serviceInfo;
	}

	public void setServiceInfo(Serviceinfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	@Column(name = "Status", length = 50)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}