package com.waseel.pbm.pbmadminservice.model.drugexclusion;

import javax.validation.constraints.NotEmpty;

public class ExclusionDrugListModel {

	@NotEmpty(message = "DrugCode {notNullOrEmpty}")
	private String drugCode;

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public ExclusionDrugListModel() {
		super();
	}

	public ExclusionDrugListModel(String drugCode) {
		super();
		this.drugCode = drugCode;
	}

}
