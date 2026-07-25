package com.waseel.pbmschedulerservice.model.enums;

public enum Privileges {
	BUSINESS_RULES(52D), FETCH_DETAILS_FROM_PAYER(52.4), POLICY_DETAILS(52.41), MEMBER_DETAILS(52.42),
	PROVIDER_NETWORKS(52.43);

	private final Double value;

	private Privileges(Double value) {
		this.value = value;
	}

	public Double value() {
		return this.value;
	}

	public static Privileges fromValue(Double v) {
		for (Privileges c : Privileges.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException();
	}
}
