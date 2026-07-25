package com.waseel.prescription.model.enums;

public enum BenefitCodeType {

	PREGNANCY_BENEFIT("Pregnancy/Delivery and Miscarriage Benefit"), BASIC_BENEFIT("Basic Medical Benefit"),
	DENTAL_BENEFIT("Dental Benefit"), VISUAL_AID_BENEFIT("Visual Aid Benefit (w/ Frames)");

	private final String value;

	private BenefitCodeType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static BenefitCodeType fromValue(String v) {
		for (BenefitCodeType c : BenefitCodeType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
