package com.waseel.prescription.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.waseel.prescription.persist.mongodb.PrescriptionAuditTrail;

public interface PrescriptionAuditTrailRepository extends MongoRepository<PrescriptionAuditTrail, String> {

}
