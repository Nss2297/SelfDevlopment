package com.waseel.authentication.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.waseel.authentication.model.portal.enity.AccountToAccountTransaction;
import com.waseel.authentication.model.portal.enity.AccountToAccountTransactionId;

public interface AccountToAccountTransactionRepository extends CrudRepository<AccountToAccountTransaction, AccountToAccountTransactionId> {

	
	List<AccountToAccountTransaction> findById_sourceAndId_transactionId(BigDecimal providerId, Double transactionId);
	
	List<AccountToAccountTransaction> findById_sourceAndId_transactionIdIn(BigDecimal providerId, List<Double> transactionId);
}
