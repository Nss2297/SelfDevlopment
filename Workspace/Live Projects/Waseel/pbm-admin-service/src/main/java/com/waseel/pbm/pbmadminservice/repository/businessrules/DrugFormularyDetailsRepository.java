package com.waseel.pbm.pbmadminservice.repository.businessrules;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyDetails;

public interface DrugFormularyDetailsRepository extends JpaRepository<DrugFormularyDetails, Long> {

	List<DrugFormularyDetails> findByFormularyIdAndIsDeleted(Long formularyId, boolean isDeleted);

	Optional<DrugFormularyDetails> findByDrugFormularyDetailsIdAndIsDeleted(Long drugFormularyDetailsId,
			boolean isDeleted);

	Optional<DrugFormularyDetails> findByFormularyIdAndWaseelDrugIdAndRegistrationNumberAndIsDeleted(Long formularyId,
			Long waseelDrugId, String registrationNumber, Boolean isDeleted);
}
