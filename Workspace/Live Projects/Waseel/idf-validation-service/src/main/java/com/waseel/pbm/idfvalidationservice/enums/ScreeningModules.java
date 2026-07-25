package com.waseel.pbm.idfvalidationservice.enums;

public enum ScreeningModules {
    IDF(9),
    IDF_DRUG_TO_DISEASE_INTERACTION(10),
    IDF_DRUG_TO_GENDER_INTERACTION(11),
    IDF_DRUG_TO_AGE_INTERACTION(12),
    IDF_QUANTITY_LIMIT_CHECK(13),
    IDF_CONCURRENT_MEDICATION(14);

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