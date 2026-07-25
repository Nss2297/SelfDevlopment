package com.waseel.smsservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnifonicResponseModel {

	@JsonProperty("success")
	private Boolean success;
	@JsonProperty("message")
	private String message;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("errorCode")
	private String errorCode;
	@JsonProperty("data")
	private Data data;

	@JsonProperty("success")
	public Boolean getSuccess() {
		return success;
	}

	@JsonProperty("success")
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty("errorCode")
	public String getErrorCode() {
		return errorCode;
	}

	@JsonProperty("errorCode")
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@JsonProperty("data")
	public Data getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(Data data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UnifonicResponseModel(Boolean success, String message, String status, String errorCode, Data data) {
		super();
		this.success = success;
		this.message = message;
		this.status = status;
		this.errorCode = errorCode;
		this.data = data;
	}

	public UnifonicResponseModel() {
		super();
	}
}
