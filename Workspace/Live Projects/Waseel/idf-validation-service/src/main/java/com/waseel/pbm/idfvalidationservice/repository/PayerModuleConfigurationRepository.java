package com.waseel.pbm.idfvalidationservice.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.idfvalidationservice.persist.PayerModuleConfiguration;
import com.waseel.pbm.idfvalidationservice.persist.PayerModuleConfigurationId;

@Repository
public interface PayerModuleConfigurationRepository
		extends CrudRepository<PayerModuleConfiguration, PayerModuleConfigurationId> {

	@Cacheable(value = "idf-cache", key = "#payerId")
	@Query("select model.id.moduleId from PayerModuleConfiguration model where model.id.payerId like (:payerId) and model.isEnabled = 1")
	List<Integer> findByPayerIdAndIsEnabled(@Param("payerId") String payerId);

	@Cacheable(value = "idf-cache", key = "{#payerId, #moduleId}")
	@Query("select model.isEnabled from PayerModuleConfiguration model where model.id.payerId like (:payerId) and model.id.moduleId = :moduleId")
	String findByPayerIdAndModuleId(@Param("payerId") String payerId, @Param("moduleId") Double moduleId);

}
