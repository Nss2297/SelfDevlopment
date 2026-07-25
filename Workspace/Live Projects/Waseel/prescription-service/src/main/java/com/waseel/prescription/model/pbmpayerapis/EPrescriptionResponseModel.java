package com.waseel.prescription.model.pbmpayerapis;

import java.util.List;

public class EPrescriptionResponseModel {

	private String ePrescriptionReferenceNumber;
	private String approvalReferenceNumber;
	private String status;
	private String statusDescription;
	private List<String> errors;

	public EPrescriptionResponseModel() {
		super();
	}

	public EPrescriptionResponseModel(String status, String statusDescription) {
		this.status = status;
		this.statusDescription = statusDescription;
	}

	public EPrescriptionResponseModel(String ePrescriptionReferenceNumber, String approvalReferenceNumber,
			String status, String statusDescription, List<String> errors) {
		super();
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
		this.approvalReferenceNumber = approvalReferenceNumber;
		this.status = status;
		this.statusDescription = statusDescription;
		this.errors = errors;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public String getApprovalReferenceNumber() {
		return approvalReferenceNumber;
	}

	public void setApprovalReferenceNumber(String approvalReferenceNumber) {
		this.approvalReferenceNumber = approvalReferenceNumber;
	}
}
