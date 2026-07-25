package com.waseel.pbm.pbmadminservice.model.drugexclusion;

import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan256Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan50Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DrugExclusionDrugDetailsRequestModel {

    @NotBlank(message = "drugCode {notNullOrEmpty}")
    @NoMoreThan50Length(message = "drugCode {noMoreThan50LengthValidation}")
    private String drugCode;
    @NotBlank(message = "drugName {notNullOrEmpty}")
    @NoMoreThan256Length(message = "drugName {noMoreThan256LengthValidation}")
    private String drugName;
    @NotBlank(message = "genericName {notNullOrEmpty}")
    @NoMoreThan256Length(message = "genericName {noMoreThan256LengthValidation}")
    private String genericName;
    @NotNull(message = "price {notNullOrEmpty}")
    private BigDecimal price;

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

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
