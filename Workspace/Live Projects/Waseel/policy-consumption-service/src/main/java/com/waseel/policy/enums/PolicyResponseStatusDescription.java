package com.waseel.policy.enums;

public enum PolicyResponseStatusDescription {
	NO_REMAINING_LIMIT("NO REMAINING LIMIT"), PASSED_POLICY_CHECK("PASSED POLICY CHECK"), DISPENSED("DISPENSED"),
	EXTRA_PATIENT_SHARE(
			"Remaining limit don't cover all needed amount. An extra patient share amount of <amount> <currency> has to be paid."),
	NO_BENEFIT_DETAILS("Benefit details are not present."), NO_BENEFIT_CASE("Benefit cases are unavailable."),
	NO_POLICY("Policy not found."), MISSING_ID_NUMBER("Member does not exists."),
	LOCKED_AMOUNT_EQUAL_REMAINING_LIMIT(
			"The locked amount equals or exceeds the remaining limit. Please consider either dispensing or cancelling your previous prescriptions first."),
	DISPENSE_NO_REMAINING_LIMIT("Due to other dispenses performed, the remaining limit has been exhausted."),
	DISPENSED_WITH_EXTRA_PATIENT_SHARE(
			"Due to consuming from the remaining limit this prescription can be dispensed, but with extra patient share of <extraPatientShare> <currency> and total patient share of <totalPatientShare> <currency>."),
	EXPIRED_PRESCRIPTION("EXPIRED PRESCRIPTION"), REQUEST_NOT_FOUND("REQUEST NOT FOUND"),
	POLICY_EXPIRED("Policy has expired."), INVALID_PRESCRIPTION("Prescription not found."),
	REQUEST_IS_INACTIVE("This request is inactive."), FOLLOWUP_FAILED("FOLLOWUP FAILED"),
	INVALID_REQUEST("Invalid request."), REJECTED("Rejected."), INTERNAL_SERVER_ERROR("Internal server error."),
	FAILED_PAYER_RESPONSE("Internal server error."), PARTIAL_DISPENSED("PARTIAL_DISPENSED"),
	PARTIALLY_DISPENSED_WITH_EXTRA_PATIENT_SHARE(
			"Due to consuming from the remaining limit this prescription can be partially-dispensed, but with extra patient share of <extraPatientShare> <currency> and total patient share of <totalPatientShare> <currency>."),
	PROVIDER_CODE_NOT_FOUND("Provider not associated with payer."),
	MEMBER_POLICY_DETAILS_FOR_DISPENSE("Member policy details for dispensible drugs."), NO_PAYER_ID("PayerId is not present."), NO_PROVIDER_ID("ProviderId is not present."), NO_DRUGS_TO_DISPENSE("No drugs to dispense.");

	private final String value;

	private PolicyResponseStatusDescription(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static PolicyResponseStatusDescription fromValue(String v) {
		for (PolicyResponseStatusDescription c : PolicyResponseStatusDescription.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
