package com.waseel.policy.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicyRequestModel {

	@NotEmpty(message = "benefitCode {notEmptyValidation}")
	private String benefitCode;
	@NotEmpty(message = "benefitCase {notEmptyValidation}")
	private String benefitCase;
	@NotEmpty(message = "prescriptionValue {notEmptyValidation}")
	private String prescriptionValue;
	@NotEmpty(message = "payerId {notEmptyValidation}")
	private String payerId;
	@NotEmpty(message = "requestId {notEmptyValidation}")
	private String requestId;
	@NotEmpty(message = "drugList {notEmptyValidation}")
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

	public String getPrescriptionValue() {
		return prescriptionValue;
	}

	public void setPrescriptionValue(String prescriptionValue) {
		this.prescriptionValue = prescriptionValue;
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

}
