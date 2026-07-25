package com.waseel.prescription.model.dispense;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrescriptionDispenseRequestModel {

	@NotEmpty(message = "ePrescriptionReferenceNumber should not be null or empty")
	@JsonProperty("ePrescriptionReferenceNumber")
	private String ePrescriptionReferenceNumber;
	
	private List<String> drugList;

	public List<String> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<String> drugList) {
		this.drugList = drugList;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public PrescriptionDispenseRequestModel(
			@NotEmpty(message = "ePrescriptionReferenceNumber should not be null or empty") String ePrescriptionReferenceNumber) {
		super();
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public PrescriptionDispenseRequestModel() {
		super();
	}

}
