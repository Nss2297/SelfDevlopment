package com.waseel.pbm.fdbvalidationservice.enums;

public enum ScreeningModules {
	FDB(1), FDB_DRUG_TO_DRUG_INTERACTION(2), FDB_DRUG_TO_DISEASE_INTERACTION(3), FDB_DRUG_TO_GENDER_INTERACTION(4),
	FDB_DRUG_TO_AGE_INTERACTION(5), FDB_DUPLICATE_THERAPY(6), FDB_QUANTITY_LIMIT_CHECK(7);

	private final Integer value;

	private ScreeningModules(Integer v) {
		this.value = v;
	}

	public Integer value() {
		return this.value;
	}

	public static ScreeningModules fromValue(Integer v) {
		for (ScreeningModules c : ScreeningModules.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException();
	}
}