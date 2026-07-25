package com.waseel.dssadminservice.repository.mdss;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.dssadminservice.persist.mdss.CustomizationUploadAudit;

public interface CustomizationUploadAuditRepository extends JpaRepository<CustomizationUploadAudit, Long> {

}
