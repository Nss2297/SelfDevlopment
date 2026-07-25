package com.waseel.pbmschedulerservice.persist.businessrules;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "POLICY_INFORMATION", schema = "PBM_BUSINESS_RULES")
public class PolicyInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POLICY_INFORMATION_ID", nullable = false, updatable = false)
	private Long policyInformationId;

	@Column(name = "PAYER_ID", nullable = false, length = 20, unique = true)
	private String payerId;

	@Column(name = "TPA_ID", length = 20)
	private String tpaId;

	@Column(name = "POLICY_NUMBER", nullable = false, length = 50, unique = true)
	private String policyNumber;

	@Column(name = "POLICY_HOLDER_NAME", nullable = false, length = 250)
	private String policyHolderName;

	@Column(name = "POLICY_TYPE", nullable = false, length = 250)
	private String policyType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ISSUE_DATE", nullable = false)
	private Date issueDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE", nullable = false)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE", nullable = false)
	private Date endDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_DATE", nullable = false)
	private Date lastUpdateDate;

	@Column(name = "COVERAGE", length = 2500)
	private String coverage;

	@Column(name = "EXCLUSION", length = 2500)
	private String exclusion;

	@Column(name = "COMMENTS", length = 3000)
	private String comments;

	public Long getPolicyInformationId() {
		return policyInformationId;
	}

	public void setPolicyInformationId(Long policyInformationId) {
		this.policyInformationId = policyInformationId;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getTpaId() {
		return tpaId;
	}

	public void setTpaId(String tpaId) {
		this.tpaId = tpaId;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getPolicyHolderName() {
		return policyHolderName;
	}

	public void setPolicyHolderName(String policyHolderName) {
		this.policyHolderName = policyHolderName;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public String getExclusion() {
		return exclusion;
	}

	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
