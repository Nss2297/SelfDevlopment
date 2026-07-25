package com.waseel.drugformulary.model.pbmpayerapis;

public class PolicyInformationModel {

	private String policyNumber;
	private String policyHolderName;
	private String memberId;
	private String classCode;
	private String className;
	private Boolean isChiPolicy;

	public PolicyInformationModel() {
		super();
	}

	public PolicyInformationModel(String policyNumber, String policyHolderName, String memberId, String classCode,
			String className) {
		this.policyNumber = policyNumber;
		this.policyHolderName = policyHolderName;
		this.memberId = memberId;
		this.classCode = classCode;
		this.className = className;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getPolicyHolderName() {
		return policyHolderName;
	}

	public void setPolicyHolderName(String policyHolderName) {
		this.policyHolderName = policyHolderName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getClassCode() {
		return classCode;
	}

	public String getClassName() {
		return className;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Boolean getIsChiPolicy() {
		return isChiPolicy;
	}

	public void setIsChiPolicy(Boolean isChiPolicy) {
		this.isChiPolicy = isChiPolicy;
	}
}
