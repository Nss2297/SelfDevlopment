package com.waseel.pbm.dssservice.repository.mdss;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.dssservice.persist.mdss.PayerConfig;
import com.waseel.pbm.dssservice.persist.mdss.PayerConfigId;

@Repository
public interface PayerConfigRepository extends CrudRepository<PayerConfig, PayerConfigId> {

	@Cacheable(value = "dss-cache", key = "#payerIdEnabled")
	@Query("select model from PayerConfig model where model.id.payerId like (:payerId) and model.id.isEnabled = 1")
	PayerConfig findByPayerIdAndIsEnabled(@Param("payerId") String payerIdEnabled);

	@Cacheable(value = "dss-cache", key = "#payerId")
	@Query("select model from PayerConfig model where model.id.payerId like (:payerId)")
	PayerConfig findByPayerId(@Param("payerId") String payerId);
}
