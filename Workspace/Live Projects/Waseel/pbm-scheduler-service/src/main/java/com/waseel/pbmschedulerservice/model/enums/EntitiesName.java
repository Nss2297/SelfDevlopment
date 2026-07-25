package com.waseel.pbmschedulerservice.model.enums;

public enum EntitiesName {
    POLICY_INFORMATION("POLICY_INFORMATION"), BENEFIT_CASES("BENEFIT_CASES"),
    BENEFIT_SUBCOVERAGE("BENEFIT_SUBCOVERAGE"), CLASS_BENEFITS("CLASS_BENEFITS"),
    POLICY_CLASSES("POLICY_CLASSES"), POLICY_ENDORSEMENT("POLICY_ENDORSEMENT"),
    MEMBER_INFORMATION("MEMBER_INFORMATION"), MEMBER_POLICY_ASSOCIATION("MEMBER_POLICY_ASSOCIATION");

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
