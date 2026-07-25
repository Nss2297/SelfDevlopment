package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DRUG_FORMULARY_POLICY_ASSOCIATION database table.
 * 
 */
@Entity
@Table(name="DRUG_FORMULARY_POLICY_ASSOCIATION")
@NamedQuery(name="DrugFormularyPolicyAssociation.findAll", query="SELECT d FROM DrugFormularyPolicyAssociation d")
public class DrugFormularyPolicyAssociation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DRUG_FORMULARY_ASSOCIATION_ID")
	private long drugFormularyAssociationId;

	@Column(name="IS_ENABLED")
	private String isEnabled;

	//bi-directional many-to-one association to DrugFormularyMetadata
	@ManyToOne
	@JoinColumn(name="FORMULARY_ID")
	private DrugFormularyMetadata drugFormularyMetadata;

	//bi-directional many-to-one association to MemberPolicyAssociation
	@ManyToOne
	@JoinColumn(name="MEMBER_POLICY_ASSOCIATION_ID")
	private MemberPolicyAssociation memberPolicyAssociation;

	//bi-directional many-to-one association to PolicyClass
	@ManyToOne
	@JoinColumn(name="POLICY_CLASS_ID")
	private PolicyClass policyClass;

	//bi-directional many-to-one association to PolicyInformation
	@ManyToOne
	@JoinColumn(name="POLICY_INFORMATION_ID")
	private PolicyInformation policyInformation;

	public DrugFormularyPolicyAssociation() {
	}

	public long getDrugFormularyAssociationId() {
		return this.drugFormularyAssociationId;
	}

	public void setDrugFormularyAssociationId(long drugFormularyAssociationId) {
		this.drugFormularyAssociationId = drugFormularyAssociationId;
	}

	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	public DrugFormularyMetadata getDrugFormularyMetadata() {
		return this.drugFormularyMetadata;
	}

	public void setDrugFormularyMetadata(DrugFormularyMetadata drugFormularyMetadata) {
		this.drugFormularyMetadata = drugFormularyMetadata;
	}

	public MemberPolicyAssociation getMemberPolicyAssociation() {
		return this.memberPolicyAssociation;
	}

	public void setMemberPolicyAssociation(MemberPolicyAssociation memberPolicyAssociation) {
		this.memberPolicyAssociation = memberPolicyAssociation;
	}

	public PolicyClass getPolicyClass() {
		return this.policyClass;
	}

	public void setPolicyClass(PolicyClass policyClass) {
		this.policyClass = policyClass;
	}

	public PolicyInformation getPolicyInformation() {
		return this.policyInformation;
	}

	public void setPolicyInformation(PolicyInformation policyInformation) {
		this.policyInformation = policyInformation;
	}

}