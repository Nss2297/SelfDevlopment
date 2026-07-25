package com.waseel.pbm.idfvalidationservice.persist;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IDFConcurrentMedicationId implements java.io.Serializable {

	private static final long serialVersionUID = 7853470311690751190L;
	private String serviceCode;
	private String cuServiceCode;

	public IDFConcurrentMedicationId() {

	}

	public IDFConcurrentMedicationId(String serviceCode, String cuServiceCode) {
		super();
		this.serviceCode = serviceCode;
		this.cuServiceCode = cuServiceCode;
	}

	@Column(name = "ServiceCode", unique = true, nullable = false, length = 250)
	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	@Column(name = "CUServiceCode", length = 250)
	public String getCuServiceCode() {
		return cuServiceCode;
	}

	public void setCuServiceCode(String cuServiceCode) {
		this.cuServiceCode = cuServiceCode;
	}
}
