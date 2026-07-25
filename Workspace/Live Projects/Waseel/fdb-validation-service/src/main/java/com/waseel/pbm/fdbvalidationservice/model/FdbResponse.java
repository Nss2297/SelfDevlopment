package com.waseel.pbm.fdbvalidationservice.model;

import java.util.List;

public class FdbResponse {

	private String requestId ;
	private List<FdbDrugResult> drugResults;
	
	public FdbResponse() {
		super();
	}

	public FdbResponse(String requestId, List<FdbDrugResult> drugResults) {
		super();
		this.requestId = requestId;
		this.drugResults = drugResults;
	}
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public List<FdbDrugResult> getDrugResults() {
		return drugResults;
	}
	public void setDrugResults(List<FdbDrugResult> drugResults) {
		this.drugResults = drugResults;
	}
}
