package com.waseel.drugexclusionvalidationservice.model.enums;

public enum DenialCode {
	SPECIALITY_EXCLUSION("BR_EXLPHYSPECDF01"), HIGH_COST_DRUGS_EXCLUSION("BR_EXLHCDF03"),
	PROVIDER_EXCLUSION("BR_EXLPRODF02"), NETWORK_EXCLUSION("BR_EXLNETDF04");

	private final String value;

	private DenialCode(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static DenialCode fromValue(String v) {
		for (DenialCode c : DenialCode.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
