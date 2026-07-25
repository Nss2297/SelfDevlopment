package com.waseel.pbm.fdbvalidationservice.enums;

public enum Gender {
	MALE("MALE"), FEMALE("FEMALE");

	private final String value;

	private Gender(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static Gender fromValue(String v) {
		for (Gender c : Gender.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
