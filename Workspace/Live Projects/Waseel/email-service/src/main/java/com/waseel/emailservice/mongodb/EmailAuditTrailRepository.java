package com.waseel.emailservice.mongodb;

import com.waseel.emailservice.persist.mongodb.EmailAuditTrail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailAuditTrailRepository extends MongoRepository<EmailAuditTrail, String> {

}
