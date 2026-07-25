package com.waseel.pbm.pbmadminservice.repository.prescriptionservice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.pbmadminservice.persist.prescriptionservice.MappingPayerId;

@Repository
public interface MappingPayerIdRepository extends JpaRepository<MappingPayerId, Long> {

	Optional<MappingPayerId> findByPayerIdAndTransactionTypeAndIsEnabled(String payerId, String transactionType,
			Boolean isEnabled);
}
