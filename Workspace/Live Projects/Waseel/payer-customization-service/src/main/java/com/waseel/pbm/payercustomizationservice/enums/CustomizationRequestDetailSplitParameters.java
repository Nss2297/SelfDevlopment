package com.waseel.pbm.payercustomizationservice.enums;

public enum CustomizationRequestDetailSplitParameters {

	CODE_PARAMETER("code"), COMMA_PARAMETER(","), WITH_PARAMETER("with"), HAS_PARAMETER("has"),
	AND_DRUG_PARAMETER("and drug");

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
