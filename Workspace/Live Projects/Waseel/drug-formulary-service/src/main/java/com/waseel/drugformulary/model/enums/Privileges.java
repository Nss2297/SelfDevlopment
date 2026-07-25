package com.waseel.drugformulary.model.enums;

public enum Privileges {

	DRUG_FORMULARY(52.3);
	
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
