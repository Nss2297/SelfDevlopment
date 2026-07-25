package com.waseel.pbm.pbmadminservice.repository.mdss;

import com.waseel.pbm.pbmadminservice.persist.mdss.PhysicianInfo;
import com.waseel.pbm.pbmadminservice.persist.mdss.PhysicianInfoId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhysicianInfoRepository extends CrudRepository<PhysicianInfo, PhysicianInfoId> {

    @Query("Select model from PhysicianInfo model where model.id.requestId = :requestId")
    Optional<PhysicianInfo> findByRequestId(@Param("requestId") String requestId);
}
