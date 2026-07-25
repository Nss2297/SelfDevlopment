package com.waseel.prescription.repository.prescriptionservice;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.waseel.prescription.persist.prescriptionservice.InvalidPrescriptionRequest;

public interface InvalidPrescriptionRequestRepository extends CrudRepository<InvalidPrescriptionRequest, Long> {

	Optional<InvalidPrescriptionRequest> findByePrescriptionReferenceNumber(String ePrescriptionReferenceNumber);

	Optional<List<InvalidPrescriptionRequest>> findByIdNumberAndProviderIdAndPayerIdAndSendDateTimeGreaterThanEqualAndSendDateTimeLessThanEqual(
			long idNumber, String providerId, String payerId, Timestamp startDate, Timestamp endDate);

	Optional<List<InvalidPrescriptionRequest>> findByMemberIdAndPolicyNumberAndProviderIdAndPayerIdAndSendDateTimeGreaterThanEqualAndSendDateTimeLessThanEqual(
			String memberId, String policyNumber, String providerId, String payerId, Timestamp startDate,
			Timestamp endDate);

}