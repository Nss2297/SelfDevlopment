package com.waseel.dssadminservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PCAgeGenderId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "`ServiceCode`")
	private String serviceCode;

	@Column(name = "`PayerId`")
	private String payerId;

	@Column(name = "`ModuleName`")
	private String moduleName;

	public PCAgeGenderId() {
	}

	public PCAgeGenderId(String serviceCode, String payerId, String moduleName) {
		this.serviceCode = serviceCode;
		this.payerId = payerId;
		this.moduleName = moduleName;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public String getPayerId() {
		return payerId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

}
