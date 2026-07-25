package com.waseel.prescription.service.management;

import com.waseel.prescription.model.dispense.DispenseDrugsRequestModel;
import com.waseel.prescription.model.dispense.PrescriptionDispenseRequestModel;
import com.waseel.prescription.model.dispense.PrescriptionDispenseResponseModel;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.inquiry.InquiryInvalidResponseModel;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryRequestModel;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryResponseModel;
import com.waseel.prescription.model.inquiry.eprescription.EPrescriptionInquiryRequestModel;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryRequestModel;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryResponseModel;
import com.waseel.prescription.model.modifydecision.ModifyDecisionRequestModel;
import com.waseel.prescription.model.modifydecision.ModifyDecisionResponseModel;
import com.waseel.prescription.model.prescription.PayerMemberPhysicianInfoModel;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.persist.mongodb.PrescriptionAuditTrail;
import com.waseel.prescription.repository.mongodb.PrescriptionAuditTrailRepository;
import com.waseel.prescription.service.mapper.MapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Service
public class AuditLogService {

	@Autowired
	MapperService mapperService;

	private static final Logger log = LoggerFactory.getLogger(AuditLogService.class);

	@Autowired
	private PrescriptionAuditTrailRepository prescriptionAuditTrailRepository;

	public void saveAuditLogInMongoDb(PrescriptionRequestModel prescriptionRequest,
			PrescriptionResponseModel prescriptionResponse, RequestType requestType, Long transactionLogId,
			Object invalidPrescriptionRequest) {
		try {
			CompletableFuture.runAsync(() ->
			// Used to save data in background -- CompletableFuture.runAsync
			prescriptionAuditTrailRepository.save(setPrescriptionAuditData(prescriptionRequest, prescriptionResponse,
					requestType, transactionLogId, invalidPrescriptionRequest)));
		} catch (Exception e) {
			log.error("", e);
		}
	}

	public void saveAuditLogInMongoDbForDetailInquiry(PrescriptionDetailInquiryRequestModel detailInquiryRequestModel,
			PrescriptionDetailInquiryResponseModel detailInquiryResponseModel,
			InquiryInvalidResponseModel invalidResponseModel, RequestType requestType, Long transactionLogId,
			String requestId, Object invalidDetailInquiryRequest) {
		try {
			CompletableFuture.runAsync(() ->
			// Used to save data in background -- CompletableFuture.runAsync
			prescriptionAuditTrailRepository.save(setPrescriptionInquiryAuditData(detailInquiryRequestModel,
					detailInquiryResponseModel, invalidResponseModel, requestType, transactionLogId, requestId,
					invalidDetailInquiryRequest)));
		} catch (Exception e) {
			log.error("", e);
		}
	}

	public void saveAuditLogInMongoDbForInquiryDetail(PayerMemberPhysicianInfoModel payerMemberPhysicianInfoModel,
			PrescriptionResponseModel invalidResponseModel, RequestType requestType, Long transactionLogId,
			String requestId) {
		try {
			CompletableFuture.runAsync(() ->
			// Used to save data in background -- CompletableFuture.runAsync
			prescriptionAuditTrailRepository.save(setPrescriptionInquiryDetailAuditData(payerMemberPhysicianInfoModel,
					invalidResponseModel, requestType, transactionLogId, requestId)));
		} catch (Exception e) {
			log.error("", e);
		}
	}

	public void saveAuditLogInMongoDbForSummaryInquiry(PrescriptionSummaryRequestModel summaryInquiryRequestModel,
			PrescriptionSummaryResponseModel summaryInquiryResponseModel,
			InquiryInvalidResponseModel invalidResponseModel, RequestType requestType, String transactionLogId,
			String requestId) {
		try {
			CompletableFuture.runAsync(() ->
			// Used to save data in background -- CompletableFuture.runAsync
			prescriptionAuditTrailRepository.save(setPrescriptionSummaryInquiryAuditData(summaryInquiryRequestModel,
					summaryInquiryResponseModel, invalidResponseModel, requestType, transactionLogId, requestId)));
		} catch (Exception e) {
			log.error("", e);
		}
	}

	public void saveAuditLogInMongoDbForDispense(PrescriptionDispenseRequestModel dispensedRequestModel,
			PrescriptionDispenseResponseModel dispensedResponseModel,
			DispenseDrugsRequestModel dispenseDrugsRequestModel, RequestType requestType, Long transactionLogId,
			String requestId, Object invalidDetailInquiryRequest) {
		try {
			CompletableFuture.runAsync(() ->
			// Used to save data in background -- CompletableFuture.runAsync
			prescriptionAuditTrailRepository.save(setPrescriptionDispenseAuditData(dispensedRequestModel,
					dispensedResponseModel, dispenseDrugsRequestModel, requestType, transactionLogId, requestId,
					invalidDetailInquiryRequest)));
		} catch (Exception e) {
			log.error("", e);
		}
	}

	public void saveAuditLogInMongoDbForModifyDecision(ModifyDecisionRequestModel requestModel,
			ModifyDecisionResponseModel responseModel, Long transactionLogId, String ePrescriptionRefNum) {
		try {
			CompletableFuture.runAsync(() ->
			// Used to save data in background -- CompletableFuture.runAsync
			prescriptionAuditTrailRepository.save(
					setModifyDecisionAuditData(requestModel, responseModel, transactionLogId, ePrescriptionRefNum)));
		} catch (Exception e) {
			log.error("", e);
		}
	}

	private PrescriptionAuditTrail setPrescriptionInquiryAuditData(
			PrescriptionDetailInquiryRequestModel detailInquiryRequestModel,
			PrescriptionDetailInquiryResponseModel detailInquiryResponseModel,
			InquiryInvalidResponseModel invalidResponseModel, RequestType requestType, Long transactionLogId,
			String requestId, Object invalidDetailInquiryRequest) {
		PrescriptionAuditTrail audit = new PrescriptionAuditTrail();
		audit.setDetailInquiryRequestModel(detailInquiryRequestModel);
		audit.setInvalidPrescriptionRequest(invalidDetailInquiryRequest);
		audit.setDetailInquiryResponseModel(detailInquiryResponseModel);
		audit.setInquiryInvalidResponseModel(invalidResponseModel);
		audit.setDateTime(new Date());
		audit.setRequestType(requestType.name());
		audit.setRequestId(requestId);
		audit.setPrescriptionTransactionLogId(transactionLogId);
		return audit;
	}

	private PrescriptionAuditTrail setPrescriptionInquiryDetailAuditData(PayerMemberPhysicianInfoModel model,
			PrescriptionResponseModel invalidResponseModel, RequestType requestType, Long transactionLogId,
			String requestId) {
		PrescriptionAuditTrail audit = new PrescriptionAuditTrail();
		audit.setPayerMemberPhysicianInfoModel(model);
		audit.setPrescriptionResponse(invalidResponseModel);
		audit.setDateTime(new Date());
		audit.setRequestType(requestType.name());
		audit.setRequestId(requestId);
		audit.setPrescriptionTransactionLogId(transactionLogId);
		return audit;
	}

	private PrescriptionAuditTrail setPrescriptionDispenseAuditData(
			PrescriptionDispenseRequestModel dispensedRequestModel,
			PrescriptionDispenseResponseModel dispensedResponseModel,
			DispenseDrugsRequestModel dispenseDrugsRequestModel, RequestType requestType, Long transactionLogId,
			String requestId, Object invalidDetailInquiryRequest) {
		PrescriptionAuditTrail audit = new PrescriptionAuditTrail();
		audit.setInvalidPrescriptionRequest(invalidDetailInquiryRequest);
		audit.setDispensedRequestModel(dispensedRequestModel);
		audit.setDispenseDrugsRequestModel(dispenseDrugsRequestModel);
		audit.setDispensedResponseModel(dispensedResponseModel);
		audit.setDateTime(new Date());
		audit.setRequestType(requestType.name());
		audit.setRequestId(requestId);
		audit.setPrescriptionTransactionLogId(transactionLogId);
		return audit;
	}

	private PrescriptionAuditTrail setPrescriptionAuditData(PrescriptionRequestModel prescriptionRequest,
			PrescriptionResponseModel prescriptionResponse, RequestType requestType, Long transactionLogId,
			Object invalidPrescriptionRequest) {
		PrescriptionAuditTrail audit = new PrescriptionAuditTrail();
		audit.setPrescriptionRequest(prescriptionRequest);
		audit.setPrescriptionResponse(prescriptionResponse);
		audit.setInvalidPrescriptionRequest(invalidPrescriptionRequest);
		audit.setDateTime(new Date());
		audit.setRequestType(requestType.name());
		if (prescriptionResponse != null) {
			audit.setRequestId(prescriptionResponse.getRequestId());
		}
		if (transactionLogId != null) {
			audit.setPrescriptionTransactionLogId(transactionLogId);
		}
		return audit;
	}

	public PrescriptionAuditTrail setPrescriptionSummaryInquiryAuditData(PrescriptionSummaryRequestModel summaryRequest,
			PrescriptionSummaryResponseModel summaryResponse, InquiryInvalidResponseModel invalidResponseModel,
			RequestType requestType, String transactionLogId, String requestId) {
		PrescriptionAuditTrail audit = new PrescriptionAuditTrail();
		audit.setSummaryInquiryRequestModel(summaryRequest);
		audit.setSummaryInquiryResponseModel(summaryResponse);
		audit.setInquiryInvalidResponseModel(invalidResponseModel);
		audit.setDateTime(new Date());
		audit.setRequestType(requestType.name());
		audit.setRequestId(requestId);
		if (transactionLogId != null)
			audit.setPrescriptionTransactionLogId(Long.valueOf(transactionLogId));
		return audit;
	}

	public void saveEPrescriptionInquiryAuditData(EPrescriptionInquiryRequestModel requestModel) {
		try {
			CompletableFuture.runAsync(
					() -> prescriptionAuditTrailRepository.save(setEPrescriptionInquiryAuditData(requestModel)));
		} catch (Exception e) {
			log.error("", e);
		}
	}

	private PrescriptionAuditTrail setEPrescriptionInquiryAuditData(EPrescriptionInquiryRequestModel requestModel) {
		PrescriptionAuditTrail audit = new PrescriptionAuditTrail();
		audit.setePrescriptionInquiryRequestModel(requestModel);
		audit.setDateTime(new Date());
		audit.setRequestType(requestModel.getRequestType());
		return audit;
	}

	private PrescriptionAuditTrail setModifyDecisionAuditData(ModifyDecisionRequestModel requestModel,
			ModifyDecisionResponseModel responseModel, Long transactionLogId, String ePrescriptionRefNum) {
		PrescriptionAuditTrail audit = new PrescriptionAuditTrail();
		audit.setModifyDecisionRequestModel(requestModel);
		audit.setModifyDecisionResponseModel(responseModel);
		audit.setDateTime(new Date());
		audit.setPrescriptionTransactionLogId(transactionLogId);
		audit.setePrescriptionReferenceNumber(ePrescriptionRefNum);
		return audit;
	}
}
