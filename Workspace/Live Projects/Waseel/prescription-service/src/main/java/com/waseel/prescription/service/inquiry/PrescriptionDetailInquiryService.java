package com.waseel.prescription.service.inquiry;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.mapper.MapSuggestedDrugInquiry;
import com.waseel.prescription.model.common.ValidInvalidRequestModel;
import com.waseel.prescription.model.dispense.PrescriptionDrug;
import com.waseel.prescription.model.dispense.SuggestedDrugsModel;
import com.waseel.prescription.model.enums.CommonWords;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryRequestModel;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryResponseModel;
import com.waseel.prescription.model.inquiry.detail.ServiceInquiryResponse;
import com.waseel.prescription.model.inquiry.detail.SuggestedDrugInquiry;
import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.model.prescription.MedicalValidations;
import com.waseel.prescription.persist.prescriptionservice.InvalidPrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.prescriptionservice.DiagnosisRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceRejectionRepository;
import com.waseel.prescription.service.management.SessionService;
import com.waseel.prescription.service.management.TransactionLogService;
import com.waseel.prescription.service.prescriptions.DrugSuggestionsService;
import com.waseel.prescription.service.validation.InquiryTechnicalValidationService;
import com.waseel.prescription.util.SourceTypeUtil;
import com.waseel.prescription.util.UserInfoUtil;

@Service
public class PrescriptionDetailInquiryService {

	@Autowired
	private ServiceInfoRepository serviceInfoRepository;

	@Autowired
	private ServiceRejectionRepository serviceRejectionRepository;

	@Autowired
	private InquiryTechnicalValidationService inquiryTechnicalValidationService;

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private DiagnosisRepository diagnosisRepository;

	@Autowired
	private DrugSuggestionsService drugSuggestionsService;

	public PrescriptionDetailInquiryResponseModel managePrescriptionDetailInquiryRequest(
			PrescriptionDetailInquiryRequestModel detailInquiryRequestModel,
			ContentCachingRequestWrapper requestWrapper, String headerOrigin) throws PrescriptionException {
		String sourceType = SourceTypeUtil.getSourceTypeBasedOnHeaderOrigin(headerOrigin);
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		ValidInvalidRequestModel validInvalidRequestModel = inquiryTechnicalValidationService
				.validateDetailInquiryRequest(detailInquiryRequestModel, requestWrapper, providerId);
		return manageDetailInquiry(validInvalidRequestModel,
				detailInquiryRequestModel.getePrescriptionReferenceNumber(), requestWrapper, providerId,
				detailInquiryRequestModel, sourceType);
	}

	public PrescriptionDetailInquiryResponseModel manageDetailInquiry(ValidInvalidRequestModel validInvalidRequestModel,
			String ePrescriptionReferenceNumber, ContentCachingRequestWrapper requestWrapper, String providerId,
			PrescriptionDetailInquiryRequestModel detailInquiryRequestModel, String sourceType) {
		PrescriptionDetailInquiryResponseModel response = new PrescriptionDetailInquiryResponseModel();
		InvalidPrescriptionRequest invalidPrescriptionRequest = validInvalidRequestModel
				.getInvalidPrescriptionRequest();
		List<ServiceInquiryResponse> serviceInfo = null;
		PrescriptionRequest prescriptionRequest = validInvalidRequestModel.getPrescriptionRequest();
		String requestId = null;
		String status = null;
		String statusDesc = null;
		if (invalidPrescriptionRequest != null) {
			requestId = invalidPrescriptionRequest.getRequestId();
			status = invalidPrescriptionRequest.getStatus();
			statusDesc = invalidPrescriptionRequest.getStatusDescription();
		} else if (prescriptionRequest != null) {
			requestId = prescriptionRequest.getRequestId();
			status = prescriptionRequest.getStatusCode();
			statusDesc = prescriptionRequest.getStatusDescription();
			SuggestedDrugsModel suggestedDrugsModel = getSuggestedDrugs(ePrescriptionReferenceNumber,
					detailInquiryRequestModel.getPayerId());
			serviceInfo = getServiceDetails(requestId, suggestedDrugsModel);
		}
		addInTransactionLog(detailInquiryRequestModel.getPayerId(), providerId, ePrescriptionReferenceNumber, requestId,
				requestWrapper, sourceType);
		return getResponse(response, ePrescriptionReferenceNumber, requestId, status, statusDesc, serviceInfo,
				prescriptionRequest);
	}

	private List<ServiceInquiryResponse> getServiceDetails(String requestId, SuggestedDrugsModel suggestedDrugsModel) {
		List<ServiceInquiryResponse> serviceInfo = serviceInfoRepository.getServiceDetailsOfInquiry(requestId);
		serviceInfo.forEach(service -> {
			processTosetServiceRejections(service, requestId);
			if (suggestedDrugsModel != null) {
				processToSetSuggestedDrugs(service, suggestedDrugsModel);
			}
		});
		return serviceInfo;
	}

	private void processTosetServiceRejections(ServiceInquiryResponse service, String requestId) {
		String drugCode = service.getDrugCode();
		List<MedicalValidations> serviceRejections = getMedicalValidations(drugCode, requestId,
				service.getScientificCode());
		if (serviceRejections != null && !serviceRejections.isEmpty()) {
			service.setErrors(serviceRejections);
		}
	}

	private void processToSetSuggestedDrugs(ServiceInquiryResponse service, SuggestedDrugsModel suggestedDrugsModel) {
		MapSuggestedDrugInquiry instance = MapSuggestedDrugInquiry.INSTANCE;
		PrescriptionDrug matchingPrescriptionDrug = suggestedDrugsModel.getPrescriptionDrugs().stream()
				.filter(pd -> !StringUtils.isBlank(service.getScientificCode())
						&& service.getStatus().equalsIgnoreCase(ServiceStatus.APPROVED.name())
						&& service.getScientificCode().equalsIgnoreCase(pd.getScientificCode()))
				.findFirst().orElse(null);

		if (matchingPrescriptionDrug != null) {
			List<SuggestedDrugInquiry> suggestedDrugInquiry = instance
					.mapPrescriptionDrugToSuggestedDrugInquiry(matchingPrescriptionDrug.getSuggestedDrugs());
			service.setSuggestedDrugs(suggestedDrugInquiry);
		}
	}

	private List<MedicalValidations> getMedicalValidations(String drugCode, String requestId, String scientificCode) {
		if (StringUtils.isBlank(drugCode) || drugCode.equalsIgnoreCase(CommonWords.UNDEFINED.value())) {
			return serviceRejectionRepository.findByRequestIdAndScientificCode(requestId, scientificCode);
		} else {
			return serviceRejectionRepository.findByRequestIdAndDrugCode(requestId, drugCode);
		}
	}

	private PrescriptionDetailInquiryResponseModel getResponse(PrescriptionDetailInquiryResponseModel response,
			String ePrescriptionReferenceNumber, String requestId, String status, String statusDesc,
			List<ServiceInquiryResponse> serviceInfo, PrescriptionRequest preReq) {
		response.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		response.setRequestId(requestId);
		response.setStatus(status);
		response.setStatusDescription(statusDesc);
		if ((preReq != null && serviceInfo != null && !serviceInfo.isEmpty())) {
			response.setResults(serviceInfo);
			response.setCanCancel(preReq.getCanCancel());
			response.setCanFollowUp(preReq.getCanFollowUp());
			response.setDiagnosisCodes(getDiagnosisCodes(preReq.getRequestId()));
			response.setTotalPatientShare(preReq.getPatientShare());
			response.setTotalPatientShareCurrency(preReq.getPatientShareCurrency());
			response.setTotalPayerShare(preReq.getPayerShare());
			response.setTotalPayerShareCurrency(preReq.getPayerShareCurrency());
		}
		return response;
	}

	private List<DiagnosisCodes> getDiagnosisCodes(String requestId) {
		return diagnosisRepository.findByRequestIdAndIsNotDeleted(requestId);
	}

	private void addInTransactionLog(String payerId, String providerId, String ePrescriptionReferenceNumber,
			String requestId, ContentCachingRequestWrapper requestWrapper, String sourceType) {
		TransactionLog transactionLog = transactionLogService.addInquiryTransaction(RequestType.DETAIL_INQUIRY, payerId,
				providerId, requestId, ePrescriptionReferenceNumber, sourceType);
		if (transactionLog != null && transactionLog.getTransactionLogId() != null) {
			sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
		}
	}

	private SuggestedDrugsModel getSuggestedDrugs(String ePrescriptionReferenceNumber, String payerId) {
		try {
			return drugSuggestionsService.getSuggestedDrugs(ePrescriptionReferenceNumber, payerId, false);
		} catch (PrescriptionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
