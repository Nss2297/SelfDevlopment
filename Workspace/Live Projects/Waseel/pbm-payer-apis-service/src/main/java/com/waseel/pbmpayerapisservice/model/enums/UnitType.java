package com.waseel.pbmpayerapisservice.model.enums;

public enum UnitType {

	PACKAGE("package"), UNIT("unit");

	private final String value;

	private UnitType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
	
	public static UnitType fromValue(String v) {
		for (UnitType c : UnitType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
