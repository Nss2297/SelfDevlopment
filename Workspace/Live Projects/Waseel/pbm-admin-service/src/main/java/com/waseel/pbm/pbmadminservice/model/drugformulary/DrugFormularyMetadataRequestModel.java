package com.waseel.pbm.pbmadminservice.model.drugformulary;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang.StringUtils;

import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan100Length;

public class DrugFormularyMetadataRequestModel {

	@NotBlank(message = "formularyName {notNullOrEmpty}")
	@NoMoreThan100Length(message = "formularyName {noMoreThan100LengthValidation}")
	private String formularyName;

	public DrugFormularyMetadataRequestModel() {
	}

	public DrugFormularyMetadataRequestModel(String formularyName) {
		this.formularyName = formularyName;
	}

	public String getFormularyName() {
		return formularyName;
	}

	public void setFormularyName(String formularyName) {
		this.formularyName = !StringUtils.isBlank(formularyName) ? formularyName.trim() : formularyName;
	}
}
