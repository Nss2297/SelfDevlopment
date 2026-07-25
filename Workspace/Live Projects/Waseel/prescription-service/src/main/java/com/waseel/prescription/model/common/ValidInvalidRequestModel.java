package com.waseel.prescription.model.common;

import com.waseel.prescription.persist.prescriptionservice.InvalidPrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;

public class ValidInvalidRequestModel {

	private PrescriptionRequest prescriptionRequest;
	private InvalidPrescriptionRequest invalidPrescriptionRequest;

	public ValidInvalidRequestModel() {
	}

	public ValidInvalidRequestModel(InvalidPrescriptionRequest invalidPrescriptionRequest) {
		this.invalidPrescriptionRequest = invalidPrescriptionRequest;
	}

	public ValidInvalidRequestModel(PrescriptionRequest prescriptionRequest) {
		this.prescriptionRequest = prescriptionRequest;
	}

	public PrescriptionRequest getPrescriptionRequest() {
		return prescriptionRequest;
	}

	public void setPrescriptionRequest(PrescriptionRequest prescriptionRequest) {
		this.prescriptionRequest = prescriptionRequest;
	}

	public InvalidPrescriptionRequest getInvalidPrescriptionRequest() {
		return invalidPrescriptionRequest;
	}

	public void setInvalidPrescriptionRequest(InvalidPrescriptionRequest invalidPrescriptionRequest) {
		this.invalidPrescriptionRequest = invalidPrescriptionRequest;
	}

}
