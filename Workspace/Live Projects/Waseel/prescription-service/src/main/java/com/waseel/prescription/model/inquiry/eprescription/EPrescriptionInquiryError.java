package com.waseel.prescription.model.inquiry.eprescription;

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

	public EPrescriptionInquiryError() {
		super();
	}

	public EPrescriptionInquiryError(String denialCode, String rejectionReason) {
		super();
		this.denialCode = denialCode;
		this.rejectionReason = rejectionReason;
	}
}
