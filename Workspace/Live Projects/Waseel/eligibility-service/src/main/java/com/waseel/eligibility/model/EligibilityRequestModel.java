package com.waseel.eligibility.model;

import javax.validation.constraints.NotEmpty;

public class EligibilityRequestModel {

	@NotEmpty(message = "payerId is required.")
	private String payerId;

	@NotEmpty(message = "providerId is required.")
	private String providerId;

	@NotEmpty(message = "requestId is required.")
	private String requestId;

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public EligibilityRequestModel(String payerId, String providerId, String requestId) {
		super();
		this.payerId = payerId;
		this.providerId = providerId;
		this.requestId = requestId;
	}

	public EligibilityRequestModel() {
		super();
	}

}
