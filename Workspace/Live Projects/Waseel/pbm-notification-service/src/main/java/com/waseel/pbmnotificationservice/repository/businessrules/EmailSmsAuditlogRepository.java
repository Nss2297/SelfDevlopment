package com.waseel.pbmnotificationservice.repository.businessrules;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.pbmnotificationservice.persist.businessrules.EmailSmsAuditLog;

public interface EmailSmsAuditlogRepository  extends JpaRepository<EmailSmsAuditLog, Long>{

}
