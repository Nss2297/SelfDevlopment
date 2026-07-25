package com.waseel.pbm.rtsservice.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "requestId", "payerId", "prescriberId", "memberId", "memberGender", "memberWeight", "pharmacyId",
		"dateOfService", "dateOfBirth", "icdCodes", "drugList" })
public class RTSRequest {

	@JsonProperty("requestId")
	private String requestId;

	@JsonProperty("payerId")
	private String payerId;

	@JsonProperty("prescriberId")
	private String prescriberId;

	@JsonProperty("memberId")
	private String memberId;

	@JsonProperty("memberGender")
	private String memberGender;

	@JsonProperty("memberWeight")
	private BigDecimal memberWeight;

	@JsonProperty("pharmacyId")
	private String pharmacyId;

	@JsonProperty("dateOfService")
	private String dateOfService;

	@JsonProperty("dateOfBirth")
	private String dateOfBirth;

	@JsonProperty("icdCodes")
	private List<String> icdCodes;

	@JsonProperty("drugList")
	private List<DrugList> drugList;

	@JsonProperty("transactionLogId")
	private Long transactionLogId;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId.trim();
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId.trim();
	}

	public String getPrescriberId() {
		return prescriberId;
	}

	public void setPrescriberId(String prescriberId) {
		this.prescriberId = prescriberId.trim();
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId.trim();
	}

	public String getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender.trim();
	}

	public BigDecimal getMemberWeight() {
		return memberWeight;
	}

	public void setMemberWeight(BigDecimal memberWeight) {
		this.memberWeight = memberWeight;
	}

	public String getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(String pharmacyId) {
		this.pharmacyId = pharmacyId.trim();
	}

	public String getDateOfService() {
		return dateOfService;
	}

	public void setDateOfService(String dateOfService) {
		this.dateOfService = dateOfService.trim();
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth.trim();
	}

	public List<String> getIcdCodes() {
		return icdCodes;
	}

	public void setIcdCodes(List<String> icdCodes) {
		icdCodes.replaceAll(String::trim);
		icdCodes.replaceAll(String::toUpperCase);
		this.icdCodes = icdCodes;
	}

	public List<DrugList> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<DrugList> drugList) {
		this.drugList = drugList;
	}

	public Long getTransactionLogId() {
		return transactionLogId;
	}

	public void setTransactionLogId(Long transactionLogId) {
		this.transactionLogId = transactionLogId;
	}
	
	
}
