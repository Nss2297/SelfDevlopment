package com.waseel.pbm.payercustomizationservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class DeleteResponseModel {

	private Long customizationRequestId;
	private String errors;

	public Long getCustomizationRequestId() {
		return customizationRequestId;
	}

	public void setCustomizationRequestId(Long customizationRequestId) {
		this.customizationRequestId = customizationRequestId;
	}

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}
}
