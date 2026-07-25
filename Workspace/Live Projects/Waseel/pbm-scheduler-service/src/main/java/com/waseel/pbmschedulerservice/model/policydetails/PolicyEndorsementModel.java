package com.waseel.pbmschedulerservice.model.policydetails;

public class PolicyEndorsementModel {

	private String endorsementNumber;
	private String endorsementDate;
	private String endorsementType;
	private String endorsementMessage;

	public String getEndorsementNumber() {
		return endorsementNumber;
	}

	public void setEndorsementNumber(String endorsementNumber) {
		this.endorsementNumber = endorsementNumber;
	}

	public String getEndorsementDate() {
		return endorsementDate;
	}

	public void setEndorsementDate(String endorsementDate) {
		this.endorsementDate = endorsementDate;
	}

	public String getEndorsementType() {
		return endorsementType;
	}

	public void setEndorsementType(String endorsementType) {
		this.endorsementType = endorsementType;
	}

	public String getEndorsementMessage() {
		return endorsementMessage;
	}

	public void setEndorsementMessage(String endorsementMessage) {
		this.endorsementMessage = endorsementMessage;
	}
}
