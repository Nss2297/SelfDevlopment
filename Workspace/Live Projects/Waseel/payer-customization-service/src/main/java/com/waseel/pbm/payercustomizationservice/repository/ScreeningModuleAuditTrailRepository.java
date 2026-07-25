package com.waseel.pbm.payercustomizationservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.payercustomizationservice.persist.ScreeningModuleAuditTrail;

@Repository
public interface ScreeningModuleAuditTrailRepository extends CrudRepository<ScreeningModuleAuditTrail, String> {

}
