package com.waseel.pbm.pbmadminservice.repository.mdss;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.pbm.pbmadminservice.persist.mdss.CustomizationUploadAudit;

public interface CustomizationUploadAuditRepository extends JpaRepository<CustomizationUploadAudit, Long> {

}
