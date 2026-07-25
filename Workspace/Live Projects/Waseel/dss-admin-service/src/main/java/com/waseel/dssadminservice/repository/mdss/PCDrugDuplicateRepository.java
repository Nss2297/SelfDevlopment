package com.waseel.dssadminservice.repository.mdss;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.waseel.dssadminservice.persist.mdss.PCDrugCommonId;
import com.waseel.dssadminservice.persist.mdss.PCDuplicateTherapy;

@Repository
public interface PCDrugDuplicateRepository extends JpaRepository<PCDuplicateTherapy, PCDrugCommonId>{

	Optional<PCDuplicateTherapy> findBySeqId(Long id);

	Optional<PCDuplicateTherapy> findBySeqIdAndId_PayerId(Long id, String accId);

	Optional<PCDuplicateTherapy> findByIdServiceCodeAndIdInteractedServiceCodeAndIdPayerIdAndIdModuleNameIgnoreCase(
			String serviceCode, String interactedServiceCode, String payerId, String moduleName);

}
