package com.waseel.prescription.model.enums;

public enum FrequencyType {

	EVERY_2_HOURS("every-2-hours"), EVERY_3_HOURS("every-3-hours"), EVERY_4_HOURS("every-4-hours"),
	EVERY_6_HOURS("every-6-hours"), EVERY_8_HOURS("every-8-hours"), EVERY_12_HOURS("every-12-hours"),
	EVERY_24_HOUR("every-24-hour"), AT_BED_TIME("at-bed-time"), ONCE_DAILY("once-daily"), TWICE_DAILY("twice-daily"),
	AS_NEEDED("as-needed"), ONCE_A_WEEK("once-a-week"), OTHERS("others");

	private final String value;

	private FrequencyType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static FrequencyType fromValue(String v) {
		for (FrequencyType c : FrequencyType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
