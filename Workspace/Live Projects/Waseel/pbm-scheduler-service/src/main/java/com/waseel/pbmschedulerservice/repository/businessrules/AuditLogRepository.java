package com.waseel.pbmschedulerservice.repository.businessrules;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.pbmschedulerservice.persist.businessrules.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long>{

}
