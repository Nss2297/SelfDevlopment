package com.waseel.eligibility.logging;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.eligibility.enums.TransactionType;
import com.waseel.eligibility.model.EligibilityRequestModel;
import com.waseel.eligibility.model.EligibilityResponseModel;
import com.waseel.eligibility.persist.EligibilityAuditTrail;
import com.waseel.eligibility.repository.EligibilityAuditTrailRepository;

@Service
public class AuditLogService {

	private static final Logger logger = LoggerFactory.getLogger(AuditLogService.class);

	@Autowired
	EligibilityAuditTrailRepository eligibilityAuditTrailRepository;

	public void saveAuditLogInMongoDb(EligibilityRequestModel eligibilityRequestModel,
			EligibilityResponseModel eligibilityResponseModel, TransactionType transactionType,
			String transactionReferenceNumber, Long eligibilityTransactionLogId) {
		try {
			logger.info("Logging eligibility transaction.");
			EligibilityAuditTrail eligibilityAuditTrail = getEligibilityAuditData(eligibilityRequestModel,
					eligibilityResponseModel, transactionType, transactionReferenceNumber, eligibilityTransactionLogId);
			eligibilityAuditTrailRepository.save(eligibilityAuditTrail);
		} catch (Exception e) {
			logger.error("MongoDB logging failed", e);
		}
	}

	private EligibilityAuditTrail getEligibilityAuditData(EligibilityRequestModel eligibilityRequestModel,
			EligibilityResponseModel eligibilityResponseModel, TransactionType transactionType,
			String transactionReferenceNumber, Long eligibilityTransactionLogId) {
		EligibilityAuditTrail eligibilityAuditTrail = new EligibilityAuditTrail();
		eligibilityAuditTrail.setDateTime(new Date());
		eligibilityAuditTrail.setEligibilityRequestModel(eligibilityRequestModel);
		eligibilityAuditTrail.setEligibilityResponseModel(eligibilityResponseModel);
		eligibilityAuditTrail.setTransactionReferenceNumber(transactionReferenceNumber);
		eligibilityAuditTrail.setEligibilityTransactionLogId(eligibilityTransactionLogId);
		eligibilityAuditTrail.setTransactionType(transactionType.value());
		return eligibilityAuditTrail;
	}
}
