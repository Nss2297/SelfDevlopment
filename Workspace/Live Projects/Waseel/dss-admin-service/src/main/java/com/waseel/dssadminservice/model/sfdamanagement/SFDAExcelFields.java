package com.waseel.dssadminservice.model.sfdamanagement;

public enum SFDAExcelFields {

	GTIN_CODE("GTIN Code"), TRADE_NAME("Trade Name"), PRICE("Price"), GRANULAR_UNIT("Granular Unit"),
	UNIT_TYPE("Unit Type"), DOSAGE_FORM("Dosage Form"), ADMINISTRATION_ROUTE("Administration Route"),
	PACKAGE_TYPE("Package Type"), PACKAGE_SIZE("Package Size"), SCIENTIFIC_NAME("Scientific Name"),
	STRENGTH("Strength"), SFDA_CODE("SFDA Code"), SCIENTIFIC_CODE("Scientific Code"), STRENGTH_UNIT("Strength Unit");

	private final String fieldName;

	SFDAExcelFields(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}
}
