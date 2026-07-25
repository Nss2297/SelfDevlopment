package com.waseel.policy.enums;

public enum CurrencyType {
	SAR("SAR");

	private final String value;

	private CurrencyType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static CurrencyType fromValue(String v) {
		for (CurrencyType c : CurrencyType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
