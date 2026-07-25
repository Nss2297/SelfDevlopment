package com.waseel.pbm.idfvalidationservice.enums;

public enum Severity {

    SEVERE("Severe"), CONTRAINDICATION("Contraindication"), MODERATE("Moderate"), MINOR("Minor");

    private final String value;

    private Severity(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static Severity fromValue(String v) {
        for (Severity c : Severity.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException();
    }
}
