package com.waseel.policy.enums;

public enum TransactionType {
	POLICY_CONSUMPTION("POLICY_CONSUMPTION");

	private final String value;

	private TransactionType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static TransactionType fromValue(String v) {
		for (TransactionType c : TransactionType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
