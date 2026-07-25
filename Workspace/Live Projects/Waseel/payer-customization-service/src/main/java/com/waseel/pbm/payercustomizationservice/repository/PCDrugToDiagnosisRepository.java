package com.waseel.pbm.payercustomizationservice.repository;

import com.waseel.pbm.payercustomizationservice.persist.PCCommonId;
import com.waseel.pbm.payercustomizationservice.persist.PCDrugToDiagnosis;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PCDrugToDiagnosisRepository extends
        CrudRepository<PCDrugToDiagnosis, PCCommonId> {

    @Query("Select model from PCDrugToDiagnosis model"
            + " where model.payerId =:payerId"
            + " And model.serviceCode = :serviceCode"
            + " And model.icdCode IN (:icdCodes) "
            + " order by model.moduleName desc  ")
    List<PCDrugToDiagnosis> findByPayerIdAndServiceCodeAndIcdCodes(
            @Param("payerId") String payerId,
            @Param("serviceCode") String serviceCode,
            @Param("icdCodes") List<String> icdCodes);

    @Query(value = "SELECT \"Id\" from \"PCDrugToDiagnosis\""
            + " ORDER BY \"Id\"  DESC FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
    Long findLatestId();

    @Query("SELECT model from PCDrugToDiagnosis model"
            + " WHERE model.serviceCode = :serviceCode"
            + " AND model.icdCode = :icdCode"
            + " AND model.payerId = :payerId"
            + " AND model.moduleName = :moduleName")
    Optional<PCDrugToDiagnosis> findByServiceCodeAndRejectionCategoryAndModuleNameAndIcdCodeAndPayerId(
            @Param("serviceCode") String serviceCode, @Param("moduleName") String moduleName,
            @Param("icdCode") String icdCode, @Param("payerId") String payerId);
}
