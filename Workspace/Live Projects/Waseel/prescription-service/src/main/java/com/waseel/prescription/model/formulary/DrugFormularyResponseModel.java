package com.waseel.prescription.model.formulary;

public class DrugFormularyResponseModel {

    private String statusCode;
    private String denialCode;
    private String statusDescription;
    private String drugCode;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDenialCode() {
        return denialCode;
    }

    public void setDenialCode(String denialCode) {
        this.denialCode = denialCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public DrugFormularyResponseModel(String statusCode, String statusDescription) {
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
    }

    public DrugFormularyResponseModel() {
    }

	public DrugFormularyResponseModel(String statusCode, String denialCode, String statusDescription, String drugCode) {
		super();
		this.statusCode = statusCode;
		this.denialCode = denialCode;
		this.statusDescription = statusDescription;
		this.drugCode = drugCode;
	}
}
