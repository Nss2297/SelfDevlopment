package com.waseel.policy.enums;

public enum PolicyResponseStatus {
	APPROVED("APPROVED"), REJECTED("REJECTED"), INVALID("INVALID"), FAILED("FAILED");

	private final String value;

	private PolicyResponseStatus(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static PolicyResponseStatus fromValue(String v) {
		for (PolicyResponseStatus c : PolicyResponseStatus.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
