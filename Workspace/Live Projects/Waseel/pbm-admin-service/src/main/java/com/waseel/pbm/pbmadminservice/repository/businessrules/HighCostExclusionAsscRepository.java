package com.waseel.pbm.pbmadminservice.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.waseel.pbm.pbmadminservice.persist.businessrules.HighCostExclusionAssc;

public interface HighCostExclusionAsscRepository extends JpaRepository<HighCostExclusionAssc, Long> {

	@Query(nativeQuery = true, value = "SELECT MAX(HIGH_COST_EXCLUSION_ASSC_ID)+1 FROM PBM_BUSINESS_RULES.HIGH_COST_EXCLUSION_ASSC")
	Long fetchPrimaryKeyForHighCostExclusionAssc();

	Optional<HighCostExclusionAssc> findByExclusionId(Long exclusionId);

	Optional<HighCostExclusionAssc> findByHighCostExclusionAsscIdAndIsEnabled(Long exclusionAsscId, boolean isEnabled);
}
