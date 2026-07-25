package com.waseel.pbmschedulerservice.model.policydetails;

public class PolicyEndorsementResponseModel {

	private String policyNumber;
	private PolicyEndorsementModel policyEndorsement;

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public PolicyEndorsementModel getPolicyEndorsement() {
		return policyEndorsement;
	}

	public void setPolicyEndorsement(PolicyEndorsementModel policyEndorsement) {
		this.policyEndorsement = policyEndorsement;
	}

}
