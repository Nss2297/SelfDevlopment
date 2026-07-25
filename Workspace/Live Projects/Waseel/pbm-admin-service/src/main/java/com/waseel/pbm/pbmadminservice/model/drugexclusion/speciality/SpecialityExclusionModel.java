package com.waseel.pbm.pbmadminservice.model.drugexclusion.speciality;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SpecialityExclusionModel {

    private static final long serialVersionUID = 1L;
    private BigDecimal specialityId;
    private String specialityName;
    private Long payerId;
    private Long specialityExclusionAsscId;

    public SpecialityExclusionModel() {
    }

    public SpecialityExclusionModel(BigDecimal specialityId, String specialityName) {
        this.specialityId = specialityId;
        this.specialityName = specialityName;
    }

    public BigDecimal getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(BigDecimal specialityId) {
        this.specialityId = specialityId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public Long getPayerId() {
        return payerId;
    }

    public void setPayerId(Long payerId) {
        this.payerId = payerId;
    }

    public Long getSpecialityExclusionAsscId() {
        return specialityExclusionAsscId;
    }

    public void setSpecialityExclusionAsscId(Long specialityExclusionAsscId) {
        this.specialityExclusionAsscId = specialityExclusionAsscId;
    }
}
