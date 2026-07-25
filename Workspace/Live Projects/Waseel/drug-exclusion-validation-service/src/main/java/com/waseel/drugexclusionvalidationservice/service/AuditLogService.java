package com.waseel.drugexclusionvalidationservice.service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.drugexclusionvalidationservice.model.DrugExclusionRequestModel;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionResponseModel;
import com.waseel.drugexclusionvalidationservice.persist.mongodb.DrugExclusionAuditTrail;
import com.waseel.drugexclusionvalidationservice.repository.mongodb.SpecialityExclusionAuditTrailRepository;

@Service
public class AuditLogService {

	private static final Logger log = LoggerFactory.getLogger(AuditLogService.class);

	@Autowired
	private SpecialityExclusionAuditTrailRepository specialityExclusionAuditTrailRepository;

	public void saveAuditLogInMongoDb(DrugExclusionRequestModel requestModel, DrugExclusionResponseModel responseModel,
			Long transactionLogId) {
		try {
			CompletableFuture.runAsync(() ->
			// Used to save data in background -- CompletableFuture.runAsync
			specialityExclusionAuditTrailRepository
					.save(setDrugFormularyAuditData(requestModel, responseModel, transactionLogId)));
		} catch (Exception e) {
			log.error("", e);
		}
	}

	private DrugExclusionAuditTrail setDrugFormularyAuditData(DrugExclusionRequestModel requestModel,
			DrugExclusionResponseModel responseModel, Long transactionLogId) {
		DrugExclusionAuditTrail audit = new DrugExclusionAuditTrail();
		audit.setSpecialityExclusionRequestModel(requestModel);
		audit.setSpecialityExclusionResponseModel(responseModel);
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
