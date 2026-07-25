package com.waseel.pbm.pbmadminservice.model;

public class IDFDrugToDiagnosisModel {

    private Long id;
    private String icdDiagnosisCode;
    private String serviceCode;
    private String oldServiceCode;

    public IDFDrugToDiagnosisModel(Long id, String icdDiagnosisCode, String serviceCode, String oldServiceCode) {
        this.id = id;
        this.icdDiagnosisCode = icdDiagnosisCode;
        this.serviceCode = serviceCode;
        this.oldServiceCode = oldServiceCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcdDiagnosisCode() {
        return icdDiagnosisCode;
    }

    public void setIcdDiagnosisCode(String icdDiagnosisCode) {
        this.icdDiagnosisCode = icdDiagnosisCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getOldServiceCode() {
        return oldServiceCode;
    }

    public void setOldServiceCode(String oldServiceCode) {
        this.oldServiceCode = oldServiceCode;
    }
}
