package com.waseel.policy.model.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvalidResponseModel {

	private String status;
	private String statusDescription;
	private List<String> errors;

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public InvalidResponseModel() {
		super();
	}

	public InvalidResponseModel(String status, String statusDescription, List<String> errors) {
		super();
		this.status = status;
		this.statusDescription = statusDescription;
		this.errors = errors;
	}

}
