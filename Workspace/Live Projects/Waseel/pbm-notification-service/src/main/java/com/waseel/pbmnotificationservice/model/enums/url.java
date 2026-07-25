package com.waseel.pbmnotificationservice.model.enums;

public enum url {

	EMAIL_URL("/email"), SMS_URL("/sms");

	private final String value;

	private url(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static url fromValue(String v) {
		for (url c : url.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
