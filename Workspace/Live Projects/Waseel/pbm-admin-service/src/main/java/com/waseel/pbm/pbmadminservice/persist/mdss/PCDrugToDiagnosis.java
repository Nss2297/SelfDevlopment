package com.waseel.pbm.pbmadminservice.persist.mdss;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "PCDrugToDiagnosis", schema = "MDSS")
public class PCDrugToDiagnosis implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "ServiceCode")
    private String serviceCode;

    @Column(name = "IcdCode")
    private String icdCode;

    @Column(name = "PayerId")
    private String payerId;

    @Column(name = "ModuleName")
    private String moduleName;

    @Column(name = "CategoryOfApproval")
    private String categoryOfApproval;

    @Column(name = "ServiceStatus")
    private String serviceStatus;

    @Column(name = "AdditionalRejectionReason")
    private String additionalRejectionReason;

    @Column(name = "RuleId")
    private String ruleId;

    @Column(name = "RejectionCategory")
    private String rejectionCategory;

    @Column(name = "BatchId")
    private Long batchId;

    @Column(name = "LastUpdatedDateTime")
    private Timestamp lastUpdatedDateTime = Timestamp.from(Instant.now());

    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Timestamp getLastUpdatedDateTime() {
        return lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime(Timestamp lastUpdatedDateTime) {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }

    public String getCategoryOfApproval() {
        return categoryOfApproval;
    }

    public void setCategoryOfApproval(String categoryOfApproval) {
        this.categoryOfApproval = categoryOfApproval;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getAdditionalRejectionReason() {
        return additionalRejectionReason;
    }

    public void setAdditionalRejectionReason(String additionalRejectionReason) {
        this.additionalRejectionReason = additionalRejectionReason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getRejectionCategory() {
        return rejectionCategory;
    }

    public void setRejectionCategory(String rejectionCategory) {
        this.rejectionCategory = rejectionCategory;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getIcdCode() {
        return icdCode;
    }

    public void setIcdCode(String icdCode) {
        this.icdCode = icdCode;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @PreUpdate
    protected void preUpdate() {
        this.lastUpdatedDateTime = Timestamp.from(Instant.now());
    }

    public PCDrugToDiagnosis(String serviceCode, String icdCode, String payerId,
                             String moduleName, String categoryOfApproval,
                             String serviceStatus, String additionalRejectionReason,
                             String rejectionCategory, String updatedBy) {
        this.serviceCode = serviceCode;
        this.icdCode = icdCode;
        this.payerId = payerId;
        this.moduleName = moduleName;
        this.categoryOfApproval = categoryOfApproval;
        this.serviceStatus = serviceStatus;
        this.additionalRejectionReason = additionalRejectionReason;
        this.rejectionCategory = rejectionCategory;
        this.lastUpdatedBy = updatedBy;
    }

    public PCDrugToDiagnosis() {
    }
}
