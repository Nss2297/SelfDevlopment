package com.waseel.drugexclusionvalidationservice.repository.businessrules;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.waseel.drugexclusionvalidationservice.persist.businessrules.NetworkExclusionAssc;

public interface NetworkExclusionAsscRepository extends CrudRepository<NetworkExclusionAssc, Long> {

	List<NetworkExclusionAssc> findByProviderNetwork_NetworkIdInAndIsEnabled(List<Long> networkIds, Boolean isEnabled);
}
