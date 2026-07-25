package com.waseel.pbm.pbmadminservice.enums;

public enum PbmAdminConstants {
	SERVICE_CODE("{serviceCode}"), PAYER_ID("{payerId}"), MODULE_NAME("{moduleName}"), ICD_CODE("{icdCode}"),
	REJECTION_CATEGORY("{rejectionCategory}"), REQUESTED_RECORDS_FIELD("{requestedRecords}"),
	TOTAL_RECORDS_FIELD("{totalRecords}"), RECORD_FIELD("record"), RECORDS_FIELD("records");

	private final String value;

	private PbmAdminConstants(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static PbmAdminConstants fromValue(String value) {
		for (PbmAdminConstants adminExceptionMessages : PbmAdminConstants.values()) {
			if (adminExceptionMessages.value.equals(value)) {
				return adminExceptionMessages;
			}
		}
		throw new IllegalArgumentException(value);
	}
}
