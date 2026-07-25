package com.waseel.prescription.model.dss;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "requestId", "drugList" })
public class DssCancellationRequest {

	private String requestId;

	private List<String> drugList = null;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId.trim();
	}

	public List<String> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<String> drugList) {
		drugList.replaceAll(String::trim);
		this.drugList = drugList;
	}

	public DssCancellationRequest(String requestId) {
		super();
		this.requestId = requestId;
	}

}
