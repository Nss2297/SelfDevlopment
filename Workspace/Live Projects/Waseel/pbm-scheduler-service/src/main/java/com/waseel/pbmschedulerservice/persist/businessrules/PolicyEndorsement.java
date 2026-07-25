package com.waseel.pbmschedulerservice.persist.businessrules;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POLICY_ENDORSEMENT", schema = "PBM_BUSINESS_RULES")
public class PolicyEndorsement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POLICY_ENDORSEMENT_ID", nullable = false, updatable = false)
	private Long policyEndorsementId;

	@Column(name = "POLICY_INFORMATION_ID", nullable = false)
	private Long policyInformationId;

	@Column(name = "ENDORSEMENT_NUMBER", length = 50)
	private String endorsementNumber;

	@Column(name = "ENDORSEMENT_DATE")
	private Date endorsementDate;

	@Column(name = "ENDORSEMENT_TYPE", length = 100)
	private String endorsementType;

	@Column(name = "ENDORSEMENT_MESSAGE", length = 1000)
	private String endorsementMessage;

	public Long getPolicyEndorsementId() {
		return policyEndorsementId;
	}

	public void setPolicyEndorsementId(Long policyEndorsementId) {
		this.policyEndorsementId = policyEndorsementId;
	}

	public Long getPolicyInformationId() {
		return policyInformationId;
	}

	public void setPolicyInformationId(Long policyInformationId) {
		this.policyInformationId = policyInformationId;
	}

	public String getEndorsementNumber() {
		return endorsementNumber;
	}

	public void setEndorsementNumber(String endorsementNumber) {
		this.endorsementNumber = endorsementNumber;
	}

	public Date getEndorsementDate() {
		return endorsementDate;
	}

	public void setEndorsementDate(Date endorsementDate) {
		this.endorsementDate = endorsementDate;
	}

	public String getEndorsementType() {
		return endorsementType;
	}

	public void setEndorsementType(String endorsementType) {
		this.endorsementType = endorsementType;
	}

	public String getEndorsementMessage() {
		return endorsementMessage;
	}

	public void setEndorsementMessage(String endorsementMessage) {
		this.endorsementMessage = endorsementMessage;
	}
}
