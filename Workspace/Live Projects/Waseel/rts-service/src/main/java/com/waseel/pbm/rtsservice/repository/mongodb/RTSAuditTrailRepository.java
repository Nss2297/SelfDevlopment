package com.waseel.pbm.rtsservice.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.waseel.pbm.rtsservice.persist.mongodb.RTSAuditTrail;

public interface RTSAuditTrailRepository extends MongoRepository<RTSAuditTrail, String>{

}
