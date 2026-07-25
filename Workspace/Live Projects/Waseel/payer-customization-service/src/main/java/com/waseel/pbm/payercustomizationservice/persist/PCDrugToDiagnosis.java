package com.waseel.pbm.payercustomizationservice.persist;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "PCDrugToDiagnosis", schema = "MDSS")
public class PCDrugToDiagnosis implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id", nullable = false)
    private Long seqId;

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

    @Column(name = "LastUpdatedDateTime")
    private Timestamp lastUpdatedDateTime;

    @Column(name = "RejectionCategory")
    private String rejectionCategory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BatchId", nullable = false, insertable = false, updatable = false)
    private CustomizationBatch batch;
    
    @Column(name = "ScientificCode")
	private String ScientificCode;
	
	
	public String getScientificCode() {
		return ScientificCode;
	}

	public void setScientificCode(String scientificCode) {
		ScientificCode = scientificCode;
	}

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

    public CustomizationBatch getBatch() {
        return batch;
    }

    public void setBatch(CustomizationBatch batch) {
        this.batch = batch;
    }

    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
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
}
