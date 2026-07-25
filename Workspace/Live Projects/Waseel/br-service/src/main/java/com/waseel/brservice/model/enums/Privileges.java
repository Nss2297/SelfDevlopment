package com.waseel.brservice.model.enums;

public enum Privileges {

	SENSITIVE_DRUG(52.6);
	
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
