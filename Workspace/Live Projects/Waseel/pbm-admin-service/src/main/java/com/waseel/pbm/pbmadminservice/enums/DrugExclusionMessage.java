package com.waseel.pbm.pbmadminservice.enums;

public enum DrugExclusionMessage {

	EXCLUSION_NAME_ALREADY_EXISTS("Drug Exclusion name already exists."),
	NULL_OR_EMPTY_MESSAGE("<field> should not be null or empty"), FIELD_PARAMETER("<field>"),
	NO_LIST("No exclusion list was found");

	private final String value;

	private DrugExclusionMessage(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static DrugExclusionMessage fromValue(String v) {
		for (DrugExclusionMessage c : DrugExclusionMessage.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
