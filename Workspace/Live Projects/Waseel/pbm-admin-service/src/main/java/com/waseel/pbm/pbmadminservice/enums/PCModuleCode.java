package com.waseel.pbm.pbmadminservice.enums;

public enum PCModuleCode {
	
	PC_DRUGTODIAGNOSIS_INDICATION("PC_CPINDI001"), PC_DRUGTODIAGNOSIS_CONTRAINDICATION("PC_CPINDC001");

	private final String value;

	private PCModuleCode(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
}
