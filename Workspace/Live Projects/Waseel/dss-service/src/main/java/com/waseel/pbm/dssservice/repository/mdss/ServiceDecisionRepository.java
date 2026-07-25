package com.waseel.pbm.dssservice.repository.mdss;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.dssservice.persist.mdss.ServiceDecision;
import com.waseel.pbm.dssservice.persist.mdss.ServiceDecisionId;

import feign.Param;

@Repository
public interface ServiceDecisionRepository extends CrudRepository<ServiceDecision,ServiceDecisionId>{

	@Query("Select model from ServiceDecision model where model.id.requestId = :requestId")
	List<ServiceDecision>  findByrequestId(@Param("requestId") String requestId);
	
	@Query("Select model from ServiceDecision model where model.id.requestId = :requestId AND model.id.serviceId = :serviceId")
	ServiceDecision findByRequestIdAndServiceId(@Param("requestId") String requestId,@Param("serviceId") Long serviceId);
}
