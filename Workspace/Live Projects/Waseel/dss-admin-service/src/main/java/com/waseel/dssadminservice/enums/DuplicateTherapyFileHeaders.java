package com.waseel.dssadminservice.enums;

public enum DuplicateTherapyFileHeaders {
	SERVICE_CODE("ServiceCode"), INTERACTED_SERVICE_CODE("InteractedServiceCode"),
	PAYER_ID("PayerId"), MODULE_NAME("ModuleName"), SERVICE_STATUS("ServiceStatus"),
	ADDITIONAL_REJECTION_REASON("AddtionalRejectionReason");

	private final String header;

	private DuplicateTherapyFileHeaders(String header) {
		this.header = header;
	}

	public String header() {
		return this.header;
	}

	public static DuplicateTherapyFileHeaders fromValue(String v) {
		for (DuplicateTherapyFileHeaders fileHeader : DuplicateTherapyFileHeaders.values()) {
			if (fileHeader.header.equals(v)) {
				return fileHeader;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
