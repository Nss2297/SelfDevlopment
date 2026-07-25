package com.waseel.prescription.model.eligibility;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EligibilityResponseModel {

	private String status;
	private String statusDescription;
	private String referenceNumber;
	private String denialCode;
	private String requestId;
	private int httpStatusCode;
	private String description;

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

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public String getDenialCode() {
		return denialCode;
	}

	public String getRequestId() {
		return requestId;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public void setDenialCode(String denialCode) {
		this.denialCode = denialCode;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EligibilityResponseModel() {
		super();
	}

	public EligibilityResponseModel(String status, String statusDescription, String referenceNumber, String denialCode,
			String requestId, int httpStatusCode, String description) {
		super();
		this.status = status;
		this.statusDescription = statusDescription;
		this.referenceNumber = referenceNumber;
		this.denialCode = denialCode;
		this.requestId = requestId;
		this.httpStatusCode = httpStatusCode;
		this.description = description;
	}

	public EligibilityResponseModel(String status, String statusDescription, String referenceNumber, String denialCode,
			String requestId, int httpStatusCode) {
		super();
		this.status = status;
		this.statusDescription = statusDescription;
		this.referenceNumber = referenceNumber;
		this.denialCode = denialCode;
		this.requestId = requestId;
		this.httpStatusCode = httpStatusCode;
	}
}
