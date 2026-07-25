package com.waseel.prescription.model.pbmpayerapis;

public class EPrescriptionError {

	private String denialCode;
	private String rejectionReason;

	public EPrescriptionError() {
	}

	public EPrescriptionError(String denialCode, String rejectionReason) {
		this.denialCode = denialCode;
		this.rejectionReason = rejectionReason;
	}

	public String getDenialCode() {
		return denialCode;
	}

	public void setDenialCode(String denialCode) {
		this.denialCode = denialCode;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

}
