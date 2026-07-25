package com.waseel.pbm.payercustomizationservice.common;

import com.waseel.pbm.payercustomizationservice.persist.CustomizationAuditTrail;
import com.waseel.pbm.payercustomizationservice.repository.CustomizationAuditTrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Component
public class CommonMethods {

    @Autowired
    CustomizationAuditTrailRepository customizationAuditTrailRepository;

    public void saveDataToAuditTrailList(String requestId, String ruleId,
                                         List<CustomizationAuditTrail> auditTrailList,
                                         List<String> ruleIdList) {
        if (!ruleIdList.contains(ruleId)) {
            CustomizationAuditTrail customizationAuditTrail = new CustomizationAuditTrail();
            customizationAuditTrail.setRequestId(requestId);
            customizationAuditTrail.setRuleId(ruleId);
            customizationAuditTrail.setSubmissionDateTime(Timestamp.from(Instant.now()));
            auditTrailList.add(customizationAuditTrail);
            ruleIdList.add(ruleId);
        }
    }

    public void saveDataToAuditTrail(List<CustomizationAuditTrail> auditTrailList) {
        if (!auditTrailList.isEmpty())
            customizationAuditTrailRepository.saveAll(auditTrailList);
    }
}
