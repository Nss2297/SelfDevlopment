package com.waseel.prescription.model.enums;

public enum CommonWords {

	UNDEFINED("undefined");

	private final String value;

	private CommonWords(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static CommonWords fromValue(String v) {
		for (CommonWords c : CommonWords.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
