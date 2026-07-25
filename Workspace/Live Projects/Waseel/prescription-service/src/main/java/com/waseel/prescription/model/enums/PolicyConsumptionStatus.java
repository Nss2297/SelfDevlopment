package com.waseel.prescription.model.enums;

public enum PolicyConsumptionStatus {
	APPROVED("APPROVED"), REJECTED("REJECTED"), INVALID("INVALID"), FAILED("FAILED");

	private final String value;

	private PolicyConsumptionStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
