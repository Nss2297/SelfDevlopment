package com.waseel.policy.enums;

public enum PolicyConsumptionUrl {
	NEW_OR_FOLLOWUP_URL("/policyConsumption"), CANCELLATION_URL("/cancel"), DISPENSING_URL("/dispense");

	private final String value;

	private PolicyConsumptionUrl(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static PolicyConsumptionUrl fromValue(String v) {
		for (PolicyConsumptionUrl c : PolicyConsumptionUrl.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
