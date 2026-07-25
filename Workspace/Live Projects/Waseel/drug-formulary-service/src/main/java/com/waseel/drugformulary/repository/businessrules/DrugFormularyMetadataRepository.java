package com.waseel.drugformulary.repository.businessrules;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.drugformulary.persist.businessrules.DrugFormularyMetadata;

public interface DrugFormularyMetadataRepository extends JpaRepository<DrugFormularyMetadata, Long> {

	List<DrugFormularyMetadata> findByPayerId(String payerId);
}
