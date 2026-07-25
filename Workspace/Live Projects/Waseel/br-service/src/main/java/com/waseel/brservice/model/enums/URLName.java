package com.waseel.brservice.model.enums;

public enum URLName {
	SENSITIVE_DRUG("/sensitive-drug/validate");

	private final String value;

	private URLName(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static URLName fromValue(String v) {
		for (URLName c : URLName.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
