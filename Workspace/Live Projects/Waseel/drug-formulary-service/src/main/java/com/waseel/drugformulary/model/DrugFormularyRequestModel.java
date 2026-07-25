package com.waseel.drugformulary.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.drugformulary.validator.customannotation.NoMoreThan100Length;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugFormularyRequestModel {

	@NotEmpty(message = "drug list {notEmptyValidation}")
	private List<@NotBlank(message = "drug list {notEmptyValidation}") String> drugList;
	
	@NotEmpty(message = "RequestId {notEmptyValidation}")
	@NoMoreThan100Length(message = "RequestId {noMoreThan100LengthValidation}")
	private String requestId;

	public DrugFormularyRequestModel() {
		super();
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public List<String> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<String> drugList) {
		this.drugList = drugList;
	}
}
