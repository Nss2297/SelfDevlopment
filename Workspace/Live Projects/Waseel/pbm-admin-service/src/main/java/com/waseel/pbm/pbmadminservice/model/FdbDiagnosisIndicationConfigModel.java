package com.waseel.pbm.pbmadminservice.model;

public class FdbDiagnosisIndicationConfigModel {

    private String icdCode;
    private String validateSubChapters;
    private Character isEnabled;
    private Long id;

    public FdbDiagnosisIndicationConfigModel(String icdCode, String validateSubChapters, Character isEnabled, Long id) {
        this.icdCode = icdCode;
        this.validateSubChapters = validateSubChapters;
        this.isEnabled = isEnabled;
        this.id = id;
    }

    public String getIcdCode() {
        return icdCode;
    }

    public void setIcdCode(String icdCode) {
        this.icdCode = icdCode;
    }

    public String getValidateSubChapters() {
        return validateSubChapters;
    }

    public void setValidateSubChapters(String validateSubChapters) {
        this.validateSubChapters = validateSubChapters;
    }

    public Character getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Character isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
