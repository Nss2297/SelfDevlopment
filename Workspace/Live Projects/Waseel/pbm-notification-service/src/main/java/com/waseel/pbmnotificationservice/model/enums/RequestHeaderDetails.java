package com.waseel.pbmnotificationservice.model.enums;

public enum RequestHeaderDetails {

	AUTHORIZATION_BASIC("Basic "), AUTHORIZATION_BEARER("Bearer ");

	private final String value;

	private RequestHeaderDetails(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static RequestHeaderDetails fromValue(String v) {
		for (RequestHeaderDetails c : RequestHeaderDetails.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
