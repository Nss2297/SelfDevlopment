package com.waseel.pbmnotificationservice.model.enums;

public enum ServiceName {

	WASEEL_SMS_SERVICE("Waseel Sms Service");

	private final String value;

	private ServiceName(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static ServiceName fromValue(String v) {
		for (ServiceName c : ServiceName.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
