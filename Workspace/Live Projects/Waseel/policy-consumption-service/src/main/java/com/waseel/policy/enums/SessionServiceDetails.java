package com.waseel.policy.enums;

public enum SessionServiceDetails {
	TRANSACTION_LOG_ID("TransactionLogId");

	private final String value;

	private SessionServiceDetails(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static SessionServiceDetails fromValue(String v) {
		for (SessionServiceDetails c : SessionServiceDetails.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
