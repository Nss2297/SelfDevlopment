package com.waseel.prescription.model.modifydecision;

public class ProviderOverrideDecisionRequestModel {

	private String drugCode;
	private String drugName;
	private String scientificCode;
	private String scientificName;
	private String denialCode;
	private String rejectionReason;
	private String overridingReason;

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getDenialCode() {
		return denialCode;
	}

	public void setDenialCode(String denialCode) {
		this.denialCode = denialCode;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public String getOverridingReason() {
		return overridingReason;
	}

	public void setOverridingReason(String overridingReason) {
		this.overridingReason = overridingReason;
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

}
