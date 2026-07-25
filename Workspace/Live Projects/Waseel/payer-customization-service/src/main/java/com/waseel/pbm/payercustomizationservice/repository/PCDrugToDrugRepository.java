package com.waseel.pbm.payercustomizationservice.repository;

import com.waseel.pbm.payercustomizationservice.persist.PCDrugCommonId;
import com.waseel.pbm.payercustomizationservice.persist.PCDuplicateTherapy;
import com.waseel.pbm.payercustomizationservice.persist.PcDrugToDrug;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PCDrugToDrugRepository extends CrudRepository<PcDrugToDrug, PCDrugCommonId> {

    @Query("SELECT model FROM PcDrugToDrug model"
            + " WHERE (model.id.payerId = :payerId OR model.id.payerId = '101')"
            + " AND (model.id.moduleName = :moduleName OR model.id.moduleName = 'ALL')"
            + " AND model.id.serviceCode =:serviceCode"
            + " AND model.id.interactedServiceCode IN (:interactedServiceCodes)"
            + " ORDER BY model.id.moduleName DESC")
    List<PcDrugToDrug> findByPayerIdAndServiceCodeAndModuleName(
            @Param("payerId") String payerId,
            @Param("moduleName") String moduleName,
            @Param("serviceCode") String serviceCode,
            @Param("interactedServiceCodes") List<String> interactedServiceCodes);

    @Query("SELECT model FROM PcDrugToDrug model"
            + " WHERE (model.id.payerId = :payerId OR model.id.payerId = '101')"
            + " AND model.id.serviceCode =:serviceCode"
            + " AND model.id.interactedServiceCode IN (:interactedServiceCodes)"
            + " AND model.serviceStatus = :serviceStatus"
            + " ORDER BY model.id.moduleName DESC")
    List<PcDrugToDrug> findByPayerIdAndServiceCode(
            @Param("payerId") String payerId,
            @Param("serviceCode") String serviceCode,
            @Param("interactedServiceCodes") List<String> interactedServiceCodes,
            @Param("serviceStatus") String serviceStatus);

    @Query(value = "SELECT \"Id\" from \"PcDrugToDrug\""
            + " ORDER BY \"Id\"  DESC FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
    Long findLatestId();

    @Query("SELECT model from PcDrugToDrug model"
            + " WHERE model.id.payerId = :payerId"
            + " AND model.id.serviceCode = :serviceCode"
            + " AND model.id.moduleName = :moduleName"
            + " AND model.id.interactedServiceCode = :interactedServiceCode")
    Optional<PcDrugToDrug> findByPayerIdAndServiceCodeAndModuleNameAndInteractedServiceCode(
            @Param("payerId") String payerId,
            @Param("serviceCode") String serviceCode,
            @Param("moduleName") String moduleName,
            @Param("interactedServiceCode") String interactedServiceCode);
}
