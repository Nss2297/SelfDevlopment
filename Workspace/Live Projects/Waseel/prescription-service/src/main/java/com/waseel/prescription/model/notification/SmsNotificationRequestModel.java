package com.waseel.prescription.model.notification;

public class SmsNotificationRequestModel extends CommonNotificationModel {

	private String mobileNumber;
	private String message;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SmsNotificationRequestModel() {
		super();
	}

	public SmsNotificationRequestModel(String mobileNumber, String url, String requestType, String requestId,
			String ePrescriptionReferenceNumber, String message) {
		super(url, requestType, requestId, ePrescriptionReferenceNumber);
		this.mobileNumber = mobileNumber;
		this.message = message;
	}
}
