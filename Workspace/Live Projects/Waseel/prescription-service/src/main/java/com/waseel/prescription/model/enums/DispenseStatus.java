package com.waseel.prescription.model.enums;

public enum DispenseStatus {

	MSG_INVALID_IDNUMBER("IdNumber doesnot exists."),
	MSG_INVALID_E_PRESCRIPTION_REF_NO("EprescriptionReferenceNumber doesnot exists."),
	MSG_DISPENSED_SUCCESS("Dispensed successfully"), MSG_INVALID("Invalid");

	private final String value;

	private DispenseStatus(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static DispenseStatus fromValue(String v) {
		for (DispenseStatus c : DispenseStatus.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
