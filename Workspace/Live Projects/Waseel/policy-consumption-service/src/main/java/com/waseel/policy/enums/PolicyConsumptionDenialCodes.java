package com.waseel.policy.enums;

public enum PolicyConsumptionDenialCodes {

	BR_PC_FAILED("BR_PCF01"), BR_PC_REJECTED("BR_PCRJ02"), BR_PC_INVALID("BR_PCIV03"), BR_PC_NO_BENEFITS("BR_PCNOB04"),
	BR_PC_NO_BENEFIT_CASE("BR_PCNOBC05"), BR_PC_NO_POLICY("BR_PCNOP06"), BR_PC_NO_MEMBER("BR_PCNOM07"),
	BR_PC_INVALID_ID_NUMBER("BR_PCIVIDNUM14"), BR_PC_INVALID_PRESCRIPTION("BR_PCIVPRE08"), BR_PC_INACTIVE("BR_PCIAR09"),
	BR_PC_NO_REMAINING_LIMIT("BR_PCNORL10"), BR_PC_NO_LOCKED_AMOUNT("BR_PCNOLAMT11"), BR_PC_EXPIRED("BR_PCREX12"),
	BR_PC_NO_REQUEST("BR_PCNOR13");

	private final String value;

	private PolicyConsumptionDenialCodes(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static PolicyConsumptionDenialCodes fromValue(String v) {
		for (PolicyConsumptionDenialCodes c : PolicyConsumptionDenialCodes.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
