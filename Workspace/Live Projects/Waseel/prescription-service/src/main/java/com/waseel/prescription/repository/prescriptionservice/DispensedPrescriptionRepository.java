package com.waseel.prescription.repository.prescriptionservice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.waseel.prescription.persist.prescriptionservice.DispensedPrescription;

public interface DispensedPrescriptionRepository extends CrudRepository<DispensedPrescription, Long> {

	List<DispensedPrescription> findByePrescriptionReferenceNumber(String ePrescriptionReferenceNumber);
}
