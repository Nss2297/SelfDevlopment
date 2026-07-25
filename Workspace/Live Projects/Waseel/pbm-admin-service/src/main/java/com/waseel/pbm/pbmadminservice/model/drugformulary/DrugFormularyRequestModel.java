package com.waseel.pbm.pbmadminservice.model.drugformulary;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class DrugFormularyRequestModel {

    @NotEmpty(message = "FormularyName should not be null or empty")
    private String formularyName;
    @NotEmpty(message = "PolicyDetails should not be null or empty")
    List<PolicyMetaDataModel> policyDetails;
    @NotEmpty(message = "DrugDetails should not be null or empty")
    List<DrugFormularyDrugDetailsModel> drugDetails;
    private MemberPolicyMetaDataModel memberDetails;

    public String getFormularyName() {
        return formularyName;
    }

    public void setFormularyName(String formularyName) {
        this.formularyName = formularyName;
    }

    public List<PolicyMetaDataModel> getPolicyDetails() {
        return policyDetails;
    }

    public void setPolicyDetails(List<PolicyMetaDataModel> policyDetails) {
        this.policyDetails = policyDetails;
    }

    public List<DrugFormularyDrugDetailsModel> getDrugDetails() {
        return drugDetails;
    }

    public void setDrugDetails(List<DrugFormularyDrugDetailsModel> drugDetails) {
        this.drugDetails = drugDetails;
    }

    public MemberPolicyMetaDataModel getMemberDetails() {
        return memberDetails;
    }

    public void setMemberDetails(MemberPolicyMetaDataModel memberDetails) {
        this.memberDetails = memberDetails;
    }
}
