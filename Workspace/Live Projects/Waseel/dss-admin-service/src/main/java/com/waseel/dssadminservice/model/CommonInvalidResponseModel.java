package com.waseel.dssadminservice.model;

import java.util.List;

public class CommonInvalidResponseModel {

	private List<String> errors;

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public CommonInvalidResponseModel() {
	}

	public CommonInvalidResponseModel(List<String> errors) {
		this.errors = errors;
	}
}
