package com.waseel.prescription.model.enums;

public enum SourceType {

	PBM_GUI("PBM_GUI"), INTEGRATION("INTEGRATION");

	private final String value;

	private SourceType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
}
