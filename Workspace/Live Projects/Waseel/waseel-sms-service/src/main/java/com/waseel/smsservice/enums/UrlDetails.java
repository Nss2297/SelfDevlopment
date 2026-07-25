package com.waseel.smsservice.enums;

public enum UrlDetails {
	WASEEL_SMS_URL("/sms/send");

	private final String value;

	private UrlDetails(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static UrlDetails fromValue(String v) {
		for (UrlDetails c : UrlDetails.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
