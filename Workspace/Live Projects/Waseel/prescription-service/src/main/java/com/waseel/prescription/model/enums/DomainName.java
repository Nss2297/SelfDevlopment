package com.waseel.prescription.model.enums;

public enum DomainName {

	WASEEL(".waseel.com");

	private final String value;

	private DomainName(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static DomainName fromValue(String v) {
		for (DomainName c : DomainName.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
