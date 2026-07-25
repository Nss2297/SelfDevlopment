package com.waseel.pbm.pbmadminservice.enums;

public enum RejectionCategory {

	DIAGNOSIS_INDICATION("Diagnosis-Indication"),
	DIAGNOSIS_CONTRAINDICATION("Diagnosis-ContraIndication"),
	ALL("ALL");
	
	private final String value;
	
	private RejectionCategory(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
	
	public static RejectionCategory fromValue(String v) {
		for (RejectionCategory c : RejectionCategory.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
