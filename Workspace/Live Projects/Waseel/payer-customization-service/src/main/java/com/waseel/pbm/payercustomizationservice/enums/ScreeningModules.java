package com.waseel.pbm.payercustomizationservice.enums;

public enum ScreeningModules {
    PAYER_CUSTOMIZATION(15), PC_DRUGTODIAGNOSIS(16), PC_QUANTITY_LIMIT_CHECK(17),
    PC_GENDER(18), PC_AGE(19), PC_DUPLICATE_THERAPY(21), PC_DRUG_TO_DRUG(20);

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