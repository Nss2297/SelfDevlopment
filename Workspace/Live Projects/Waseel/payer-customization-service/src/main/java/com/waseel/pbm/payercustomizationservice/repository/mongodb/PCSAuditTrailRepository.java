package com.waseel.pbm.payercustomizationservice.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.payercustomizationservice.persist.mongodb.PCSAuditTrail;

@Repository
public interface PCSAuditTrailRepository extends MongoRepository<PCSAuditTrail, String> {

}
