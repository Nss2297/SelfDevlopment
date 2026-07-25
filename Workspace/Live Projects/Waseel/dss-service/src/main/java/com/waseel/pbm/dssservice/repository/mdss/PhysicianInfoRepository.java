package com.waseel.pbm.dssservice.repository.mdss;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.waseel.pbm.dssservice.persist.mdss.PhysicianInfo;
import com.waseel.pbm.dssservice.persist.mdss.PhysicianInfoId;

import feign.Param;

@Repository
public interface PhysicianInfoRepository extends CrudRepository<PhysicianInfo, PhysicianInfoId> {

	@Query("Select model from PhysicianInfo model where model.id.requestId = :requestId")
	PhysicianInfo findByrequestId(@Param("requestId") String requestId);
	
	@Transactional
	@Modifying
	@Query("Update PhysicianInfo set id.physicianId = :physicianId where id.requestId = :requestId")
	int updatePhysicianInfo(@Param("physicianId") String physicianId,@Param("requestId") String requestId);
}
