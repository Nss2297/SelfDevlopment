package com.waseel.drugformulary.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.waseel.drugformulary.persist.mongodb.DrugFormularyAuditTrail;

public interface DrugFormularyAuditTrailRepository extends MongoRepository<DrugFormularyAuditTrail, String> {

}
