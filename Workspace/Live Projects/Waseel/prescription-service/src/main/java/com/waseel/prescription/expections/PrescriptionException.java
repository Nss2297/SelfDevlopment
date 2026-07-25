package com.waseel.prescription.expections;

import com.waseel.prescription.model.common.CommonPrescriptionUpdationResponseModel;
import com.waseel.prescription.model.dispense.PrescriptionDispenseResponseModel;
import com.waseel.prescription.model.inquiry.InquiryInvalidResponseModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;

public class PrescriptionException extends Exception {

	private static final long serialVersionUID = 1L;
	private PrescriptionResponseModel invalidResponse;
	private InquiryInvalidResponseModel invalidInquiryResponse;
	private PrescriptionDispenseResponseModel dispensedResponseModel;
	private CommonPrescriptionUpdationResponseModel prescriptionUpdationResponseModel;

	public PrescriptionException() {
		super();
	}

	public PrescriptionException(CommonPrescriptionUpdationResponseModel invalidResponse) {
		this.prescriptionUpdationResponseModel = invalidResponse;
	}

	public PrescriptionException(PrescriptionResponseModel invalidResponse) {
		this.invalidResponse = invalidResponse;
	}

	public PrescriptionException(InquiryInvalidResponseModel invalidInquiryResponse) {
		this.invalidInquiryResponse = invalidInquiryResponse;
	}

	public PrescriptionException(PrescriptionDispenseResponseModel dispensedResponseModel) {
		this.dispensedResponseModel = dispensedResponseModel;
	}

	public PrescriptionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PrescriptionException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrescriptionException(String message) {
		super(message);
	}
	
	public PrescriptionException(Throwable cause) {
		super(cause);
	}

	public PrescriptionResponseModel getInvalidResponse() {
		return invalidResponse;
	}

	public void setInvalidResponse(PrescriptionResponseModel invalidResponse) {
		this.invalidResponse = invalidResponse;
	}

	public InquiryInvalidResponseModel getInvalidInquiryResponse() {
		return invalidInquiryResponse;
	}

	public void setInvalidInquiryResponse(InquiryInvalidResponseModel invalidInquiryResponse) {
		this.invalidInquiryResponse = invalidInquiryResponse;
	}

	public PrescriptionDispenseResponseModel getDispensedResponseModel() {
		return dispensedResponseModel;
	}

	public void setDispensedResponseModel(PrescriptionDispenseResponseModel dispensedResponseModel) {
		this.dispensedResponseModel = dispensedResponseModel;
	}

	public CommonPrescriptionUpdationResponseModel getPrescriptionUpdationResponseModel() {
		return prescriptionUpdationResponseModel;
	}

	public void setPrescriptionUpdationResponseModel(
			CommonPrescriptionUpdationResponseModel prescriptionUpdationResponseModel) {
		this.prescriptionUpdationResponseModel = prescriptionUpdationResponseModel;
	}
}
