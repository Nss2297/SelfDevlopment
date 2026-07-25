package com.waseel.prescription.model.modifydecision;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class ModifyDecisionRequestModel {

	@NotEmpty(message = "drugList {notEmptyValidation}")
	@Valid
	private List<ModifyDecisionDrugList> drugList;

	private BigDecimal totalPrescriptionValue;

	private BigDecimal totalPatientShare;

	private BigDecimal totalPayerShare;

	public List<ModifyDecisionDrugList> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<ModifyDecisionDrugList> drugList) {
		this.drugList = drugList;
	}

	public BigDecimal getTotalPrescriptionValue() {
		return totalPrescriptionValue;
	}

	public BigDecimal getTotalPatientShare() {
		return totalPatientShare;
	}

	public BigDecimal getTotalPayerShare() {
		return totalPayerShare;
	}

	public void setTotalPrescriptionValue(BigDecimal totalPrescriptionValue) {
		this.totalPrescriptionValue = totalPrescriptionValue;
	}

	public void setTotalPatientShare(BigDecimal totalPatientShare) {
		this.totalPatientShare = totalPatientShare;
	}

	public void setTotalPayerShare(BigDecimal totalPayerShare) {
		this.totalPayerShare = totalPayerShare;
	}

}
