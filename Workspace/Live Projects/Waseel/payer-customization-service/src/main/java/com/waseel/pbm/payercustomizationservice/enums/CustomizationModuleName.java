package com.waseel.pbm.payercustomizationservice.enums;

public enum CustomizationModuleName {

	DUPLICATE_THERAPY_RULE("DuplicateTherapy"), DRUG_TO_GENDER_INTERACTION_RULE("DrugToGenderInteraction"),
	DRUG_TO_AGE_INTERACTION_RULE("DrugToAgeInteraction"), DRUG_TO_DRUG_INTERACTION_RULE("DrugToDrugInteraction"),
	DRUG_TO_DISEASE_INTERACTION_RULE("DrugToDiseaseInteraction"), QUANTITY_LIMIT_CHECK_RULE("QuantityLimitCheck");

	private final String value;

	private CustomizationModuleName(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static CustomizationModuleName fromValue(String value) {
		for (CustomizationModuleName c : CustomizationModuleName.values()) {
			if (c.value.equals(value)) {
				return c;
			}
		}
		throw new IllegalArgumentException();
	}

}
