package com.waseel.policy.enums;

public enum BenefitCase {
	IRREPLACEABLE_BRAND("IRREPLACEABLE_BRAND"), REPLACEABLE_BRAND("REPLACEABLE_BRAND"), OUTPATIENT("OUTPATIENT"),
	INPATIENT("INPATIENT");

	private final String value;

	private BenefitCase(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static BenefitCase fromValue(String v) {
		for (BenefitCase c : BenefitCase.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
