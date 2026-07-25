package com.waseel.pbm.pbmadminservice.repository.mdss;

import com.waseel.pbm.pbmadminservice.persist.mdss.PCDrugToDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

public interface PCDrugToDiagnosisRepository extends JpaRepository<PCDrugToDiagnosis,
        Long>, JpaSpecificationExecutor<PCDrugToDiagnosis> {

    @Query("SELECT model from PCDrugToDiagnosis model"
            + " WHERE model.id = :id")
    Optional<PCDrugToDiagnosis> findByNotDeletedConfiguration(@Param("id") Long id);

    @Query("SELECT model from PCDrugToDiagnosis model"
            + " WHERE model.serviceCode = :serviceCode"
            + " AND model.icdCode =:icdCode"
            + " AND model.payerId = :payerId"
            + " AND model.moduleName = :moduleName")
    Optional<PCDrugToDiagnosis> findByServiceCodeAndPayerIdAndModuleNameAndIcdCode(
            @Param("serviceCode") String serviceCode, @Param("payerId") String payerId,
            @Param("moduleName") String moduleName, @Param("icdCode") String icdCode);

    @Query(value = "SELECT \"Id\" from \"PCDrugToDiagnosis\""
            + " ORDER BY \"Id\"  DESC FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
    Long findLatestId();

    @Transactional
    @Modifying
    @Query(" UPDATE  PCDrugToDiagnosis model SET"
            + " model.payerId = :payerId,"
            + " model.moduleName = :moduleName,"
            + " model.categoryOfApproval = :categoryOfApproval,"
            + " model.rejectionCategory = :rejectionCategory,"
            + " model.serviceStatus = :serviceStatus,"
            + " model.additionalRejectionReason = :additionalRejectionReason,"
            + " model.lastUpdatedBy = :lastUpdatedBy,"
            + " model.lastUpdatedDateTime = :lastUpdatedDateTime"
            + " WHERE model.id = :id")
    int updateDataById(@Param("id") Long id, @Param("payerId") String payerId,
                                 @Param("moduleName") String moduleName,
                                 @Param("categoryOfApproval") String categoryOfApproval,
                                 @Param("rejectionCategory") String rejectionCategory,
                                 @Param("serviceStatus") String serviceStatus,
                                 @Param("additionalRejectionReason") String additionalRejectionReason,
                                 @Param("lastUpdatedDateTime") Timestamp lastUpdatedDateTime,
                                 @Param("lastUpdatedBy") String lastUpdatedBy);

    @Query("SELECT model from PCDrugToDiagnosis model"
            + " WHERE model.serviceCode = :serviceCode"
            + " AND model.icdCode = :icdCode"
            + " AND model.payerId = :payerId"
            + " AND model.moduleName = :moduleName")
    Optional<PCDrugToDiagnosis> findByServiceCodeAndModuleNameAndIcdCodeAndPayerId(
            @Param("serviceCode") String serviceCode, @Param("moduleName") String moduleName,
            @Param("icdCode") String icdCode, @Param("payerId") String payerId);
    
	Optional<PCDrugToDiagnosis> findByServiceCodeAndIcdCodeAndPayerIdAndModuleNameAndRejectionCategory(
			String serviceCode, String icdCode, String payerId, String moduleName, String rejectionCategory);
}
