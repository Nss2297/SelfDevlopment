package com.waseel.pbmnotificationservice.model.enums;

public enum ConnectionIssueStatus {

	FAILED("Failed"), ERROR_MESSAGE("Not able to call ");

	private final String value;

	private ConnectionIssueStatus(String value) {
		this.value = value;
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
