package com.waseel.prescription.model.enums;

public enum PrescriptionUrl {
	NEW("/new"), CANCELLATION("/cancellation"), INQUIRY("/inquiry"), DETAIL_INQUIRY("/detail"),
	SUMMARY_INQUIRY("/summary"), DISPENSE("/dispense"), PRESCRIPTIONS("/prescriptions"), PAYERS("/payers"),
	MODIFY_DECISION("/modify-decision"),DISPENSABLE_DRUGS("dispensable-drugs");

	private final String value;

	private PrescriptionUrl(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
