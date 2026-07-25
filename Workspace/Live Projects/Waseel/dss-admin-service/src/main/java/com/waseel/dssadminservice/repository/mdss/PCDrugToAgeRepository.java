package com.waseel.dssadminservice.repository.mdss;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.waseel.dssadminservice.persist.mdss.PCAge;
import com.waseel.dssadminservice.persist.mdss.PCAgeGenderId;

public interface PCDrugToAgeRepository extends CrudRepository<PCAge, PCAgeGenderId> {

	Optional<PCAge> findByIdServiceCodeAndIdPayerIdAndIdModuleName(String serviceCode, String payerId,
			String moduleName);

	@Query(value = "SELECT \"Id\" from \"PCAge\"" + " ORDER BY \"Id\"  DESC FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
	Long findLatestId();

	Optional<PCAge> findByIdServiceCodeAndIdPayerIdAndIdModuleNameIgnoreCase(String serviceCode, String payerId,
			String moduleName);

	Optional<PCAge> findBySeqId(Long id);

	Optional<PCAge> findBySeqIdAndId_PayerId(Long id, String accId);

	Optional<PCAge> findBySeqIdAndIdServiceCode(Long seqId, String serviceCode);

	@Transactional
	@Modifying
	@Query("UPDATE PCAge SET id.payerId = :payerId," + "serviceStatus = :serviceStatus, "
			+ "additionalRejectionReason = :additionalRejectionReason," + "id.moduleName = :moduleName,"
			+ "fromAgeInDays = :fromAgeInDays," + "lastUpdatedDateTime = :lastUpdatedDateTime, "
			+ "toAgeInDays = :toAgeInDays " + "WHERE seqId = :seqId")
	int updatePCAgeCustomizationRequestById(@Param("payerId") String payerId,
			@Param("serviceStatus") String serviceStatus,
			@Param("additionalRejectionReason") String additionalRejectionReason,
			@Param("moduleName") String moduleName, @Param("seqId") Long seqId,
			@Param("fromAgeInDays") Long fromAgeInDays, @Param("toAgeInDays") Long toAgeInDays,
			@Param("lastUpdatedDateTime") Timestamp lastUpdatedDateTime);
}
