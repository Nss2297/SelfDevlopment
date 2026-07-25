package com.waseel.prescription.repository.prescriptionservice;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.persist.prescriptionservice.Physician;

@Repository
public interface PhysicianRepository extends CrudRepository<Physician, Long> {

	Optional<Physician> findByRequestId(String requestId);
}
