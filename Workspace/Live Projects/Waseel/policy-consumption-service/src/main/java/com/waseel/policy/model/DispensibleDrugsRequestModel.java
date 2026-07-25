package com.waseel.policy.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DispensibleDrugsRequestModel {

	private String benefitCode;

	private String benefitCase;

	private String payerId;

	private String requestId;

	private String providerId;

	private List<String> dispensibleDrugs;

	public String getBenefitCode() {
		return benefitCode;
	}

	public String getBenefitCase() {
		return benefitCase;
	}

	public String getPayerId() {
		return payerId;
	}

	public String getRequestId() {
		return requestId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setBenefitCode(String benefitCode) {
		this.benefitCode = benefitCode;
	}

	public void setBenefitCase(String benefitCase) {
		this.benefitCase = benefitCase;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public List<String> getDispensibleDrugs() {
		return dispensibleDrugs;
	}

	public void setDispensibleDrugs(List<String> dispensibleDrugs) {
		this.dispensibleDrugs = dispensibleDrugs;
	}

	public DispensibleDrugsRequestModel() {
		super();
	}

	public DispensibleDrugsRequestModel(String benefitCode, String benefitCase, String payerId, String requestId,
			String providerId, List<String> dispensibleDrugs) {
		super();
		this.benefitCode = benefitCode;
		this.benefitCase = benefitCase;
		this.payerId = payerId;
		this.requestId = requestId;
		this.providerId = providerId;
		this.dispensibleDrugs = dispensibleDrugs;
	}

}
