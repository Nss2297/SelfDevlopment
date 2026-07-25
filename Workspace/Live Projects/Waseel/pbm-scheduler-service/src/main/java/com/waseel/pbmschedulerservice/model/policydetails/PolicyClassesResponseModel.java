package com.waseel.pbmschedulerservice.model.policydetails;

import java.util.List;

public class PolicyClassesResponseModel {

	private String policyNumber;
	private List<PolicyClassesModel> policyClasses;

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public List<PolicyClassesModel> getPolicyClasses() {
		return policyClasses;
	}

	public void setPolicyClasses(List<PolicyClassesModel> policyClasses) {
		this.policyClasses = policyClasses;
	}
}
