package com.waseel.smsservice.enums;

public enum DenialDescription {
	UNCONFIGURED_APP("This application is unconfigured/disabled and can't use the SMS service"),
	INTERNAL_SERVER_ERROR("INTERNAL SERVER ERROR"), INVALID_REQUEST_BODY("Request body is missing or invalid."),
	NO_RESPONSE("No sms response returned.");

	private final String value;

	private DenialDescription(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static DenialDescription fromValue(String v) {
		for (DenialDescription c : DenialDescription.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
