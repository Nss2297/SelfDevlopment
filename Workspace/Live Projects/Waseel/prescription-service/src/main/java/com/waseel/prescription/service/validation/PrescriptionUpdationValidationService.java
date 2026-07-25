package com.waseel.prescription.service.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.enums.CommonWords;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.modifydecision.ModifyDecisionDrugList;
import com.waseel.prescription.model.prescription.ServiceDetailsModel;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;

@Service
public class PrescriptionUpdationValidationService {

	@Autowired
	private ServiceInfoRepository serviceInfoRepository;
	@Autowired
	private PrescriptionRequestRepository prescriptionRequestRepository;
	@Autowired
	private ServiceResponseInfoRepository serviceResponseInfoRepository;

	public PrescriptionRequest checkRequiredValidationToUpdatePrescription(
			List<ModifyDecisionDrugList> modifyDecisionDrugList, String ePrescriptionReferenceNumber)
			throws PrescriptionException {
		PrescriptionRequest prescriptionRequest = checkEPrescriptionRefNumberIsValid(ePrescriptionReferenceNumber);
		String requestId = prescriptionRequest.getRequestId();
		checkPrescriptionStatusIsPending(prescriptionRequest);
		checkIfAnyDrugIsDispense(requestId, modifyDecisionDrugList);
		checkAllTheDrugsComesInRequest(requestId, modifyDecisionDrugList);
		checkDecisionDescMandatoryWhenStatusChanged(modifyDecisionDrugList, requestId);
		return prescriptionRequest;
	}

	private void checkDecisionDescMandatoryWhenStatusChanged(List<ModifyDecisionDrugList> modifyDecisionDrugList,
			String requestId) throws PrescriptionException {
		Set<String> drugResponseStatus = Arrays
				.stream(ServiceStatus.values()).filter(status -> status.name().equals("APPROVED")
						|| status.name().equals("REJECTED") || status.name().equals("PENDING"))
				.map(ServiceStatus::name).collect(Collectors.toSet());
		List<ServiceDetailsModel> serviceDetails = serviceResponseInfoRepository
				.getIsNotDeletedDrugAndRequestId(requestId, drugResponseStatus);

		if (serviceDetails != null && !serviceDetails.isEmpty()) {
			List<String> drugList = modifyDecisionDrugList.stream()
					.filter(modifyDecisionDrug -> serviceDetails.stream()
							.anyMatch(serviceInfo -> isMatchingDrug(modifyDecisionDrug, serviceInfo)
									&& !serviceInfo.getStatus().equalsIgnoreCase(modifyDecisionDrug.getStatus())
									&& StringUtils.isBlank(modifyDecisionDrug.getDecisionDescription())))
					.map(modifiedDrug -> StringUtils.isNotBlank(modifiedDrug.getDrugCode())
							&& !modifiedDrug.getDrugCode().equalsIgnoreCase(CommonWords.UNDEFINED.value())
									? modifiedDrug.getDrugCode()
									: modifiedDrug.getScientificCode())
					.collect(Collectors.toList());
			;

			if (!drugList.isEmpty()) {
				throw new PrescriptionException(
						"Decision description is mandatory for drugCode " + drugList.toString());
			}
		}
	}

	private Boolean isMatchingDrug(ModifyDecisionDrugList modifyDecisionDrug, ServiceDetailsModel serviceDetails) {
		if (StringUtils.isNotBlank(serviceDetails.getDrugCode())
				&& !serviceDetails.getDrugCode().equalsIgnoreCase(CommonWords.UNDEFINED.value())) {
			return serviceDetails.getDrugCode().equals(modifyDecisionDrug.getDrugCode());
		} else {
			return StringUtils.isNotBlank(serviceDetails.getScientificCode())
					&& serviceDetails.getScientificCode().equals(modifyDecisionDrug.getScientificCode());
		}
	}

	private PrescriptionRequest checkEPrescriptionRefNumberIsValid(String ePrescriptionReferenceNumber)
			throws PrescriptionException {
		Optional<PrescriptionRequest> prescriptionRequestOptional = prescriptionRequestRepository
				.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		if (prescriptionRequestOptional.isPresent()) {
			return prescriptionRequestOptional.get();
		}
		throw new PrescriptionException("EPrescriptionReferenceNumber is not found or exists.");
	}

	private void checkPrescriptionStatusIsPending(PrescriptionRequest prescriptionRequest)
			throws PrescriptionException {
		if (!prescriptionRequest.getStatusCode().equalsIgnoreCase(RequestStatusType.PENDING.value())) {
			throw new PrescriptionException("Modification only allow for Pending Prescription");
		}
	}

	private void checkIfAnyDrugIsDispense(String requestId, List<ModifyDecisionDrugList> modifyDecisionDrugList)
			throws PrescriptionException {
		List<ServiceInfo> serviceInfoList = serviceInfoRepository.findByIsDeletedAndRequestIdAndStatuses(requestId,
				Arrays.asList(ServiceStatus.DISPENSED.name()));
		if (null != serviceInfoList && !serviceInfoList.isEmpty()) {
			List<String> dispensedDrugs = new ArrayList<>();
			serviceInfoList.stream().forEach(serviceInfo -> {
				String drug = StringUtils.isNotBlank(serviceInfo.getDrugCode())
						&& !CommonWords.UNDEFINED.value().equals(serviceInfo.getDrugCode()) ? serviceInfo.getDrugCode()
								: serviceInfo.getScientificCode();
				modifyDecisionDrugList.stream()
						.filter(modifiedDrug -> drug.equals(modifiedDrug.getDrugCode())
								|| drug.equals(modifiedDrug.getScientificCode()))
						.forEach(modifiedDrug -> dispensedDrugs.add(drug));
			});
			if (!dispensedDrugs.isEmpty()) {
				int listSize = dispensedDrugs.size();
				String stateMessage = listSize > 1 ? " are" : " is";
				throw new PrescriptionException(listSize > 1 ? "Drugs "
						: "Drug " + StringUtils.join(dispensedDrugs, ",") + " " + stateMessage + " already dispensed.");
			}
		}
	}

	private void checkAllTheDrugsComesInRequest(String requestId, List<ModifyDecisionDrugList> reqDrugList)
			throws PrescriptionException {
		List<ServiceInfo> serviceInfoList = serviceInfoRepository.findByIsDeletedAndRequestIdAndStatuses(requestId,
				Arrays.asList(ServiceStatus.APPROVED.name(), ServiceStatus.PENDING.name(),
						ServiceStatus.REJECTED.name()));
		if (serviceInfoList != null && !serviceInfoList.isEmpty()) {
			List<String> availableDrugsInDB = serviceInfoList.stream().map(this::findDrug).collect(Collectors.toList());
			boolean isAllMatchFound = availableDrugsInDB.stream()
					.allMatch(drug -> comparePayerRequestDrugsWithPrescribedDrug(reqDrugList, drug));
			if (!isAllMatchFound) {
				throw new PrescriptionException(
						"Please provide the decision for all the drugs" + availableDrugsInDB.toString() + ".");
			}
		}
	}

	private String findDrug(ServiceInfo serviceInfo) {
		return StringUtils.isNotBlank(serviceInfo.getDrugCode())
				&& !serviceInfo.getDrugCode().equalsIgnoreCase(CommonWords.UNDEFINED.value())
						? serviceInfo.getDrugCode()
						: serviceInfo.getScientificCode();
	}

	private Boolean comparePayerRequestDrugsWithPrescribedDrug(List<ModifyDecisionDrugList> payerRequestedDrugs,
			String prescribedDrug) {
		return payerRequestedDrugs.stream()
				.anyMatch(payerRequestedDrug -> (StringUtils.isNotBlank(payerRequestedDrug.getDrugCode())
						&& payerRequestedDrug.getDrugCode().equalsIgnoreCase(prescribedDrug))
						|| (StringUtils.isNotBlank(payerRequestedDrug.getScientificCode())
								&& payerRequestedDrug.getScientificCode().equalsIgnoreCase(prescribedDrug)));
	}
}
