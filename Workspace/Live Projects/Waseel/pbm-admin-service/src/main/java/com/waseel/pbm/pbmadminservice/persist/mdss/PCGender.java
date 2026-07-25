package com.waseel.pbm.pbmadminservice.persist.mdss;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "PCGender", schema = "MDSS")
public class PCGender implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverride(name = "serviceCode", column = @Column(name = "ServiceCode"))
    @AttributeOverride(name = "payerId", column = @Column(name = "PayerId"))
    @AttributeOverride(name = "moduleName", column = @Column(name = "ModuleName"))
    private PCAgeGenderId id;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "ServiceStatus")
    private String serviceStatus;

    @Column(name = "AdditionalRejectionReason")
    private String additionalRejectionReason;

    @Column(name = "RuleId")
    private String ruleId;

    @Column(name = "LastUpdatedDateTime")
    private Timestamp lastUpdatedDateTime;

    @Column(name = "Id")
    @GeneratedValue(generator = "PCGender_SEQ")
	@SequenceGenerator(name = "PCGender_SEQ", sequenceName = "PCGender_Seq_Id", allocationSize = 0, initialValue = 1)
    private Long seqId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BatchId", nullable = false, insertable = false, updatable = false)
    private CustomizationBatch batch;

    public PCAgeGenderId getId() {
        return id;
    }

    public void setId(PCAgeGenderId id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
}
