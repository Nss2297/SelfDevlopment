package com.waseel.pbm.payercustomizationservice.enums;

public enum CustomizationRequestErrorMessage {

	PC_IN_PROGRESS("Customization Request is inprogress by payer:[payerID]"),
	DRUG_CODE_IS_EMPTY("Drug Code should not be null/empty."),
	DRUG_NAME_IS_EMPTY("Drug Name should not be null/empty."), GENDER_IS_EMPTY("Gender should not be null/empty."),
	REJECTION_CATEGORY_IS_EMPTY("Rejection category should not be null/empty."),
	MODULE_NAME_IS_EMPTY("Module name should not be null/empty."), STATUS_IS_EMPTY("Status should not be null/empty."),
	REJECTION_REASON_IS_EMPTY("Rejection reason should not be null/empty."),
	EPRESCRIPTION_REFERENCE_NO_IS_EMPTY("ePrescriptionReferenceNo should not be null/empty."),
	INVALID_REJECTION_CATEGORY(
			"Rejection category has to be either Diagnosis-ContraIndication or Diagnosis-Indication. "),
	INVALID_MODULE_NAME(
			"Module name has to be DuplicateTherapy, DrugToGenderInteraction, DrugToAgeInteraction, DrugToDrugInteraction, DrugToDiseaseInteraction, or QuantityLimitCheck."),
	INVALID_MESSAGE_FORMAT("Invalid message format.");

	private final String value;

	private CustomizationRequestErrorMessage(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static CustomizationRequestErrorMessage fromValue(String value) {
		for (CustomizationRequestErrorMessage c : CustomizationRequestErrorMessage.values()) {
			if (c.value.equals(value)) {
				return c;
			}
		}
		throw new IllegalArgumentException();
	}

}
