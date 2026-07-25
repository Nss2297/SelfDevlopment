package com.waseel.pbmnotificationservice.model.enums;

public enum TransactionStatusType {

	SENT("SENT"), RECEIVED("RECEIVED");

	private final String value;

	private TransactionStatusType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}
}
