package com.waseel.pbm.idfvalidationservice.persist;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IDFQuantityLimitCheckId implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6051260007192035109L;

	@Column(name = "ServiceCode")
	private String serviceCode;
	
	@Column(name = "FromAgeDuration-[InDays]")
	private Long fromAgeDurationInDays;
	
	@Column(name = "ToAgeDuration-[InDays]")
	private Long toAgeDurationInDays;

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Long getFromAgeDurationInDays() {
		return fromAgeDurationInDays;
	}

	public void setFromAgeDurationInDays(Long fromAgeDurationInDays) {
		this.fromAgeDurationInDays = fromAgeDurationInDays;
	}

	public Long getToAgeDurationInDays() {
		return toAgeDurationInDays;
	}

	public void setToAgeDurationInDays(Long toAgeDurationInDays) {
		this.toAgeDurationInDays = toAgeDurationInDays;
	}

	
}
