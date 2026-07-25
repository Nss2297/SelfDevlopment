package com.waseel.policy.enums;

public enum ExceptionLogs {
	FAILED_TRANSACTION("Failed to update transactionLog for requestId:");

	private final String value;

	private ExceptionLogs(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static ExceptionLogs fromValue(String v) {
		for (ExceptionLogs c : ExceptionLogs.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
