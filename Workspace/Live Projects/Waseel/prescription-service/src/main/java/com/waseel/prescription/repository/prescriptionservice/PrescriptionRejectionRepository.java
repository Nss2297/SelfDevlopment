package com.waseel.prescription.repository.prescriptionservice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.persist.prescriptionservice.PrescriptionRejection;

@Repository
public interface PrescriptionRejectionRepository extends CrudRepository<PrescriptionRejection, Long> {

	List<PrescriptionRejection> findByRequestId(String requestId);

	List<PrescriptionRejection> findByRequestIdAndShowUnderBusinessValidation(String requestId,
			boolean showUnderBusinessValidation);
}