package com.waseel.pbm.dssservice.repository.mdss;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.dssservice.persist.mdss.ChronicDzDiagnosisAssoc;

@Repository
public interface ChronicDzDiagnosisAssocRepository extends CrudRepository<ChronicDzDiagnosisAssoc, Double> {

	List<ChronicDzDiagnosisAssoc> findByIsEnabledAndDiagnosisCodeIn(String isEnabled, List<String> diagnosisCodes);

	@Query("select model.diagnosisCode from ChronicDzDiagnosisAssoc model where model.chronicDzInformation.chronicDiseasesId in (:chronicDzIds) and model.isEnabled = '1' ")
	List<String> findDiagnosisCodesByChronicDzids(List<Integer> chronicDzIds);

}
