package com.waseel.pbmschedulerservice.model.policydetails;

import java.util.List;

public class ClassBenefitsResponseModel {

	private String policyNumber;
	private String classCode;
	private List<ClassBenefitsModel> classBenefits;

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public List<ClassBenefitsModel> getClassBenefits() {
		return classBenefits;
	}

	public void setClassBenefits(List<ClassBenefitsModel> classBenefits) {
		this.classBenefits = classBenefits;
	}
}
