package com.waseel.pbm.payercustomizationservice.persist;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "CustomizationAuditTrail", schema = "MDSS")
public class CustomizationAuditTrail {

    @Id
    @GeneratedValue(generator = "PCCustomizationAuditTrailSeq")
    @SequenceGenerator(name = "PCCustomizationAuditTrailSeq", sequenceName = "PC_CustomizationAuditTrail_Seq", allocationSize = 0, initialValue = 1)
    @Column(name = "Id")
    private Long id;

    @Column(name = "RuleId")
    private String ruleId;

    @Column(name = "RequestId")
    private String requestId;

    @Column(name = "SubmissionDateTime")
    private Timestamp submissionDateTime = Timestamp.from(Instant.now());

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Timestamp getSubmissionDateTime() {
        return submissionDateTime;
    }

    public void setSubmissionDateTime(Timestamp submissionDateTime) {
        this.submissionDateTime = submissionDateTime;
    }
}
