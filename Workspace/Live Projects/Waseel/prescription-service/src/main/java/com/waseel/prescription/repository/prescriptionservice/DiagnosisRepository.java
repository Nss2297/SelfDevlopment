package com.waseel.prescription.repository.prescriptionservice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.persist.prescriptionservice.Diagnosis;
import com.waseel.prescription.persist.prescriptionservice.DiagnosisId;

@Repository
public interface DiagnosisRepository extends CrudRepository<Diagnosis, DiagnosisId> {

	@Query(value = "SELECT model FROM Diagnosis model " + "WHERE model.isDeleted = :isDeleted "
			+ "AND model.diagnosisId.requestId = :requestId")
	List<Diagnosis> findByRequestIdAndIsDeleted(@Param("requestId") String requestId,
			@Param("isDeleted") boolean isDeleted);

	@Query(value = "SELECT model FROM Diagnosis model " + "WHERE model.diagnosisId.requestId = :requestId "
			+ "AND model.diagnosisId.diagnosisCode = :diagnosisCode")
	Optional<Diagnosis> findByDiagnosisCodeAndRequestId(@Param("diagnosisCode") String diagnosisCode,
			@Param("requestId") String requestId);

	@Query(value = "SELECT new com.waseel.prescription.model.prescription.DiagnosisCodes("
			+ " model.diagnosisId.diagnosisCode,model.diagnosisType) " + " FROM Diagnosis model "
			+ " WHERE model.isDeleted = '0' " + " AND model.diagnosisId.requestId = :requestId")
	List<DiagnosisCodes> findByRequestIdAndIsNotDeleted(@Param("requestId") String requestId);
}
