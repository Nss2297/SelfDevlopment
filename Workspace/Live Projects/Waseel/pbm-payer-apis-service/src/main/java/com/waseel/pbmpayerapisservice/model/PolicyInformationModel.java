package com.waseel.pbmpayerapisservice.model;

public class PolicyInformationModel {

	private String policyNumber;
	private String policyHolderName;
	private String memberId;
	private String classCode;
	private String className;
	private Boolean isChiPolicy;

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
