package com.waseel.pbm.fdbvalidationservice.enums;

public enum ValidationMessages {
	DRUGCODE_NOT_FOUND("Drug Not Found for code : <DrugCode>"), 
	ICDCODE_NOT_FOUND("ICD Not Found for code : <IcdCode>"),
	INVALID_AGE_RANGE("Invalid Patient Age Range for dateOfBirth : <DOB> . The Patient Age should be between 0 and 110 years");

	private final String value;

	private ValidationMessages(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static ValidationMessages fromValue(String v) {
		for (ValidationMessages c : ValidationMessages.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
