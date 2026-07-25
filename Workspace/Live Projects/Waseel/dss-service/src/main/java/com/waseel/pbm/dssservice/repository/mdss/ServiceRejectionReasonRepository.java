package com.waseel.pbm.dssservice.repository.mdss;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.dssservice.persist.mdss.ServiceRejectionReason;
import com.waseel.pbm.dssservice.persist.mdss.ServiceRejectionReasonId;

import feign.Param;

@Repository
public interface ServiceRejectionReasonRepository
		extends CrudRepository<ServiceRejectionReason, ServiceRejectionReasonId> {

	@Query("Select model from ServiceRejectionReason model where model.id.requestId = :requestId")
	List<ServiceRejectionReason> findByrequestId(@Param("requestId") String requestId);
}
