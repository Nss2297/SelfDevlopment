package com.waseel.pbm.payercustomizationservice.enums;

public enum ModuleName {

	FDB("FDB"), IDF("IDF"), ALL("ALL");

	private final String value;

	private ModuleName(String value) {
		this.value = value;
	}

	public String value(){
		return value;
	}

}
