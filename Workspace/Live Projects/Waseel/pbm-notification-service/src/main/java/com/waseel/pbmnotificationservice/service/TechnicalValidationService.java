package com.waseel.pbmnotificationservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbmnotificationservice.exceptions.NotificationException;
import com.waseel.pbmnotificationservice.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.pbmnotificationservice.repository.prescriptionservice.PrescriptionRequestRepository;

@Service
public class TechnicalValidationService {

	@Autowired
	private PrescriptionRequestRepository prescriptionRequestRepository;

	public void checkEPrescriptionReferenceNumberIsExistsOrNot(String ePrescriptionReferenceNumber)
			throws NotificationException {
		Optional<PrescriptionRequest> prescriptionReqOpt = prescriptionRequestRepository
				.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		if (prescriptionReqOpt.isEmpty()) {
			throw new NotificationException("EPrescriptionReferenceNumber is not found or exists.");
		}
	}
}
