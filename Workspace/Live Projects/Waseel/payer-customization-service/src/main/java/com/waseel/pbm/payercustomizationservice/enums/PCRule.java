package com.waseel.pbm.payercustomizationservice.enums;

public enum PCRule {

    PC_DRUG_TO_DIAGNOSIS_INDICATION_CONTRAINDICATION("PCDTDICRule"),
    PC_DRUG_TO_AGE("PCDTARule"),
    PC_DUPLICATE_THERAPY("PCDTRule"),
    PC_DRUG_TO_GENDER("PCDTGRule"),
    PC_DRUG_TO_DRUG("PCDTDIRule"),
    PC_QUANTITY_LIMIT_CHECK("PCQLCRule");

    private final String value;

    private PCRule(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
