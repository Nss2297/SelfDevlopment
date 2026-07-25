package com.waseel.pbm.dssservice.repository.mdss;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.dssservice.persist.mdss.TransactionLog;

@Repository
public interface TransactionLogRepository extends CrudRepository<TransactionLog, Long> {

	TransactionLog findBytransactionLogId(Long transactionLogId);

	Optional<List<TransactionLog>> findByRequestId(String requestId);

	@Query("select distinct model from TransactionLog model where model.requestId like (:requestId) and model.transactionType like ('NEW') and httpStatus=200")
	TransactionLog findNewReqByRequestId(@Param("requestId") String requestId);
}
