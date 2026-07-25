package com.waseel.prescription.model.prescription;

import java.util.Date;

public class ProviderPrescriptionResponseModel {

	private String referenceNo;
	private String status;
	private Date dateAndTime;
	private String memberId;
	private Long idNumber;
	private String policyNumber;
	private String memberName;
	private String insurance;
	private String payerId;
	private String id;

	public ProviderPrescriptionResponseModel(String referenceNo, String status, Date dateAndTime, String memberId,
			Long idNumber, String policyNumber, String memberName, String insurance, String payerId) {
		super();
		this.referenceNo = referenceNo;
		this.status = status;
		this.dateAndTime = dateAndTime;
		this.memberId = memberId;
		this.idNumber = idNumber;
		this.policyNumber = policyNumber;
		this.memberName = memberName;
		this.insurance = insurance;
		this.payerId = payerId;
		this.id = referenceNo;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Long getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(Long idNumber) {
		this.idNumber = idNumber;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ProviderPrescriptionResponseModel() {
		super();
	}

}
