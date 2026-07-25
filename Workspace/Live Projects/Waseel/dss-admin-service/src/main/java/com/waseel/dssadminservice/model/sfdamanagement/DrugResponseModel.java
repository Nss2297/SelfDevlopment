package com.waseel.dssadminservice.model.sfdamanagement;

public class DrugResponseModel {

	private Long waseelDrugId;

	public DrugResponseModel(Long waseelDrugId) {
		super();
		this.waseelDrugId = waseelDrugId;
	}

	public Long getWaseelDrugId() {
		return waseelDrugId;
	}

	public void setWaseelDrugId(Long waseelDrugId) {
		this.waseelDrugId = waseelDrugId;
	}

}
