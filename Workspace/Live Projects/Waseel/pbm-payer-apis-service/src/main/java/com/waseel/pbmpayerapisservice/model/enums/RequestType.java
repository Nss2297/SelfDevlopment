package com.waseel.pbmpayerapisservice.model.enums;

public enum RequestType {
	NEW("NEW"), FOLLOWUP("FOLLOWUP"), CANCELLATION("CANCELLATION"),DISPENSED("DISPENSE"); 
	
	private final String value;

	private RequestType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
	
	public static RequestType fromValue(String v) {
		for (RequestType c : RequestType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
