package com.waseel.pbm.rtsservice.repository.mdss;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.rtsservice.persist.mdss.ScreeningModuleAuditTrail;

@Repository
public interface ScreeningModuleAuditTrailRepository extends CrudRepository<ScreeningModuleAuditTrail, String>{

}
