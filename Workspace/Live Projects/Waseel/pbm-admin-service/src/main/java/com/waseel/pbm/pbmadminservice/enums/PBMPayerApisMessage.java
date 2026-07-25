package com.waseel.pbm.pbmadminservice.enums;

public enum PBMPayerApisMessage {

	ERROR("error"), ERROR_MESSAGE("<policyNumber> policy details for member <idNumber> not found."),
	POLICY_NUMBER("<policyNumber>"), ID_NUMBER("<idNumber>");

	private final String value;

	private PBMPayerApisMessage(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static PBMPayerApisMessage fromValue(String v) {
		for (PBMPayerApisMessage c : PBMPayerApisMessage.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
