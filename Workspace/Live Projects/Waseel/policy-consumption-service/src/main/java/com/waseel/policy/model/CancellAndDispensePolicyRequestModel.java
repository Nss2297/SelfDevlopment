package com.waseel.policy.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CancellAndDispensePolicyRequestModel {

	@NotEmpty(message = "benefitCode {notEmptyValidation}")
	private String benefitCode;
	@NotEmpty(message = "benefitCase {notEmptyValidation}")
	private String benefitCase;
	@NotEmpty(message = "payerId {notEmptyValidation}")
	private String payerId;
	@NotEmpty(message = "requestId {notEmptyValidation}")
	private String requestId;
	private List<DrugListModel> drugList;
	@NotEmpty(message = "providerId {notEmptyValidation}")
	private String providerId;
	@NotEmpty(message = "requestType {notEmptyValidation}")
	private String requestType;

	public String getBenefitCode() {
		return benefitCode;
	}

	public void setBenefitCode(String benefitCode) {
		this.benefitCode = benefitCode;
	}

	public String getBenefitCase() {
		return benefitCase;
	}

	public void setBenefitCase(String benefitCase) {
		this.benefitCase = benefitCase;
	}

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

	public List<DrugListModel> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<DrugListModel> drugList) {
		this.drugList = drugList;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public CancellAndDispensePolicyRequestModel() {
		super();
	}

	public CancellAndDispensePolicyRequestModel(String benefitCode, String benefitCase, String payerId,
			String requestId, List<DrugListModel> drugList) {
		super();
		this.benefitCode = benefitCode;
		this.benefitCase = benefitCase;
		this.payerId = payerId;
		this.requestId = requestId;
		this.drugList = drugList;
	}

	public CancellAndDispensePolicyRequestModel(String benefitCode, String benefitCase, String payerId,
			String requestId, List<DrugListModel> drugList, String providerId, String requestType) {
		super();
		this.benefitCode = benefitCode;
		this.benefitCase = benefitCase;
		this.payerId = payerId;
		this.requestId = requestId;
		this.drugList = drugList;
		this.providerId = providerId;
		this.requestType = requestType;
	}

}
