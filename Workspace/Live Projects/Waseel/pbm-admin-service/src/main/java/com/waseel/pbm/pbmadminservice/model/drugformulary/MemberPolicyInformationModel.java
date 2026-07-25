package com.waseel.pbm.pbmadminservice.model.drugformulary;

import javax.validation.constraints.NotEmpty;

import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan250Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan50Length;

public class MemberPolicyInformationModel {

	@NotEmpty(message = "policyNumber {notNullOrEmpty}")
	@NoMoreThan50Length(message = "policyNumber {noMoreThan50LengthValidation}")
	private String policyNumber;

	@NotEmpty(message = "policyHolderName {notNullOrEmpty}")
	@NoMoreThan250Length(message = "policyHolderName {noMoreThan250LengthValidation}")
	private String policyHolderName;

	@NotEmpty(message = "memberId {notNullOrEmpty}")
	@NoMoreThan50Length(message = "memberId {noMoreThan50LengthValidation}")
	private String memberId;

	@NotEmpty(message = "classCode {notNullOrEmpty}")
	@NoMoreThan50Length(message = "classCode {noMoreThan50LengthValidation}")
	private String classCode;

	@NoMoreThan50Length(message = "className {noMoreThan50LengthValidation}")
	private String className;

	public String getPolicyNumber() {
		return policyNumber;
	}

	public String getPolicyHolderName() {
		return policyHolderName;
	}

	public String getMemberId() {
		return memberId;
	}

	public String getClassCode() {
		return classCode;
	}

	public String getClassName() {
		return className;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public void setPolicyHolderName(String policyHolderName) {
		this.policyHolderName = policyHolderName;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
