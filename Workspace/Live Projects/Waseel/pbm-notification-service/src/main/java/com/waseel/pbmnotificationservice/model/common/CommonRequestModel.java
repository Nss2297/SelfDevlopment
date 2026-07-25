package com.waseel.pbmnotificationservice.model.common;

import javax.validation.constraints.NotBlank;

public class CommonRequestModel {

	@NotBlank(message = "url {notEmptyValidation}")
	private String url;
	@NotBlank(message = "requestType {notEmptyValidation}")
	private String requestType;
	@NotBlank(message = "requestId {notEmptyValidation}")
	private String requestId;
	@NotBlank(message = "ePrescriptionReferenceNumber {notEmptyValidation}")
	private String ePrescriptionReferenceNumber;

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public String getUrl() {
		return url;
	}

	public String getRequestType() {
		return requestType;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public CommonRequestModel() {
		super();
	}

	public CommonRequestModel(String url, String requestType, String requestId, String ePrescriptionReferenceNumber) {
		super();
		this.url = url;
		this.requestType = requestType;
		this.requestId = requestId;
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

}
