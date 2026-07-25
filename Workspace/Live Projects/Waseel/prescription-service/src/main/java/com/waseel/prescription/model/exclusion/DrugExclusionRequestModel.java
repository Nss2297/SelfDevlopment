package com.waseel.prescription.model.exclusion;

import com.waseel.prescription.validator.customannotation.IsNumber;
import com.waseel.prescription.validator.customannotation.NoMoreThan100Length;
import com.waseel.prescription.validator.customannotation.NoMoreThanTwentyLength;
import com.waseel.prescription.validator.customannotation.NoSpecialCharacter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

public class DrugExclusionRequestModel {

    @NotEmpty(message = "requestId {notEmptyValidation}")
    @NoMoreThan100Length(message = "requestId {noMoreThan100LengthValidation}")
    @Pattern(regexp = "^(?!\\s*$).+", message = "requestId {noWhiteSpaceCharacterValidation}")
    private String requestId;

    @NotEmpty(message = "physicianLicenseNumber {notEmptyValidation}")
    @NoMoreThanTwentyLength(message = "physicianLicenseNumber {noMoreThan20LengthValidation}")
    @Pattern(regexp = "^(?!.*\\s)\\S*$", message = "physicianLicenseNumber {noWhiteSpaceCharacterValidation}")
    @NoSpecialCharacter(message = "physicianLicenseNumber {noSpecialCharactersValidation}")
    private String physicianLicenseNumber;

    @NotEmpty(message = "drugList {notEmptyValidation}")
    private List<@NotBlank(message = "drugList {notEmptyValidation}") String> drugList;

    @NotEmpty(message = "physicianSpeciality {notEmptyValidation}")
    @NoMoreThan100Length(message = "physicianSpeciality {noMoreThan100LengthValidation}")
    @Pattern(regexp = "^(?!\\s*$).+", message = "physicianSpeciality {noWhiteSpaceCharacterValidation}")
    private String physicianSpeciality;

    @NotEmpty(message = "providerId {notEmptyValidation}")
    @Pattern(regexp = "^(?!\\s*$).+", message = "providerId {noWhiteSpaceCharacterValidation}")
    @IsNumber(message = "providerId {notANumberValidation}")
    private String providerId;

    @NotEmpty(message = "payerId {notEmptyValidation}")
    @Pattern(regexp = "^(?!\\s*$).+", message = "payerId {noWhiteSpaceCharacterValidation}")
    @IsNumber(message = "payerId {notANumberValidation}")
    private String payerId;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPhysicianLicenseNumber() {
        return physicianLicenseNumber;
    }

    public void setPhysicianLicenseNumber(String physicianLicenseNumber) {
        this.physicianLicenseNumber = physicianLicenseNumber;
    }

    public List<String> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<String> drugList) {
        this.drugList = drugList;
    }

    public String getPhysicianSpeciality() {
        return physicianSpeciality;
    }

    public void setPhysicianSpeciality(String physicianSpeciality) {
        this.physicianSpeciality = physicianSpeciality;
    }

    public DrugExclusionRequestModel(String requestId, String physicianLicenseNumber, List<String> drugList,
                                     String physicianSpeciality, String payerId, String providerId) {
        this.requestId = requestId;
        this.physicianLicenseNumber = physicianLicenseNumber;
        this.drugList = drugList;
        this.physicianSpeciality = physicianSpeciality;
        this.providerId = providerId;
        this.payerId = payerId;
    }

    public DrugExclusionRequestModel() {
    }
}
