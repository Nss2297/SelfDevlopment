package com.waseel.pbm.rtsservice.repository.mdss;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.rtsservice.persist.mdss.CommonRejectionReason;

@Repository
public interface CommonRejectionReasonRepository extends CrudRepository<CommonRejectionReason, String> {

    @Query("select model from CommonRejectionReason model where model.rejectionCode= :rejectionCode")
    CommonRejectionReason findByRejectionCode(@Param("rejectionCode") String rejectionCode);
}
