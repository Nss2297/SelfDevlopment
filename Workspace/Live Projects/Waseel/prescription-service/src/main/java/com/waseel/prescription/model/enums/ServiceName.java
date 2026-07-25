package com.waseel.prescription.model.enums;

public enum ServiceName {
    DSS_SERVICE("dss-service"), ELIGIBILITY_SERVICE("eligibility-service"),
    DRUG_FORMULARY_SERVICE("drug-formulary-service"),
    POLICY_CONSUMPTION_SERVICE("policy-consumption-service"),
    DRUG_EXCLUSION_SERVICE("drug-exclusion-service"),
    BR_SERVICE("br-service");

    private final String value;

    private ServiceName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
