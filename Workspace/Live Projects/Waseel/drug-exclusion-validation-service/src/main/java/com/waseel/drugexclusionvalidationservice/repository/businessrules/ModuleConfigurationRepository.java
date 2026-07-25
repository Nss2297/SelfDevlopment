package com.waseel.drugexclusionvalidationservice.repository.businessrules;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.waseel.drugexclusionvalidationservice.persist.businessrules.ModuleConfiguration;

public interface ModuleConfigurationRepository extends JpaRepository<ModuleConfiguration, Long> {

	@Query("SELECT model.moduleConfigurationId.moduleId FROM ModuleConfiguration model WHERE"
			+ " model.moduleConfigurationId.payerId = :payerId "
			+ " AND model.moduleConfigurationId.providerId = :providerId " 
			+ " AND model.isEnabled = true")
	List<Long> findByProviderIdAndPayerIdAndIsEnabled(@Param("payerId") Long payerId,
			@Param("providerId") Long providerId);
}
