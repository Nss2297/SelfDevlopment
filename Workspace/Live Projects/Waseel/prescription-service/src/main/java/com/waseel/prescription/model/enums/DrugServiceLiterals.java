package com.waseel.prescription.model.enums;

public enum DrugServiceLiterals {
	DRUG_LIST_ID("drugListId");

	private final String value;

	private DrugServiceLiterals(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static DrugServiceLiterals fromValue(String v) {
		for (DrugServiceLiterals c : DrugServiceLiterals.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
