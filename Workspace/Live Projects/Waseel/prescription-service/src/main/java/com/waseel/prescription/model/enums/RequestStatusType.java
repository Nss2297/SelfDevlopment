package com.waseel.prescription.model.enums;

public enum RequestStatusType {

	APPROVED("APPROVED"), REJECTED("REJECTED"), PARTIAL_APPROVED("PARTIAL_APPROVED"), DISPENSED("DISPENSED"),
	CANCELLED("CANCELLED"), PARTIAL_DISPENSED("PARTIAL_DISPENSED"), PENDING("PENDING"), INVALID("INVALID"),
	FAILED("FAILED");

	private final String value;

	private RequestStatusType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static RequestStatusType fromValue(String v) {
		for (RequestStatusType c : RequestStatusType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
