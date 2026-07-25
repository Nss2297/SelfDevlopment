package com.waseel.prescription.model.enums;

public enum PrescriptionExceptionConstants {

	PHYSICIAN_LICENSE_NUMBER_FIELD("{physicianLicenseNumber}"), PROVIDER_ID_FIELD("{providerId}");

	private final String value;

	private PrescriptionExceptionConstants(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static PrescriptionExceptionConstants fromValue(String v) {
		for (PrescriptionExceptionConstants c : PrescriptionExceptionConstants.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
