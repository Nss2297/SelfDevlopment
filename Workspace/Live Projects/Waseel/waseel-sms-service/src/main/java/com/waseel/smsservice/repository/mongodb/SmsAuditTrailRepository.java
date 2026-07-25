package com.waseel.smsservice.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.waseel.smsservice.persist.mongodb.SmsAuditTrail;

@Repository
public interface SmsAuditTrailRepository extends MongoRepository<SmsAuditTrail, String> {

}
