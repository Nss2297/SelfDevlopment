package com.waseel.prescription.model.formulary;

import java.util.List;

public class DrugFormularyRequestModel {

    private List<String> drugList;
    private String requestId;

    public DrugFormularyRequestModel() {
        super();
    }

    public DrugFormularyRequestModel(List<String> drugList, String requestId) {
        this.drugList = drugList;
        this.requestId = requestId;
    }

    public List<String> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<String> drugList) {
        this.drugList = drugList;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
