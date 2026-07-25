package com.waseel.pbm.payercustomizationservice.enums;

public enum DrugType {
	
	SOLID("Solid"),LIQUID("Liquid"),CREAM("Cream");

	private final String value;

	private DrugType(String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
}
