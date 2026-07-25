package com.waseel.pbm.pbmadminservice.model.drugexclusion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugExclusionResponseModel {

	private Long exclusionId;

	private List<String> errors;

	public Long getExclusionId() {
		return exclusionId;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setExclusionId(Long exclusionId) {
		this.exclusionId = exclusionId;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public DrugExclusionResponseModel() {
		super();
	}

	public DrugExclusionResponseModel(Long exclusionId, List<String> errors) {
		super();
		this.exclusionId = exclusionId;
		this.errors = errors;
	}

}
