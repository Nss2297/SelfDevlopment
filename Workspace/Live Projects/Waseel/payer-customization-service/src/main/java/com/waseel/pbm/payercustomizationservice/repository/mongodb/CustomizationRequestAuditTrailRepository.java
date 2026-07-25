package com.waseel.pbm.payercustomizationservice.repository.mongodb;

import com.waseel.pbm.payercustomizationservice.persist.mongodb.CustomizationRequestAuditTrail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomizationRequestAuditTrailRepository extends MongoRepository<CustomizationRequestAuditTrail, String> {
}
