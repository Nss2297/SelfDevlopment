package com.waseel.prescription.model.prescription;

public class ServiceDetailsModel {

	private String drugCode;
	private String status;
	private String scientificCode;

	public ServiceDetailsModel() {
	}

	public ServiceDetailsModel(String drugCode, String status, String scientificCode) {
		this.drugCode = drugCode;
		this.status = status;
		this.scientificCode = scientificCode;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}
}
