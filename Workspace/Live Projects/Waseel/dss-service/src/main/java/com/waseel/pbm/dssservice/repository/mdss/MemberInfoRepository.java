package com.waseel.pbm.dssservice.repository.mdss;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.dssservice.persist.mdss.MemberInfo;

import feign.Param;

@Repository
public interface MemberInfoRepository extends CrudRepository<MemberInfo,String>{

	@Query("Select model from MemberInfo model where model.requestId = :requestId")
	MemberInfo findByrequestId(@Param("requestId") String requestId);
}
