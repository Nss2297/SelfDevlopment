package com.waseel.pbm.pbmadminservice.model.drugformulary;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AddMemberPolicyDetailsResponseModel {

	private Long formularyId;
	private List<String> errors;

	public Long getFormularyId() {
		return formularyId;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setFormularyId(Long formularyId) {
		this.formularyId = formularyId;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public AddMemberPolicyDetailsResponseModel() {
		super();
	}

	public AddMemberPolicyDetailsResponseModel(Long formularyId, List<String> errors) {
		super();
		this.formularyId = formularyId;
		this.errors = errors;
	}

}
