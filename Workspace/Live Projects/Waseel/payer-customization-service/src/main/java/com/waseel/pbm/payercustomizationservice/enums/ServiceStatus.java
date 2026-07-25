package com.waseel.pbm.payercustomizationservice.enums;

public enum ServiceStatus {

	APPROVED("APPROVED"), REJECTED("REJECTED");

	private final String value;

	private ServiceStatus(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
}
