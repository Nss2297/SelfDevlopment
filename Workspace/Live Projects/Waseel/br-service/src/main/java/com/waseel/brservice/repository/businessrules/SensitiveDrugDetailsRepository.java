package com.waseel.brservice.repository.businessrules;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.waseel.brservice.persist.businessrules.SensitiveDrugDetails;

@Repository
public interface SensitiveDrugDetailsRepository extends JpaRepository<SensitiveDrugDetails, Long> {

    List<SensitiveDrugDetails> findByRegistrationNumberInAndIsDeleted(List<String> registrationNumbers,
    		boolean isDeleted);
}
