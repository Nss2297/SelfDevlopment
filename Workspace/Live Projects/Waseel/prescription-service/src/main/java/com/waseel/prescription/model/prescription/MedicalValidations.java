package com.waseel.prescription.model.prescription;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicalValidations {

	private String drugCode;
	
	private String scientificCode;

	private String denialCode;

	private String rejectionReason;

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getDenialCode() {
		return denialCode;
	}

	public void setDenialCode(String denialCode) {
		this.denialCode = denialCode;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public MedicalValidations() {
		super();
	}

	public MedicalValidations(String drugCode, String denialCode, String rejectionReason) {
		this.drugCode = drugCode;
		this.denialCode = denialCode;
		this.rejectionReason = rejectionReason;
	}
	
	public MedicalValidations(String drugCode,String scientificCode, String denialCode, String rejectionReason) {
		this.drugCode = drugCode;
		this.scientificCode = scientificCode;
		this.denialCode = denialCode;
		this.rejectionReason = rejectionReason;
	}
}