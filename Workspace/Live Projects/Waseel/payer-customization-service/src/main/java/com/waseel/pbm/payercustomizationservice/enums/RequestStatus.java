package com.waseel.pbm.payercustomizationservice.enums;

public enum RequestStatus {
	
	APPROVED("APPROVED"), REJECTED("REJECTED"), PARTIAL_APPROVED("PARTIAL_APPROVED");

	private final String value;

	private RequestStatus(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}
}
