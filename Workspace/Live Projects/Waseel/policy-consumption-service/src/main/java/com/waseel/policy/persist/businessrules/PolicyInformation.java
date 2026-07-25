package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the POLICY_INFORMATION database table.
 * 
 */
@Entity
@Table(name = "POLICY_INFORMATION")
@NamedQuery(name = "PolicyInformation.findAll", query = "SELECT p FROM PolicyInformation p")
public class PolicyInformation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "POLICY_INFORMATION_ID")
	private long policyInformationId;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "COVERAGE")
	private String coverage;

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE")
	private Date endDate;

	@Column(name = "EXCLUSION")
	private String exclusion;

	@Temporal(TemporalType.DATE)
	@Column(name = "ISSUE_DATE")
	private Date issueDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name = "PAYER_ID")
	private String payerId;

	@Column(name = "POLICY_HOLDER_NAME")
	private String policyHolderName;

	@Column(name = "POLICY_NUMBER")
	private String policyNumber;

	@Column(name = "POLICY_TYPE")
	private String policyType;

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "TPA_ID")
	private String tpaId;

	// bi-directional many-to-one association to PolicyEndorsement
	@OneToMany(mappedBy = "policyInformation", fetch = FetchType.LAZY)
	private List<PolicyEndorsement> policyEndorsements;

	public PolicyInformation() {
	}

	public long getPolicyInformationId() {
		return this.policyInformationId;
	}

	public void setPolicyInformationId(long policyInformationId) {
		this.policyInformationId = policyInformationId;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCoverage() {
		return this.coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getExclusion() {
		return this.exclusion;
	}

	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
	}

	public Date getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getPayerId() {
		return this.payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getPolicyHolderName() {
		return this.policyHolderName;
	}

	public void setPolicyHolderName(String policyHolderName) {
		this.policyHolderName = policyHolderName;
	}

	public String getPolicyNumber() {
		return this.policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getPolicyType() {
		return this.policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getTpaId() {
		return this.tpaId;
	}

	public void setTpaId(String tpaId) {
		this.tpaId = tpaId;
	}

	public List<PolicyEndorsement> getPolicyEndorsements() {
		return this.policyEndorsements;
	}

	public void setPolicyEndorsements(List<PolicyEndorsement> policyEndorsements) {
		this.policyEndorsements = policyEndorsements;
	}

	public PolicyEndorsement addPolicyEndorsement(PolicyEndorsement policyEndorsement) {
		getPolicyEndorsements().add(policyEndorsement);
		policyEndorsement.setPolicyInformation(this);

		return policyEndorsement;
	}

	public PolicyEndorsement removePolicyEndorsement(PolicyEndorsement policyEndorsement) {
		getPolicyEndorsements().remove(policyEndorsement);
		policyEndorsement.setPolicyInformation(null);

		return policyEndorsement;
	}

}