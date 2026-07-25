package com.waseel.pbm.idfvalidationservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.idfvalidationservice.persist.ScreeningModuleAuditTrail;

@Repository
public interface ScreeningModuleAuditTrailRepository extends CrudRepository<ScreeningModuleAuditTrail, String> {

}
