package com.waseel.pbmnotificationservice.model.enums;

public enum EPrescriptionStatusType {

	APPROVED("APPROVED"), REJECTED("REJECTED"), PARTIAL_APPROVED("PARTIAL_APPROVED"), DISPENSED("DISPENSED"),
	PARTIAL_DISPENSED("PARTIAL_DISPENSED");

	private final String value;

	private EPrescriptionStatusType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static EPrescriptionStatusType fromValue(String v) {
		for (EPrescriptionStatusType c : EPrescriptionStatusType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
