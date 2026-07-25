package com.waseel.prescription.model.prescription;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.prescription.model.common.MemberInfoModel;
import com.waseel.prescription.model.common.PhysicianModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayerMemberPhysicianInfoModel {

	private String status;
	private String statusDescription;
	private MemberInfoModel memberInfoModel;
	private PhysicianModel physicianModel;
	private String payerName;
	private String payerId;
	private BigDecimal totalNet;
	private BigDecimal totalPatientShare;
	private BigDecimal totalPatientShareVatAmountValue = BigDecimal.ZERO;
	private String totalPatientShareVatAmountCurrency = "SAR";
	private String caseType;
	private String providerId;
	private String providerName;

	public BigDecimal getTotalNet() {
		return totalNet;
	}

	public void setTotalNet(BigDecimal totalNet) {
		this.totalNet = totalNet;
	}

	public BigDecimal getTotalPatientShare() {
		return totalPatientShare;
	}

	public void setTotalPatientShare(BigDecimal totalPatientShare) {
		this.totalPatientShare = totalPatientShare;
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

	public MemberInfoModel getMemberInfoModel() {
		return memberInfoModel;
	}

	public void setMemberInfoModel(MemberInfoModel memberInfoModel) {
		this.memberInfoModel = memberInfoModel;
	}

	public PhysicianModel getPhysicianModel() {
		return physicianModel;
	}

	public void setPhysicianModel(PhysicianModel physicianModel) {
		this.physicianModel = physicianModel;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getProviderId() {
		return providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public BigDecimal getTotalPatientShareVatAmountValue() {
		return totalPatientShareVatAmountValue;
	}

	public void setTotalPatientShareVatAmountValue(BigDecimal totalPatientShareVatAmountValue) {
		this.totalPatientShareVatAmountValue = totalPatientShareVatAmountValue;
	}

	public String getTotalPatientShareVatAmountCurrency() {
		return totalPatientShareVatAmountCurrency;
	}

	public void setTotalPatientShareVatAmountCurrency(String totalPatientShareVatAmountCurrency) {
		this.totalPatientShareVatAmountCurrency = totalPatientShareVatAmountCurrency;
	}
}
