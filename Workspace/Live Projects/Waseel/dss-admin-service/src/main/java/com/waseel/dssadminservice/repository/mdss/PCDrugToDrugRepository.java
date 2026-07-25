package com.waseel.dssadminservice.repository.mdss;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.dssadminservice.persist.mdss.PCDrugCommonId;
import com.waseel.dssadminservice.persist.mdss.PcDrugToDrug;

public interface PCDrugToDrugRepository extends CrudRepository<PcDrugToDrug, PCDrugCommonId> {

	@Query("SELECT model FROM PcDrugToDrug model" + " WHERE (model.id.payerId = :payerId OR model.id.payerId = '101')"
			+ " AND (model.id.moduleName = :moduleName OR model.id.moduleName = 'ALL')"
			+ " AND model.id.serviceCode =:serviceCode"
			+ " AND model.id.interactedServiceCode IN (:interactedServiceCodes)" + " ORDER BY model.id.moduleName DESC")
	List<PcDrugToDrug> findByPayerIdAndServiceCodeAndModuleName(@Param("payerId") String payerId,
			@Param("moduleName") String moduleName, @Param("serviceCode") String serviceCode,
			@Param("interactedServiceCodes") List<String> interactedServiceCodes);

	@Query("SELECT model FROM PcDrugToDrug model" + " WHERE (model.id.payerId = :payerId OR model.id.payerId = '101')"
			+ " AND model.id.serviceCode =:serviceCode"
			+ " AND model.id.interactedServiceCode IN (:interactedServiceCodes)"
			+ " AND model.serviceStatus = :serviceStatus" + " ORDER BY model.id.moduleName DESC")
	List<PcDrugToDrug> findByPayerIdAndServiceCode(@Param("payerId") String payerId,
			@Param("serviceCode") String serviceCode,
			@Param("interactedServiceCodes") List<String> interactedServiceCodes,
			@Param("serviceStatus") String serviceStatus);

	@Query(value = "SELECT \"Id\" from \"PCDrugToDrug\""
			+ " ORDER BY \"Id\"  DESC FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
	Long findLatestId();

	@Query("SELECT model from PcDrugToDrug model" + " WHERE model.id.serviceCode = :serviceCode"
			+ " AND model.id.interactedServiceCode = :interactedServiceCode" + " AND model.id.payerId = :payerId"
			+ " AND model.id.moduleName = :moduleName")
	Optional<PcDrugToDrug> findByServiceCodeAndInteractedServiceCodeAndPayerIdAndModuleName(
			@Param("serviceCode") String serviceCode,
			@Param("interactedServiceCode") String interactedServiceCode,
			@Param("payerId") String payerId,
			@Param("moduleName") String moduleName);

	Optional<PcDrugToDrug> findBySeqId(Long id);

	Optional<PcDrugToDrug> findBySeqIdAndId_PayerId(Long id, String accId);

	Optional<PcDrugToDrug> findByIdServiceCodeAndIdInteractedServiceCodeAndIdPayerIdAndIdModuleNameIgnoreCase(
			String serviceCode, String interactedServiceCode, String payerId, String moduleName);

	Optional<PcDrugToDrug> findBySeqIdAndIdServiceCode(Long seqId, String serviceCode);

	Optional<PcDrugToDrug> findBySeqIdAndIdInteractedServiceCode(Long seqId, String interactedServiceCode);

	@Modifying
	@Query("UPDATE PcDrugToDrug SET serviceStatus = :serviceStatus, " + "id.payerId = :payerId, "
			+ "additionalRejectionReason = :additionalRejectionReason, " + "id.moduleName = :moduleName, "
			+ "lastUpdatedDateTime = :lastUpdatedDateTime " + "WHERE seqId = :seqId")
	Integer updatePCDrugCustomizationRequestById(@Param("serviceStatus") String serviceStatus,
			@Param("payerId") String payerId, @Param("additionalRejectionReason") String additionalRejectionReason,
			@Param("moduleName") String moduleName, @Param("seqId") Long seqId,
			@Param("lastUpdatedDateTime") Timestamp lastUpdatedDateTime);

	@Modifying
	@Query("UPDATE PcDrugToDrug SET serviceStatus = :serviceStatus, " + "id.payerId = :payerId, "
			+ "additionalRejectionReason = :additionalRejectionReason, " + "id.moduleName = :moduleName, "
			+ "lastUpdatedDateTime = :lastUpdatedDateTime " + "WHERE seqId IN :seqIds")
	Integer updatePCDrugCustomizationRequestByIds(@Param("serviceStatus") String serviceStatus,
			@Param("payerId") String payerId, @Param("additionalRejectionReason") String additionalRejectionReason,
			@Param("moduleName") String moduleName, @Param("seqIds") List<Long> seqIds,
			@Param("lastUpdatedDateTime") Timestamp lastUpdatedDateTime);
}