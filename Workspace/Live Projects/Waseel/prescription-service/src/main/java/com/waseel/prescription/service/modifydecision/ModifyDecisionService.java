package com.waseel.prescription.service.modifydecision;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.enums.PbmRequestType;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.modifydecision.ModifyDecisionDrugList;
import com.waseel.prescription.model.modifydecision.ModifyDecisionRequestModel;
import com.waseel.prescription.model.modifydecision.ModifyDecisionResponseModel;
import com.waseel.prescription.model.policyconsumption.RecalculatedDrugListModel;
import com.waseel.prescription.model.policyconsumption.RecalculatedPayerAndPatientShareModel;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.service.management.SessionService;
import com.waseel.prescription.service.management.TransactionLogService;
import com.waseel.prescription.service.policyconsumption.PolicyConsumptionService;
import com.waseel.prescription.service.prescriptions.EmailAndSmsNotificationService;
import com.waseel.prescription.service.validation.PrescriptionUpdationValidationService;
import com.waseel.prescription.util.SourceTypeUtil;

@Service
public class ModifyDecisionService {

	@Value(value = "${feature.toggle: false}")
	private boolean featureToggleEnabled;

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private PrescriptionUpdationValidationService prescriptionUpdationValidationService;

	@Autowired
	private EmailAndSmsNotificationService emailAndSmsNotificationService;

	@Autowired
	private PolicyConsumptionService policyConsumptionService;

	@Autowired
	private ModifyDecisionDMLService modifyDecisionDMLService;

	public ModifyDecisionResponseModel modifyDecisionByPayer(ModifyDecisionRequestModel modifyDecisionRequestModel,
			String ePrescriptionReferenceNumber, String payerId, ContentCachingRequestWrapper requestWrapper,
			String headerOrigin) throws PrescriptionException {
		String sourceType = SourceTypeUtil.getSourceTypeBasedOnHeaderOrigin(headerOrigin);
		PrescriptionRequest prescriptionRequest = prescriptionUpdationValidationService
				.checkRequiredValidationToUpdatePrescription(modifyDecisionRequestModel.getDrugList(),
						ePrescriptionReferenceNumber);
		addInTransactionLog(prescriptionRequest, payerId, ePrescriptionReferenceNumber, requestWrapper, sourceType);
		/*
		 * As per new flow of scientific code, policy consumption is not required as
		 * payer/patient share will be calculated during dispense process
		 */
		if (!featureToggleEnabled) {
			policyConsumptionCheck(modifyDecisionRequestModel, prescriptionRequest);
		}
		modifyDecisionDMLService.updatePrescriptionDetails(prescriptionRequest, modifyDecisionRequestModel, payerId);
		return prepareResponse(ePrescriptionReferenceNumber, prescriptionRequest);
	}

	private ModifyDecisionResponseModel prepareResponse(String ePrescriptionReferenceNumber,
			PrescriptionRequest prescriptionRequest) {
		if (!prescriptionRequest.getStatusCode().equalsIgnoreCase(RequestStatusType.PENDING.value())) {
			emailAndSmsNotificationService.notifyPatientByEmailAndSMS(prescriptionRequest.getRequestId(),
					prescriptionRequest.getMemberInfo().getIdNumber().toString(), ePrescriptionReferenceNumber,
					PbmRequestType.MODIFY_DECISION.value());
		}
		return new ModifyDecisionResponseModel(ePrescriptionReferenceNumber, prescriptionRequest.getStatusCode(),
				prescriptionRequest.getStatusDescription());
	}

	private void addInTransactionLog(PrescriptionRequest prescriptionRequest, String payerId,
			String ePrescriptionReferenceNumber, ContentCachingRequestWrapper requestWrapper, String sourceType) {
		TransactionLog transactionLog = transactionLogService.addInquiryTransaction(RequestType.MODIFY_DECISION,
				payerId, prescriptionRequest.getProviderId(), prescriptionRequest.getRequestId(),
				ePrescriptionReferenceNumber, sourceType);
		if (transactionLog != null && transactionLog.getTransactionLogId() != null) {
			sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
		}
	}

	private void policyConsumptionCheck(ModifyDecisionRequestModel modifyDecisionRequestModel,
			PrescriptionRequest prescriptionRequest) {
		List<RecalculatedDrugListModel> drugList = new ArrayList<>();
		prepareDrugListForPolicyCheck(drugList, modifyDecisionRequestModel.getDrugList());
		RecalculatedPayerAndPatientShareModel recalculatedPayerAndPatientShare = policyConsumptionService
				.fetchPatientAndPatientShareAfterUpdationByPayer(drugList, prescriptionRequest, "", "");
		populatePayerAndPatientShare(modifyDecisionRequestModel, recalculatedPayerAndPatientShare);
	}

	private void prepareDrugListForPolicyCheck(List<RecalculatedDrugListModel> drugList,
			List<ModifyDecisionDrugList> payerDrugList) {
		payerDrugList.stream().forEach(drug -> {
			RecalculatedDrugListModel recalculatedDrug = new RecalculatedDrugListModel();
			recalculatedDrug.setDrugCode(drug.getDrugCode());
			recalculatedDrug.setQuantity(drug.getQuantity());
			recalculatedDrug.setStatus(drug.getStatus());
			recalculatedDrug.setUnitPrice(BigDecimal.valueOf(drug.getUnitPrice()));
			drugList.add(recalculatedDrug);
		});
	}

	private void populatePayerAndPatientShare(ModifyDecisionRequestModel modifyDecisionRequestModel,
			RecalculatedPayerAndPatientShareModel recalculatedPayerAndPatientShare) {
		modifyDecisionRequestModel.setTotalPayerShare(recalculatedPayerAndPatientShare.getTotalPayerShare());
		modifyDecisionRequestModel.setTotalPatientShare(recalculatedPayerAndPatientShare.getTotalPatientShare());
		modifyDecisionRequestModel
				.setTotalPrescriptionValue(recalculatedPayerAndPatientShare.getTotalPrescriptionValue());
		modifyDecisionRequestModel.getDrugList().stream().forEach(drug -> {
			if (recalculatedPayerAndPatientShare != null) {
				recalculatedPayerAndPatientShare.getDrugList().stream()
						.filter(policyCheckedDrug -> policyCheckedDrug.getDrugCode().equals(drug.getDrugCode()))
						.findAny().ifPresent(policyCheckedDrug -> {
							drug.setRecalculatedPatientShare(policyCheckedDrug.getPatientShare());
							drug.setRecalculatedNet(policyCheckedDrug.getPayerShare());
						});
			}
		});
	}
}
