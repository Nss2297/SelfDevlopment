package com.waseel.eligibility.enums;

public enum TransactionType {

	ELIGIBILITY("ELIGIBILITY");

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
