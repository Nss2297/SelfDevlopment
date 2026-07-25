package com.waseel.drugformulary.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugFormularyResponseModel {

	private String statusCode;
	private String denialCode;
	private String statusDescription;
	private String drugCode;

	public DrugFormularyResponseModel() {
		super();
	}

	public DrugFormularyResponseModel(String statusCode, String denialCode, String statusDescription, String drugCode) {
		super();
		this.statusCode = statusCode;
		this.denialCode = denialCode;
		this.statusDescription = statusDescription;
		this.drugCode = drugCode;
	}
	
	public DrugFormularyResponseModel(String statusCode,String drugCode) {
		this.statusCode = statusCode;
		this.drugCode = drugCode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getDenialCode() {
		return denialCode;
	}

	public void setDenialCode(String denialCode) {
		this.denialCode = denialCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}
}
