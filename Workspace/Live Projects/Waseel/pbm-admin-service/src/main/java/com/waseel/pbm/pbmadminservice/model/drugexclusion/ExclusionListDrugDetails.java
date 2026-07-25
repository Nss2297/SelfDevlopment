package com.waseel.pbm.pbmadminservice.model.drugexclusion;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExclusionListDrugDetails {

	private String drugCode;

	private String drugName;

	private String scientificName;

	private String scientificCode;

	private Date lastUpdateDate;

	private String price;

	private String waseelDrugId;

	public String getDrugCode() {
		return drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public String getScientificName() {
		return scientificName;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public String getPrice() {
		return price;
	}

	public String getWaseelDrugId() {
		return waseelDrugId;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setWaseelDrugId(String waseelDrugId) {
		this.waseelDrugId = waseelDrugId;
	}

	public ExclusionListDrugDetails() {
		super();
	}

	public ExclusionListDrugDetails(String drugCode, String drugName, String scientificName, String scientificCode,
			Date lastUpdateDate, String price, String waseelDrugId) {
		super();
		this.drugCode = drugCode;
		this.drugName = drugName;
		this.scientificName = scientificName;
		this.scientificCode = scientificCode;
		this.lastUpdateDate = lastUpdateDate;
		this.price = price;
		this.waseelDrugId = waseelDrugId;
	}

}
