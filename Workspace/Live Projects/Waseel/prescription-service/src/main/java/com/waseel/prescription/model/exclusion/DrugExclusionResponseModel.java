package com.waseel.prescription.model.exclusion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugExclusionResponseModel {

	private String requestId;
	private List<DrugList> drugList;
	private String errorCode;
	private String errorDescription;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public List<DrugList> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<DrugList> drugList) {
		this.drugList = drugList;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public DrugExclusionResponseModel(String requestId, String errorCode, String errorDescription) {
		this.requestId = requestId;
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	public DrugExclusionResponseModel() {
		super();
	}

	public DrugExclusionResponseModel(String requestId, List<DrugList> drugList, String errorCode,
			String errorDescription) {
		super();
		this.requestId = requestId;
		this.drugList = drugList;
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}
	
}
