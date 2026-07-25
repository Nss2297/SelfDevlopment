package com.waseel.pbm.rtsservice.enums;

public enum ScreeningModules {
	RTS(8);

	private final Integer value;

	private ScreeningModules(Integer v) {
		this.value = v;
	}
	public Integer value() {
		return this.value;
	}

	public static ScreeningModules fromValue(Integer v) {
		for (ScreeningModules c : ScreeningModules.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException();
	}
}