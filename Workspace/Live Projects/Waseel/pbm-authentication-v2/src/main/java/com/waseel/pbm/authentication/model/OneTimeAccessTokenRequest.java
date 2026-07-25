package com.waseel.pbm.authentication.model;

import java.math.BigInteger;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OneTimeAccessTokenRequest {

    @NotBlank
    private String patientId;

    @Size(min = 1)
    @NotNull
    private List<String> objectIds;

    @Min(30000)
    private BigInteger duration = new BigInteger("30000");


    public OneTimeAccessTokenRequest() {
    }

    public OneTimeAccessTokenRequest(String patientId, List<String> objectIds, BigInteger duration) {
        this.patientId = patientId;
        this.objectIds = objectIds;
        this.duration = duration;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public List<String> getObjectIds() {
        return objectIds;
    }

    public void setObjectIds(List<String> objectIds) {
        this.objectIds = objectIds;
    }

    public BigInteger getDuration() {
        return duration;
    }

    public void setDuration(BigInteger duration) {
        this.duration = duration;
    }

}
