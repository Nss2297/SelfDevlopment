package com.waseel.pbm.pbmadminservice.enums;

public enum DssPayerTransactionType {

	PRESCRIPTION("PRESCRIPTION");

	private final String value;

	private DssPayerTransactionType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}
}
