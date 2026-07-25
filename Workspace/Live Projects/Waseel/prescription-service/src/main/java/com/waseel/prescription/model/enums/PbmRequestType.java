package com.waseel.prescription.model.enums;

public enum PbmRequestType {

	NEW("NEW"), FOLLOWUP("FOLLOWUP"), CANCELLATION("CANCELLATION"), DISPENSED("DISPENSE"), FULL_DISPENSED("DISPENSED"),
	PARTIAL_DISPENSED("PARTIAL_DISPENSED"),MODIFY_DECISION("MODIFY_DECISION");

	private final String value;

	private PbmRequestType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static PbmRequestType fromValue(String v) {
		for (PbmRequestType c : PbmRequestType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
