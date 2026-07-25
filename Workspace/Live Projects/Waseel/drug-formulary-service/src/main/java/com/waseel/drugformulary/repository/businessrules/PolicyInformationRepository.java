package com.waseel.drugformulary.repository.businessrules;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.drugformulary.persist.businessrules.PolicyInformation;

public interface PolicyInformationRepository  extends JpaRepository<PolicyInformation, Long>{

}
