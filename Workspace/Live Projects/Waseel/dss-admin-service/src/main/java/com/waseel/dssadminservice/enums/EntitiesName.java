package com.waseel.dssadminservice.enums;

public enum EntitiesName {

    DRUG_SERVICE_METADATA("DrugServiceMetaData"),DRUG_SERVICE("DrugService"),PC_DRUG_TO_GENDER("PCGender"),
    PC_DRUG_TO_AGE("PCAge"), PC_DRUG_TO_DRUG("PCDrugToDrug"), PC_DUPLICATE_THERAPY("PCDuplicateTherapy");
	
    private final String value;

    private EntitiesName(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public static EntitiesName fromValue(String v) {
        for (EntitiesName c : EntitiesName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
