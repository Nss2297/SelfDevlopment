package com.waseel.pbm.payercustomizationservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestsAudit;

@Repository
public interface CustomizationRequestsAuditRepository extends CrudRepository<CustomizationRequestsAudit, Long> {
}
