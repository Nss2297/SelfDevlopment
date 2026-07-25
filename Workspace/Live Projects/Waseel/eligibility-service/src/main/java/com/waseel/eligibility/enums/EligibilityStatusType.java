package com.waseel.eligibility.enums;

public enum EligibilityStatusType {

	INVALID("INVALID"), FAILED("FAILED"), INELIGIBLE("INELIGIBLE"), ELIGIBLE("ELIGIBLE");

	private final String value;

	private EligibilityStatusType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static EligibilityStatusType fromValue(String v) {
		for (EligibilityStatusType c : EligibilityStatusType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
