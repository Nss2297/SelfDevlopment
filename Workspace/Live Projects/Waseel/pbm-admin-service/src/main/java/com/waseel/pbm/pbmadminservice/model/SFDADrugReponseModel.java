package com.waseel.pbm.pbmadminservice.model;

import java.io.Serializable;

public class SFDADrugReponseModel implements Serializable {

	private String sfdaCode;

	private String tradeName;

	private String scientificCode;

	private String scientificName;

	private String gtinCode;

	private String price;

	private String granularUnit;

	public String getSfdaCode() {
		return sfdaCode;
	}

	public void setSfdaCode(String sfdaCode) {
		this.sfdaCode = sfdaCode;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getGtinCode() {
		return gtinCode;
	}

	public void setGtinCode(String gtinCode) {
		this.gtinCode = gtinCode;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getGranularUnit() {
		return granularUnit;
	}

	public void setGranularUnit(String granularUnit) {
		this.granularUnit = granularUnit;
	}

	public SFDADrugReponseModel(String sfdaCode, String tradeName, String scientificCode, String scientificName,
			String gtinCode, String price, String granularUnit) {
		super();
		this.sfdaCode = sfdaCode;
		this.tradeName = tradeName;
		this.scientificCode = scientificCode;
		this.scientificName = scientificName;
		this.gtinCode = gtinCode;
		this.price = price;
		this.granularUnit = granularUnit;
	}

	@Override
	public String toString() {
		return "SFDADrugModel [sfdaCode=" + sfdaCode + ", tradeName=" + tradeName + ", scientificCode=" + scientificCode
				+ ", scientificName=" + scientificName + ", gtinCode=" + gtinCode + ", price=" + price
				+ ", granularUnit=" + granularUnit + ", getSfdaCode()=" + getSfdaCode() + ", getTradeName()="
				+ getTradeName() + ", getScientificCode()=" + getScientificCode() + ", getScientificName()="
				+ getScientificName() + ", getGtinCode()=" + getGtinCode() + ", getPrice()=" + getPrice()
				+ ", getGranularUnit()=" + getGranularUnit() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	public SFDADrugReponseModel() {
		super();
	}

}
