package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the PRESCRIPTION_METADATA database table.
 * 
 */
@Entity
@Table(name = "PRESCRIPTION_METADATA")
@NamedQuery(name = "PrescriptionMetadata.findAll", query = "SELECT p FROM PrescriptionMetadata p")
public class PrescriptionMetadata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PrescMetaDataSeq")
	@SequenceGenerator(name = "PrescMetaDataSeq", sequenceName = "PRESCRIPTION_META_SEQ", allocationSize = 0, initialValue = 1)
	@Column(name = "ID")
	private long id;

	@Column(name = "BENEFIT_LIMIT_CURR")
	private String benefitLimitCurr;

	@Column(name = "BENEFIT_LIMIT_VALUE")
	private BigDecimal benefitLimitValue;

	@Column(name = "PATIENT_SHARE")
	private BigDecimal patientShare;

	@Column(name = "PAYER_SHARE")
	private BigDecimal payerShare;

	@Column(name = "POLICY_NUMBER")
	private BigDecimal policyNumber;

	@Column(name = "REMAINING_LIMIT")
	private BigDecimal remainingLimit;

	@Column(name = "REQUEST_ID")
	private String requestId;

	// bi-directional many-to-one association to MemberBenefitAssoication
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_BENEFIT_ASSOCIATION_ID")
	private MemberBenefitAssoication memberBenefitAssoication;

	@Column(name = "ACTIVE_PRESCRIPTION")
	private String activePrescription = "1";

	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;

	@Column(name = "PRESCRIPTION_VALUE")
	private BigDecimal prescriptionValue;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBenefitLimitCurr() {
		return this.benefitLimitCurr;
	}

	public void setBenefitLimitCurr(String benefitLimitCurr) {
		this.benefitLimitCurr = benefitLimitCurr;
	}

	public BigDecimal getBenefitLimitValue() {
		return this.benefitLimitValue;
	}

	public void setBenefitLimitValue(BigDecimal benefitLimitValue) {
		this.benefitLimitValue = benefitLimitValue;
	}

	public BigDecimal getPatientShare() {
		return this.patientShare;
	}

	public void setPatientShare(BigDecimal patientShare) {
		this.patientShare = patientShare;
	}

	public BigDecimal getPayerShare() {
		return this.payerShare;
	}

	public void setPayerShare(BigDecimal payerShare) {
		this.payerShare = payerShare;
	}

	public BigDecimal getPolicyNumber() {
		return this.policyNumber;
	}

	public void setPolicyNumber(BigDecimal policyNumber) {
		this.policyNumber = policyNumber;
	}

	public BigDecimal getRemainingLimit() {
		return this.remainingLimit;
	}

	public void setRemainingLimit(BigDecimal remainingLimit) {
		this.remainingLimit = remainingLimit;
	}

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public MemberBenefitAssoication getMemberBenefitAssoication() {
		return this.memberBenefitAssoication;
	}

	public void setMemberBenefitAssoication(MemberBenefitAssoication memberBenefitAssoication) {
		this.memberBenefitAssoication = memberBenefitAssoication;
	}

	public String getActivePrescription() {
		return activePrescription;
	}

	public void setActivePrescription(String activePrescription) {
		this.activePrescription = activePrescription;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public BigDecimal getPrescriptionValue() {
		return prescriptionValue;
	}

	public void setPrescriptionValue(BigDecimal prescriptionValue) {
		this.prescriptionValue = prescriptionValue;
	}
}