package com.waseel.pbmnotificationservice.model.sms;

import com.waseel.pbmnotificationservice.model.common.CommonResponseModel;

public class SmsNotificationResponseModel extends CommonResponseModel {

	private String messageId;
	private String timeCreated;

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
		super(status, statusDescription);
		this.messageId = messageId;
		this.timeCreated = timeCreated;
	}

}
