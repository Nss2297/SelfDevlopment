package com.waseel.dssadminservice.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.waseel.dssadminservice.model.AgeRangeResponseModel;

public enum AgeGroup {

	NEONATE("0 to 29"), INFANT("30 to 364"), CHILD("365 to 4744"), ADOLESCENT("4745 to 6569"), ADULT("6570 to 23724"),
	GERIATRIC("23725 to 40150"), PEDIATRIC("365 to 6569"), ADULT_AND_ADOLESCENT("4745 to 23724"),
	ADULT_AND_GERIATRIC("6570 to 40150"), YOUNG_CHILD("365 to 2189");

	private final String range;
	private final String fromValue;
	private final String toValue;

	AgeGroup(String range) {
		this.range = range;
		String[] values = range.split(" to ");
		this.fromValue = values[0];
		this.toValue = values[1];
	}

	public String getRange() {
		return range;
	}

	public String getFromValue() {
		return fromValue;
	}

	public String getToValue() {
		return toValue;
	}

	public static List<AgeRangeResponseModel> getAgeRange() {
		return Arrays.stream(AgeGroup.values())
				.map(entry -> new AgeRangeResponseModel(entry.name(), entry.getFromValue(), entry.getToValue()))
				.collect(Collectors.toList());
	}
}
