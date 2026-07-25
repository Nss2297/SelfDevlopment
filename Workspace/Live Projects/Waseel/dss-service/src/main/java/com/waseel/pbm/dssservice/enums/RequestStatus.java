package com.waseel.pbm.dssservice.enums;

public enum RequestStatus {
	APPROVED("APPROVED"), REJECTED("REJECTED"), PARTIAL_APPROVED("PARTIAL_APPROVED");

	private final String value;

	private RequestStatus(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static RequestStatus fromValue(String v) {
		for (RequestStatus c : RequestStatus.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
