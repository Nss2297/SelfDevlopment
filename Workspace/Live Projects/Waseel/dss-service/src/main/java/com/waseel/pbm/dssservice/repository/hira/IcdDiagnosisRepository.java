package com.waseel.pbm.dssservice.repository.hira;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.dssservice.persist.hira.IcdDiagnosis;

import feign.Param;

@Repository
public interface IcdDiagnosisRepository extends CrudRepository<IcdDiagnosis, String>{

	@Query("select model from IcdDiagnosis model where model.icdDiagnosisCode In (:icdCodes)")
	List<IcdDiagnosis> findIcdCodes(@Param("icdCodes") List<String> icdCodes);
}
