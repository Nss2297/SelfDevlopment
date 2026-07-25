package com.waseel.policy.model;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeactivatePrescriptionRequestModel {

	@NotEmpty(message = "payerId {notEmptyValidation}")
	private String payerId;
	@NotEmpty(message = "requestId {notEmptyValidation}")
	private String requestId;
	@NotEmpty(message = "providerId {notEmptyValidation}")
	private String providerId;

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public DeactivatePrescriptionRequestModel() {
		super();
	}

	public DeactivatePrescriptionRequestModel(String payerId, String requestId, String providerId) {
		super();
		this.payerId = payerId;
		this.requestId = requestId;
		this.providerId = providerId;
	}

}
