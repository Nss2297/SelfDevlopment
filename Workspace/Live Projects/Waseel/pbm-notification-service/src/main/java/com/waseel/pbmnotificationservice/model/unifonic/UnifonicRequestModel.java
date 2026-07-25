package com.waseel.pbmnotificationservice.model.unifonic;

import javax.validation.constraints.NotEmpty;

import com.waseel.pbmnotificationservice.validator.customannotation.NoMoreThan15Length;

public class UnifonicRequestModel {

	@NotEmpty(message = "appSid {notEmptyValidation}")
	private String appSid;
	@NotEmpty(message = "senderID {notEmptyValidation}")
	private String senderID;
	@NotEmpty(message = "appName {notEmptyValidation}")
	private String appName;
	@NotEmpty(message = "message {notEmptyValidation}")
	private String message;
	@NotEmpty(message = "memberMobileNo {notEmptyValidation}")
	@NoMoreThan15Length(message = "memberMobileNo {noMoreThan15LengthValidation}")
	private String memberMobileNo;

	public String getAppSid() {
		return appSid;
	}

	public String getSenderID() {
		return senderID;
	}

	public String getAppName() {
		return appName;
	}

	public String getMessage() {
		return message;
	}

	public String getMemberMobileNo() {
		return memberMobileNo;
	}

	public void setAppSid(String appSid) {
		this.appSid = appSid;
	}

	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setMemberMobileNo(String memberMobileNo) {
		this.memberMobileNo = memberMobileNo;
	}

	public UnifonicRequestModel() {
		super();
	}

	public UnifonicRequestModel(String appSid, String senderID, String appName, String message, String memberMobileNo) {
		super();
		this.appSid = appSid;
		this.senderID = senderID;
		this.appName = appName;
		this.message = message;
		this.memberMobileNo = memberMobileNo;
	}

}
