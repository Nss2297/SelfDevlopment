package com.waseel.policy.enums;

public enum PbmPayerApiStatus {
	INVALID("INVALID"), FAILED("FAILED");

	private final String value;

	private PbmPayerApiStatus(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static PbmPayerApiStatus fromValue(String v) {
		for (PbmPayerApiStatus c : PbmPayerApiStatus.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
