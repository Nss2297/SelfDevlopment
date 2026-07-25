package com.waseel.pbm.pbmadminservice.model.drugformulary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PolicyResponseModel {

	private String drugFormularyAssociationId;
	private String warningMessage;

	public String getDrugFormularyAssociationId() {
		return drugFormularyAssociationId;
	}

	public void setDrugFormularyAssociationId(String drugFormularyAssociationId) {
		this.drugFormularyAssociationId = drugFormularyAssociationId;
	}

	public String getWarningMessage() {
		return warningMessage;
	}

	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}
}
