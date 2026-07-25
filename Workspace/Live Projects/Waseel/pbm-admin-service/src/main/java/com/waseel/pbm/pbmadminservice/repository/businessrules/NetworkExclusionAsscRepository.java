package com.waseel.pbm.pbmadminservice.repository.businessrules;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.waseel.pbm.pbmadminservice.persist.businessrules.NetworkExclusionAssc;

public interface NetworkExclusionAsscRepository extends CrudRepository<NetworkExclusionAssc, Long> {

	Optional<NetworkExclusionAssc> findByExclusionIdAndNetworkId(Long exclusionId, Long networkId);

	Optional<NetworkExclusionAssc> findByNetworkExclusionAsscIdAndIsEnabled(Long networkExclusionAsscId,
			Boolean isEnabled);

	Optional<NetworkExclusionAssc> findByExclusionIdAndNetworkIdAndIsEnabled(Long exclusionId, Long networkId,
			Boolean isEnabled);

	Optional<List<NetworkExclusionAssc>> findByExclusionId(Long exclusionId);

}
