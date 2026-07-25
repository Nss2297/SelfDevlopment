package com.waseel.prescription.model.inquiry.summary;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.waseel.prescription.validator.customannotation.NoSpecialCharacterExceptHyphen;
import com.waseel.prescription.validator.customannotation.NoWhiteSpaceCharacter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescriptionSummaryRequestModel {

	@JsonProperty("IDNumber")
	@NoWhiteSpaceCharacter(message = "idNumber should not contain white space")
	private String idNumber;

	@Pattern(regexp = "(0[1-9]|1[0-9]|2[0-9]|3[0-1]|[1-9])-(0[1-9]|1[0-2]|[1-9])-([0-9]{4})", message = "startDate format is Invalid, it should be dd-MM-yyyy.")
	private String startDate;

	@Pattern(regexp = "(0[1-9]|1[0-9]|2[0-9]|3[0-1]|[1-9])-(0[1-9]|1[0-2]|[1-9])-([0-9]{4})", message = "endDate format is Invalid, it should be dd-MM-yyyy.")
	private String endDate;

	@Size(max = 20, message = "MemberID shouldn't be more than 20")
	@NoWhiteSpaceCharacter(message = "MemberID should not contain white space")
	@NoSpecialCharacterExceptHyphen(message = "memberId should not have any special character except '-'")
	@JsonProperty("MemberID")
	private String memberID;

	@Size(max = 15, message = "policyNumber shouldn't be more than 15")
	@NoWhiteSpaceCharacter(message = "policyNumber should not contain white space")
	private String policyNumber;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

}
