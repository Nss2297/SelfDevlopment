package com.waseel.pbmpayerapisservice.model.enums;

public enum PhysicianCategory {
	GP("GP"), SPECIALIST("SPECIALIST"), CONSULTANT("CONSULTANT"),GENERAL_PHYSICIAN("General Physician");

	private final String value;

	private PhysicianCategory(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static PhysicianCategory fromValue(String v) {
		for (PhysicianCategory c : PhysicianCategory.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
