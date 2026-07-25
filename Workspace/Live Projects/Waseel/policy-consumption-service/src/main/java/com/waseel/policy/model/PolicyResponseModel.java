package com.waseel.policy.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PolicyResponseModel {

	private String requestId;
	private String status;
	private String statusDescription;
	private String httpStatusCode;
	private String httpStatusDescription;
	private String remainingLimit;
	private String denialCode;
	private String denialDescription;
	private String policyNumber;
	private String policyClass;
	private String policyBenefit;
	private BigDecimal benefitLimitValue;
	private String benefitLimitCurrency;
	private String benefitRemainingLimitCurrency;
	private String memberId;
	private List<DrugListModel> drugList;

	private String patientShareCurrency;
	private String payerShareCurrency;
	private String patientShare;
	private String payerShare;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getRemainingLimit() {
		return remainingLimit;
	}

	public void setRemainingLimit(String remainingLimit) {
		this.remainingLimit = remainingLimit;
	}

	public String getDenialCode() {
		return denialCode;
	}

	public void setDenialCode(String denialCode) {
		this.denialCode = denialCode;
	}

	public String getDenialDescription() {
		return denialDescription;
	}

	public void setDenialDescription(String denialDescription) {
		this.denialDescription = denialDescription;
	}

	public String getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(String httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getHttpStatusDescription() {
		return httpStatusDescription;
	}

	public void setHttpStatusDescription(String httpStatusDescription) {
		this.httpStatusDescription = httpStatusDescription;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public String getPolicyBenefit() {
		return policyBenefit;
	}

	public BigDecimal getBenefitLimitValue() {
		return benefitLimitValue;
	}

	public String getBenefitLimitCurrency() {
		return benefitLimitCurrency;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public void setPolicyBenefit(String policyBenefit) {
		this.policyBenefit = policyBenefit;
	}

	public void setBenefitLimitValue(BigDecimal benefitLimitValue) {
		this.benefitLimitValue = benefitLimitValue;
	}

	public void setBenefitLimitCurrency(String benefitLimitCurrency) {
		this.benefitLimitCurrency = benefitLimitCurrency;
	}

	public String getPolicyClass() {
		return policyClass;
	}

	public void setPolicyClass(String policyClass) {
		this.policyClass = policyClass;
	}

	public String getBenefitRemainingLimitCurrency() {
		return benefitRemainingLimitCurrency;
	}

	public void setBenefitRemainingLimitCurrency(String benefitRemainingLimitCurrency) {
		this.benefitRemainingLimitCurrency = benefitRemainingLimitCurrency;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public List<DrugListModel> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<DrugListModel> drugList) {
		this.drugList = drugList;
	}

	public String getPatientShareCurrency() {
		return patientShareCurrency;
	}

	public void setPatientShareCurrency(String patientShareCurrency) {
		this.patientShareCurrency = patientShareCurrency;
	}

	public String getPayerShareCurrency() {
		return payerShareCurrency;
	}

	public void setPayerShareCurrency(String payerShareCurrency) {
		this.payerShareCurrency = payerShareCurrency;
	}

	public String getPatientShare() {
		return patientShare;
	}

	public void setPatientShare(String patientShare) {
		this.patientShare = patientShare;
	}

	public String getPayerShare() {
		return payerShare;
	}

	public void setPayerShare(String payerShare) {
		this.payerShare = payerShare;
	}

	public PolicyResponseModel() {
		super();
	}
}
