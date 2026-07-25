package com.waseel.prescription.persist.prescriptionservice;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER_POLICY_USAGE", schema = "PRESCRIPTION_SERVICE")
public class MemberPolicyUsage implements Serializable {

	private static final long serialVersionUID = -1818254004699892876L;

	@Id
	@GeneratedValue(generator = "PsMemberPolicyUsageSeq")
	@SequenceGenerator(name = "PsMemberPolicyUsageSeq", sequenceName = "PS_MEMBER_POLICY_USAGE_SEQ", allocationSize = 0, initialValue = 1)
	@Column(name = "MEMBER_POLICY_USAGE_ID", unique = true, nullable = false, precision = 0)
	private Long memberPolicyUsageId;

	@Column(name = "PAYER_ID", nullable = false, length = 20)
	private String payerId;

	@Column(name = "PROVIDER_ID", nullable = false, length = 20)
	private String providerId;

	@Column(name = "MEMBER_ID", nullable = false, length = 50)
	private String memberId;

	@Column(name = "ID_NUMBER", nullable = false)
	private Long idNumber;

	@Column(name = "POLICY_NUMBER", nullable = false, length = 50)
	private String policyNumber;

	@Column(name = "POLICY_CLASS", nullable = false)
	private String policyClass;

	@Column(name = "POLICY_BENEFIT", length = 30)
	private String policyBenefit;

	@Column(name = "BENEFIT_LIMIT_VALUE", nullable = false, precision = 14, scale = 2)
	private BigDecimal benefitLimitValue;

	@Column(name = "BENEFIT_LIMIT_CURRENCY", nullable = false, length = 30)
	private String benefitLimitCurrency;

	@Column(name = "BENEFIT_REMAINING_LIMIT_VALUE", nullable = false, precision = 14, scale = 2)
	private BigDecimal benefitRemainingLimitValue;

	@Column(name = "BENEFIT_REMAINING_LIMIT_CURRENCY", nullable = false, length = 30)
	private String benefitRemainingLimitCurrency;

	@Column(name = "EPRESCRIPTION_REFERENCE_NUMBER", nullable = false, length = 100)
	private String ePrescriptionReferenceNumber;

	@Column(name = "EPRESCRIPTION_STATUS", nullable = false, length = 50)
	private String eprescriptionStatus;

	public Long getMemberPolicyUsageId() {
		return memberPolicyUsageId;
	}

	public String getPayerId() {
		return payerId;
	}

	public String getProviderId() {
		return providerId;
	}

	public String getMemberId() {
		return memberId;
	}

	public Long getIdNumber() {
		return idNumber;
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

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public String getEprescriptionStatus() {
		return eprescriptionStatus;
	}

	public void setMemberPolicyUsageId(Long memberPolicyUsageId) {
		this.memberPolicyUsageId = memberPolicyUsageId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public void setIdNumber(Long idNumber) {
		this.idNumber = idNumber;
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

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public void setEprescriptionStatus(String eprescriptionStatus) {
		this.eprescriptionStatus = eprescriptionStatus;
	}

	public BigDecimal getBenefitRemainingLimitValue() {
		return benefitRemainingLimitValue;
	}

	public void setBenefitRemainingLimitValue(BigDecimal benefitRemainingLimitValue) {
		this.benefitRemainingLimitValue = benefitRemainingLimitValue;
	}

	public String getBenefitRemainingLimitCurrency() {
		return benefitRemainingLimitCurrency;
	}

	public void setBenefitRemainingLimitCurrency(String benefitRemainingLimitCurrency) {
		this.benefitRemainingLimitCurrency = benefitRemainingLimitCurrency;
	}

	public String getPolicyClass() {
		return policyClass;
	}

	public void setPolicyClass(String policyClass) {
		this.policyClass = policyClass;
	}

	public MemberPolicyUsage() {
		super();
	}

	public MemberPolicyUsage(String payerId, String providerId, String memberId, Long idNumber, String policyNumber,
			String policyClass, String policyBenefit, BigDecimal benefitLimitValue, String benefitLimitCurrency,
			BigDecimal benefitRemainingLimitValue, String benefitRemainingLimitCurrency,
			String ePrescriptionReferenceNumber, String eprescriptionStatus) {
		super();
		this.payerId = payerId;
		this.providerId = providerId;
		this.memberId = memberId;
		this.idNumber = idNumber;
		this.policyNumber = policyNumber;
		this.policyClass = policyClass;
		this.policyBenefit = policyBenefit;
		this.benefitLimitValue = benefitLimitValue;
		this.benefitLimitCurrency = benefitLimitCurrency;
		this.benefitRemainingLimitValue = benefitRemainingLimitValue;
		this.benefitRemainingLimitCurrency = benefitRemainingLimitCurrency;
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
		this.eprescriptionStatus = eprescriptionStatus;
	}
}