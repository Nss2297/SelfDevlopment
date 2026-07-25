package com.waseel.policy.enums;

public enum TransactionStatusType {

	SENT("Sent"), RECEIVED("Received");

	private final String value;

	private TransactionStatusType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static TransactionStatusType fromValue(String v) {
		for (TransactionStatusType c : TransactionStatusType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
