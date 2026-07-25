package com.waseel.prescription.repository.prescriptionservice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.prescription.persist.prescriptionservice.PrescriptionApprovalDrug;

public interface PrescriptionApprovalDrugRepository extends JpaRepository<PrescriptionApprovalDrug, Long> {

	Optional<List<PrescriptionApprovalDrug>> findByEprescriptionReferenceNumber(String ePrescriptionReferenceNumber);

	Optional<PrescriptionApprovalDrug> findByEprescriptionReferenceNumberAndScientificCodeAndSuggestedDrugCode(
			String ePrescriptionReferenceNumber, String scientificCode, String suggestedDrugCode);

	Optional<PrescriptionApprovalDrug> findByEprescriptionReferenceNumberAndSuggestedDrugCodeAndStatus(
			String ePrescriptionReferenceNumber, String suggestedDrugCode, String status);

	List<PrescriptionApprovalDrug> findByEprescriptionReferenceNumberAndScientificCodeAndStatus(
			String ePrescriptionReferenceNumber, String scientificCodes, String status);
}
