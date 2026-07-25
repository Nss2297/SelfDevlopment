package com.waseel.drugexclusionvalidationservice.model.enums;

public enum ModuleConfiguration {

	EXCLUSION_VALIDATION(1L), SPECIALTY_EXCLUSION(2L), PROVIDERS_EXCLUSION(3L), NETWORKS_EXCLUSION(4L),
	HIGH_COST_DRUGS_EXCLUSION(5L);

	private final Long value;

	private ModuleConfiguration(Long value) {
		this.value = value;
	}

	public Long value() {
		return this.value;
	}

	public static ModuleConfiguration fromValue(Long v) {
		for (ModuleConfiguration c : ModuleConfiguration.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException();
	}
}
