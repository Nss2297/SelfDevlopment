package com.waseel.pbm.fdbvalidationservice.repository.mdss;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.fdbvalidationservice.persist.mdss.ScreeningModuleAuditTrail;

@Repository
public interface ScreeningModuleAuditTrailRepository extends CrudRepository<ScreeningModuleAuditTrail, String> {
}
