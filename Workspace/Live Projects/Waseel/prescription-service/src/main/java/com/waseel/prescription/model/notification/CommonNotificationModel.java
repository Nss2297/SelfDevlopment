package com.waseel.prescription.model.notification;

public class CommonNotificationModel {

	private String url;
	private String requestType;
	private String requestId;
	private String ePrescriptionReferenceNumber;

	public CommonNotificationModel(String url, String requestType, String requestId,
			String ePrescriptionReferenceNumber) {
		this.url = url;
		this.requestType = requestType;
		this.requestId = requestId;
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public CommonNotificationModel() {
		super();
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
