package com.waseel.prescription.persist.prescriptionservice;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.persistence.*;

@Entity
@Table(name = "ELIGIBLE_DSS_POLICY", schema = "PRESCRIPTION_SERVICE")
public class EligibleDssPolicy implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "seqEligibleDssPolicyGen")
    @SequenceGenerator(name = "seqEligibleDssPolicyGen", sequenceName = "SEQ_ELIGIBLE_DSS_POLICY", allocationSize = 1)
    @Column(name = "ELIGIBLE_DSS_POLICY_ID", unique = true, nullable = false)
    private Long eligibleDssPolicyId;

    @Column(name = "POLICY_NUMBER", nullable = false, length = 50)
    private String policyNumber;

    @Column(name = "POLICY_HOLDER_NAME", nullable = false, length = 100)
    private String policyHolderName;

    @Column(name = "POLICY_CLASS", nullable = false, length = 50)
    private String policyClass;

    @Column(name = "IS_ENABLED", nullable = false, length = 20)
    private String isEnabled;

    @Column(name = "UPDATED_BY", nullable = false, length = 100)
    private String updatedBy;

    @Column(name = "LAST_UPDATED_DATE", nullable = false)
    private Timestamp lastUpdatedDate;

    public EligibleDssPolicy() {
    }

    public Long getEligibleDssPolicyId() {
        return eligibleDssPolicyId;
    }

    public void setEligibleDssPolicyId(Long eligibleDssPolicyId) {
        this.eligibleDssPolicyId = eligibleDssPolicyId;
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

    public String getPolicyClass() {
        return policyClass;
    }

    public void setPolicyClass(String policyClass) {
        this.policyClass = policyClass;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
