package com.waseel.brservice.model.enums;

public enum commonDenials {

	SENSITIVE_DRUG("BR_SDNF01");

	private final String value;

	private commonDenials(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static commonDenials fromValue(String v) {
		for (commonDenials c : commonDenials.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
