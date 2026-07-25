package com.waseel.pbm.dssservice.repository.mdss;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.dssservice.persist.mdss.PayerModuleConfiguration;
import com.waseel.pbm.dssservice.persist.mdss.PayerModuleConfigurationId;

@Repository
public interface PayerModuleConfigurationRepository
		extends CrudRepository<PayerModuleConfiguration, PayerModuleConfigurationId> {

	@Cacheable(value = "dss-cache", key = "{#payerId, #moduleId}")
	@Query("select model from PayerModuleConfiguration model where model.id.payerId like (:payerId) and model.id.moduleId = :moduleId and model.isEnabled = 1 ")
	PayerModuleConfiguration findByIdAndIsEnabled(@Param("payerId") String payerId, @Param("moduleId") Double moduleId);

	@Cacheable(value = "dss-cache", key = "{#payerId, #moduleIdList}")
	@Query("select model.id.moduleId from PayerModuleConfiguration model where model.id.payerId like (:payerId) and model.id.moduleId IN (:moduleId) and model.isEnabled = 1 ")
	List<Double> findByIdAndIsEnabled(@Param("payerId") String payerId, @Param("moduleId") List<Double> moduleId);
}
