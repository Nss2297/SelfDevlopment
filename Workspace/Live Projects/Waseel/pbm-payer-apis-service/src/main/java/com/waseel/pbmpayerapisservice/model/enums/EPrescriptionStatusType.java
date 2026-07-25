package com.waseel.pbmpayerapisservice.model.enums;

public enum EPrescriptionStatusType {
	APPROVED("APPROVED"), REJECTED("REJECTED"), PARTIAL_APPROVED("PARTIAL_APPROVED"),PENDING("PENDING"),
	DISPENSED("DISPENSED"),CANCELLED("CANCELLED"),PARTIAL_DISPENSED("PARTIAL_DISPENSED"); 
	
	private final String value;

	private EPrescriptionStatusType(String value) {
		this.value = value;
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
