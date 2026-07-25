package com.waseel.pbmschedulerservice.model.policydetails;

import java.util.List;

public class BenefitCasesResponseModel {

	private String policyNumber;
	private String classCode;
	private String benefitCode;
	private List<BenefitCasesModel> benefitCases;

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

	public String getBenefitCode() {
		return benefitCode;
	}

	public void setBenefitCode(String benefitCode) {
		this.benefitCode = benefitCode;
	}

	public List<BenefitCasesModel> getBenefitCases() {
		return benefitCases;
	}

	public void setBenefitCases(List<BenefitCasesModel> benefitCases) {
		this.benefitCases = benefitCases;
	}
}
