package com.waseel.pbm.payercustomizationservice.repository;

import com.waseel.pbm.payercustomizationservice.persist.PCDrugCommonId;
import com.waseel.pbm.payercustomizationservice.persist.PCDuplicateTherapy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PCDuplicateTherapyRepository extends
        CrudRepository<PCDuplicateTherapy, PCDrugCommonId> {

    @Query("SELECT model from PCDuplicateTherapy model"
            + " WHERE (model.id.payerId =:payerId OR model.id.payerId = '101')"
            + " AND model.id.serviceCode = :serviceCode"
            + " AND (model.id.moduleName = :moduleName OR  model.id.moduleName = 'ALL')"
            + " ORDER BY model.id.moduleName DESC, model.id.payerId DESC")
    List<PCDuplicateTherapy> findByPayerIdAndServiceCodeAndModuleName(
            @Param("payerId") String payerId,
            @Param("serviceCode") String serviceCode,
            @Param("moduleName") String moduleName);

    @Query("SELECT model from PCDuplicateTherapy model"
            + " WHERE (model.id.payerId =:payerId OR model.id.payerId = '101')"
            + " AND model.id.serviceCode = :serviceCode")
    List<PCDuplicateTherapy> findByPayerIdAndServiceCode(
            @Param("payerId") String payerId,
            @Param("serviceCode") String serviceCode);

    @Query(value = "SELECT \"Id\" from \"PCDuplicateTherapy\""
            + " ORDER BY \"Id\"  DESC FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
    Long findLatestId();

    @Query("SELECT model from PCDuplicateTherapy model"
            + " WHERE model.id.payerId = :payerId"
            + " AND model.id.serviceCode = :serviceCode"
            + " AND model.id.moduleName = :moduleName"
            + " AND model.id.interactedServiceCode = :interactedServiceCode")
    Optional<PCDuplicateTherapy> findByPayerIdAndServiceCodeAndModuleNameAndInteractedServiceCode(
            @Param("payerId") String payerId,
            @Param("serviceCode") String serviceCode,
            @Param("moduleName") String moduleName,
            @Param("interactedServiceCode") String interactedServiceCode);
}
