package com.waseel.pbm.fdbvalidationservice.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "requestId", "payerId", "prescriberId", "memberId", "memberGender", "memberWeight", "pharmacyId",
		"dateOfService", "dateOfBirth", "icdCodes", "drugList" })
public class DssRequest {

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
	private List<String> icdCodes = null;

	@JsonProperty("drugList")
	public List<DrugList> drugList = null;

	@JsonProperty("transactionLogId")
	public Long transactionLogId;
	
	public Long getTransactionLogId() {
		return transactionLogId;
	}

	public void setTransactionLogId(Long transactionLogId) {
		this.transactionLogId = transactionLogId;
	}

	@JsonProperty("requestId")
	public String getRequestId() {
		return requestId;
	}

	@JsonProperty("requestId")
	public void setRequestId(String requestId) {
		this.requestId = requestId.trim();
	}

	@JsonProperty("payerId")
	public String getPayerId() {
		return payerId;
	}

	@JsonProperty("payerId")
	public void setPayerId(String payerId) {
		this.payerId = payerId.trim();
	}

	@JsonProperty("prescriberId")
	public String getPrescriberId() {
		return prescriberId;
	}

	@JsonProperty("prescriberId")
	public void setPrescriberId(String prescriberId) {
		this.prescriberId = prescriberId.trim();
	}

	@JsonProperty("memberId")
	public String getMemberId() {
		return memberId;
	}

	@JsonProperty("memberId")
	public void setMemberId(String memberId) {
		this.memberId = memberId.trim();
	}

	@JsonProperty("memberGender")
	public String getMemberGender() {
		return memberGender;
	}

	@JsonProperty("memberGender")
	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender.trim();
	}

	@JsonProperty("memberWeight")
	public BigDecimal getMemberWeight() {
		return memberWeight;
	}

	@JsonProperty("memberWeight")
	public void setMemberWeight(BigDecimal memberWeight) {
		this.memberWeight = memberWeight;
	}

	@JsonProperty("pharmacyId")
	public String getPharmacyId() {
		return pharmacyId;
	}

	@JsonProperty("pharmacyId")
	public void setPharmacyId(String pharmacyId) {
		this.pharmacyId = pharmacyId.trim();
	}

	@JsonProperty("dateOfService")
	public String getDateOfService() {
		return dateOfService;
	}

	@JsonProperty("dateOfService")
	public void setDateOfService(String dateOfService) {
		this.dateOfService = dateOfService.trim();
	}

	@JsonProperty("dateOfBirth")
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	@JsonProperty("dateOfBirth")
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth.trim();
	}

	@JsonProperty("icdCodes")
	public List<String> getIcdCodes() {
		return icdCodes;
	}

	@JsonProperty("icdCodes")
	public void setIcdCodes(List<String> icdCodes) {
		icdCodes.replaceAll(String::trim);
		icdCodes.replaceAll(String::toUpperCase);
		this.icdCodes = icdCodes;
	}

	@JsonProperty("drugList")
	public List<DrugList> getDrugList() {
		return drugList;
	}

	@JsonProperty("drugList")
	public void setDrugList(List<DrugList> drugList) {
		this.drugList = drugList;
	}

}
