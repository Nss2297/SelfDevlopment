package com.waseel.prescription.model.enums;

public enum CommonDenialsCode {

	MODIFY_BY_PAYER_CODE("PYR<PayerId>_OVERRIDE"), REQUIRED_PAYER_APPROVAL("PYR_APPR_REQ");

	private final String value;

	private CommonDenialsCode(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static CommonDenialsCode fromValue(String v) {
		for (CommonDenialsCode c : CommonDenialsCode.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
