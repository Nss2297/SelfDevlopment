package com.waseel.pbm.rtsservice.enums;

public enum RejectionCodes {

	RTS_REJECTION_CODE("CPREF390");
	private final String value;

	private RejectionCodes(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static RejectionCodes fromValue(String v) {
		for (RejectionCodes c : RejectionCodes.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
