package com.waseel.brservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SensitiveDrugResponseModel {

	private String requestId;
	private List<DrugList> drugList;

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
}
