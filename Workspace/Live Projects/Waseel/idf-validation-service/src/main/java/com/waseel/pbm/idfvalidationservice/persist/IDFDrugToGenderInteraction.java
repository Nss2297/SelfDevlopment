package com.waseel.pbm.idfvalidationservice.persist;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IDFDrugToGenderInteraction", schema = "MDSS")
public class IDFDrugToGenderInteraction implements java.io.Serializable {

	private static final long serialVersionUID = -2562943180969365292L;
	private String serviceCode;
	private String gender;

	public IDFDrugToGenderInteraction() {

	}

	public IDFDrugToGenderInteraction(String serviceCode, String gender) {
		super();
		this.serviceCode = serviceCode;
		this.gender = gender;
	}

	@Id
	@Column(name = "ServiceCode", unique = true, nullable = false, length = 250)
	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	@Column(name = "Gender", length = 20)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
