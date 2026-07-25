package com.waseel.pbmschedulerservice.model.memberdetails;

import java.util.List;

public class MemberPolicyAssociationModel {

    private String policyClass;
    private String planType;
    private String memberSince;
    private String memberId;
    private List<MemberDetailsModel> beneficiaries;
    private String lastUpdateDateAndTime;

    public String getPolicyClass() {
        return policyClass;
    }

    public void setPolicyClass(String policyClass) {
        this.policyClass = policyClass;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public List<MemberDetailsModel> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(List<MemberDetailsModel> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    public String getLastUpdateDateAndTime() {
        return lastUpdateDateAndTime;
    }

    public void setLastUpdateDateAndTime(String lastUpdateDateAndTime) {
        this.lastUpdateDateAndTime = lastUpdateDateAndTime;
    }
}
