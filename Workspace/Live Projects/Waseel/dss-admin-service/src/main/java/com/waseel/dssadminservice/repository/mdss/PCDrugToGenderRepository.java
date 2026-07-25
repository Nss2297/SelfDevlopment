package com.waseel.dssadminservice.repository.mdss;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.waseel.dssadminservice.persist.mdss.PCAgeGenderId;
import com.waseel.dssadminservice.persist.mdss.PCGender;

public interface PCDrugToGenderRepository extends JpaRepository<PCGender, PCAgeGenderId> {

	Optional<PCGender> findByIdServiceCodeAndIdPayerIdAndIdModuleNameIgnoreCase(String serviceCode, String payerId,
			String moduleName);

	Optional<PCGender> findByIdServiceCodeAndIdPayerIdAndIdModuleName(String serviceCode, String payerId,
			String moduleName);

	@Query(value = "SELECT \"Id\" from \"PCGender\""
			+ " ORDER BY \"Id\"  DESC FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
	Long findLatestId();

	Optional<PCGender> findBySeqId(Long id);

	Optional<PCGender> findBySeqIdAndId_PayerId(Long id, String accId);

	Optional<PCGender> findBySeqIdAndIdServiceCode(Long seqId, String serviceCode);

	Optional<PCGender> findByIdServiceCodeAndGenderIgnoreCaseAndIdPayerIdAndIdModuleNameIgnoreCaseAndServiceStatusIgnoreCaseAndSeqIdNot(
			String serviceCode, String gender, String payerId, String moduleName, String serviceStatus, Long seqId);

	@Query("SELECT pcg from PCGender pcg"
            + " WHERE pcg.id.serviceCode = :serviceCode"
            + " AND pcg.id.payerId = :payerId"
            + " AND pcg.id.moduleName = :moduleName"
            + " AND pcg.gender = :gender")
    Optional<PCGender> findByServiceCodeAndModuleNameAndGenderAndPayerId(
            @Param("serviceCode") String serviceCode, 
            @Param("moduleName") String moduleName,
            @Param("gender") String gender, 
            @Param("payerId") String payerId);

	Optional<PCGender> findByIdAndSeqIdNot(PCAgeGenderId id, Long seqId);

	@Modifying
	@Query("UPDATE PCGender pcg SET pcg.gender = :gender, pcg.serviceStatus = :serviceStatus, pcg.additionalRejectionReason = :additionalRejectionReason, pcg.id.moduleName = :module, pcg.id.payerId = :payerId, pcg.lastUpdatedDateTime = :lastUpdatedDateTime WHERE pcg.seqId = :seqId")
	Integer updateByPCGenderCustomizationRequest(String gender, String serviceStatus, String additionalRejectionReason,
			String module, String payerId, Timestamp lastUpdatedDateTime, Long seqId);
}
