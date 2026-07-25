package com.waseel.drugformulary.repository.businessrules;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.drugformulary.persist.businessrules.DrugFormularyDetails;

public interface DrugFormularyDetailsRepository extends JpaRepository<DrugFormularyDetails, Long> {

	List<DrugFormularyDetails> findByFormularyIdAndIsDeleted(Long formularyId,Boolean isDeleted);
}
