package com.waseel.pbm.pbmadminservice.repository.mdss;

import com.waseel.pbm.pbmadminservice.persist.mdss.IdfDrugToDiagnosisIndications;
import com.waseel.pbm.pbmadminservice.persist.mdss.IdfDrugToDiagnosisIndicationsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface IdfDrugToDiagnosisIndicationsRepository
        extends JpaRepository<IdfDrugToDiagnosisIndications, IdfDrugToDiagnosisIndicationsId>,
        JpaSpecificationExecutor<IdfDrugToDiagnosisIndications> {

    @Query("SELECT model FROM IdfDrugToDiagnosisIndications model"
            + " WHERE model.icdDiagnosisCode = :icdCode "
            + " AND model.serviceCode = :serviceCode")
    List<IdfDrugToDiagnosisIndications> findByIcdCodeAndServiceCode(@Param("icdCode") String icdCode,
                                                                    @Param("serviceCode") String serviceCode);

    @Query("SELECT model FROM IdfDrugToDiagnosisIndications model"
            + " WHERE model.id = :id"
            + " AND model.isDeleted = '0'")
    Optional<IdfDrugToDiagnosisIndications> findNotDeletedByIcdCodeAndServiceCodeAndId(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(" UPDATE IdfDrugToDiagnosisIndications model SET"
            + " model.icdDiagnosisCode = :icdCode,"
            + " model.serviceCode = :serviceCode,"
            + " model.oldServiceCode = :oldServiceCode,"
            + " model.lastUpdatedDateTime = :lastUpdatedDateTime"
            + " WHERE model.id = :id"
            + " AND model.isDeleted = '0'")
    int updateNotDeletedDataById(@Param("icdCode") String icdCode,
                                 @Param("serviceCode") String serviceCode,
                                 @Param("oldServiceCode") String oldServiceCode,
                                 @Param("id") Long id,
                                 @Param("lastUpdatedDateTime") Timestamp lastUpdatedDateTime);

    @Query("SELECT model FROM IdfDrugToDiagnosisIndications model"
            + " WHERE model.icdDiagnosisCode = :icdCode "
            + " AND model.serviceCode = :serviceCode"
            + " AND model.isDeleted = '0'")
    Optional<IdfDrugToDiagnosisIndications> findNotDeletedByIcdCodeAndServiceCode(@Param("icdCode") String icdCode,
                                                                                  @Param("serviceCode") String serviceCode);

    @Query(value = "SELECT \"Id\" from \"IDFDrugToDiagnosisIndications\""
            + " ORDER BY \"Id\"  DESC FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
    Long findLatestId();
}
