package com.waseel.prescription.model.modifydecision;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.waseel.prescription.model.common.CommonPrescriptionUpdationResponseModel;

@JsonInclude(Include.NON_NULL)
public class ModifyDecisionResponseModel extends CommonPrescriptionUpdationResponseModel {

	private String status;
	private String statusDescription;

	public ModifyDecisionResponseModel() {
		super();
	}

	public ModifyDecisionResponseModel(String ePrescriptionReferenceNumber) {
		super(ePrescriptionReferenceNumber);
	}

	public ModifyDecisionResponseModel(String ePrescriptionReferenceNumber, String status, String statusDesc) {
		super(ePrescriptionReferenceNumber);
		this.status = status;
		this.statusDescription = statusDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
}
