package com.waseel.pbm.pbmadminservice.enums.drugexclusion;

public enum ExclusionMessages {
	DUPLICATE_EXCLUSION_NAME("Drug Exclusion name already exists."), INVALID_DRUG("Failed to add <drugCode> details."),
	DRUG_CODE_FIELD("<drugCode>"), DUPLICATE_EXCLUSION_NETWORK("Duplicate exclusionNetwork found."),
	HIGH_COST_ALREADY_EXITS("High cost is already associated with this exclusion."),
	EXCLUSIONID_NOT_FOUND("ExclusionId is not found or exists."),
	DUPLICATE_EXCLUSION_PROVIDER("Duplicate exclusionProvider found."),
	DUPLICATE_EXCLUSION_SPECIALITY("Duplicate exclusionSpeciality found."),
	PROVIDER_EXCLUSION_ALREADY_EXISTS("Provider is already associated with this exclusion."),
	PROVIDER_ID_NOT_FOUND("ProviderId is not found or exists."),
	PROVIDER_NAME_NOT_FOUND("ProviderName is not found or exists."),
	NETWORK_ID_NOT_FOUND("NetworkId is not found or exists."),
	NETWORK_EXCLUSION_ALREADY_EXISTS("Network is already associated with this exclusion."),
	EXCLUSION_ASSC_ID_NOT_FOUND("exclusionAsscId is not found or exists."),
	SPECIALITY_EXCLUSION_ALREADY_EXISTS("Speciality is already associated with this exclusion.");
	
	private final String value;

	private ExclusionMessages(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static ExclusionMessages fromValue(String v) {
		for (ExclusionMessages c : ExclusionMessages.values()) {
			if (c.value().equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
