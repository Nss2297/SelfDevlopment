package com.waseel.pbm.pbmadminservice.enums.management;

public enum Gender {
	MALE("male"), FEMALE("female");

	private final String authority;

	private Gender(String value) {
		this.authority = value;
	}

	public String value() {
		return this.authority;
	}

	public static Gender fromValue(String v) {
		for (Gender c : Gender.values()) {
			if (c.authority.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
