package com.waseel.pbm.fdbvalidationservice.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.fdbvalidationservice.persist.mongodb.FDBAuditTrail;

@Repository
public interface FDBAuditTrailRepository extends MongoRepository<FDBAuditTrail, String> {

}
