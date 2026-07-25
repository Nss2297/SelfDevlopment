package com.waseel.pbm.pbmadminservice.repository.prescriptionservice;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.pbmadminservice.persist.prescriptionservice.PrescriptionRequest;

@Repository
public interface PrescriptionRequestRepository extends CrudRepository<PrescriptionRequest, String> {

    Optional<PrescriptionRequest> findByRequestId(String requestId);

  
}
