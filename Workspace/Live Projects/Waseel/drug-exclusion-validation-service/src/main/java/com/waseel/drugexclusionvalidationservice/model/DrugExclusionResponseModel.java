package com.waseel.drugexclusionvalidationservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugExclusionResponseModel {

    private String requestId;
    private List<DrugList> drugList;
    private String errorCode;
    private String errorDescription;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<DrugList> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<DrugList> drugList) {
        this.drugList = drugList;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
