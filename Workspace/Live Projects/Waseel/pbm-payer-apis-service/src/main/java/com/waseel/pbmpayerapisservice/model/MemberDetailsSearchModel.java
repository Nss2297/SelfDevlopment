package com.waseel.pbmpayerapisservice.model;

import javax.validation.constraints.AssertTrue;

public class MemberDetailsSearchModel {

	private String idNumber;
	private String memberId;
	private String policyNumber;
	private String providerPayerCode;

	@AssertTrue(message = "A combination of either (memberId, policyNumber and providerPayerCode) or (idNumber and providerPayerCode) should be provided.")
	private boolean isValid() {
		return idNumber != null ? providerPayerCode != null
				: memberId != null && policyNumber != null && providerPayerCode != null;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getProviderPayerCode() {
		return providerPayerCode;
	}

	public void setProviderPayerCode(String providerPayerCode) {
		this.providerPayerCode = providerPayerCode;
	}

}
