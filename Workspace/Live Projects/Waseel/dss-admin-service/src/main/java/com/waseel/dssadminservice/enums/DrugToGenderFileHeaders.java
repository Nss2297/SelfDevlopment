package com.waseel.dssadminservice.enums;

public enum DrugToGenderFileHeaders {
	SERVICE_CODE("ServiceCode"), GENDER("Gender"),
	PAYER_ID("PayerId"), MODULE_NAME("ModuleName"), SERVICE_STATUS("ServiceStatus"),
	REJECTION_REASON("RejectionReason");

	private final String header;

	private DrugToGenderFileHeaders(String header) {
		this.header = header;
	}

	public String header() {
		return this.header;
	}

	public static DrugToGenderFileHeaders fromValue(String v) {
		for (DrugToGenderFileHeaders fileHeader : DrugToGenderFileHeaders.values()) {
			if (fileHeader.header.equals(v)) {
				return fileHeader;
			}
		}
		throw new IllegalArgumentException(v);
	}
}