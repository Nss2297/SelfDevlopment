package com.waseel.pbm.pbmadminservice.repository.businessrules;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.pbmadminservice.persist.businessrules.ProviderExclusionAssc;

@Repository
public interface ProviderExclusionAsscRepository extends JpaRepository<ProviderExclusionAssc, Long> {

    Optional<ProviderExclusionAssc> findByProviderExclusionAsscIdAndIsEnabled(Long providerExclusionAssId, Boolean isEnabled);
    
	Optional<ProviderExclusionAssc> findByProviderIdAndPayerIdAndExclusionId(Long providerId, Long payerId,
			Long exclusionId);

	Optional<List<ProviderExclusionAssc>> findByExclusionId(Long exclusionId);
}
