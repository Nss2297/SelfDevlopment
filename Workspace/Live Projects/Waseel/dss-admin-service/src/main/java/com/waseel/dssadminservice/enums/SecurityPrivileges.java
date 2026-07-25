package com.waseel.dssadminservice.enums;

public enum SecurityPrivileges {
	PBM_ADMIN("101;PBM_ADMIN"), SFDA_MANAGEMENT("101;SFDA_MANAGEMENT"), MEMBER_DETAILS("101;MEMBER_DETAILS"),
	DRUG_TO_GENDER_CUSTOMIZATION("101;DRUG_TO_GENDER_CUSTOMIZATION"),
	GENDER_CUSTOMIZATION_UPLOAD("101;GENDER_CUSTOMIZATION_UPLOAD"),
	DRUG_TO_AGE_CUSTOMIZATION("101;DRUG_TO_AGE_CUSTOMIZATION"),
	AGE_CUSTOMIZATION_UPLOAD("101;AGE_CUSTOMIZATION_UPLOAD"),
	DRUG_TO_DRUG_CUSTOMIZATION("101;DRUG_TO_DRUG_CUSTOMIZATION"),
	DRUG_CUSTOMIZATION_UPLOAD("101;DRUG_CUSTOMIZATION_UPLOAD"),
	DUPLICATE_THERAPY_CUSTOMIZATION("101;DUPLICATE_THERAPY_CUSTOMIZATION"),
	DUPLICATE_THERAPY_CUSTOMIZATION_UPLOAD("101;DUPLICATE_THERAPY_CUSTOMIZATION_UPLOAD");
	
	private final String authority;

	private SecurityPrivileges(String value) {
		this.authority = value;
	}

	public String value() {
		return this.authority;
	}

	public static SecurityPrivileges fromValue(String v) {
		for (SecurityPrivileges c : SecurityPrivileges.values()) {
			if (c.authority.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
