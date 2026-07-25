package com.waseel.pbmpayerapisservice.model.enums;

public enum ServiceStatus {
	APPROVED("APPROVED"), REJECTED("REJECTED"), DISPENSED("DISPENSED"), PENDING("PENDING");

	private final String value;

	private ServiceStatus(String v) {
		this.value = v;
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
