package com.waseel.pbm.pbmadminservice.repository.businessrules;

import com.waseel.pbm.pbmadminservice.persist.businessrules.PolicyInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PolicyInformationRepository extends JpaRepository<PolicyInformation, Long> {

    Optional<PolicyInformation> findByPolicyNumber(String policyNumber);

    Optional<PolicyInformation> findByPolicyNumberAndPayerId(String policyNumber, String payerId);

    @Query(value = "SELECT MAX(\"POLICY_INFORMATION_ID\") from \"POLICY_INFORMATION\"", nativeQuery = true)
    Long findLatestId();
}
