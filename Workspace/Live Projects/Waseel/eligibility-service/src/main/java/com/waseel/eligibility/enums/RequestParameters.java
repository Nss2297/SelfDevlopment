package com.waseel.eligibility.enums;

public enum RequestParameters {

	PAYER_ID("payerId"), PROVIDER_ID("providerId"), REQUEST_ID("requestId");

	private final String value;

	private RequestParameters(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static RequestParameters fromValue(String v) {
		for (RequestParameters c : RequestParameters.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
