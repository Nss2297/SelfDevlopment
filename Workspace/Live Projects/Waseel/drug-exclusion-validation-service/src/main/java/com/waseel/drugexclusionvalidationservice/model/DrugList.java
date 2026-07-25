package com.waseel.drugexclusionvalidationservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugList {

    private String statusCode;
    private String drugCode;
    private List<Rejections> rejectionsList;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public List<Rejections> getRejectionsList() {
        return rejectionsList;
    }

    public void setRejectionsList(List<Rejections> rejectionsList) {
        this.rejectionsList = rejectionsList;
    }
}
