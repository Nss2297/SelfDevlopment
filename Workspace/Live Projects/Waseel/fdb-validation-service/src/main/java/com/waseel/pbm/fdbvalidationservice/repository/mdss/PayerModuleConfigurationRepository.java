package com.waseel.pbm.fdbvalidationservice.repository.mdss;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.fdbvalidationservice.persist.mdss.PayerModuleConfiguration;
import com.waseel.pbm.fdbvalidationservice.persist.mdss.PayerModuleConfigurationId;

@Repository
public interface PayerModuleConfigurationRepository
		extends CrudRepository<PayerModuleConfiguration, PayerModuleConfigurationId> {

	@Cacheable(value = "fdb-cache", key = "#payerId")
	@Query("select model.id.moduleId from PayerModuleConfiguration model where model.id.payerId like (:payerId) and model.isEnabled = 1")
	List<Integer> findByPayerIdAndIsEnabled(@Param("payerId") String payerId);
}
