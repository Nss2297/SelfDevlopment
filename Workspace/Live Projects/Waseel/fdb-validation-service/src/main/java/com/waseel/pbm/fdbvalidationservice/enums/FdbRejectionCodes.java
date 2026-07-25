package com.waseel.pbm.fdbvalidationservice.enums;

public enum FdbRejectionCodes {

	DRUG_TO_DISEASE_INDICATIONS_REJECTIONCODE("FDB_CPINDI001"),
	DRUG_TO_DISEASE_CONTRAINDICATIONS_REJECTIONCODE("FDB_CPINDC001"),
	DRUG_TODRUG_INTERACTION_REJECTIONCODE("FDB_CPDDI701"),
	DRUG_TO_GENDER_REJECTIONCODE("FDB_CPGNDR403"),
	DRUG_TO_AGE_REJECTIONCODE("FDB_CPAGE902"),
	DUPLICATE_THERAPY_CODE("FDB_CPTDE0001"),
	NON_CUMULATIVE_QUANTITY_LIMIT_CHECK_REJECTIONCODE("FDB_CPQTL912"),
	CUMULATIVE_QUANTITY_LIMIT_CHECK_REJECTIONCODE("FDB_CPCQTL912");
	
	private final String value;

	private FdbRejectionCodes(String v) {
		this.value = v;
	}
	public String value() {
		return this.value;
	}
	
	public static FdbRejectionCodes fromValue(String v) {
		for (FdbRejectionCodes c : FdbRejectionCodes.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
