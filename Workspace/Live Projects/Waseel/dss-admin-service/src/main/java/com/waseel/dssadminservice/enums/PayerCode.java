package com.waseel.dssadminservice.enums;

public enum PayerCode {

	ALL_PAYER("101");
	
	private final String value;

	private PayerCode(String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
	
}
