package com.waseel.prescription.service.management;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.prescription.persist.prescriptionservice.PrescriptionApprovalDrug;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionApprovalDrugRepository;

@Service
public class PrescriptionApprovalDrugService {

	@Autowired
	private PrescriptionApprovalDrugRepository prescriptionApprovedDrugRepository;

	public void addDataInPrescriptionApprovalDrug(String eprescriptionReferenceNumber, String scientificCode,
			String status, String suggestedDrugCode) {
		PrescriptionApprovalDrug approvalDrug = new PrescriptionApprovalDrug(eprescriptionReferenceNumber,
				new Timestamp(Calendar.getInstance().getTimeInMillis()), scientificCode, status, suggestedDrugCode);
		prescriptionApprovedDrugRepository.save(approvalDrug);
	}
}
