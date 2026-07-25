package com.waseel.pbm.idfvalidationservice.persist.mongodb;

import com.waseel.pbm.idfvalidationservice.model.DssRequest;
import com.waseel.pbm.idfvalidationservice.model.DssResponse;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Document(value = "IDFAuditTrail")
public class IDFAuditTrail {

    @Id
    @Field(name = "DocumentId")
    private String documentId = UUID.randomUUID().toString();

    @Field(name = "RequestId")
    public String requestId;

    @Field(name = "PayerId")
    public String payerId;

    @Field(name = "SubmissionDateTime")
    public Date submissionDateTime;

    @Field(name = "DssRequest")
    public DssRequest dssRequest;

    @Field(name = "DssResponse")
    public DssResponse dssResponse;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public Date getSubmissionDateTime() {
        return submissionDateTime;
    }

    public void setSubmissionDateTime(Date submissionDateTime) {
        this.submissionDateTime = submissionDateTime;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public DssRequest getDssRequest() {
        return dssRequest;
    }

    public void setDssRequest(DssRequest dssRequest) {
        this.dssRequest = dssRequest;
    }

    public DssResponse getDssResponse() {
        return dssResponse;
    }

    public void setDssResponse(DssResponse dssResponse) {
        this.dssResponse = dssResponse;
    }

}
