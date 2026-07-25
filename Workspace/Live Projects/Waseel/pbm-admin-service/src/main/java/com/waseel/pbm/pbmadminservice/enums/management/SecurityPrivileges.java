package com.waseel.pbm.pbmadminservice.enums.management;

public enum SecurityPrivileges {
	BUSINESS_RULE_ADMINISTRATION("101;BUSINESS_RULE_ADMINISTRATION"), MEMBER_MANAGEMENT("101;MEMBER_MANAGEMENT");

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
