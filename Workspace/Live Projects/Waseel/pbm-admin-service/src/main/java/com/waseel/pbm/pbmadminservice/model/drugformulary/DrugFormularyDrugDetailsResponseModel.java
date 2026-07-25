package com.waseel.pbm.pbmadminservice.model.drugformulary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DrugFormularyDrugDetailsResponseModel {

	private Long drugFormularyDetailsId;

	public DrugFormularyDrugDetailsResponseModel() {
	}

	public DrugFormularyDrugDetailsResponseModel(Long drugFormularyDetailsId) {
		this.drugFormularyDetailsId = drugFormularyDetailsId;
	}

	public Long getDrugFormularyDetailsId() {
		return drugFormularyDetailsId;
	}

	public void setDrugFormularyDetailsId(Long drugFormularyDetailsId) {
		this.drugFormularyDetailsId = drugFormularyDetailsId;
	}
}
