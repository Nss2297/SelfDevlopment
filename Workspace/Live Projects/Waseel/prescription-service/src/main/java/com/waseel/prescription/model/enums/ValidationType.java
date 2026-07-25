package com.waseel.prescription.model.enums;

public enum ValidationType {

	MEDICAL("medical"), BUSINESS("business");

	private final String value;

	private ValidationType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static ValidationType fromValue(String v) {
		for (ValidationType c : ValidationType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
