package com.waseel.pbm.dssservice.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.waseel.pbm.dssservice.persist.mongodb.DSSAuditTrail;

public interface DSSAuditTrailRepository extends MongoRepository<DSSAuditTrail, String>{

}
