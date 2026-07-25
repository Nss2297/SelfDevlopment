package com.waseel.pbmschedulerservice.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.pbmschedulerservice.persist.businessrules.TransactionLog;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long>{

	Optional<TransactionLog> findByTransactionLogId(Long transactionLogId);
}
