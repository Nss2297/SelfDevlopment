package com.waseel.pbm.dssservice.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.waseel.pbm.dssservice.validator.customannotation.IsValidDateFormat;
import com.waseel.pbm.dssservice.validator.customannotation.IsValidGender;
import com.waseel.pbm.dssservice.validator.customannotation.NoLessThanThreeLength;
import com.waseel.pbm.dssservice.validator.customannotation.NoMoreThan100Length;
import com.waseel.pbm.dssservice.validator.customannotation.NoMoreThanThirtyLength;
import com.waseel.pbm.dssservice.validator.customannotation.NoMoreThanTwentyLength;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "requestId", "payerId", "prescriberId", "memberId", "memberGender", "memberWeight", "pharmacyId",
		"dateOfService", "dateOfBirth", "icdCodes", "drugList" })
public class DssRequest {

	@NotEmpty(message = "requestId should not be null or empty")
	@NoMoreThan100Length(message = "RequestId {noMoreThan100LengthValidation}")
	@JsonProperty("requestId")
	private String requestId;

	@NotEmpty(message = "payerId should not be null or empty")
	@NoMoreThanTwentyLength(message = "PayerId {noMoreThanTwentyLengthValidation}")
	@JsonProperty("payerId")
	private String payerId;

	@NoMoreThanTwentyLength(message = "PrescriberId {noMoreThanTwentyLengthValidation}")
	@JsonProperty("prescriberId")
	private String prescriberId;

	@NotEmpty(message = "memberId should not be null or empty")
	@NoLessThanThreeLength(message = "memberId length should not be less than 3 digits")
	@NoMoreThanThirtyLength(message = "memberId length should not be more than 30 digits")
	@JsonProperty("memberId")
	private String memberId;

	@NotEmpty(message = "memberGender should not be null or empty")
	@IsValidGender(message = "{memberGenderValidation}")
	@JsonProperty("memberGender")
	private String memberGender;

	@JsonProperty("memberWeight")
	private BigDecimal memberWeight;

	@NotEmpty(message = "pharmacyId should not be null or empty")
	@NoMoreThanTwentyLength(message = "PharmacyId {noMoreThanTwentyLengthValidation}")
	@JsonProperty("pharmacyId")
	private String pharmacyId;

	@NotEmpty(message = "dateOfService {emptyDateValidation}")
	@IsValidDateFormat(message = "dateOfService {dateFormatValidation}")
	@JsonProperty("dateOfService")
	private String dateOfService;

	@NotEmpty(message = "dateOfBirth {emptyDateValidation}")
	@IsValidDateFormat(message = "dateOfBirth {dateFormatValidation}")
	@JsonProperty("dateOfBirth")
	private String dateOfBirth;

	@NotEmpty(message = "icdCodes list should not be null or empty")
	@JsonProperty("icdCodes")
	private List<@NotBlank(message = "icdCodes list should not be null or empty") String> icdCodes = null;

	@NotEmpty(message = "drugList should not be null or empty")
	@Valid
	@JsonProperty("drugList")
	public List<DrugList> drugList = null;

	@JsonProperty("transactionLogId")
	public Long transactionLogId;

	public DssRequest() {
		super();
	}

	public DssRequest(DssRequest dssRequest) {
		this.requestId = dssRequest.getRequestId();
		this.payerId = dssRequest.getPayerId();
		this.prescriberId = dssRequest.getPrescriberId();
		this.memberId = dssRequest.getMemberId();
		this.memberGender = dssRequest.getMemberGender();
		this.memberWeight = dssRequest.getMemberWeight();
		this.pharmacyId = dssRequest.getPharmacyId();
		this.dateOfService = dssRequest.getDateOfService();
		this.dateOfBirth = dssRequest.getDateOfBirth();
		this.icdCodes = new ArrayList<>(dssRequest.getIcdCodes());
		this.drugList = new ArrayList<>(dssRequest.getDrugList());
		this.transactionLogId = dssRequest.getTransactionLogId();
	}

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
