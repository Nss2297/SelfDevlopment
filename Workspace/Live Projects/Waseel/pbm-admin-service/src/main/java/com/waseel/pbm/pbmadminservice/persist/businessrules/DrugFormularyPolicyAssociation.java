package com.waseel.pbm.pbmadminservice.persist.businessrules;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DRUG_FORMULARY_POLICY_ASSOCIATION", schema = "PBM_BUSINESS_RULES")
@SQLDelete(sql = "UPDATE DRUG_FORMULARY_POLICY_ASSOCIATION SET IS_ENABLED = '0' WHERE DRUG_FORMULARY_ASSOCIATION_ID=?")
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

    @Column(name = "FORMULARY_ID", nullable = false, unique = true)
    private Long formularyId;

    @Column(name = "POLICY_INFORMATION_ID", unique = true)
    private Long policyInformationId;

    @Column(name = "POLICY_CLASS_ID", unique = true)
    private Long policyClassId;

    @Column(name = "MEMBER_POLICY_ASSOCIATION_ID", unique = true)
    private Long memberPolicyAssociationId;

    @Column(name = "IS_ENABLED", columnDefinition = "CHAR(1) default ('1')")
    private Boolean isEnabled = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POLICY_INFORMATION_ID", referencedColumnName = "POLICY_INFORMATION_ID", insertable = false, updatable = false)
    private PolicyInformation policyInformation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POLICY_CLASS_ID", referencedColumnName = "POLICY_CLASS_ID", insertable = false, updatable = false)
    private PolicyClasses policyClasses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_POLICY_ASSOCIATION_ID", referencedColumnName = "MEMBER_POLICY_ASSOCIATION_ID", insertable = false, updatable = false)
    private MemberPolicyAssociation memberPolicyAssociation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FORMULARY_ID", referencedColumnName = "FORMULARY_ID", insertable = false, updatable = false)
    private DrugFormularyMetadata drugFormularyMetadata;

    public DrugFormularyMetadata getDrugFormularyMetadata() {
        return drugFormularyMetadata;
    }

    public void setDrugFormularyMetadata(DrugFormularyMetadata drugFormularyMetadata) {
        this.drugFormularyMetadata = drugFormularyMetadata;
    }

    public MemberPolicyAssociation getMemberPolicyAssociation() {
        return memberPolicyAssociation;
    }

    public void setMemberPolicyAssociation(MemberPolicyAssociation memberPolicyAssociation) {
        this.memberPolicyAssociation = memberPolicyAssociation;
    }

    public PolicyClasses getPolicyClasses() {
        return policyClasses;
    }

    public void setPolicyClasses(PolicyClasses policyClasses) {
        this.policyClasses = policyClasses;
    }

    public PolicyInformation getPolicyInformation() {
        return policyInformation;
    }

    public void setPolicyInformation(PolicyInformation policyInformation) {
        this.policyInformation = policyInformation;
    }

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

    public DrugFormularyPolicyAssociation() {
        super();
    }

    public DrugFormularyPolicyAssociation(Long drugFormularyAssociationId, Long formularyId, Long policyInformationId,
                                          Long policyClassId, Long memberPolicyAssociationId, Boolean isEnabled) {
        super();
        this.drugFormularyAssociationId = drugFormularyAssociationId;
        this.formularyId = formularyId;
        this.policyInformationId = policyInformationId;
        this.policyClassId = policyClassId;
        this.memberPolicyAssociationId = memberPolicyAssociationId;
        this.isEnabled = isEnabled;
    }
}
