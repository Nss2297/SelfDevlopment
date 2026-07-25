package com.waseel.smsservice.model.sms;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

	@JsonProperty("ID")
	private Integer id;
	@JsonProperty("number")
	private String number;
	@JsonProperty("message")
	private String message;
	@JsonProperty("deviceID")
	private Integer deviceID;
	@JsonProperty("simSlot")
	private String simSlot;
	@JsonProperty("schedule")
	private String schedule;
	@JsonProperty("userID")
	private Integer userID;
	@JsonProperty("groupID")
	private String groupID;
	@JsonProperty("status")
	private String status;
	@JsonProperty("resultCode")
	private String resultCode;
	@JsonProperty("errorCode")
	private String errorCode;
	@JsonProperty("type")
	private String type;
	@JsonProperty("attachments")
	private String attachments;
	@JsonProperty("prioritize")
	private Boolean prioritize;
	@JsonProperty("retries")
	private String retries;
	@JsonProperty("sentDate")
	private String sentDate;
	@JsonProperty("deliveredDate")
	private String deliveredDate;
	@JsonProperty("expiryDate")
	private String expiryDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
	}

	public String getSimSlot() {
		return simSlot;
	}

	public void setSimSlot(String simSlot) {
		this.simSlot = simSlot;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public Boolean getPrioritize() {
		return prioritize;
	}

	public void setPrioritize(Boolean prioritize) {
		this.prioritize = prioritize;
	}

	public String getRetries() {
		return retries;
	}

	public void setRetries(String retries) {
		this.retries = retries;
	}

	public String getSentDate() {
		return sentDate;
	}

	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}

	public String getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(String deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
}
