package com.waseel.pbm.pbmadminservice.model.drugformulary;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsNumber;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicyDetailsRequestModel {

	@IsNumber(message = "drugFormularyAssociationId {onlyAllowDigits}")
	private String drugFormularyAssociationId;

	@NotEmpty(message = "policyDetails {notNullOrEmpty}")
	@Valid
	private List<PolicyMetaDataModel> policyDetails;

	@Valid
	private MemberPolicyMetaDataModel memberDetails;

	public String getDrugFormularyAssociationId() {
		return drugFormularyAssociationId;
	}

	public void setDrugFormularyAssociationId(String drugFormularyAssociationId) {
		this.drugFormularyAssociationId = drugFormularyAssociationId;
	}

	public MemberPolicyMetaDataModel getMemberDetails() {
		return memberDetails;
	}

	public void setMemberDetails(MemberPolicyMetaDataModel memberDetails) {
		this.memberDetails = memberDetails;
	}

	public List<PolicyMetaDataModel> getPolicyDetails() {
		return policyDetails;
	}

	public void setPolicyDetails(List<PolicyMetaDataModel> policyDetails) {
		this.policyDetails = policyDetails;
	}

}
