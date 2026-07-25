package com.waseel.pbm.payercustomizationservice.enums;

public enum CustomizationRequestMetaDataStatus {

	PC_PENDING_REQUEST("Pending"), PC_ACCEPTED_REQUEST("Accepted"), PC_REJECTED_REQUEST("Rejected");

	private final String value;

	private CustomizationRequestMetaDataStatus(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static CustomizationRequestMetaDataStatus fromValue(String value) {
		for (CustomizationRequestMetaDataStatus c : CustomizationRequestMetaDataStatus.values()) {
			if (c.value.equals(value)) {
				return c;
			}
		}
		throw new IllegalArgumentException();
	}

}
