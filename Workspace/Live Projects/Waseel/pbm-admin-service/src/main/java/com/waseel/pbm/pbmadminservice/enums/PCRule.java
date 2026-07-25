package com.waseel.pbm.pbmadminservice.enums;

public enum PCRule {

	PC_DRUG_TO_DIAGNOSIS_INDICATION_CONTRAINDICATION("PCDTDICRule");

	private final String value;

	private PCRule(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

}
