package com.waseel.pbm.pbmadminservice.enums.drugexclusion;

public enum DrugExclusionMetadataDefaultData {
	NOT_APPLICABLE("NA");

	private final String value;

	private DrugExclusionMetadataDefaultData(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static DrugExclusionMetadataDefaultData fromValue(String v) {
		for (DrugExclusionMetadataDefaultData c : DrugExclusionMetadataDefaultData.values()) {
			if (c.value().equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
