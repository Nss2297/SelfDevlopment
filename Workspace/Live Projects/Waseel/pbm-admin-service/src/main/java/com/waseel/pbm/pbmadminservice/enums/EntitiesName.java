package com.waseel.pbm.pbmadminservice.enums;

public enum EntitiesName {

    DRUG_FORMULARY_METADATA("DRUG_FORMULARY_METADATA"), DRUG_FORMULARY_DETAILS("DRUG_FORMULARY_DETAILS"),
    DRUG_FORMULARY_POLICY_ASSOCIATION("DRUG_FORMULARY_POLICY_ASSOCIATION"),
    DRUG_EXCLUSION_METADATA("DRUG_EXCLUSION_METADATA"), PC_DRUG_TO_DIAGNOSIS("PCDrugToDiagnosis"),
    NETWORK_EXCLUSION_ASSC("NETWORK_EXCLUSION_ASSC"), DRUG_EXCLUSION_DETAILS("DRUG_EXCLUSION_DETAILS"),
    HIGH_COST_EXCLUSION_ASSC("HIGH_COST_EXCLUSION_ASSC"), PROVIDER_EXCLUSION_ASSC("PROVIDER_EXCLUSION_ASSC"),
    SPECIALITY_EXCLUSION_ASSC("SPECIALITY_EXCLUSION_ASSC");

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
