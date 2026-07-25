package com.waseel.policy.enums;

public enum BenefitCode {
	PHARMACY("PHARMACY"), MATERNITY("MATERNITY"), DENTAL("DENTAL"), OPTICAL("OPTICAL"), GENERAL("GENERAL");

	private final String value;

	private BenefitCode(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static BenefitCode fromValue(String v) {
		for (BenefitCode c : BenefitCode.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
