package com.waseel.pbm.pbmadminservice.enums;

public enum ModuleName {

	FDB("FDB"), IDF("IDF"), ALL("ALL");

	private final String value;

	private ModuleName(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static ModuleName fromValue(String v) {
		for (ModuleName c : ModuleName.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
