package com.waseel.prescription.model.enums;

public enum CustomizationRequestDetailSplitParameters {

	CODE_PARAMETER("code"), COMMA_PARAMETER(","), WITH_PARAMETER("with"), HAS_PARAMETER("has"),
	AND_DRUG_PARAMETER("and drug"), COLON_PARAMETER(":"), FOR_PARAMETER("for"), MALE_PARAMETER("MALE"),
	FEMALE_PARAMETER("FEMALE");

	private final String value;

	private CustomizationRequestDetailSplitParameters(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static CustomizationRequestDetailSplitParameters fromValue(String value) {
		for (CustomizationRequestDetailSplitParameters c : CustomizationRequestDetailSplitParameters.values()) {
			if (c.value.equals(value)) {
				return c;
			}
		}
		throw new IllegalArgumentException();
	}

}
