package com.waseel.pbmschedulerservice.model.enums;

public enum RequestType {

	POLICY_DETAILS("POLICY_DETAILS"), MEMBER_DETAILS("MEMBER_DETAILS"),PROVIDER_NETWORKS("PROVIDER_NETWORKS");

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
