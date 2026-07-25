package com.waseel.drugformulary.model.enums;

public enum ServiceStatus {
	APPROVED("APPROVED"), REJECTED("REJECTED");
	
	private final String value;

	private ServiceStatus(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
	
	public static ServiceStatus fromValue(String v) {
		for (ServiceStatus c : ServiceStatus.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
