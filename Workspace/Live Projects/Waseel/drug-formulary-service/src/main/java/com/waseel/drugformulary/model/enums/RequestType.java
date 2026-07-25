package com.waseel.drugformulary.model.enums;

public enum RequestType {

	DRUG_FORMULARY("DRUG_FORMULARY");

	private final String value;

	private RequestType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static RequestType fromValue(String v) {
		for (RequestType c : RequestType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
