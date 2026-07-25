package com.waseel.pbmnotificationservice.repository.prescriptionservice;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbmnotificationservice.persist.prescriptionservice.PrescriptionRequest;

@Repository
public interface PrescriptionRequestRepository extends CrudRepository<PrescriptionRequest, String> {

	Optional<PrescriptionRequest> findByePrescriptionReferenceNumber(String ePrescriptionReferenceNumber);

}
