package com.waseel.prescription.model.enums;

public enum EligibilityStatus {
	ELIGIBLE("ELIGIBLE"), INELIGIBLE("INELIGIBLE"), INVALID("INVALID"), FAILED("FAILED");

	private final String value;

	private EligibilityStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
