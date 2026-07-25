package com.waseel.eligibility.enums;

public enum InvalidStatusDescription {

	INVALID_MEMBER("Policy or Member does not exist");

	private final String value;

	private InvalidStatusDescription(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static InvalidStatusDescription fromValue(String v) {
		for (InvalidStatusDescription c : InvalidStatusDescription.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
