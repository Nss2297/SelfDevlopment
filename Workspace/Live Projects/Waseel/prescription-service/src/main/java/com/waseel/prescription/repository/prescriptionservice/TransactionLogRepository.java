package com.waseel.prescription.repository.prescriptionservice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.persist.prescriptionservice.TransactionLog;

@Repository
public interface TransactionLogRepository extends CrudRepository<TransactionLog, Long> {

	Optional<List<TransactionLog>> findByRequestId(String requestId);

	@Query(value = "SELECT to_char(sysdate, 'YYYY') || '-' ||TO_CHAR(\"PRESCRIPTION_SERVICE\".\"PS_EprescriptionReferenceNumber_SEQ\".\"NEXTVAL\") FROM dual", nativeQuery = true)
	public String generateEPrescriptionReferenceNumber();

	@Query("SELECT model from TransactionLog model WHERE model.ePrescriptionReferenceNumber = :ePrescriptionReferenceNumber "
			+ " AND model.httpStatus = '200' AND model.transactionType = 'NEW'")
	Optional<TransactionLog> findByePrescriptionReferenceNumberWithValidStatus(String ePrescriptionReferenceNumber);

	@Query("SELECT model from TransactionLog model WHERE model.ePrescriptionReferenceNumber = :ePrescriptionReferenceNumber "
			+ " AND model.transactionType = 'NEW'")
	Optional<TransactionLog> findByePrescriptionReferenceNumberWithInvalidStatus(String ePrescriptionReferenceNumber);

	Optional<List<TransactionLog>> findByePrescriptionReferenceNumber(String ePrescriptionReferenceNumber);

	Optional<TransactionLog> findByePrescriptionReferenceNumberAndTransactionType(String ePrescriptionReferenceNumber,
			String transactionType);

	Optional<List<TransactionLog>> findByrequestIdAndTransactionType(String requestId, String transactionType);
}