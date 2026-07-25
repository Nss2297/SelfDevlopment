package com.waseel.pbmnotificationservice.model.enums;

public enum EmailInformation {
	
	SUBJECT("Waseel ePrescription notification"), SENDER_NAME("Waseel PBM");
	
	private final String value;

	private EmailInformation(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static EmailInformation fromValue(String v) {
		for (EmailInformation c : EmailInformation.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
