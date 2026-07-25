package com.waseel.pbmnotificationservice.model.eprescription.inquiry;

public class EPrescriptionInquiryError {

	private String denialCode;
	private String rejectionReason;

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
