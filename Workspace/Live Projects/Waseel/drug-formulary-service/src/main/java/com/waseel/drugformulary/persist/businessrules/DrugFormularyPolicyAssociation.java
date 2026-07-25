package com.waseel.drugformulary.persist.businessrules;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "DRUG_FORMULARY_POLICY_ASSOCIATION", schema = "PBM_BUSINESS_RULES")
public class DrugFormularyPolicyAssociation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "Seq")
	@SequenceGenerator(name = "Seq", sequenceName = "DRUG_FORMULARY_POLICY_ASSOCIATION_SEQ", allocationSize = 0)
	@Column(name = "DRUG_FORMULARY_ASSOCIATION_ID", nullable = false, updatable = false)
	private Long drugFormularyAssociationId;

	@Column(name = "FORMULARY_ID", nullable = false,unique = true)
	private Long formularyId;

	@Column(name = "POLICY_INFORMATION_ID",unique = true)
	private Long policyInformationId;

	@Column(name = "POLICY_CLASS_ID",unique = true)
	private Long policyClassId;

	@Column(name = "MEMBER_POLICY_ASSOCIATION_ID",unique = true)
	private Long memberPolicyAssociationId;

	@Column(name = "IS_ENABLED", columnDefinition = "CHAR(1) default ('1')")
	private Boolean isEnabled = true;

	public Long getDrugFormularyAssociationId() {
		return drugFormularyAssociationId;
	}

	public void setDrugFormularyAssociationId(Long drugFormularyAssociationId) {
		this.drugFormularyAssociationId = drugFormularyAssociationId;
	}

	public Long getFormularyId() {
		return formularyId;
	}

	public void setFormularyId(Long formularyId) {
		this.formularyId = formularyId;
	}

	public Long getPolicyInformationId() {
		return policyInformationId;
	}

	public void setPolicyInformationId(Long policyInformationId) {
		this.policyInformationId = policyInformationId;
	}

	public Long getPolicyClassId() {
		return policyClassId;
	}

	public void setPolicyClassId(Long policyClassId) {
		this.policyClassId = policyClassId;
	}

	public Long getMemberPolicyAssociationId() {
		return memberPolicyAssociationId;
	}

	public void setMemberPolicyAssociationId(Long memberPolicyAssociationId) {
		this.memberPolicyAssociationId = memberPolicyAssociationId;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
