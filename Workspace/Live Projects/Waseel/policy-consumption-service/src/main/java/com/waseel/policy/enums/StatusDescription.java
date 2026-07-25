package com.waseel.policy.enums;

public enum StatusDescription {
	AMOUNT("<amount>"), CURRENCY("<currency>");

	private final String value;

	private StatusDescription(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static StatusDescription fromValue(String v) {
		for (StatusDescription c : StatusDescription.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
