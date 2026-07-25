package com.waseel.prescription.service.modifydecision;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waseel.prescription.model.enums.CommonDenialsCode;
import com.waseel.prescription.model.enums.CommonWords;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.modifydecision.ModifyDecisionDrugList;
import com.waseel.prescription.model.modifydecision.ModifyDecisionRequestModel;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionApprovalDrug;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.persist.prescriptionservice.ServiceRejection;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionApprovalDrugRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;

@Service
public class ModifyDecisionDMLService {

	@Autowired
	private ServiceResponseInfoRepository serviceResponseInfoRepository;

	@Autowired
	private ServiceRejectionRepository serviceRejectionRepository;

	@Autowired
	private PrescriptionRequestRepository prescriptionRequestRepository;

	@Autowired
	private ServiceInfoRepository serviceInfoRepository;

	@Autowired
	private PrescriptionApprovalDrugRepository prescriptionApprovalDrugRepository;

	@Transactional("PrescriptionServiceTransactionManager")
	public void updatePrescriptionDetails(PrescriptionRequest prescriptionRequest,
			ModifyDecisionRequestModel modifyDecisionRequestModel, String payerId) {
		List<ModifyDecisionDrugList> drugList = modifyDecisionRequestModel.getDrugList();
		List<String> prescriptionStatusDescList = new ArrayList<>();
		updateDrugLevelDetails(drugList, prescriptionRequest, payerId, prescriptionStatusDescList);
		updatePrescriptionRequest(prescriptionRequest, prescriptionStatusDescList, modifyDecisionRequestModel);
	}

	private void updatePrescriptionRequest(PrescriptionRequest prescriptionRequest,
			List<String> prescriptionStatusDescList, ModifyDecisionRequestModel modifyDecisionRequestModel) {
//		String prescriptionStatus = preparePrescriptionStatus(prescriptionRequest.getRequestId());
		String prescriptionStatus = serviceResponseInfoRepository.fetchPrescriptionStatusCodeByRequestId(prescriptionRequest.getRequestId());
		prescriptionRequest.setStatusCode(prescriptionStatus);
		prescriptionRequest.setStatusDescription(preparePrescriptionStatusDescription(prescriptionStatusDescList));
		prescriptionRequest.setLastUpdateDate(new Date());
		if (modifyDecisionRequestModel.getTotalPayerShare() != null
				&& modifyDecisionRequestModel.getTotalPatientShare() != null) {
			prescriptionRequest.setPayerShare(modifyDecisionRequestModel.getTotalPayerShare());
			prescriptionRequest.setPatientShare(modifyDecisionRequestModel.getTotalPatientShare());
		}
		prescriptionRequestRepository.save(prescriptionRequest);
	}

	private String preparePrescriptionStatus(String requestId) {
		List<ServiceResponseInfo> serviceResInfoList = serviceResponseInfoRepository
				.findByIsNotDeletedDrugAndRequestId(requestId);
		long distinctStatusCount = serviceResInfoList.stream().map(drug -> drug.getStatus().toLowerCase()).distinct()
				.count();
		return distinctStatusCount <= 1 ? serviceResInfoList.get(0).getStatus().toUpperCase()
				: RequestStatusType.PARTIAL_APPROVED.value();
	}

	private String preparePrescriptionStatusDescription(List<String> prescriptionStatusDescList) {
		return !prescriptionStatusDescList.isEmpty() ? StringUtils.strip(prescriptionStatusDescList.toString(), "[]")
				: null;
	}

	private void updateDrugLevelDetails(List<ModifyDecisionDrugList> modifyDecisionDrugList,
			PrescriptionRequest prescriptionRequest, String payerId, List<String> prescriptionStatusDescList) {
		String requestId = prescriptionRequest.getRequestId();
		modifyDecisionDrugList.forEach(requestedDrug -> {
			Optional<ServiceInfo> serviceInfoOpt = serviceInfoRepository
					.findByRequestIdAndDrugCodeAndIsDeleted(requestId, requestedDrug.getDrugCode(), false);

			ServiceInfo serviceInfo = serviceInfoOpt.or(() -> serviceInfoRepository
					.findByRequestIdAndScientificCodeAndIsDeleted(requestId, requestedDrug.getScientificCode(), false))
					.orElse(null);
			if (null != serviceInfo) {
				Optional<ServiceResponseInfo> serviceResponseInfoOpt = serviceResponseInfoRepository
						.findByRequestIdAndServiceID(requestId, serviceInfo.getId());
				if (serviceResponseInfoOpt.isPresent()) {
					ServiceResponseInfo serviceResponseInfo = serviceResponseInfoOpt.get();
					addOrUpdateServiceResponseInfoAndServiceRejection(serviceResponseInfo, requestId, payerId,
							requestedDrug, prescriptionStatusDescList, serviceInfo.getScientificCode(), serviceInfo,
							prescriptionRequest.getePrescriptionReferenceNumber());
				}
			}
		});
	}

	private void addOrUpdateServiceResponseInfoAndServiceRejection(ServiceResponseInfo serviceResponseInfo,
			String requestId, String payerId, ModifyDecisionDrugList requestedDrug,
			List<String> prescriptionStatusDescList, String scientificCode, ServiceInfo serviceInfo,
			String ePrescriptionReferenceNumber) {
		String reqDrugStatus = requestedDrug.getStatus();
		String serviceResInfoStatus = serviceResponseInfo.getStatus();
		Long serviceResponseInfoId = serviceResponseInfo.getId();

		if (serviceResInfoStatus.equalsIgnoreCase(ServiceStatus.PENDING.name())
				&& !reqDrugStatus.equalsIgnoreCase(ServiceStatus.PENDING.name())) {
			serviceRejectionRepository.deleteByRequestIdAndDenialCodeAndScientificCode(requestId,
					CommonDenialsCode.REQUIRED_PAYER_APPROVAL.value(), scientificCode);
		}

		if (reqDrugStatus.equalsIgnoreCase(ServiceStatus.REJECTED.name())) {
			if (!reqDrugStatus.equalsIgnoreCase(serviceResInfoStatus)) {
				serviceResponseInfo.setApprovedAmount(BigDecimal.ZERO);
				addErrorInServiceRejection(requestId, payerId, requestedDrug, serviceResponseInfoId);
				prescriptionStatusDescList.add(requestedDrug.getDecisionDescription());
				serviceResponseInfo.setOverrideDescription(requestedDrug.getDecisionDescription());
			} else {
				List<ServiceRejection> serviceRejectionList = getListOfServiceRejection(requestId,
						serviceResponseInfoId);
				if (!serviceRejectionList.isEmpty()) {
					serviceRejectionList.forEach(
							serviceRejection -> prescriptionStatusDescList.add(serviceRejection.getRejectionReason()));
				}
			}
		} else if (reqDrugStatus.equalsIgnoreCase(ServiceStatus.APPROVED.name())
				&& !reqDrugStatus.equalsIgnoreCase(serviceResInfoStatus)) {
			serviceResponseInfo.setApprovedAmount(serviceResponseInfo.getRequestedAmount());
			setTrueForColumnModifiedByPayer(requestId, serviceResponseInfoId);
			serviceResponseInfo.setOverrideDescription(requestedDrug.getDecisionDescription());
		}
		serviceResponseInfo.setStatus(reqDrugStatus);
		serviceResponseInfo.setOverrideDecision(true);
		serviceResponseInfo.setPatientShare(requestedDrug.getRecalculatedPatientShare());
		serviceResponseInfo.setNet(requestedDrug.getRecalculatedNet());
		serviceResponseInfoRepository.save(serviceResponseInfo);
		if (StringUtils.isNotBlank(reqDrugStatus) && StringUtils.isNotBlank(serviceResInfoStatus)
				&& reqDrugStatus.equalsIgnoreCase(ServiceStatus.APPROVED.name())
				&& !reqDrugStatus.equalsIgnoreCase(serviceResInfoStatus)
				&& serviceResInfoStatus.equals(ServiceStatus.PENDING.name())) {
			manageApprovedDrug(serviceInfo, ePrescriptionReferenceNumber, reqDrugStatus);
		}
	}

	private void addErrorInServiceRejection(String requestId, String payerId,
			ModifyDecisionDrugList modifyDecisionDrugList, Long serviceResponseInfoId) {
		ServiceRejection serviceRejection = new ServiceRejection();
		serviceRejection.setDrugCode(modifyDecisionDrugList.getDrugCode());
		serviceRejection.setRequestId(requestId);
		serviceRejection.setRejectionReason(modifyDecisionDrugList.getDecisionDescription());
		serviceRejection.setServiceResponseId(serviceResponseInfoId);
		serviceRejection.setDenialCode(generateDenialCodeBasedOnPayer(payerId));
		serviceRejectionRepository.save(serviceRejection);
	}

	private void setTrueForColumnModifiedByPayer(String requestId, Long serviceResponseInfoId) {
		List<ServiceRejection> serviceRejectionList = getListOfServiceRejection(requestId, serviceResponseInfoId);
		if (!serviceRejectionList.isEmpty()) {
			List<ServiceRejection> serviceRejections = new ArrayList<>();
			serviceRejectionList.forEach(serviceRejection -> {
				serviceRejection.setModifiedByPayer(true);
				serviceRejections.add(serviceRejection);
			});
			serviceRejectionRepository.saveAll(serviceRejectionList);
		}
	}

	private List<ServiceRejection> getListOfServiceRejection(String requestId, Long serviceResponseInfoId) {
		Optional<List<ServiceRejection>> serviceRejectionListOpt = serviceRejectionRepository
				.findByRequestIdAndServiceResponseId(requestId, serviceResponseInfoId);
		if (serviceRejectionListOpt.isPresent()) {
			return serviceRejectionListOpt.get();
		}
		return Collections.emptyList();
	}

	private String generateDenialCodeBasedOnPayer(String payerId) {
		return CommonDenialsCode.MODIFY_BY_PAYER_CODE.value().replace("<PayerId>", payerId);
	}

	private void manageApprovedDrug(ServiceInfo serviceInfo, String ePrescriptionReferenceNumber, String payerStatus) {
		String drugCode = serviceInfo.getDrugCode();
		if (StringUtils.isNotBlank(drugCode) && !CommonWords.UNDEFINED.value().equals(drugCode)) {
			prescriptionApprovalDrugRepository
					.findByEprescriptionReferenceNumberAndSuggestedDrugCodeAndStatus(ePrescriptionReferenceNumber,
							drugCode, ServiceStatus.PENDING.name())
					.ifPresent(prescriptionApprovalDrug -> approvePendingDrug(prescriptionApprovalDrug, payerStatus));
		} else {
			List<PrescriptionApprovalDrug> prescriptionApprovalDrugList = prescriptionApprovalDrugRepository
					.findByEprescriptionReferenceNumberAndScientificCodeAndStatus(ePrescriptionReferenceNumber,
							serviceInfo.getScientificCode(), ServiceStatus.PENDING.name());
			if (null != prescriptionApprovalDrugList && !prescriptionApprovalDrugList.isEmpty()) {
				prescriptionApprovalDrugList.stream()
						.forEach(prescriptionApprovalDrug -> approvePendingDrug(prescriptionApprovalDrug, payerStatus));
			}
		}
	}

	private void approvePendingDrug(PrescriptionApprovalDrug prescriptionApprovalDrug, String status) {
		prescriptionApprovalDrug.setStatus(status);
		prescriptionApprovalDrug.setLatestUpdateDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		prescriptionApprovalDrugRepository.save(prescriptionApprovalDrug);
	}
}
