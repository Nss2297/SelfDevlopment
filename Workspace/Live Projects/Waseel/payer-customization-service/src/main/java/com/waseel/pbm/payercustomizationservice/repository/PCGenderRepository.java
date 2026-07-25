package com.waseel.pbm.payercustomizationservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.payercustomizationservice.persist.PCGender;

@Repository
public interface PCGenderRepository extends CrudRepository<PCGender, Long> {

    @Query("SELECT model from PCGender model"
            + " WHERE (model.payerId =:payerId OR model.payerId = '101')"
            + " AND model.serviceCode = :serviceCode"
            + " AND model.serviceStatus = :serviceStatus"
            + " AND (model.moduleName = :moduleName OR  model.moduleName = 'ALL')"
            + " AND LOWER(model.gender) = LOWER(:gender)")
    List<PCGender> findByPayerIdAndServiceCodeAndModuleNameAndGender(
            @Param("payerId") String payerId,
            @Param("serviceCode") String serviceCode,
            @Param("moduleName") String moduleName,
            @Param("serviceStatus") String serviceStatus,
            @Param("gender") String gender);

    @Query("SELECT model from PCGender model"
            + " WHERE (model.payerId =:payerId OR model.payerId = '101')"
            + " AND model.serviceCode = :serviceCode"
            + " AND model.serviceStatus = :serviceStatus"
            + " AND LOWER(model.gender) = LOWER(:gender)"
            + " ORDER BY model.moduleName DESC")
    List<PCGender> findByPayerIdAndServiceCodeAndGender(
            @Param("payerId") String payerId,
            @Param("serviceCode") String serviceCode,
            @Param("serviceStatus") String serviceStatus,
            @Param("gender") String gender);

    @Query(value = "SELECT \"Id\" from \"PCGender\""
            + " ORDER BY \"Id\"  DESC FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
    Long findLatestId();

    @Query("SELECT model from PCGender model"
            + " WHERE model.payerId = :payerId"
            + " AND model.serviceCode = :serviceCode"
            + " AND model.moduleName = :moduleName")
    Optional<PCGender> findByPayerIdAndServiceCodeAndModuleName(
            @Param("payerId") String payerId,
            @Param("serviceCode") String serviceCode,
            @Param("moduleName") String moduleName);
    
    
    @Query("SELECT model from PCGender model"
            + " WHERE (model.payerId =:payerId OR model.payerId = '101')"
            + " AND model.scientificCode = :scientificCode"
            + " AND model.serviceStatus = :serviceStatus"
            + " AND LOWER(model.gender) = LOWER(:gender)"
            + " ORDER BY model.moduleName DESC")
    List<PCGender> findByPayerIdAndScientificCodeAndGender(
            @Param("payerId") String payerId,
            @Param("scientificCode") String scientificCode,
            @Param("serviceStatus") String serviceStatus,
            @Param("gender") String gender);
    
    @Query("SELECT model from PCGender model"
            + " WHERE (model.payerId =:payerId OR model.payerId = '101')"
            + " AND model.scientificCode = :scientificCode"
            + " AND model.serviceStatus = :serviceStatus"
            + " AND (model.moduleName = :moduleName OR  model.moduleName = 'ALL')"
            + " AND LOWER(model.gender) = LOWER(:gender)")
    List<PCGender> findByPayerIdAndScientificCodeAndModuleNameAndGender(
            @Param("payerId") String payerId,
            @Param("scientificCode") String scientificCode,
            @Param("moduleName") String moduleName,
            @Param("serviceStatus") String serviceStatus,
            @Param("gender") String gender);
}
