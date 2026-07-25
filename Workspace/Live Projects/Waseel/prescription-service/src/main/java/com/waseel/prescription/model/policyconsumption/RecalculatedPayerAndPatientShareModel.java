package com.waseel.prescription.model.policyconsumption;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecalculatedPayerAndPatientShareModel {

	private BigDecimal totalPrescriptionValue;
	private BigDecimal totalPatientShare;
	private BigDecimal totalPayerShare;
	private List<RecalculatedDrugListModel> drugList;

	public BigDecimal getTotalPrescriptionValue() {
		return totalPrescriptionValue;
	}

	public BigDecimal getTotalPatientShare() {
		return totalPatientShare;
	}

	public BigDecimal getTotalPayerShare() {
		return totalPayerShare;
	}

	public List<RecalculatedDrugListModel> getDrugList() {
		return drugList;
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

	public void setDrugList(List<RecalculatedDrugListModel> drugList) {
		this.drugList = drugList;
	}

}
