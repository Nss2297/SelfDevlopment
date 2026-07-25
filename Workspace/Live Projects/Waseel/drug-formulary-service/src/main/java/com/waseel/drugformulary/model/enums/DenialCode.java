package com.waseel.drugformulary.model.enums;

public enum DenialCode {
	DRUG_FORMULARY("BR_DFDNF01");

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
