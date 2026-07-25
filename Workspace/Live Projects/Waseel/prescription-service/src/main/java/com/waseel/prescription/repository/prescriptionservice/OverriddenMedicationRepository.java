package com.waseel.prescription.repository.prescriptionservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.waseel.prescription.persist.prescriptionservice.OverriddenMedication;

@Repository
public interface OverriddenMedicationRepository extends JpaRepository<OverriddenMedication, Long> {

    List<OverriddenMedication> findAll();
}
