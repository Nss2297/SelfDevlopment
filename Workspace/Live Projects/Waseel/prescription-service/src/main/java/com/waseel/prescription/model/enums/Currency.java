package com.waseel.prescription.model.enums;

public enum Currency {
	SAR("SAR");

	private final String value;

	private Currency(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static Currency fromValue(String v) {
		for (Currency c : Currency.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
