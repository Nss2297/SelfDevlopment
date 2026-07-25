package com.waseel.pbm.idfvalidationservice.persist;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IDFDrugToAge", schema = "MDSS")
public class IDFDrugToAge implements java.io.Serializable {

	private static final long serialVersionUID = -2562943180969365292L;
	private String serviceCode;
	private String fromAgeInDays;
	private String toAgeInDays;

	public IDFDrugToAge() {

	}

	public IDFDrugToAge(String serviceCode, String fromAgeInDays, String toAgeInDays) {
		super();
		this.serviceCode = serviceCode;
		this.fromAgeInDays = fromAgeInDays;
		this.toAgeInDays = toAgeInDays;
	}

	@Id
	@Column(name = "ServiceCode", unique = true, nullable = false, length = 250)
	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	@Column(name = "FromAgeInDays", length = 20)
	public String getFromAgeInDays() {
		return fromAgeInDays;
	}

	public void setFromAgeInDays(String fromAgeInDays) {
		this.fromAgeInDays = fromAgeInDays;
	}

	@Column(name = "ToAgeInDays", length = 20)
	public String getToAgeInDays() {
		return toAgeInDays;
	}

	public void setToAgeInDays(String toAgeInDays) {
		this.toAgeInDays = toAgeInDays;
	}
}
