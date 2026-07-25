package com.waseel.prescription.model.enums;

public enum PrescriptionExceptionMessages {

	PHYSICIAN_LICENSE_NUMBER_NOT_FOUND(
			"Physician License Number: {physicianLicenseNumber} does not exists for Provider: {providerId}.");

	private final String value;

	private PrescriptionExceptionMessages(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static PrescriptionExceptionMessages fromValue(String v) {
		for (PrescriptionExceptionMessages c : PrescriptionExceptionMessages.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
