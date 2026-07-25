package com.waseel.pbm.dssservice.repository.mdss;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.dssservice.persist.mdss.ChronicDzInformation;
import com.waseel.pbm.dssservice.persist.mdss.MemberChronicDzAssoc;

import feign.Param;

@Repository
public interface MemberChronicDzAssocRepository extends CrudRepository<MemberChronicDzAssoc, Double> {
	
	MemberChronicDzAssoc findByChronicDzInformationAndMemberId(ChronicDzInformation chronicDzInfo, String memberId);

	@Query("select model.chronicDzInformation.chronicDiseasesId from MemberChronicDzAssoc model where model.memberId = :memberId and model.isEnabled='1' ")
	List<Integer> findChronicDiseasesIdByMemberId(@Param("memberId") String memberId);

}