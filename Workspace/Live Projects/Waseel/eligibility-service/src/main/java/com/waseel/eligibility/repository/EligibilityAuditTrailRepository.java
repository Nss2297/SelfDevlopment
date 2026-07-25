package com.waseel.eligibility.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.waseel.eligibility.persist.EligibilityAuditTrail;

public interface EligibilityAuditTrailRepository extends MongoRepository<EligibilityAuditTrail, String> {

}
