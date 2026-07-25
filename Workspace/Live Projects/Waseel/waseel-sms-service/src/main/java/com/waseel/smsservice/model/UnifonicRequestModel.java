package com.waseel.smsservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnifonicRequestModel {

	@JsonProperty("AppSid")
	private String appSid;
	@JsonProperty("SenderID")
	private String senderID;
	@JsonProperty("Body")
	private String body;
	@JsonProperty("Recipient")
	private String recipient;
	@JsonProperty("responseType")
	private String responseType;
	@JsonProperty("CorrelationID")
	private String correlationID;
	@JsonProperty("baseEncode")
	private String baseEncode;
	@JsonProperty("statusCallback")
	private String statusCallback;
	@JsonProperty("async")
	private String async;

	@JsonProperty("AppSid")
	public String getAppSid() {
		return appSid;
	}

	@JsonProperty("AppSid")
	public void setAppSid(String appSid) {
		this.appSid = appSid;
	}

	@JsonProperty("SenderID")
	public String getSenderID() {
		return senderID;
	}

	@JsonProperty("SenderID")
	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	@JsonProperty("Body")
	public String getBody() {
		return body;
	}

	@JsonProperty("Body")
	public void setBody(String body) {
		this.body = body;
	}

	@JsonProperty("Recipient")
	public String getRecipient() {
		return recipient;
	}

	@JsonProperty("Recipient")
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	@JsonProperty("responseType")
	public String getResponseType() {
		return responseType;
	}

	@JsonProperty("responseType")
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	@JsonProperty("CorrelationID")
	public String getCorrelationID() {
		return correlationID;
	}

	@JsonProperty("CorrelationID")
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}

	@JsonProperty("baseEncode")
	public String getBaseEncode() {
		return baseEncode;
	}

	@JsonProperty("baseEncode")
	public void setBaseEncode(String baseEncode) {
		this.baseEncode = baseEncode;
	}

	@JsonProperty("statusCallback")
	public String getStatusCallback() {
		return statusCallback;
	}

	@JsonProperty("statusCallback")
	public void setStatusCallback(String statusCallback) {
		this.statusCallback = statusCallback;
	}

	@JsonProperty("async")
	public String getAsync() {
		return async;
	}

	@JsonProperty("async")
	public void setAsync(String async) {
		this.async = async;
	}
}
