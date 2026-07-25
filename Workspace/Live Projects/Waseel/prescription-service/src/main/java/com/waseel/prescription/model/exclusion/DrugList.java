package com.waseel.prescription.model.exclusion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugList {

	private String statusCode;
	private String drugCode;
	private List<Rejections> rejectionsList;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public List<Rejections> getRejectionsList() {
		return rejectionsList;
	}

	public void setRejectionsList(List<Rejections> rejectionsList) {
		this.rejectionsList = rejectionsList;
	}

	public DrugList() {
		super();
	}

	public DrugList(String statusCode, String drugCode, List<Rejections> rejectionsList) {
		super();
		this.statusCode = statusCode;
		this.drugCode = drugCode;
		this.rejectionsList = rejectionsList;
	}

}
