package com.waseel.drugexclusionvalidationservice.model.enums;

public enum Privileges {

	EXCLUSION_VALIDATION(52.5), SPECIALTY_EXCLUSION(52.51);
	
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
