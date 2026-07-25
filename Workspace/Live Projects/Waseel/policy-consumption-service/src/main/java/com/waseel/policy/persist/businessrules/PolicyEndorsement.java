package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the POLICY_ENDORSEMENT database table.
 * 
 */
@Entity
@Table(name="POLICY_ENDORSEMENT")
@NamedQuery(name="PolicyEndorsement.findAll", query="SELECT p FROM PolicyEndorsement p")
public class PolicyEndorsement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="POLICY_ENDORSEMENT_ID")
	private long policyEndorsementId;

	@Temporal(TemporalType.DATE)
	@Column(name="ENDORSEMENT_DATE")
	private Date endorsementDate;

	@Column(name="ENDORSEMENT_MESSAGE")
	private String endorsementMessage;

	@Column(name="ENDORSEMENT_NUMBER")
	private String endorsementNumber;

	@Column(name="ENDORSEMENT_TYPE")
	private String endorsementType;

	//bi-directional many-to-one association to PolicyInformation
	@ManyToOne
	@JoinColumn(name="POLICY_INFORMATION_ID")
	private PolicyInformation policyInformation;

	public PolicyEndorsement() {
	}

	public long getPolicyEndorsementId() {
		return this.policyEndorsementId;
	}

	public void setPolicyEndorsementId(long policyEndorsementId) {
		this.policyEndorsementId = policyEndorsementId;
	}

	public Date getEndorsementDate() {
		return this.endorsementDate;
	}

	public void setEndorsementDate(Date endorsementDate) {
		this.endorsementDate = endorsementDate;
	}

	public String getEndorsementMessage() {
		return this.endorsementMessage;
	}

	public void setEndorsementMessage(String endorsementMessage) {
		this.endorsementMessage = endorsementMessage;
	}

	public String getEndorsementNumber() {
		return this.endorsementNumber;
	}

	public void setEndorsementNumber(String endorsementNumber) {
		this.endorsementNumber = endorsementNumber;
	}

	public String getEndorsementType() {
		return this.endorsementType;
	}

	public void setEndorsementType(String endorsementType) {
		this.endorsementType = endorsementType;
	}

	public PolicyInformation getPolicyInformation() {
		return this.policyInformation;
	}

	public void setPolicyInformation(PolicyInformation policyInformation) {
		this.policyInformation = policyInformation;
	}

}