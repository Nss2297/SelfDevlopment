package com.waseel.pbm.dssservice.enums;

public enum DssRejectionCodes {

	DRUG_NOT_FOUND_REJECTIONCODE("CPDRGNF0019"),
	QUANTITY_MANDATORY_REJECTIONCODE("CPDRGQTY0319"),
	AMOUNT_MANDATORY_REJECTIONCODE("CPDRGAMNT0219"),
	DAYSOFSUPPLY_MANDATORY_REJECTIONCODE("CPDRGDOS0419");

	private final String value;

	private DssRejectionCodes(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static DssRejectionCodes fromValue(String v) {
		for (DssRejectionCodes c : DssRejectionCodes.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
