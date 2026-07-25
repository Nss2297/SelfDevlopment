package com.waseel.policy.exception;

import com.waseel.policy.model.PolicyResponseModel;

public class PolicyException extends Exception {
	private static final long serialVersionUID = 1L;

	private PolicyResponseModel invalidResponse;

	public PolicyResponseModel getInvalidResponse() {
		return invalidResponse;
	}

	public void setInvalidResponse(PolicyResponseModel invalidResponse) {
		this.invalidResponse = invalidResponse;
	}

	public PolicyException(PolicyResponseModel invalidResponse) {
		super();
		this.invalidResponse = invalidResponse;
	}
}
