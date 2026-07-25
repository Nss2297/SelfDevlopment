package com.waseel.prescription.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.persist.businessrules.BenefitCodes;

@Repository
public interface BenefitCodesRepository extends JpaRepository<BenefitCodes, Long> {

}
