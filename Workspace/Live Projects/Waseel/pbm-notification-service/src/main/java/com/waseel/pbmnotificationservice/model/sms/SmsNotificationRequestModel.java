package com.waseel.pbmnotificationservice.model.sms;

import javax.validation.constraints.NotEmpty;

import com.waseel.pbmnotificationservice.model.common.CommonRequestModel;

public class SmsNotificationRequestModel extends CommonRequestModel {

	@NotEmpty(message = "mobileNumber {notEmptyValidation}")
	private String mobileNumber;
	@NotEmpty(message = "message {notEmptyValidation}")
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

	public SmsNotificationRequestModel(String mobileNumber, String message) {
		super();
		this.mobileNumber = mobileNumber;
		this.message = message;
	}

}
