package com.waseel.emailservice.service.management;

import com.waseel.emailservice.model.EmailDetails;
import com.waseel.emailservice.mongodb.EmailAuditTrailRepository;
import com.waseel.emailservice.persist.mongodb.EmailAuditTrail;
import com.waseel.emailservice.response.EmailSenderResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Service
public class AuditLogService {

    private static final Logger log = LoggerFactory.getLogger(AuditLogService.class);

    @Autowired
    private EmailAuditTrailRepository emailAuditTrailRepository;

    public void saveEmailRequestAuditData(EmailDetails emailDetails) {
        try {
            CompletableFuture.runAsync(() ->
                    emailAuditTrailRepository.save(setEmailRequestAuditData(emailDetails, null)));
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public void saveInvalidResponseAuditData(EmailSenderResponseModel responseModel) {
        try {
            CompletableFuture.runAsync(() ->
                    emailAuditTrailRepository.save(setEmailRequestAuditData(null, responseModel)));
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private EmailAuditTrail setEmailRequestAuditData(EmailDetails emailDetails, EmailSenderResponseModel responseModel) {
        EmailAuditTrail audit = new EmailAuditTrail();
        audit.setEmailDetails(emailDetails);
        audit.setDateTime(new Date());
        audit.setResponseModel(responseModel);
        return audit;
    }
}
