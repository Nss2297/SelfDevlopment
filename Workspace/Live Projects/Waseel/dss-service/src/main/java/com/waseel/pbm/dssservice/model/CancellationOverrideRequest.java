package com.waseel.pbm.dssservice.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.waseel.pbm.dssservice.validator.customannotation.NoMoreThan100Length;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "requestId", "drugList" })
public class CancellationOverrideRequest {

	@NotEmpty(message = "requestId should not be null or empty")
	@NoMoreThan100Length(message = "RequestId {noMoreThan100LengthValidation}")
	@JsonProperty("requestId")
	private String requestId;

	@JsonProperty("drugList")
	private List<String> drugList = null;

	@JsonProperty("requestId")
	public String getRequestId() {
		return requestId;
	}

	@JsonProperty("requestId")
	public void setRequestId(String requestId) {
		this.requestId = requestId.trim();
	}

	@JsonProperty("drugList")
	public List<String> getDrugList() {
		return drugList;
	}

	@JsonProperty("drugList")
	public void setDrugList(List<String> drugList) {
		drugList.replaceAll(String::trim);
		this.drugList = drugList;
	}
}
