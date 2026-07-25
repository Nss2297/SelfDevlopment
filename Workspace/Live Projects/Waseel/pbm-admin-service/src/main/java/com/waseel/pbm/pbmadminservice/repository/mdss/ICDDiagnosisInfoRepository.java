package com.waseel.pbm.pbmadminservice.repository.mdss;

import com.waseel.pbm.pbmadminservice.persist.mdss.IcdDiagnosisInfo;
import com.waseel.pbm.pbmadminservice.persist.mdss.IcdDiagnosisInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICDDiagnosisInfoRepository extends JpaRepository<IcdDiagnosisInfo, IcdDiagnosisInfoId> {

    @Query(value = "select model from IcdDiagnosisInfo model "
            + " where model.id.requestId = :requestId AND model.isDeletedFromProvider = '0'")
    List<IcdDiagnosisInfo> findByRequestIdAndIsDeletedFromProvider(@Param("requestId") String requestId);
}
