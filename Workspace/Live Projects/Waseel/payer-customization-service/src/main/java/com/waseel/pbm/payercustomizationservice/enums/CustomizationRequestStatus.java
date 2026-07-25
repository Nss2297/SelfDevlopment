package com.waseel.pbm.payercustomizationservice.enums;

public enum CustomizationRequestStatus {

    ACCEPTED("Accepted"), REJECTED("Rejected");

    private final String value;

    private CustomizationRequestStatus(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }
}
