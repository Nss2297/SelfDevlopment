package com.waseel.brservice.service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.brservice.model.InvalidResponseModel;
import com.waseel.brservice.model.SensitiveDrugRequestModel;
import com.waseel.brservice.model.SensitiveDrugResponseModel;
import com.waseel.brservice.persist.mongodb.BusinessRuleAuditTrail;
import com.waseel.brservice.repository.mongodb.BusinessRuleAuditTrailRepository;

@Service
public class AuditLogService {

	private static final Logger log = LoggerFactory.getLogger(AuditLogService.class);

	@Autowired
	private BusinessRuleAuditTrailRepository businessRuleAuditTrailRepository;

	public void saveSensitiveDrugAuditLogInMongoDb(SensitiveDrugRequestModel requestModel,
			SensitiveDrugResponseModel responseModel, Long transactionLogId,
			InvalidResponseModel invalidResponseModel) {
		try {
			CompletableFuture.runAsync(() ->
			// Used to save data in background -- CompletableFuture.runAsync
			businessRuleAuditTrailRepository.save(
					setSensitiveDrugAuditData(requestModel, responseModel, transactionLogId, invalidResponseModel)));
		} catch (Exception e) {
			log.error("Mongodb audit exception: ", e);
		}
	}

	private BusinessRuleAuditTrail setSensitiveDrugAuditData(SensitiveDrugRequestModel requestModel,
			SensitiveDrugResponseModel responseModel, Long transactionLogId,
			InvalidResponseModel invalidResponseModel) {
		BusinessRuleAuditTrail audit = new BusinessRuleAuditTrail();
		audit.setSensitiveDrugRequestModel(requestModel);
		audit.setSensitiveDrugResponseModel(responseModel);
		audit.setInvalidResponseModel(invalidResponseModel);
		audit.setDateTime(new Date());
		if (transactionLogId != null) {
			audit.setTransactionLogId(transactionLogId);
		}
		if (requestModel != null) {
			audit.setRequestId(requestModel.getRequestId());
		}
		return audit;
	}

}
