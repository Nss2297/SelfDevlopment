package com.waseel.emailservice.persist.mongodb;

import com.waseel.emailservice.model.EmailDetails;
import com.waseel.emailservice.response.EmailSenderResponseModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Document(value = "EmailAuditTrail")
public class EmailAuditTrail {

    @Id
    @Field(name = "DocumentId")
    private String documentId = UUID.randomUUID().toString();

    @Field(name = "DateTime")
    public Date dateTime;

    @Field(name = "EmailDetailsRequestModel")
    private EmailDetails emailDetails;

    @Field(name = "EmailSenderResponseModel")
    private EmailSenderResponseModel responseModel;


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public EmailDetails getEmailDetails() {
        return emailDetails;
    }

    public void setEmailDetails(EmailDetails emailDetails) {
        this.emailDetails = emailDetails;
    }

    public EmailSenderResponseModel getResponseModel() {
        return responseModel;
    }

    public void setResponseModel(EmailSenderResponseModel responseModel) {
        this.responseModel = responseModel;
    }
}
