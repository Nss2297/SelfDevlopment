package com.waseel.brservice.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.waseel.brservice.persist.mongodb.BusinessRuleAuditTrail;

public interface BusinessRuleAuditTrailRepository extends MongoRepository<BusinessRuleAuditTrail, String> {

}
