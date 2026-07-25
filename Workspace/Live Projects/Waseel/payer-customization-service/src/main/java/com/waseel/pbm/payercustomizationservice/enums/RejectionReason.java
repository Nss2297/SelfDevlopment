package com.waseel.pbm.payercustomizationservice.enums;

public enum RejectionReason {

	DIAGNOSIS_INDICATION("Diagnosis-Indication"),
	DIAGNOSIS_CONTRAINDICATION("Diagnosis-ContraIndication"),
	ALL("ALL");

	private final String value;

	private RejectionReason(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
}
