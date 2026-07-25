package com.waseel.pbm.dssservice.enums;

public enum PayerFeatures {
	CHECK_MEMBER_CHRONIC_DZ_HISTORY("CHECK_MEMBER_CHRONIC_DZ_HISTORY");

	private final String value;

	private PayerFeatures(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static PayerFeatures fromValue(String v) {
		for (PayerFeatures c : PayerFeatures.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
