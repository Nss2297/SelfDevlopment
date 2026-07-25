package com.waseel.prescription.model.prescription;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModifyDssDecisionResponseModel {

	@JsonProperty("prescriptionStatus")
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ModifyDssDecisionResponseModel(String status) {
		super();
		this.status = status;
	}

}