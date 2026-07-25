package com.waseel.drugexclusionvalidationservice.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.waseel.drugexclusionvalidationservice.persist.mongodb.DrugExclusionAuditTrail;

public interface SpecialityExclusionAuditTrailRepository extends MongoRepository<DrugExclusionAuditTrail, String> {

}
