package com.waseel.pbm.payercustomizationservice.enums;

public enum URL {

	CUSTOMIZATION_REQUESTS_URL("/customizations/requests");

	private final String value;

	private URL(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static URL fromValue(String value) {
		for (URL c : URL.values()) {
			if (c.value.equals(value)) {
				return c;
			}
		}
		throw new IllegalArgumentException();
	}

}
