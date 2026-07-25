package com.waseel.pbm.pbmadminservice.repository.businessrules;

import com.waseel.pbm.pbmadminservice.persist.businessrules.PolicyClasses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PolicyClassRepository extends JpaRepository<PolicyClasses, Long> {

    Optional<List<PolicyClasses>> findByPolicyInformationId(Long policyInformationId);

    Optional<PolicyClasses> findByPolicyInformationIdAndClassCode(Long policyInformationId, String code);

    Optional<PolicyClasses> findByPolicyInformationIdAndClassCodeAndIsEnabled(
            Long policyInformationId, String code, boolean isEnabled);

    Optional<PolicyClasses> findByClassCode(String classCode);

    @Query(value = "SELECT MAX(\"POLICY_CLASS_ID\") from \"POLICY_CLASSES\"", nativeQuery = true)
    Long findLatestId();
}
