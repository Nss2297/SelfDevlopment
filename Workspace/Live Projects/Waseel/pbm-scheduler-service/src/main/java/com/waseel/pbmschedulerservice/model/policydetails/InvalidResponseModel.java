package com.waseel.pbmschedulerservice.model.policydetails;

import java.util.List;

public class InvalidResponseModel {

	private String status;
	private List<String> errors;

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
}
