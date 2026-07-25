package com.waseel.pbm.pbmadminservice.model.drugformulary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidDateFormat;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan250Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan50Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class PolicyMetaDataModel {

    @NotEmpty(message = "policyNumber {notNullOrEmpty}")
    @NoMoreThan50Length(message = "policyNumber {noMoreThan50LengthValidation}")
    private String policyNumber;

    @NotEmpty(message = "policyHolderName {notNullOrEmpty}")
    @NoMoreThan250Length(message = "policyHolderName {noMoreThan250LengthValidation}")
    private String policyHolderName;

    @NoMoreThan50Length(message = "memberId {noMoreThan50LengthValidation}")
    private String memberId;

    @NotEmpty(message = "policyType {notNullOrEmpty}")
    @NoMoreThan250Length(message = "policyType {noMoreThan250LengthValidation}")
    private String policyType;

    @NotNull(message = "issueDate {notNullOrEmpty}")
    @IsValidDateFormat(message = "issueDate {invalidDateFormat}")
    private String issueDate;

    @NotNull(message = "startDate {notNullOrEmpty}")
    @IsValidDateFormat(message = "startDate {invalidDateFormat}")
    private String startDate;

    @NotNull(message = "endDate {notNullOrEmpty}")
    @IsValidDateFormat(message = "endDate {invalidDateFormat}")
    private String endDate;

    @NotEmpty(message = "policyClasses {notNullOrEmpty}")
    @Valid
    private List<PolicyClassesModel> policyClasses;

    private String policyClassName;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getPolicyHolderName() {
        return policyHolderName;
    }

    public String getPolicyType() {
        return policyType;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public List<PolicyClassesModel> getPolicyClasses() {
        return policyClasses;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public void setPolicyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setPolicyClasses(List<PolicyClassesModel> policyClasses) {
        this.policyClasses = policyClasses;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPolicyClassName() {
        return policyClassName;
    }

    public void setPolicyClassName(String policyClassName) {
        this.policyClassName = policyClassName;
    }
}
