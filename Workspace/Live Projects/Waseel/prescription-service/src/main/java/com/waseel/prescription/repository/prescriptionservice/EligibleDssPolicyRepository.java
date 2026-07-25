package com.waseel.prescription.repository.prescriptionservice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.persist.prescriptionservice.EligibleDssPolicy;

@Repository
public interface EligibleDssPolicyRepository extends JpaRepository<EligibleDssPolicy, Long> {

	@Query("SELECT e FROM EligibleDssPolicy e WHERE e.policyNumber IN :policyNumbers"
			+ " And e.isEnabled = 1")
	Optional<List<EligibleDssPolicy>> findByPolicyNumber(List<String> policyNumbers);
   
}
