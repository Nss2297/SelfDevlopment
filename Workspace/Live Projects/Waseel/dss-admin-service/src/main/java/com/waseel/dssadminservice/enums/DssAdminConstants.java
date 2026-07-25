package com.waseel.dssadminservice.enums;

public enum DssAdminConstants {
	SERVICE_CODE("{serviceCode}"), PAYER_ID("{payerId}"), MODULE_NAME("{moduleName}"),
	SERVICE_STATUS("{serviceStatus}"), GENDER("{gender}"), RULE_ID("{ruleId}"), PAYER_FIELD("payerId"),
	CUSTOMIZATION_NAME("{customizationName}"), INTERACTED_SERVICE_CODE("{interactedServiceCode}"),
	REQUESTED_RECORDS_FIELD("{requestedRecords}"), TOTAL_RECORDS_FIELD("{totalRecords}"), RECORD_FIELD("record"),
	RECORDS_FIELD("records");

	private final String value;

	private DssAdminConstants(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static DssAdminConstants fromValue(String value) {
		for (DssAdminConstants adminExceptionMessages : DssAdminConstants.values()) {
			if (adminExceptionMessages.value.equals(value)) {
				return adminExceptionMessages;
			}
		}
		throw new IllegalArgumentException(value);
	}
}
