package com.waseel.prescription.model.cancellation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescriptionCancellationResponseModel {

	@JsonProperty("ePrescriptionReferenceNumber")
	private String ePrescriptionReferenceNumber;
	private String status;
	private String statusDescription;
	private boolean canCancel;
	private boolean canFollowUp;
	private int httpStatusCode;

	public boolean isCanFollowUp() {
		return canFollowUp;
	}

	public void setCanFollowUp(boolean canFollowUp) {
		this.canFollowUp = canFollowUp;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public boolean isCanCancel() {
		return canCancel;
	}

	public void setCanCancel(boolean canCancel) {
		this.canCancel = canCancel;
	}

	public PrescriptionCancellationResponseModel(String ePrescriptionReferenceNumber, String status,
			String statusDescription, boolean canCancel, boolean canFollowUp, int httpStatusCode) {
		super();
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
		this.status = status;
		this.statusDescription = statusDescription;
		this.canCancel = canCancel;
		this.canFollowUp = canFollowUp;
		this.httpStatusCode = httpStatusCode;
	}

	public PrescriptionCancellationResponseModel() {
		super();
	}

}
