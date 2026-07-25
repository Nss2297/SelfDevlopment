package com.waseel.prescription.model.enums;

public enum NotificationUrl {

	URL("/patient-prescription?access_token="), BASIC("Basic ");

	private final String value;

	private NotificationUrl(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static NotificationUrl fromValue(String v) {
		for (NotificationUrl c : NotificationUrl.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
