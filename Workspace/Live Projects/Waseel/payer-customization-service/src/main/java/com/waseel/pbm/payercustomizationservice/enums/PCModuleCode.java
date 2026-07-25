package com.waseel.pbm.payercustomizationservice.enums;

public enum PCModuleCode {

    PAYER_CUSTOMIZATION("CUS_CPINDI001"),
    PC_DRUGTODIAGNOSIS_INDICATION("PC_CPINDI001"),
    PC_DRUGTODIAGNOSIS_CONTRAINDICATION("PC_CPINDC001"),
    PC_GENDER("PC_CPGNDR403"),
    PC_QLC("PC_CPQTL912"),
    PC_DUPLICATE_THERAPY("PC_CPTDE0001"),
    PC_AGE("PC_CPAGE902"), PC_DRUG_TO_DRUG("PC_CPDDI701");

    private final String value;

    private PCModuleCode(String value) {
        this.value = value;
    }

    public String code() {
        return this.value;
    }
}
