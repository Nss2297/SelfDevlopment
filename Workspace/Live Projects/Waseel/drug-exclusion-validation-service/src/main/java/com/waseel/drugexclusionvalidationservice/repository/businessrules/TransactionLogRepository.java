package com.waseel.drugexclusionvalidationservice.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.drugexclusionvalidationservice.persist.businessrules.TransactionLog;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long>{

	Optional<TransactionLog> findByTransactionLogId(Long transactionLogId);
}
