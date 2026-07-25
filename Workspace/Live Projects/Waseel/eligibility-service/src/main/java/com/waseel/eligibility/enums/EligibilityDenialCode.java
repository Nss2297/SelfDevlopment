package com.waseel.eligibility.enums;

public enum EligibilityDenialCode {
	INELIGIBLE("BR_ELGIE03"), INVALID("BR_ELGIV01"), FAILED("BR_ELGF02"), INVALID_MEMBER("BR_ELGIVMP04");

	private final String value;

	private EligibilityDenialCode(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static EligibilityDenialCode fromValue(String v) {
		for (EligibilityDenialCode c : EligibilityDenialCode.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
