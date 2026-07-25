package com.waseel.dssadminservice.enums;

public enum DrugToAgeFileHeaders {
	SERVICE_CODE("ServiceCode"), FROM_AGE_IN_DAYS("From Age(in days)"), TO_AGE_IN_DAYS("To Age(in days)"),
	PAYER_ID("PayerId"), MODULE_NAME("ModuleName"), SERVICE_STATUS("ServiceStatus"),
	REJECTION_REASON("RejectionReason");

	private final String header;

	private DrugToAgeFileHeaders(String header) {
		this.header = header;
	}

	public String header() {
		return this.header;
	}

	public static DrugToAgeFileHeaders fromValue(String v) {
		for (DrugToAgeFileHeaders fileHeader : DrugToAgeFileHeaders.values()) {
			if (fileHeader.header.equals(v)) {
				return fileHeader;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
