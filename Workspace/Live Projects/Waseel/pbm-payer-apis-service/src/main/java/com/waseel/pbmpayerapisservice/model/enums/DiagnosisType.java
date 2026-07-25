package com.waseel.pbmpayerapisservice.model.enums;

public enum DiagnosisType {
	PRIMARY("PRIMARY"), SECONDARY("SECONDARY");
	private final String value;

	private DiagnosisType(String v) {
		this.value = v;
	}
	public String value() {
		return this.value;
	}

	public static DiagnosisType fromValue(String v) {
		for (DiagnosisType c : DiagnosisType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
