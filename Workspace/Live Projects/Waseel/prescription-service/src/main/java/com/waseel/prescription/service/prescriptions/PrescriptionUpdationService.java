package com.waseel.prescription.service.prescriptions;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.enums.CommonDenialsCode;
import com.waseel.prescription.model.enums.PbmRequestType;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.inquiry.eprescription.EPrescriptionInquiryDrugList;
import com.waseel.prescription.model.inquiry.eprescription.EPrescriptionInquiryError;
import com.waseel.prescription.model.inquiry.eprescription.EPrescriptionInquiryRequestModel;
import com.waseel.prescription.model.inquiry.eprescription.InsuranceCompanyDecision;
import com.waseel.prescription.model.policyconsumption.RecalculatedDrugListModel;
import com.waseel.prescription.model.policyconsumption.RecalculatedPayerAndPatientShareModel;
import com.waseel.prescription.model.prescription.ServiceRejectionDTO;
import com.waseel.prescription.persist.mdss.DrugService;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionApprovalDrug;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.persist.prescriptionservice.ServiceRejection;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.repository.mdss.DrugServiceRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionApprovalDrugRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;
import com.waseel.prescription.service.policyconsumption.PolicyConsumptionService;

@Service
public class PrescriptionUpdationService {

	@Autowired
	private PrescriptionRequestRepository prescriptionRequestRepository;
	@Autowired
	private ServiceInfoRepository serviceInfoRepository;
	@Autowired
	private ServiceResponseInfoRepository serviceResponseInfoRepository;
	@Autowired
	private ServiceRejectionRepository serviceRejectionRepository;
	@Autowired
	private PolicyConsumptionService policyConsumptionService;
	@Autowired
	private EmailAndSmsNotificationService emailAndSmsNotificationService;

	@Value(value = "${feature.toggle: false}")
	private boolean featureToggleEnabled;

	@Autowired
	private DrugServiceRepository drugServiceRepository;

	@Autowired
	private PrescriptionApprovalDrugRepository prescriptionApprovalDrugRepository;

	private static final String E_PRESCRIPTION_REFERENCE_NUMBER_NOT_EXIST_STRING = "EPrescriptionReferenceNumber is not found or exists.";

	public void updatePrescriptionStatus(EPrescriptionInquiryRequestModel ePrescriptionRequestModel)
			throws PrescriptionException {
		String ePrescriptionReferenceNumber = ePrescriptionRequestModel.getePrescriptionReferenceNumber();
		Optional<PrescriptionRequest> prescriptionRequestOptional = prescriptionRequestRepository
				.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		if (prescriptionRequestOptional.isPresent()) {
			PrescriptionRequest prescriptionRequest = prescriptionRequestOptional.get();
			if (!prescriptionRequest.getStatusCode().equals(ePrescriptionRequestModel.getePrescriptionStatus())) {
				if (!featureToggleEnabled) {
					policyConsumptionCheck(ePrescriptionRequestModel, prescriptionRequest);
				}
				updatePrescriptionRequestStatus(ePrescriptionRequestModel, prescriptionRequest);
				if (!prescriptionRequest.getStatusCode().equalsIgnoreCase(RequestStatusType.PENDING.value())) {
					emailAndSmsNotificationService.notifyPatientByEmailAndSMS(prescriptionRequest.getRequestId(),
							ePrescriptionRequestModel.getIdNumber().toString(),
							ePrescriptionRequestModel.getePrescriptionReferenceNumber(),
							PbmRequestType.MODIFY_DECISION.value());
				}
			}
			return;
		}
		throw new PrescriptionException(E_PRESCRIPTION_REFERENCE_NUMBER_NOT_EXIST_STRING);
	}

	@Transactional
	private void updatePrescriptionRequestStatus(EPrescriptionInquiryRequestModel ePrescriptionRequestModel,
			PrescriptionRequest prescriptionRequest) {
		updateServiceResponseInfoStatus(ePrescriptionRequestModel, prescriptionRequest);
		updatePrescriptionRequest(prescriptionRequest, ePrescriptionRequestModel);
	}

	private void updateServiceResponseInfoStatus(EPrescriptionInquiryRequestModel ePrescriptionRequestModel,
			PrescriptionRequest prescriptionRequest) {
		List<EPrescriptionInquiryDrugList> ePrescriptionDrugLists = ePrescriptionRequestModel.getDrugList();
		String requestId = prescriptionRequest.getRequestId();
		ePrescriptionDrugLists.forEach(ePrescriptionDrugList -> {
			ServiceInfo serviceInfo = null;
			String drugCode = ePrescriptionDrugList.getDrugCode();
			Optional<ServiceInfo> optionalServiceInfo = serviceInfoRepository.findByRequestIdAndDrugCode(requestId,
					drugCode);
			if (optionalServiceInfo.isEmpty()) {
				serviceInfo = fetchServiceInfoForDrugWithScientificCode(drugCode, requestId);
			} else {
				serviceInfo = optionalServiceInfo.get();
			}
			if (null != serviceInfo) {
				Optional<ServiceResponseInfo> serviceResponseInfoOptional = serviceResponseInfoRepository
						.findByRequestIdAndServiceIDAndStatusNot(requestId, serviceInfo.getId(),
								ServiceStatus.DISPENSED.name());
				if (serviceResponseInfoOptional.isPresent()) {
					ServiceResponseInfo serviceResponseInfo = serviceResponseInfoOptional.get();
					InsuranceCompanyDecision decision = ePrescriptionDrugList.getInsuranceCompanyDecision();
					if (decision != null) {
						String status = decision.getStatus();
						if (!status.equals(serviceResponseInfo.getStatus())) {
							serviceResponseInfo.setStatus(status);
							serviceResponseInfo.setApprovedAmount(decision.getApprovedAmount());
							serviceResponseInfo.setRequestedAmount(decision.getRequestedAmount());
							serviceResponseInfo.setPatientShare(ePrescriptionDrugList.getPatientShare());
							serviceResponseInfo.setNet(ePrescriptionDrugList.getPayerShare());
							serviceResponseInfoRepository.save(serviceResponseInfo);
							addOrRemoveErrorsInServiceRejection(ePrescriptionDrugList, serviceResponseInfo, requestId,
									status);
							managePrescriptionApprovalDrugEntity(prescriptionRequest.getePrescriptionReferenceNumber(),
									getDrugScientificCode(drugCode, serviceInfo.getDrugListId()), status, drugCode);
						}
					}
				}
			}
		});
	}

	private ServiceInfo fetchServiceInfoForDrugWithScientificCode(String drugCode, String requestId) {
		Optional<List<ServiceInfo>> servicesWithScientificCodeOpt = serviceInfoRepository
				.findByRequestIdAndIsDeletedAndScientificCodeNotNull(requestId, false);
		if (servicesWithScientificCodeOpt.isPresent()) {
			List<ServiceInfo> servicesWithScientificCodes = servicesWithScientificCodeOpt.get();
			Set<Long> drugListIds = servicesWithScientificCodes.stream().map(ServiceInfo::getDrugListId)
					.collect(Collectors.toSet());
			Optional<List<DrugService>> drugServiceOpt = drugServiceRepository
					.findByOtherCodesValueAndDrugListIdIn(drugCode, drugListIds);
			if (drugServiceOpt.isPresent()) {
				List<DrugService> drugDetails = drugServiceOpt.get();
				if (null != drugDetails && !drugDetails.isEmpty()) {
					return servicesWithScientificCodes.stream()
							.filter(serviceInfoWithScientificCode -> drugDetails.stream()
									.anyMatch(drug -> drug.getScientificCode()
											.equals(serviceInfoWithScientificCode.getScientificCode())
											&& drug.getDrugListId() == serviceInfoWithScientificCode.getDrugListId()))
							.findAny().orElse(null);
				}
			}
		}
		return null;
	}

	private void managePrescriptionApprovalDrugEntity(String ePrescriptionReferenceNumber, String scientificCode,
			String status, String drugCode) {
		Timestamp latestUpdateDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		PrescriptionApprovalDrug prescriptionApprovalDrug = null;
		Optional<PrescriptionApprovalDrug> prescriptionApprovalDrugOpt = prescriptionApprovalDrugRepository
				.findByEprescriptionReferenceNumberAndScientificCodeAndSuggestedDrugCode(ePrescriptionReferenceNumber,
						scientificCode, drugCode);
		if (prescriptionApprovalDrugOpt.isPresent()) {
			prescriptionApprovalDrug = prescriptionApprovalDrugOpt.get();
			prescriptionApprovalDrug.setStatus(status);
			prescriptionApprovalDrug.setLatestUpdateDate(latestUpdateDate);
		} else {
			prescriptionApprovalDrug = new PrescriptionApprovalDrug(ePrescriptionReferenceNumber, latestUpdateDate,
					scientificCode, status, drugCode);
		}
		prescriptionApprovalDrugRepository.save(prescriptionApprovalDrug);
	}

	private void addOrRemoveErrorsInServiceRejection(EPrescriptionInquiryDrugList ePrescriptionDrugList,
			ServiceResponseInfo serviceResponseInfo, String requestId, String status) {
		Long serviceResponseId = serviceResponseInfo.getId();
		if (status.equals(ServiceStatus.REJECTED.name())) {
			removePendingDrugRejectionReasonSentForPayerApproval(requestId, serviceResponseId);
			List<ServiceRejection> serviceRejectionList = new ArrayList<>();
			List<EPrescriptionInquiryError> ePrescriptionInquiryErrors = ePrescriptionDrugList
					.getInsuranceCompanyDecision().getErrors();
			ePrescriptionInquiryErrors.forEach(ePrescriptionInquiryError -> {
				ServiceRejection serviceRejection = new ServiceRejection();
				serviceRejection.setDrugCode(ePrescriptionDrugList.getDrugCode());
				serviceRejection.setDenialCode(ePrescriptionInquiryError.getDenialCode());
				serviceRejection.setRejectionReason(ePrescriptionInquiryError.getRejectionReason());
				serviceRejection.setRequestId(requestId);
				serviceRejection.setServiceResponseId(serviceResponseId);
				serviceRejectionList.add(serviceRejection);
			});
			serviceRejectionRepository.saveAll(serviceRejectionList);
			return;
		}
		Optional<List<ServiceRejectionDTO>> optionalServiceRejectionDtoList = serviceRejectionRepository
				.fetchByRequestIdAndServiceResponseId(requestId, serviceResponseId);
		if (optionalServiceRejectionDtoList.isPresent()) {
			List<ServiceRejectionDTO> serviceRejections = optionalServiceRejectionDtoList.get();
			if (null != serviceRejections && !serviceRejections.isEmpty()) {
				serviceRejectionRepository.deleteByRequestIdAndServiceResponseId(requestId, serviceResponseId);
			}
		}
	}

	private void removePendingDrugRejectionReasonSentForPayerApproval(String requestId, Long serviceResponseId) {
		Optional<ServiceRejectionDTO> serviceRejectionDtoOpt = serviceRejectionRepository
				.findByRequestIdAndDenialCodeAndServiceResponseId(requestId,
						CommonDenialsCode.REQUIRED_PAYER_APPROVAL.value(), serviceResponseId);
		if (serviceRejectionDtoOpt.isPresent()) {
			serviceRejectionRepository.deleteByRequestIdAndId(requestId, serviceRejectionDtoOpt.get().getId());
		}
	}

	private String getDrugScientificCode(String drugCode, Long drugListId) {
		Optional<DrugService> drugServiceOpt = drugServiceRepository.findByOtherCodesValueAndDrugListId(drugCode,
				drugListId);
		return drugServiceOpt.isPresent() ? drugServiceOpt.get().getScientificCode() : drugCode;
	}

	private void policyConsumptionCheck(EPrescriptionInquiryRequestModel ePrescriptionRequestModel,
			PrescriptionRequest prescriptionRequest) {
		List<RecalculatedDrugListModel> drugList = new ArrayList<>();
		prepareDrugListForPolicyCheck(drugList, ePrescriptionRequestModel.getDrugList());
		RecalculatedPayerAndPatientShareModel recalculatedPayerAndPatientShare = policyConsumptionService
				.fetchPatientAndPatientShareAfterUpdationByPayer(drugList, prescriptionRequest,
						ePrescriptionRequestModel.getPhysicianLicenseNumber(),
						ePrescriptionRequestModel.getIdNumber().toString());
		populatePayerAndPatientShare(ePrescriptionRequestModel, recalculatedPayerAndPatientShare);
	}

	private void prepareDrugListForPolicyCheck(List<RecalculatedDrugListModel> drugList,
			List<EPrescriptionInquiryDrugList> payerDrugList) {
		payerDrugList.stream().forEach(drug -> {
			RecalculatedDrugListModel recalculatedDrug = new RecalculatedDrugListModel();
			recalculatedDrug.setDrugCode(drug.getDrugCode());
			recalculatedDrug.setQuantity(drug.getQuantity());
			recalculatedDrug.setStatus(
					null != drug.getInsuranceCompanyDecision() ? drug.getInsuranceCompanyDecision().getStatus()
							: drug.getPbmValidationResult().getStatus());
			recalculatedDrug.setUnitPrice(BigDecimal.valueOf(drug.getUnitPrice()));
			drugList.add(recalculatedDrug);
		});
	}

	private void populatePayerAndPatientShare(EPrescriptionInquiryRequestModel ePrescriptionRequestModel,
			RecalculatedPayerAndPatientShareModel recalculatedPayerAndPatientShare) {
		ePrescriptionRequestModel.setTotalPayerShareValue(recalculatedPayerAndPatientShare.getTotalPayerShare());
		ePrescriptionRequestModel.setTotalPatientShareValue(recalculatedPayerAndPatientShare.getTotalPatientShare());
		ePrescriptionRequestModel
				.setTotalPrescriptionPrice(recalculatedPayerAndPatientShare.getTotalPrescriptionValue());
		ePrescriptionRequestModel.getDrugList().stream()
				.forEach(drug -> recalculatedPayerAndPatientShare.getDrugList().stream()
						.filter(policyCheckedDrug -> policyCheckedDrug.getDrugCode().equals(drug.getDrugCode()))
						.findAny().ifPresent(policyCheckedDrug -> {
							drug.setPatientShare(policyCheckedDrug.getPatientShare());
							drug.setPayerShare(policyCheckedDrug.getPayerShare());
						}));
	}

	private void updatePrescriptionRequest(PrescriptionRequest prescriptionRequest,
			EPrescriptionInquiryRequestModel ePrescriptionRequestModel) {
		String requestId = prescriptionRequest.getRequestId();
		String prescriptionStatus = serviceResponseInfoRepository.fetchPrescriptionStatusCodeByRequestId(requestId);
		prescriptionRequest.setStatusCode(prescriptionStatus);
		prescriptionRequest.setLastUpdateDate(new Date());
		prescriptionRequest.setStatusDescription(getPrescriptionStatusDescription(requestId));
		prescriptionRequest.setPayerShare(ePrescriptionRequestModel.getTotalPatientShareValue());
		prescriptionRequest.setPatientShare(ePrescriptionRequestModel.getTotalPatientShareValue());
		prescriptionRequestRepository.save(prescriptionRequest);
	}

	private String getPrescriptionStatusDescription(String requestId) {
		List<String> drugsMedicalRejectionList = serviceRejectionRepository.getAllRejectionsByRequestId(requestId);
		return null != drugsMedicalRejectionList && !drugsMedicalRejectionList.isEmpty()
				? StringUtils.join(drugsMedicalRejectionList, ",")
				: "";
	}
}
