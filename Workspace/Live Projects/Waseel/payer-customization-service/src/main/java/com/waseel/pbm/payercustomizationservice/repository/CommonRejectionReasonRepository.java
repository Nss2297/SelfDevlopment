package com.waseel.pbm.payercustomizationservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.payercustomizationservice.persist.CommonRejectionReason;

@Repository
public interface CommonRejectionReasonRepository extends CrudRepository<CommonRejectionReason, String> {

	@Query("select model.rejectionReason from CommonRejectionReason model where model.rejectionCode like (:rejectionCode)")
	String findByRejectionCode(@Param("rejectionCode") String rejectionCode);
}
