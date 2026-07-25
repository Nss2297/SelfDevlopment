package com.waseel.pbm.payercustomizationservice.enums;

public enum CustomizationRequestDetailLabels {

	ICD_CODE("Icd Code"), ICD_DESCRIPTION("Icd Description"), INTERACTED_DRUG_CODE("Interacted Drug Code"),
	INTERACTED_DRUG_NAME("Interacted Drug Name"), GENDER("Gender"), REJECTION_CATEGORY("Rejection Category"),
	FROM_AGE_IN_DAYS("From Age In Days"), TO_AGE_IN_DAYS("To Age In Days"), DRUG_TYPE("Drug Type"),
	MAX_VALUE_PER_DAY("Max Value Per Day"), UNIT_TYPE("Unit Type"), PRODUCT_PACKAGE_SIZE("Product Package Size");

	private final String value;

	private CustomizationRequestDetailLabels(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static CustomizationRequestDetailLabels fromValue(String value) {
		for (CustomizationRequestDetailLabels c : CustomizationRequestDetailLabels.values()) {
			if (c.value.equals(value)) {
				return c;
			}
		}
		throw new IllegalArgumentException();
	}

}
