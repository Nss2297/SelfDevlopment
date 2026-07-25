package com.waseel.pbm.pbmadminservice.repository.businessrules;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.pbm.pbmadminservice.persist.businessrules.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
