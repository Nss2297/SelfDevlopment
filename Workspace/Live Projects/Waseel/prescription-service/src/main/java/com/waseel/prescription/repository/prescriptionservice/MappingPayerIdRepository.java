package com.waseel.prescription.repository.prescriptionservice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.persist.prescriptionservice.MappingPayerId;

@Repository
public interface MappingPayerIdRepository extends JpaRepository<MappingPayerId, Long> {

	Optional<MappingPayerId> findByPayerIdAndTransactionTypeAndIsEnabled(String payerId, String transactionType,
			Boolean isEnabled);

	Optional<MappingPayerId> findByMapperPayerIdAndIsEnabled(String mapperPayerId, Boolean isEnabled);
	
	Optional<MappingPayerId> findByMapperPayerIdAndIsEnabledAndTransactionType(String mapperPayerId, Boolean isEnabled,
			String transactionType);

}
