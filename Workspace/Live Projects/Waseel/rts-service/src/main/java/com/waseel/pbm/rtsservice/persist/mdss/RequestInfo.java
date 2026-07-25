package com.waseel.pbm.rtsservice.persist.mdss;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RequestInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RequestInfo", schema = "MDSS")

public class RequestInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3336244856242751744L;
	private String requestId;
	private String payerId;
	private String providerId;
	private Character isDeletedFromProvider = '0';
	private Character isCancelled = '0';
	private Character isOverriden = '0';

//	// Constructors

	/** default constructor */
	public RequestInfo() {
	}

	/** minimal constructor */
	public RequestInfo(String requestId) {
		this.requestId = requestId;
	}

	// Property accessors
	@Id
	@Column(name = "RequestId", unique = true, nullable = false, precision = 0)
	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}


	@Column(name = "PayerId", precision = 0)
	public String getPayerId() {
		return this.payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	@Column(name = "ProviderId", precision = 0)

	public String getProviderId() {
		return this.providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	@Column(name = "IsDeletedFromProvider", columnDefinition = "CHAR(1) default ('0')")
	public Character getIsDeletedFromProvider() {
		return isDeletedFromProvider;
	}

	public void setIsDeletedFromProvider(Character isDeletedFromProvider) {
		this.isDeletedFromProvider = isDeletedFromProvider;
	}

	@Column(name = "IsCancelled",columnDefinition = "CHAR(1) default ('0')")
	public Character getIsCancelled() {
		return isCancelled;
	}

	public void setIsCancelled(Character isCancelled) {
		this.isCancelled = isCancelled;
	}

	@Column(name = "IsOverriden",columnDefinition = "CHAR(1) default ('0')")
	public Character getIsOverriden() {
		return isOverriden;
	}

	public void setIsOverriden(Character isOverriden) {
		this.isOverriden = isOverriden;
	}

	
}