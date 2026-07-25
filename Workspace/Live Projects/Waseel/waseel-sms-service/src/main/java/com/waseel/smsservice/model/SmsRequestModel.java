package com.waseel.smsservice.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsRequestModel {

	@NotNull(message = "appSid is required")
	@JsonProperty("appSid")
	private String appSid;
	@NotNull(message = "senderID is required")
	@JsonProperty("senderID")
	private String senderID;
	@NotNull(message = "appName is required")
	@JsonProperty("appName")
	private String appName;
	@NotNull(message = "message is required")
	@JsonProperty("message")
	private String message;
	@NotNull(message = "memberMobileNo is required")
	@Pattern(regexp = "^(009665|9665|\\+9665)(5|0|3|6|4|9|1|8|7)([0-9]{7})$", message = "Invalid phone number, sample valid phone numbers: 966506990436, 00966506990436 or +966506990436")
	@JsonProperty("memberMobileNo")
	private String memberMobileNo;

	@JsonProperty("appSid")
	public String getAppSid() {
		return appSid;
	}

	@JsonProperty("appSid")
	public void setAppSid(String appSid) {
		this.appSid = appSid;
	}

	@JsonProperty("senderID")
	public String getSenderID() {
		return senderID;
	}

	@JsonProperty("senderID")
	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	@JsonProperty("appName")
	public String getAppName() {
		return appName;
	}

	@JsonProperty("appName")
	public void setAppName(String appName) {
		this.appName = appName;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty("memberMobileNo")
	public String getMemberMobileNo() {
		return memberMobileNo;
	}

	@JsonProperty("memberMobileNo")
	public void setMemberMobileNo(String memberMobileNo) {
		this.memberMobileNo = memberMobileNo;
	}
}
