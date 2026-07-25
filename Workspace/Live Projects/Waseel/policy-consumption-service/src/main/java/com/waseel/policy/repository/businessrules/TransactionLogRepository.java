package com.waseel.policy.repository.businessrules;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.policy.persist.businessrules.TransactionLog;

@Repository
public interface TransactionLogRepository extends CrudRepository<TransactionLog, Long> {
}
