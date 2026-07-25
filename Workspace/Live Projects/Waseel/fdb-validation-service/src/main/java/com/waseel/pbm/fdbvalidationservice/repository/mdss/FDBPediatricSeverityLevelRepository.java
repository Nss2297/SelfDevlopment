package com.waseel.pbm.fdbvalidationservice.repository.mdss;

import com.waseel.pbm.fdbvalidationservice.persist.mdss.FDBPediatricAgeSeverityLevel;
import com.waseel.pbm.fdbvalidationservice.persist.mdss.FDBPediatricAgeSeverityLevelId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FDBPediatricSeverityLevelRepository extends
        CrudRepository<FDBPediatricAgeSeverityLevel, FDBPediatricAgeSeverityLevelId> {

    @Query("select model from FDBPediatricAgeSeverityLevel model "
            + " WHERE (model.levelId.payerId =:payerId OR model.levelId.payerId = '101')"
            + " AND model.levelId.serviceCode = :serviceCode"
            + " AND model.pediatricAgeSeverityLevel = 'PRECAUTION' AND model.isDeleted = '0'")
    Optional<FDBPediatricAgeSeverityLevel> findByServiceCodeAndPayerIdAndSeverityLevel(
            @Param("payerId") String payerId, @Param("serviceCode") String serviceCode);
}
