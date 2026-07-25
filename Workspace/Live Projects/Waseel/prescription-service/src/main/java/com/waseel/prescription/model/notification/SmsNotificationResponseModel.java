package com.waseel.prescription.model.notification;

public class SmsNotificationResponseModel {

	private String status;
	private String statusDescription;
	private String messageId;
	private String timeCreated;

	public String getStatus() {
		return status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getMessageId() {
		return messageId;
	}

	public String getTimeCreated() {
		return timeCreated;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public void setTimeCreated(String timeCreated) {
		this.timeCreated = timeCreated;
	}

	public SmsNotificationResponseModel() {
		super();
	}

	public SmsNotificationResponseModel(String status, String statusDescription, String messageId, String timeCreated) {
		super();
		this.status = status;
		this.statusDescription = statusDescription;
		this.messageId = messageId;
		this.timeCreated = timeCreated;
	}

}
