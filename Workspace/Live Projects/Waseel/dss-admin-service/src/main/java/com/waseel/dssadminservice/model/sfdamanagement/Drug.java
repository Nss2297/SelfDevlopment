
package com.waseel.dssadminservice.model.sfdamanagement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Drug {

	@JsonProperty("sfdaCode")
	private String sfdaCode;
	@JsonProperty("gtinCode")
	private String gtinCode;
	@JsonProperty("tradeName")
	private String tradeName;
	@JsonProperty("scientificName")
	private String scientificName;
	@JsonProperty("price")
	private String price;
	@JsonProperty("waseelDrugId")
	private String waseelDrugId;
	@JsonProperty("scientificCode")
	private String scientificCode;
	

	public Drug(String sfdaCode, String gtinCode, String tradeName, String scientificName, String price,
			String waseelDrugId,String scientificCode) {
		super();
		this.sfdaCode = sfdaCode;
		this.gtinCode = gtinCode;
		this.tradeName = tradeName;
		this.scientificName = scientificName;
		this.price = price;
		this.waseelDrugId = waseelDrugId;
		this.scientificCode = scientificCode;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	@JsonProperty("sfdaCode")
	public String getSfdaCode() {
		return sfdaCode;
	}

	@JsonProperty("sfdaCode")
	public void setSfdaCode(String sfdaCode) {
		this.sfdaCode = sfdaCode;
	}

	@JsonProperty("gtinCode")
	public String getGtinCode() {
		return gtinCode;
	}

	@JsonProperty("gtinCode")
	public void setGtinCode(String gtinCode) {
		this.gtinCode = gtinCode;
	}

	@JsonProperty("tradeName")
	public String getTradeName() {
		return tradeName;
	}

	@JsonProperty("tradeName")
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	@JsonProperty("scientificName")
	public String getScientificName() {
		return scientificName;
	}

	@JsonProperty("scientificName")
	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	@JsonProperty("price")
	public String getPrice() {
		return price;
	}

	@JsonProperty("price")
	public void setPrice(String price) {
		this.price = price;
	}

	public String getWaseelDrugId() {
		return waseelDrugId;
	}

	public void setWaseelDrugId(String waseelDrugId) {
		this.waseelDrugId = waseelDrugId;
	}

}
