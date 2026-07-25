package com.waseel.prescription.model.dispense;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescriptionDrug {

	@JsonProperty("isBrand")
	private Boolean isBrand;
	@JsonProperty("scientificName")
	private String scientificName;
	@JsonProperty("scientificCode")
	private String scientificCode;
	@JsonProperty("quantity")
	private Integer quantity;
	@JsonProperty("suggestedDrugs")
	private List<SuggestedDrug> suggestedDrugs;

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public List<SuggestedDrug> getSuggestedDrugs() {
		return suggestedDrugs;
	}

	public void setSuggestedDrugs(List<SuggestedDrug> suggestedDrugs) {
		this.suggestedDrugs = suggestedDrugs;
	}

	public Boolean getIsBrand() {
		return isBrand;
	}

	public void setIsBrand(Boolean isBrand) {
		this.isBrand = isBrand;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
