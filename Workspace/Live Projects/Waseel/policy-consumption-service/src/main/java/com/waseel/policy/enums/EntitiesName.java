package com.waseel.policy.enums;

public enum EntitiesName {
	MEMBER_BENEFIT_ASSOICATION("MEMBER_BENEFIT_ASSOICATION"), PRESCRIPTION_METADATA("PRESCRIPTION_METADATA");

	private final String value;

	private EntitiesName(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static EntitiesName fromValue(String v) {
		for (EntitiesName c : EntitiesName.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
