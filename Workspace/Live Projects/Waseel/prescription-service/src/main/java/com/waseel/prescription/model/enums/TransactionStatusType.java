package com.waseel.prescription.model.enums;

public enum TransactionStatusType {

	SENT("Sent"), RECEIVED("Received");

	private final String value;

	private TransactionStatusType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}
}
