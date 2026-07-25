package com.waseel.pbm.payercustomizationservice.enums;

public enum CustomizationRequestsAuthority {

	VIEW_PRESCRIPTION("101;VIEW_PRESCRIPTION"), EDIT_PRESCRIPTION_DECISION("101;EDIT_PRESCRIPTION_DECISION"),
	CUSTOMIZATION_REQUEST("101;CUSTOMIZATION_REQUEST"), MEDICAL_CUSTOMIZATION("101;MEDICAL_CUSTOMIZATION"),
	BUSINESS_CUSTOMIZATION("101;BUSINESS_CUSTOMIZATION"), PBM_ADMIN("101;PBM_ADMIN");

	private final String value;

	private CustomizationRequestsAuthority(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static CustomizationRequestsAuthority fromValue(String value) {
		for (CustomizationRequestsAuthority c : CustomizationRequestsAuthority.values()) {
			if (c.value.equals(value)) {
				return c;
			}
		}
		throw new IllegalArgumentException();
	}

}
