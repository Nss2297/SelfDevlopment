package com.waseel.policy.enums;

public enum ConnectionIssueStatus {
	ERROR_MESSAGE("Not able to call pbm-payer-apis-service.");

	private final String value;

	private ConnectionIssueStatus(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static ConnectionIssueStatus fromValue(String v) {
		for (ConnectionIssueStatus c : ConnectionIssueStatus.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
