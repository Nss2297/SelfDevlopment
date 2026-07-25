package com.waseel.policy.repository.businessrules;

import org.springframework.data.repository.CrudRepository;

import com.waseel.policy.persist.businessrules.AuditLog;

public interface AuditLogRepository extends CrudRepository<AuditLog, Long> {

}
