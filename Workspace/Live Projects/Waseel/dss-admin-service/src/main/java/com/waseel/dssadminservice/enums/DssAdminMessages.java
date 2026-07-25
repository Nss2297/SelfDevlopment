package com.waseel.dssadminservice.enums;

public enum DssAdminMessages {
	CANNOT_EDIT_DRUG_CODE_MESSAGE(
			"Service Code: [{serviceCode}] cannot be edited for this {customizationName} customization request."),
	CANNOT_ADD_DRUG_CODE_MESSAGE(
			"Service Code: [{serviceCode}] and Interacted Service Code: [{interactedServiceCode}] cannot be same."),
	CANNOT_EDIT_INTERACTED_SERVICE_CODE_MESSAGE(
			"Interacted Service Code: [{interactedServiceCode}] cannot be edited for this {customizationName} customization request."),
	DUPLICATE_DRUG_CUSTOMIZATION_REQUEST(
			"{customizationName} customization rule already exists for Payer: [{payerId}], Service Code: [{serviceCode}], Interacted Service Code: [{interactedServiceCode}] and Module: [{moduleName}]"),
	DUPLICATE_CUSTOMIZATION_REQUEST(
			"{customizationName} customization rule already exists for Payer: [{payerId}], Service Code: [{serviceCode}], and Module: [{moduleName}]"),
	GENDER_CUSTOMIZATION_REQUEST_ALREADY_EXISTS(
			"Gender customization rule already exists for Service Code: [{serviceCode}], Gender: [{gender}], Payer: [{payerId}], Module: [{moduleName}], and Service Status: [{serviceStatus}]"),
	INVALID_CUSTOMIZATION_REQUEST("Invalid Gender customization request."),
	GENDER_CUSTOMIZATION_REQUEST_UPDATED("PC Drug to Gender updated successfully for Rule ID: [{ruleId}]."),
	FAILED_TO_UPDATE_CUSTOMIZATION_REQUEST("Failed to edit this Gender customization request."),
	INVALID_PAYER_ID("Payer: [{payerId}] is not allowed to add customization request."),
	INVALID_FILE_EMPTY_MESSAGE("Please select a file to upload."), HEADERS_NOT_FOUND("Headers not found."),
	INVALID_FILE_HEADERS(
			"Invalid format. Please reorder/rename the headers or check the values or refer the format of Sample File."),
	NUMBER_FORMAT_EXCEPTION_FOR_AGE("From Age(in days) and To Age(in days) should be numbers."),
	AGE_IN_DAYS_EXCEED_LIMIT("From Age(in days) and To Age(in days) shouldn't exceed 20 numbers each."),
	INVALID_FROM_AGE_IN_DAYS("From Age(in days) should not be greater than To Age(in days)."),
	CUSTOMIZATION_SUCCESS_MESSAGE("[{requestedRecords}] record successfully added or updated out of [{totalRecords}].");

	private final String message;

	private DssAdminMessages(String message) {
		this.message = message;
	}

	public String message() {
		return this.message;
	}

	public static DssAdminMessages fromValue(String message) {
		for (DssAdminMessages adminExceptionMessages : DssAdminMessages.values()) {
			if (adminExceptionMessages.message.equals(message)) {
				return adminExceptionMessages;
			}
		}
		throw new IllegalArgumentException(message);
	}
}
