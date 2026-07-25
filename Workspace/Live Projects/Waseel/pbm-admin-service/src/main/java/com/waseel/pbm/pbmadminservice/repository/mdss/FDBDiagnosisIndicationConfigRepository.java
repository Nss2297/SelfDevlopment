package com.waseel.pbm.pbmadminservice.repository.mdss;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.waseel.pbm.pbmadminservice.persist.mdss.FdbDiagnosisIndicationConfig;

public interface FDBDiagnosisIndicationConfigRepository extends JpaRepository<FdbDiagnosisIndicationConfig, String>,
		JpaSpecificationExecutor<FdbDiagnosisIndicationConfig> {

	@Query(value = "select model from FdbDiagnosisIndicationConfig model "
			+ " where model.icdCode = :icdCode AND model.isDeleted = '0'")
	Optional<FdbDiagnosisIndicationConfig> findByNotDeletedIcdCode(String icdCode);

	@Query("SELECT model from FdbDiagnosisIndicationConfig model"
			+ " WHERE model.id = :id"
			+ " AND model.isDeleted = '0'")
	Optional<FdbDiagnosisIndicationConfig> findByNotDeletedConfiguration(Long id);

	@Query(value = "SELECT \"Id\" from \"FDBDiagnosisIndicationConfig\""
			+ " ORDER BY \"Id\"  DESC FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
	Long findLatestId();
}
