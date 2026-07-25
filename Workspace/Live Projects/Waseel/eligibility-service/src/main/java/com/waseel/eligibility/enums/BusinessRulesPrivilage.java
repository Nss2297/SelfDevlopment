package com.waseel.eligibility.enums;

public enum BusinessRulesPrivilage {

	BUSINESS_RULES_PRIVILAGE("52"), ELIGIBILITY_PRIVILAGE("52.1"), POLICY_CONSUMPTION_PRIVILAGE("52.2"),
	DRUG_FORMULARY_PRIVILAGE("52.3");

	private final String value;

	private BusinessRulesPrivilage(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static BusinessRulesPrivilage fromValue(String v) {
		for (BusinessRulesPrivilage c : BusinessRulesPrivilage.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
