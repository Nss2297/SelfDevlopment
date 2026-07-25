package com.waseel.pbm.dssservice.enums;

public enum EnableDisableStatus {

	TRUE('1') , FALSE('0');
	
	private final char value;

	private EnableDisableStatus(char v) {
		this.value = v;
	}
	public char value() {
		return this.value;
	}

	public static EnableDisableStatus fromValue(char v) {
		for (EnableDisableStatus c : EnableDisableStatus.values()) {
			if (c.value == v) {
				return c;
			}
		}
		throw new IllegalArgumentException();
	}
}
