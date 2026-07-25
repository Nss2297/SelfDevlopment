package com.waseel.pbm.idfvalidationservice.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.idfvalidationservice.persist.mongodb.IDFAuditTrail;

@Repository
public interface IDFAuditTrailRepository extends MongoRepository<IDFAuditTrail, String> {

}
