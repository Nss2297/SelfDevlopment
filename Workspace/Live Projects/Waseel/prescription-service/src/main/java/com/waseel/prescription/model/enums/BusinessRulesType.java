package com.waseel.prescription.model.enums;

public enum BusinessRulesType {

	ELIGIBILITY_CHECK("eligibility"), POLICY_CONSUMPTION_CHECK("policy-consumption");

	private final String value;

	private BusinessRulesType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static BusinessRulesType fromValue(String v) {
		for (BusinessRulesType c : BusinessRulesType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
