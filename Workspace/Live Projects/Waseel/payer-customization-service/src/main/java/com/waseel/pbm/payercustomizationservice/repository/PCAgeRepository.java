package com.waseel.pbm.payercustomizationservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.pbm.payercustomizationservice.persist.PCAge;

public interface PCAgeRepository extends CrudRepository<PCAge, Long> {

	  @Query("SELECT model from PCAge model"
	            + " WHERE (model.payerId =:payerId OR model.payerId = '101')"
	            + " AND (model.serviceCode = :serviceCode OR model.scientificCode = :scientificCode)"
	            + " AND model.serviceStatus = :ServiceStatus "
	            + " AND (model.moduleName = :moduleName OR  model.moduleName = 'ALL')"
	            + " AND :ageInDays BETWEEN model.fromAgeInDays AND model.toAgeInDays "
	            + " order by model.moduleName desc ")
	    List<PCAge> findByPayerIdAndServiceCodeOrScientificCodeAndModuleNameAndDOB(
	            @Param("payerId") String payerId,
	            @Param("serviceCode") String serviceCode,
	            @Param("moduleName") String moduleName,
	            @Param("ageInDays") Long ageInDays,
	            @Param("ServiceStatus") String serviceStatus,
	            @Param("scientificCode") String scientificCode);
	

    @Query("SELECT model from PCAge model"
            + " WHERE (model.payerId =:payerId OR model.payerId = '101')"
            + " AND (model.serviceCode = :serviceCode OR model.scientificCode = :scientificCode)"
            + " AND model.serviceStatus = :ServiceStatus "
            + " AND :ageInDays BETWEEN model.fromAgeInDays AND model.toAgeInDays"
            + "  order by model.moduleName desc ")
    List<PCAge> findByPayerIdAndServiceCodeOrScientificCodeAndDOB(
            @Param("payerId") String payerId,
            @Param("serviceCode") String serviceCode,
            @Param("ageInDays") Long ageInDays,
            @Param("ServiceStatus") String serviceStatus,
            @Param("scientificCode") String scientificCode);

    @Query(value = "SELECT \"Id\" from \"PCAge\""
            + " ORDER BY \"Id\"  DESC FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
    Long findLatestId();

    @Query("SELECT model from PCAge model"
            + " WHERE model.payerId = :payerId"
            + " AND model.serviceCode = :serviceCode"
            + " AND model.moduleName = :moduleName")
    Optional<PCAge> findByPayerIdAndServiceCodeAndModuleName(
            @Param("payerId") String payerId,
            @Param("serviceCode") String serviceCode,
            @Param("moduleName") String moduleName);
}
