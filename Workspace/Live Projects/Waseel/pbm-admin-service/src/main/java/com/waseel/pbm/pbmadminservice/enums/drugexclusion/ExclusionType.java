package com.waseel.pbm.pbmadminservice.enums.drugexclusion;

public enum ExclusionType {
	HIGH_COST_EXCLUSION("High Cost Medicine"), NETWORK_EXCLUSION("Network Exclusion"),
	PROVIDER_EXCLUSION("Provider Exclusion"), SPECIALITY_EXCLUSION("Speciality Exclusion");

	private final String value;

	private ExclusionType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static ExclusionType fromValue(String v) {
		for (ExclusionType c : ExclusionType.values()) {
			if (c.value().equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
