package com.waseel.pbmschedulerservice.model.memberdetails;

import java.util.List;

public class MemberDetailsModel {

    private String memberName;
    private String email;
    private Long idNumber;
    private String gender;
    private String dateOfBirth;
    private String maritalStatus;
    private String nationality;
    private String mobileNumber;
    private String memberType;
    private List<MemberPolicyDetailsModel> policyDetails;
    private String lastUpdateDateAndTime;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Long idNumber) {
        this.idNumber = idNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public List<MemberPolicyDetailsModel> getPolicyDetails() {
        return policyDetails;
    }

    public void setPolicyDetails(List<MemberPolicyDetailsModel> policyDetails) {
        this.policyDetails = policyDetails;
    }

    public String getLastUpdateDateAndTime() {
        return lastUpdateDateAndTime;
    }

    public void setLastUpdateDateAndTime(String lastUpdateDateAndTime) {
        this.lastUpdateDateAndTime = lastUpdateDateAndTime;
    }
}
