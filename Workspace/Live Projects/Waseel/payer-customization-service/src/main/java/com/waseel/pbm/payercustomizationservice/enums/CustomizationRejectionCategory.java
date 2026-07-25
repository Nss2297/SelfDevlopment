package com.waseel.pbm.payercustomizationservice.enums;

public enum CustomizationRejectionCategory {

	DIAGNOSIS_CONTRAINDICATION("Diagnosis-ContraIndication"), DIAGNOSIS_INDICATION("Diagnosis-Indication");

	private final String value;

	private CustomizationRejectionCategory(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static CustomizationRejectionCategory fromValue(String value) {
		for (CustomizationRejectionCategory c : CustomizationRejectionCategory.values()) {
			if (c.value.equals(value)) {
				return c;
			}
		}
		throw new IllegalArgumentException();
	}

}
