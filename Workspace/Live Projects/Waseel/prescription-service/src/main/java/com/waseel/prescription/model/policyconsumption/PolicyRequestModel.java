package com.waseel.prescription.model.policyconsumption;

import java.util.List;

public class PolicyRequestModel {

	private String benefitCode;
	private String benefitCase;
	private String prescriptionValue;
	private String requestId;
	private String payerId;
	private List<DrugListModel> drugList;
	private String providerId;
	private String requestType;

	public String getBenefitCode() {
		return benefitCode;
	}

	public String getBenefitCase() {
		return benefitCase;
	}

	public String getPrescriptionValue() {
		return prescriptionValue;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setBenefitCode(String benefitCode) {
		this.benefitCode = benefitCode;
	}

	public void setBenefitCase(String benefitCase) {
		this.benefitCase = benefitCase;
	}

	public void setPrescriptionValue(String prescriptionValue) {
		this.prescriptionValue = prescriptionValue;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
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

	public PolicyRequestModel() {
		super();
	}

	public PolicyRequestModel(String benefitCode, String benefitCase, String prescriptionValue, String requestId,
			String payerId, List<DrugListModel> drugList, String providerId, String requestType) {
		super();
		this.benefitCode = benefitCode;
		this.benefitCase = benefitCase;
		this.prescriptionValue = prescriptionValue;
		this.requestId = requestId;
		this.payerId = payerId;
		this.drugList = drugList;
		this.providerId = providerId;
		this.requestType = requestType;
	}

}
