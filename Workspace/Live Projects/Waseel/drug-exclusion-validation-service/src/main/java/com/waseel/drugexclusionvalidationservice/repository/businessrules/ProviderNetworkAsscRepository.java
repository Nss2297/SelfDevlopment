package com.waseel.drugexclusionvalidationservice.repository.businessrules;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.waseel.drugexclusionvalidationservice.persist.businessrules.ProviderNetworkAssc;

public interface ProviderNetworkAsscRepository extends CrudRepository<ProviderNetworkAssc, Long> {

	List<ProviderNetworkAssc> findByProviderIdAndIsEnabled(BigDecimal providerId, Boolean isEnabled);
}
