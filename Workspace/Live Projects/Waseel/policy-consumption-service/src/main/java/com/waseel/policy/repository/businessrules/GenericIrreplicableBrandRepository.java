package com.waseel.policy.repository.businessrules;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.waseel.policy.persist.businessrules.GenericIrreplicableBrand;

public interface GenericIrreplicableBrandRepository extends JpaRepository<GenericIrreplicableBrand, Long> {

	@Query(value = "SELECT model.drugcode FROM GenericIrreplicableBrand model where model.isDeleted = 0")
	Optional<List<String>> findAllNonDeletedDrugs();

	@Query(value = "SELECT model.drugcode FROM GenericIrreplicableBrand model where model.isDeleted = 0 and model.drugcode in (:drugCodes)")
	Optional<List<String>> findAllNonDeletedGenericIrreplaceableDrugsByDrugCodes(
			@Param("drugCodes") Set<String> drugCodes);
}
