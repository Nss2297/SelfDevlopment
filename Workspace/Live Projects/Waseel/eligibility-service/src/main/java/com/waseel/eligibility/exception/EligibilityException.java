package com.waseel.eligibility.exception;

import com.waseel.eligibility.model.EligibilityResponseModel;

public class EligibilityException extends Exception {
	private static final long serialVersionUID = 1L;

	private EligibilityResponseModel invalidResponse;

	public EligibilityResponseModel getInvalidResponse() {
		return invalidResponse;
	}

	public void setInvalidResponse(EligibilityResponseModel invalidResponse) {
		this.invalidResponse = invalidResponse;
	}

	public EligibilityException(EligibilityResponseModel invalidResponse) {
		super();
		this.invalidResponse = invalidResponse;
	}

}
