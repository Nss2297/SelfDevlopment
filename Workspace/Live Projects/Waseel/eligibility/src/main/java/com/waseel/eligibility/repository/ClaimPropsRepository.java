package com.waseel.eligibility.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.waseel.eligibility.entity.Claimprop;


public interface ClaimPropsRepository extends CrudRepository<Claimprop, Long> {

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Claimprop cp set eligibilitycheck=:eligibilitycheck, eligibilityStatusDesc=:eligiblitystatuesdescription where cp.geninfo.claimid = :claimid")
	int updateEligibilitycheckByClaimid(String eligibilitycheck, Long claimid, String eligiblitystatuesdescription);
}
