package com.waseel.drugexclusionvalidationservice.repository.businessrules;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.waseel.drugexclusionvalidationservice.persist.businessrules.HighCostExclusionAssc;

public interface HighCostExclusionAsscRepository  extends JpaRepository<HighCostExclusionAssc, Long>{

	@Query("SELECT  dd.registrationNumber FROM"
			+ " HighCostExclusionAssc hc, DrugExclusionMetadata dm, DrugExclusionDetails dd"
			+ " WHERE "
			+ " hc.exclusionId = dm.exclusionId"
			+ " AND dm.exclusionId = dd.exclusionId"
			+ " AND hc.payerId = dm.payerId"
			+ " AND hc.isEnabled = true "
			+ " AND dm.isDeleted = false"
			+ " AND dd.isDeleted = false"
			+ " AND hc.payerId = :payerId"
			+ " AND dd.registrationNumber IN (:drugList)")
	List<String> checkHighCostDrugsExclusionByPayerIdAndDrugList(
			@Param("payerId") Long payerId,
			@Param("drugList") Set<String> drugList);
}
