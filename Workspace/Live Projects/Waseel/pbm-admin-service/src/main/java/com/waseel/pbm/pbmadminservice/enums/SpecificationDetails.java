package com.waseel.pbm.pbmadminservice.enums;

public enum SpecificationDetails {

	PRESCRIPTION_REQUEST_PROPERTY("prescriptionRequest"), ID_NUMBER_PROPERTY("idNumber");

	private final String value;

	private SpecificationDetails(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static SpecificationDetails fromValue(String v) {
		for (SpecificationDetails c : SpecificationDetails.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
