package com.waseel.pbm.pbmadminservice.enums;

public enum DrugFormularyMessage {

	INVALID_DRUG_FORMULARY_ASSOCIATION_ID("Invalid DrugFormularyAssociationId."),
	FAILED_TO_DELETE_DRUG_FORMULARY_ASSOCIATION("Failed to delete DrugFormularyAssociation."),
	FORMULARY_NAME_ALREADY_EXISTS("Formulary Name already exists."),
	NULL_OR_EMPTY_MESSAGE("<field> should not be null or empty"), FIELD_PARAMETER("<field>"),
	GENERIC_NAME_FIELD("genericName"), PRICE_FIELD("price"), DRUG_CODE_FIELD("drugCode"), DRUG_NAME_FIELD("drugName"),
	NULL_OR_EMPTY_ERROR_MESSAGE("<field> should not be null or empty for drug drugCode"),
	POLICY_DETAILS_DOES_NOT_FOUND("Policy details does not found."),
	INVALID_POLICY_DETAILS("<policyNumber> has invalid details."), POLICY_NUMBER_FIELD("<policyNumber>");

	private final String value;

	private DrugFormularyMessage(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static DrugFormularyMessage fromValue(String v) {
		for (DrugFormularyMessage c : DrugFormularyMessage.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
