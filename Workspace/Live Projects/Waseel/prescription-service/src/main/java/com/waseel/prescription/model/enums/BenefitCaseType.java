package com.waseel.prescription.model.enums;

public enum BenefitCaseType {

	OUTPATIENT("Outpatient"), INPATIENT("INPATIENT");

	private final String value;

	private BenefitCaseType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
	
	public static BenefitCaseType fromValue(String v) {
		for (BenefitCaseType c : BenefitCaseType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
