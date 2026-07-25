package com.waseel.pbm.dssservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayerCustomizationRequest {

	@JsonProperty("dssRequest")
	private DssRequest dssRequest;

	@JsonProperty("dssResponse")
	private DssResponse dssResponse;

	public DssRequest getDssRequest() {
		return dssRequest;
	}

	public void setDssRequest(DssRequest dssRequest) {
		this.dssRequest = dssRequest;
	}

	public DssResponse getDssResponse() {
		return dssResponse;
	}

	public void setDssResponse(DssResponse dssResponse) {
		this.dssResponse = dssResponse;
	}
	
	
}
