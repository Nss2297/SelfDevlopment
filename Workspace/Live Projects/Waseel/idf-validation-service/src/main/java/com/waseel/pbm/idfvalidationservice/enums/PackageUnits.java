package com.waseel.pbm.idfvalidationservice.enums;

public enum PackageUnits {
    SOLID("Solid"), LIQUID("Liquid"), CREAM("Cream");

    private final String value;

    private PackageUnits(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static PackageUnits fromValue(String v) {
        for (PackageUnits c : PackageUnits.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException();
    }
}
