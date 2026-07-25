package com.waseel.dssadminservice.repository.mdss;

import com.waseel.dssadminservice.persist.mdss.PCDuplicateTherapy;
import com.waseel.dssadminservice.persist.mdss.PcDrugToDrug;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.dssadminservice.persist.mdss.PCDrugCommonId;

public interface PCDuplicateTherapyRepository extends CrudRepository<PCDuplicateTherapy, PCDrugCommonId> {

        @Query(value = "SELECT \"Id\" from \"PCDuplicateTherapy\""
                        + " ORDER BY \"Id\"  DESC FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
        Long findLatestId();

        Optional<PCDuplicateTherapy> findBySeqId(Long id);

        Optional<PCDuplicateTherapy> findBySeqIdAndId_PayerId(Long id, String accId);

        Optional<PCDuplicateTherapy> findBySeqIdAndIdServiceCode(Long seqId, String serviceCode);

        Optional<PCDuplicateTherapy> findBySeqIdAndIdInteractedServiceCode(Long seqId, String interactedServiceCode);

        Optional<PCDuplicateTherapy> findByIdServiceCodeAndIdInteractedServiceCodeAndIdPayerIdAndIdModuleNameIgnoreCase(
                        String serviceCode, String interactedServiceCode, String payerId, String moduleName);

        @Modifying
        @Query("UPDATE PCDuplicateTherapy SET serviceStatus = :serviceStatus, " + "id.payerId = :payerId, "
                        + "additionalRejectionReason = :additionalRejectionReason, " + "id.moduleName = :moduleName, "
                        + "lastUpdatedDateTime = :lastUpdatedDateTime " + "WHERE seqId IN :seqIds")
        Integer updatePCDuplicateTherapyCustomizationRequestByIds(@Param("serviceStatus") String serviceStatus,
                        @Param("payerId") String payerId,
                        @Param("additionalRejectionReason") String additionalRejectionReason,
                        @Param("moduleName") String moduleName, @Param("seqIds") List<Long> seqIds,
                        @Param("lastUpdatedDateTime") Timestamp lastUpdatedDateTime);
}
