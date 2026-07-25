package com.waseel.pbm.payercustomizationservice.repository;

import com.waseel.pbm.payercustomizationservice.persist.CustomizationAuditTrail;
import org.springframework.data.repository.CrudRepository;

public interface CustomizationAuditTrailRepository extends CrudRepository<CustomizationAuditTrail, Long> {
}
