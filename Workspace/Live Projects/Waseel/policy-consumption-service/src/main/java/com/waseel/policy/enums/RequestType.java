package com.waseel.policy.enums;

public enum RequestType {
	NEW("NEW_PRESCRIPTION"), FOLLOWUP("FOLLOW_UP_PRESCRIPTION"), CANCELLATION("PRESCRIPTION_CANCELLATION"),
	DISPENSED("DISPENSED"), PARTIALLY_DISPENSED("PARTIALLY_DISPENSED");

	private final String value;

	private RequestType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static RequestType fromValue(String v) {
		for (RequestType c : RequestType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
