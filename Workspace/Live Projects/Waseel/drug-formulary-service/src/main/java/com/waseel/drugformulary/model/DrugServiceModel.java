package com.waseel.drugformulary.model;

public class DrugServiceModel {

	private String drugCode;
	private Long waseelDrugId;

	public DrugServiceModel(String drugCode, Long waseelDrugId) {
		this.drugCode = drugCode;
		this.waseelDrugId = waseelDrugId;
	}

	public DrugServiceModel() {
		super();
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public Long getWaseelDrugId() {
		return waseelDrugId;
	}

	public void setWaseelDrugId(Long waseelDrugId) {
		this.waseelDrugId = waseelDrugId;
	}

}
