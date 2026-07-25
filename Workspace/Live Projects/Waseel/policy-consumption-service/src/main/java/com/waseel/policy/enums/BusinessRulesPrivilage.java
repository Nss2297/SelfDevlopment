package com.waseel.policy.enums;

public enum BusinessRulesPrivilage {

	PBM_PRESCRIPTION("51.1"),NEW_PRESCRIPTION_PRIVILAGE("51.11"), PRESCRIPTION_FOLLOWUP_PRIVILAGE("51.12"),
	PRESCRIPTION_CANCELLATION_PRIVILAGE("51.13"), DISPENSE_PRIVILAGE("51.15");

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
