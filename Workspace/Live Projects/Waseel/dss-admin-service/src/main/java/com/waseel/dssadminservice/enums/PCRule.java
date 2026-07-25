package com.waseel.dssadminservice.enums;

public enum PCRule {

	PC_DRUG_TO_GENDER("PCDTGRule"),PC_DRUG_TO_AGE("PCDTARule"),PC_DRUG_TO_DRUG("PCDTDRule"),PC_DUPLICATE_THERAPY("PCDTRule");

	private final String value;

	private PCRule(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

}
