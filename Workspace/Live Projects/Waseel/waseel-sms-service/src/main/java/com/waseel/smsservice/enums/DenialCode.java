package com.waseel.smsservice.enums;

public enum DenialCode {
	INTERNAL_SERVER_ERROR("SMS_ISE01"), UNCONFIGURED_OR_DISABLED_APP("SMS_UCDA02"), INVALID_REQUEST_BODY("SMS_INR03"),
	NO_SMS_RESPONSE("SMS_NR04");

	private final String value;

	private DenialCode(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static DenialCode fromValue(String v) {
		for (DenialCode c : DenialCode.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
