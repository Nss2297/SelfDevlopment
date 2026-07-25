package com.waseel.pbm.dssservice.enums;

public enum ScreeningModules {
	IDF((double) 9), FDB((double) 1),
	RTS((double) 8), IDFQL((double) 13),FDBQL((double) 7),PAYER_CUSTOMIZATION((double) 15);

	private final Double value;

	private ScreeningModules(Double v) {
		this.value = v;
	}
	public Double value() {
		return this.value;
	}

	public static ScreeningModules fromValue(Double v) {
		for (ScreeningModules c : ScreeningModules.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException();
	}
}