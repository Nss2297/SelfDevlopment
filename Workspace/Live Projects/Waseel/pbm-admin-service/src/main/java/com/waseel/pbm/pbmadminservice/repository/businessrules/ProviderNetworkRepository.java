package com.waseel.pbm.pbmadminservice.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.pbm.pbmadminservice.persist.businessrules.ProviderNetwork;

public interface ProviderNetworkRepository extends JpaRepository<ProviderNetwork, Long>{

	Optional<ProviderNetwork> findByNetworkIdAndPayerIdAndIsDeleted(Long exclusionId, Long payerId,
			boolean isDeleted);
}
