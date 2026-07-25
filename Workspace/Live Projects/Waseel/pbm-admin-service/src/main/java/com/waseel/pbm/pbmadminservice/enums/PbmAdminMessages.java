package com.waseel.pbm.pbmadminservice.enums;

public enum PbmAdminMessages {

	DUPLICATE_CUSTOMIZATION_REQUEST(
			"Drug to Diagnosis customization rule already exists for Payer: [{payerId}], ICD Code: [{icdCode}], Service Code: [{serviceCode}], Rejection Category: [{rejectionCategory}] and Module: [{moduleName}]"),
	CUSTOMIZATION_SUCCESS_MESSAGE("[{requestedRecords}] record successfully added or updated out of [{totalRecords}].");

	private final String message;

	private PbmAdminMessages(String message) {
		this.message = message;
	}

	public String message() {
		return this.message;
	}

	public static PbmAdminMessages fromValue(String message) {
		for (PbmAdminMessages adminExceptionMessages : PbmAdminMessages.values()) {
			if (adminExceptionMessages.message.equals(message)) {
				return adminExceptionMessages;
			}
		}
		throw new IllegalArgumentException(message);
	}
}
