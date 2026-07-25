package com.waseel.pbmpayerapisservice.model.enums;

public enum CaseType {

	OUTPATIENT("OUTPATIENT"), INPATIENT("INPATIENT");

	private final String value;

	private CaseType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
	
	public static CaseType fromValue(String v) {
		for (CaseType c : CaseType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
