package com.waseel.prescription.model.enums;

public enum GenderType {

	MALE("MALE"), FEMALE("FEMALE");

	private final String value;

	private GenderType(String v) {
		this.value = v;
	}
	public String value() {
		return this.value;
	}

	public static GenderType fromValue(String v) {
		for (GenderType c : GenderType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
