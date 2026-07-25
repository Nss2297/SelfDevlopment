package com.waseel.pbmnotificationservice.model.enums;

public enum ResponseStatusType {

	RECEIVED("RECEIVED"),FAILED("FAILED"),INVALID("INVALID"),SUCCESS("SUCCESS");

	private final String value;

	private ResponseStatusType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static ResponseStatusType fromValue(String v) {
		for (ResponseStatusType c : ResponseStatusType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
