package com.waseel.pbm.payercustomizationservice.repository;

import com.waseel.pbm.payercustomizationservice.persist.PCCommonId;
import com.waseel.pbm.payercustomizationservice.persist.PCQuantityLimitCheck;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PCQuantityLimitCheckRepository extends CrudRepository<PCQuantityLimitCheck, PCCommonId> {

    @Query("SELECT model from PCQuantityLimitCheck model"
            + " WHERE model.id.payerId =:payerId"
            + " AND model.id.serviceCode = :serviceCode"
            + " AND model.id.icdCode IN (:icdCodes) "
            + " AND (model.id.moduleName = :moduleName OR  model.id.moduleName = 'ALL')"
            + " AND :ageInDays BETWEEN model.fromAgeInDays AND model.toAgeInDays")
    List<PCQuantityLimitCheck> findByPayerIdAndServiceCodeAndIcdCodesAndModuleNameAndDOB(
            @Param("payerId") String payerId,
            @Param("serviceCode") String serviceCode,
            @Param("icdCodes") List<String> icdCodes,
            @Param("moduleName") String moduleName,
            @Param("ageInDays") Long ageInDays);

    @Query("SELECT model from PCQuantityLimitCheck model"
            + " WHERE model.id.payerId =:payerId"
            + " AND model.id.serviceCode = :serviceCode"
            + " AND model.id.icdCode IN (:icdCodes) "
            + " AND :ageInDays BETWEEN model.fromAgeInDays AND model.toAgeInDays")
    List<PCQuantityLimitCheck> findByPayerIdAndServiceCodeAndIcdCodesAndDOB(
            @Param("payerId") String payerId,
            @Param("serviceCode") String serviceCode,
            @Param("icdCodes") List<String> icdCodes,
            @Param("ageInDays") Long ageInDays);

    @Query(value = "SELECT \"Id\" from \"PCQuantityLimitCheck\""
            + " ORDER BY \"Id\"  DESC FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
    Long findLatestId();

    @Query("SELECT model from PCQuantityLimitCheck model"
            + " WHERE model.id.payerId = :payerId"
            + " AND model.id.serviceCode = :serviceCode"
            + " AND model.id.icdCode = :icdCode"
            + " AND model.id.moduleName = :moduleName")
    Optional<PCQuantityLimitCheck> findByPayerIdAndServiceCodeAndIcdCodeAndModuleName(
            @Param("payerId") String payerId,
            @Param("serviceCode") String serviceCode,
            @Param("icdCode") String icdCode,
            @Param("moduleName") String moduleName);
}
