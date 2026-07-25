package com.waseel.pbm.idfvalidationservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.idfvalidationservice.persist.MemberChronicDzAssoc;

@Repository
public interface MemberChronicDzAssocRepository extends CrudRepository<MemberChronicDzAssoc, Double> {

	@Query("select distinct model.chronicDzInformation.chronicDiseasesId from MemberChronicDzAssoc model where model.memberId = :memberId and model.isEnabled='1' ")
	List<Integer> findByMemberId(@Param("memberId") String memerId);

}