package com.waseel.pbm.dssservice.enums;

public enum ValidationMessages {
	ICDCODE_NOTFOUND ("ICD Not Found for code : "),
	FOUND_DUPLICATE_ICDS("Found Duplicate Icd for code : ");

	private final String value;

	private ValidationMessages(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static ValidationMessages fromValue(String v) {
		for (ValidationMessages c : ValidationMessages.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
