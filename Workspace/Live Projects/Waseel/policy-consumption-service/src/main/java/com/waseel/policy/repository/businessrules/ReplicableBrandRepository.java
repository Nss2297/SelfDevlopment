package com.waseel.policy.repository.businessrules;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.waseel.policy.persist.businessrules.ReplicableBrand;

public interface ReplicableBrandRepository extends JpaRepository<ReplicableBrand, Long> {

	@Query(value = "SELECT model.drugcode FROM ReplicableBrand model WHERE model.isDeleted = 0")
	Optional<List<String>> findAllNonDeletedDrugs();

	@Query(value = "SELECT model.drugcode FROM ReplicableBrand model WHERE model.isDeleted = 0 and model.drugcode in (:drugCodes)")
	Optional<List<String>> findAllNonDeletedReplicableDrugsByDrugCodes(@Param("drugCodes") Set<String> drugCodes);
}
