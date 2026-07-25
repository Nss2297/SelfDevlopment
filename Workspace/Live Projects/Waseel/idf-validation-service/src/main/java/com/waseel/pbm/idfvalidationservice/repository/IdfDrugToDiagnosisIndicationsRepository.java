package com.waseel.pbm.idfvalidationservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.idfvalidationservice.persist.IdfDrugToDiagnosisIndications;
import com.waseel.pbm.idfvalidationservice.persist.IdfDrugToDiagnosisIndicationsId;

@Repository
public interface IdfDrugToDiagnosisIndicationsRepository extends CrudRepository<IdfDrugToDiagnosisIndications, IdfDrugToDiagnosisIndicationsId> {

	@Query("select model.id.icdDiagnosisCode from IdfDrugToDiagnosisIndications model where model.id.icdDiagnosisCode in (:icdCodes) and (model.id.serviceCode like (:serviceCode) or model.id.oldServiceCode like (:serviceCode))")
	List<String> findByIcd10CodeServiceCode(@Param("icdCodes") List<String> icdCodes,@Param("serviceCode") String serviceCode);

	@Query("select model.id.icdDiagnosisCode from IdfDrugToDiagnosisIndications model where model.id.serviceCode like (:serviceCode)")
	List<String> findByServiceCode(@Param("serviceCode") String serviceCode);

}
