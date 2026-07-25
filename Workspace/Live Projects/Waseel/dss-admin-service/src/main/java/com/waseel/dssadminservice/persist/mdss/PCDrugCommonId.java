package com.waseel.dssadminservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PCDrugCommonId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "`ServiceCode`")
	private String serviceCode;

	@Column(name = "`InteractedServiceCode`")
	private String interactedServiceCode;

	@Column(name = "`PayerId`")
	private String payerId;

	@Column(name = "`ModuleName`")
	private String moduleName;

	public String getPayerId() {
		return payerId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getInteractedServiceCode() {
		return interactedServiceCode;
	}

	public void setInteractedServiceCode(String interactedServiceCode) {
		this.interactedServiceCode = interactedServiceCode;
	}

	public PCDrugCommonId() {
		super();
	}

	public PCDrugCommonId(String serviceCode, String interactedServiceCode, String payerId, String moduleName) {
		super();
		this.serviceCode = serviceCode;
		this.interactedServiceCode = interactedServiceCode;
		this.payerId = payerId;
		this.moduleName = moduleName;
	}
}
