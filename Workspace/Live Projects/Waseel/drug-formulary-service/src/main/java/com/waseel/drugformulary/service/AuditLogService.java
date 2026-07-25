package com.waseel.drugformulary.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.drugformulary.model.DrugFormularyRequestModel;
import com.waseel.drugformulary.model.DrugFormularyResponseModel;
import com.waseel.drugformulary.persist.mongodb.DrugFormularyAuditTrail;
import com.waseel.drugformulary.repository.mongodb.DrugFormularyAuditTrailRepository;

@Service
public class AuditLogService {

	private static final Logger log = LoggerFactory.getLogger(AuditLogService.class);

	@Autowired
	private DrugFormularyAuditTrailRepository drugFormularyAuditTrailRepository;

	public void saveAuditLogInMongoDb(DrugFormularyRequestModel requestModel, DrugFormularyResponseModel responseModel,
			Long transactionLogId, List<DrugFormularyResponseModel> drugFormularyResponseModelList) {
		try {
			CompletableFuture.runAsync(() ->
			// Used to save data in background -- CompletableFuture.runAsync
			drugFormularyAuditTrailRepository.save(setDrugFormularyAuditData(requestModel, responseModel,
					transactionLogId, drugFormularyResponseModelList)));
		} catch (Exception e) {
			log.error("", e);
		}
	}

	private DrugFormularyAuditTrail setDrugFormularyAuditData(DrugFormularyRequestModel requestModel,
			DrugFormularyResponseModel responseModel, Long transactionLogId,
			List<DrugFormularyResponseModel> drugFormularyResponseModelList) {
		DrugFormularyAuditTrail audit = new DrugFormularyAuditTrail();
		audit.setDrugFormularyRequestModel(requestModel);
		audit.setDrugFormularyResponseModel(responseModel);
		audit.setDrugFormularyResponseModelList(drugFormularyResponseModelList);
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
