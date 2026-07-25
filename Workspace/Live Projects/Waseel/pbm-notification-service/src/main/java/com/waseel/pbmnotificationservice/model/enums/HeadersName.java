package com.waseel.pbmnotificationservice.model.enums;

public enum HeadersName {

	SENDER_CODE("Sender-Code"), RECEIVER_CODE("Receiver-Code"), DIRECTION("Direction"),
	TRANSACTION_ID("Transaction-Id"),TIME_STAMP("Time-Stamp");

	private final String value;

	private HeadersName(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static HeadersName fromValue(String v) {
		for (HeadersName c : HeadersName.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
