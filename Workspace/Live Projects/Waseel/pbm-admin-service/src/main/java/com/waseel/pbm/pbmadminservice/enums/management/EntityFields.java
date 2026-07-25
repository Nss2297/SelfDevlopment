package com.waseel.pbm.pbmadminservice.enums.management;

public enum EntityFields {
	NAME("name"), ID_NUMBER("idNumber"), GENDER("gender"), NATIONALITY("nationality"), PAYER_ID("payerId");

	private final String authority;

	private EntityFields(String value) {
		this.authority = value;
	}

	public String value() {
		return this.authority;
	}

	public static EntityFields fromValue(String v) {
		for (EntityFields c : EntityFields.values()) {
			if (c.authority.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
