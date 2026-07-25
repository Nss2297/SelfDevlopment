package com.waseel.prescription.model.prescription;

public class DrugListServiceModel {

	private String drugCode;
	private Long granularUnit;

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public Long getGranularUnit() {
		return granularUnit;
	}

	public void setGranularUnit(Long granularUnit) {
		this.granularUnit = granularUnit;
	}

	public DrugListServiceModel(String drugCode, Long granularUnit) {
		super();
		this.drugCode = drugCode;
		this.granularUnit = granularUnit;
	}

}
