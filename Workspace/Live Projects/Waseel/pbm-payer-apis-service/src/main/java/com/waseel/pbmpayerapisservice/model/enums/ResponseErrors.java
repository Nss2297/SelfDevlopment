package com.waseel.pbmpayerapisservice.model.enums;

public enum ResponseErrors {

	MEMBER_ID_POLICY_NUMBER_ARE_MANDATORY("MemberId and PolicyNumber should not be null/empty."),
	ID_NUMBER_IS_MANDATORY("IdNumber should not be null/empty."),
	INVALID_REQUEST_PARAMETERS("Either IdNumber or MemberId and PolicyNumber are required."),
	INVALID_MEMBER_DETAILS_PARAMETERS(
			"A combination of either (memberId, policyNumber and providerPayerCode) or (idNumber and providerPayerCode) should be provided.");

	private final String value;

	private ResponseErrors(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static ResponseErrors fromValue(String v) {
		for (ResponseErrors c : ResponseErrors.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
