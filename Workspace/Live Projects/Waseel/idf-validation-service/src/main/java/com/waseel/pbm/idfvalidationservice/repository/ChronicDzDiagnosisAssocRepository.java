package com.waseel.pbm.idfvalidationservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.idfvalidationservice.persist.ChronicDzDiagnosisAssoc;

@Repository
public interface ChronicDzDiagnosisAssocRepository extends CrudRepository<ChronicDzDiagnosisAssoc, Double> {

	// @Query("select model from ChronicDzDiagnosisAssoc model where
	// model.diagnosisCode in (:diagnosisCodes)")
	List<ChronicDzDiagnosisAssoc> findByDiagnosisCodeIn(List<String> diagnosisCodes);

}
