package com.waseel.pbm.fdbvalidationservice.enums;

public enum CustomRejectionReasonsAndCodes {

	DRUG_TO_DISEASE_INDICATIONS_REJECTIONCODE("FDB_CPINDI001"),
	DRUG_TO_DISEASE_INDICATIONS_REJECTIONREASON(
			"Medication <DrugName> (<DrugCode>) is not indicated with diagnosis code <ICD>"),
	DRUG_TO_DISEASE_CONTRAINDICATIONS_REJECTIONCODE("FDB_CPINDC001"),
	DRUG_TO_DISEASE_CONTRAINDICATIONS_REJECTIONREASON(
			"Medication <DrugName> (<DrugCode>) has SEVERE CONTRAINDICATION with diagnosis code <ICD>, HIGH ALERT: MEMBER HEALTH MIGHT SEVERELY HARMED"),
	DRUG_TO_DRUG_INTERACTION_REJECTIONCODE("FDB_CPDDI701"),
	DRUG_TO_DRUG_INTERACTION_REJECTIONREASON(
			"Requested drug <DrugName> (<DrugCode>) has Severe Interactions, HIGH ALERT: MEMBER HEALTH MIGHT SEVERELY HARMED"),
	DRUG_TO_GENDER_REJECTIONCODE("FDB_CPGNDR403"),
	DRUG_TO_GENDER_REJECTIONREASON("Gender rule violates the condition : <Condition> for drug <DrugName> (<DrugCode>)"),
	DUPLICATED_THERAPY_CODE("FDB_CPTDE0001"),
	DRUG_TO_AGE_REJECTIONCODE("FDB_CPAGE902"),
	DRUG_TO_AGE_REJECTIONREASON("Drug <DrugName> (<DrugCode>) is inconsistent with the patient\\'s age"),
	QUANTITY_LIMIT_CHECK_REJECTIONCODE("FDB_CPQTL912"), 
	QUANTITY_LIMIT_CHECK_REJECTIONREASON(
			"Requested drug : <DrugName> (<DrugCode>) quantity exceeds the maximum limit per <UnitType>");

	private final String value;

	private CustomRejectionReasonsAndCodes(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static CustomRejectionReasonsAndCodes fromValue(String v) {
		for (CustomRejectionReasonsAndCodes c : CustomRejectionReasonsAndCodes.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
