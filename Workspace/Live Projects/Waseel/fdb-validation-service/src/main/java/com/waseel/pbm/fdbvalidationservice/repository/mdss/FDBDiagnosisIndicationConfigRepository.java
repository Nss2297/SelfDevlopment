package com.waseel.pbm.fdbvalidationservice.repository.mdss;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.pbm.fdbvalidationservice.persist.mdss.FdbdiagnosisIndicationConfig;

public interface FDBDiagnosisIndicationConfigRepository extends CrudRepository<FdbdiagnosisIndicationConfig, String> {

	@Cacheable(value = "fdb-cache", key = "#icdCode")
	@Query(value = "select model from FdbdiagnosisIndicationConfig model where model.icdcode = :icdCode")
	FdbdiagnosisIndicationConfig findByICDCode(@Param("icdCode") String icdCode);
}
