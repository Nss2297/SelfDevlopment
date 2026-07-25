package com.waseel.pbm.payercustomizationservice.persist.mongodb;

import com.waseel.pbm.payercustomizationservice.model.DssResponse;
import com.waseel.pbm.payercustomizationservice.model.PCRequest;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Document(value = "PCSAuditTrail")
@Data
public class PCSAuditTrail {

    @Id
    @Field(name = "DocumentId")
    private String documentId = UUID.randomUUID().toString();

    @Field(name = "RequestId")
    public String requestId;

    @Field(name = "PayerId")
    public String payerId;

    @Field(name = "SubmissionDateTime")
    public Date submissionDateTime;

    @Field(name = "PCSRequest")
    public PCRequest pcsRequest;

    @Field(name = "PCSResponse")
    public DssResponse pcsResponse;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

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

    public PCRequest getPcsRequest() {
        return pcsRequest;
    }

    public void setPcsRequest(PCRequest pcsRequest) {
        this.pcsRequest = pcsRequest;
    }

    public DssResponse getPcsResponse() {
        return pcsResponse;
    }

    public void setPcsResponse(DssResponse pcsResponse) {
        this.pcsResponse = pcsResponse;
    }
}
