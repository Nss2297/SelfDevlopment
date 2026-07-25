package com.waseel.pbm.idfvalidationservice.model;

public class EnumTypes {

    public enum Gender {
        MALE("MALE"), FEMALE("FEMALE");

        private final String value;

        private Gender(String v) {
            this.value = v;
        }

        public String value() {
            return this.value;
        }

        public static Gender fromValue(String v) {
            for (Gender c : Gender.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }
    }

    public enum ServiceStatus {
        APPROVED("APPROVED"), REJECTED("REJECTED");

        private final String value;

        private ServiceStatus(String v) {
            this.value = v;
        }

        public String value() {
            return this.value;
        }

        public static ServiceStatus fromValue(String v) {
            for (ServiceStatus c : ServiceStatus.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }
    }

    public enum RequestStatus {
        APPROVED("APPROVED"), REJECTED("REJECTED"), PARTIAL_APPROVED("PARTIAL_APPROVED");

        private final String value;

        private RequestStatus(String v) {
            this.value = v;
        }

        public String value() {
            return this.value;
        }

        public static RequestStatus fromValue(String v) {
            for (RequestStatus c : RequestStatus.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }
    }

    public enum IdfRejectionCode {
        DRUG_TO_DIAGNOSIS_INDICATION("IDF_CPINDI001"), DRUG_TO_GENDER_MISMATCH("IDF_CPGNDR403"),
        DRUG_TO_AGE_MISMATCH("IDF_CPAGE902"), QUANTITY_LIMIT_CHECK("IDF_CPQTL912"), DAYS_OF_SUPPLY("IDF_CPDOS"),
        CONCURRENT_DRUG("IDF_CPDDI701");

        private final String value;

        private IdfRejectionCode(String v) {
            this.value = v;
        }

        public String value() {
            return this.value;
        }

        public static IdfRejectionCode fromValue(String v) {
            for (IdfRejectionCode c : IdfRejectionCode.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }
    }

}
