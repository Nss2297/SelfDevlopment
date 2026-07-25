package com.waseel.prescription.service.prescriptions;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.modifydecision.ProviderOverrideDecisionRequestModel;
import com.waseel.prescription.model.prescription.ModifyDssDecisionResponseModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.persist.prescriptionservice.OverriddenMedication;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.repository.prescriptionservice.OverriddenMedicationRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;
import com.waseel.prescription.util.UserInfoUtil;

@Service
public class ProviderPrescriptionUpdationService {

	@Autowired
	private ServiceResponseInfoRepository serviceResponseInfoRepository;
	@Autowired
	private ServiceRejectionRepository serviceRejectionRepository;
	@Autowired
	PrescriptionRequestRepository prescriptionRequestRepository;
	@Autowired
	OverriddenMedicationRepository overriddenMedicationRepository;

	public ModifyDssDecisionResponseModel updateDssDecision(String ePrescriptionReferenceNumber,
			ProviderOverrideDecisionRequestModel overrideDecisionRequestModel) throws PrescriptionException {
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		PrescriptionRequest prescriptionRequest = checkEPrescriptionRefNumberIsValid(ePrescriptionReferenceNumber,
				providerId);
		Long serviceResponseId = updateDrugStatus(overrideDecisionRequestModel, prescriptionRequest.getRequestId());
		serviceRejectionRepository.deleteByRequestIdAndServiceResponseIdAndDenialCode(
				prescriptionRequest.getRequestId(), serviceResponseId);
		List<String> rejectionReasons = serviceRejectionRepository
				.findRejectionReasonByRequestId(prescriptionRequest.getRequestId());
		saveOverriddenMedication(ePrescriptionReferenceNumber, overrideDecisionRequestModel,
				prescriptionRequest.getPayerId());
		return new ModifyDssDecisionResponseModel(
				updatePrescriptionRequestStatus(prescriptionRequest, rejectionReasons));

	}

	private OverriddenMedication saveOverriddenMedication(String ePrescriptionReferenceNumber,
			ProviderOverrideDecisionRequestModel overrideDecisionRequestModel, String payerId)
			throws PrescriptionException {
		try {
			String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
			String providerName = UserInfoUtil.getAccName(SecurityContextHolder.getContext().getAuthentication());
			String overriddenBy = UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication());
			OverriddenMedication overriddenMedication = new OverriddenMedication();
			overriddenMedication.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
			overriddenMedication.setProviderId(providerId);
			overriddenMedication.setProviderName(providerName);
			overriddenMedication.setPayerId(payerId);
			overriddenMedication.setDrugCode(overrideDecisionRequestModel.getDrugCode());
			overriddenMedication.setDrugName(overrideDecisionRequestModel.getDrugName());
			overriddenMedication.setScientificCode(overrideDecisionRequestModel.getScientificCode());
			overriddenMedication.setScientificName(overrideDecisionRequestModel.getScientificName());
			overriddenMedication.setDenialCode(overrideDecisionRequestModel.getDenialCode());
			overriddenMedication.setRejectionReason(overrideDecisionRequestModel.getRejectionReason());
			overriddenMedication.setOverridingReason(overrideDecisionRequestModel.getOverridingReason());
			overriddenMedication.setOverriddenDate(new Timestamp(new Date().getTime()));
			overriddenMedication.setOverriddenBy(overriddenBy);
			return overriddenMedicationRepository.save(overriddenMedication);
		} catch (Exception e) {
			throw new PrescriptionException(
					new PrescriptionResponseModel(400, "Couldn't save overriden drugs into log table."));
		}
	}

	private Long updateDrugStatus(ProviderOverrideDecisionRequestModel overrideDecisionRequestModel, String requestId)
			throws PrescriptionException {
		Optional<ServiceResponseInfo> serviceInfoBrand = serviceResponseInfoRepository
				.findByRequestIdAndDrugCode(requestId, overrideDecisionRequestModel.getDrugCode());
		Optional<ServiceResponseInfo> serviceInfoScientific = serviceResponseInfoRepository
				.findByRequestIdAndScientificCode(requestId, overrideDecisionRequestModel.getScientificCode());
		if (serviceInfoBrand.isPresent()) {
			ServiceResponseInfo serviceResponseInfo = serviceInfoBrand.get();
			serviceResponseInfo.setStatus("APPROVED");
			serviceResponseInfoRepository.save(serviceResponseInfo);
			return serviceResponseInfo.getId();
		} else if (!serviceInfoBrand.isPresent() && serviceInfoScientific.isPresent()) {
			ServiceResponseInfo serviceScientific = serviceInfoScientific.get();
			serviceScientific.setStatus("APPROVED");
			serviceResponseInfoRepository.save(serviceScientific);
			return serviceScientific.getId();
		} else {
			throw new PrescriptionException(new PrescriptionResponseModel(400,
					"The drug couldn't be found associated with this prescription."));
		}
	}

	private String updatePrescriptionRequestStatus(PrescriptionRequest prescriptionRequest,
			List<String> rejectionReasons) throws PrescriptionException {
		try {
			String requestId = prescriptionRequest.getRequestId();
			String prescriptionStatus = serviceResponseInfoRepository.fetchPrescriptionStatusCodeByRequestId(requestId);
			manageCanCancelAndCanFollowup(prescriptionStatus, prescriptionRequest);
			prescriptionRequest.setStatusCode(prescriptionStatus);
			prescriptionRequest.setLastUpdateDate(new Date());
			String updatedPrescriptionStatus = null;
			if (rejectionReasons != null && !rejectionReasons.isEmpty()) {
				updatedPrescriptionStatus = String.join(", ", rejectionReasons) + ".";
			}
			prescriptionRequest.setStatusDescription(updatedPrescriptionStatus);
			prescriptionRequestRepository.save(prescriptionRequest);
			return prescriptionRequest.getStatusCode();
		} catch (Exception e) {
			throw new PrescriptionException(
					new PrescriptionResponseModel(400, "Couldn't update prescription new status."));
		}
	}

	private void manageCanCancelAndCanFollowup(String prescriptionStatus, PrescriptionRequest prescriptionRequest) {
		if (prescriptionStatus.equalsIgnoreCase("REJECTED")) {
			prescriptionRequest.setCanCancel(false);
			prescriptionRequest.setCanFollowUp(true);
		} else if (prescriptionStatus.equalsIgnoreCase("CANCELLED") || prescriptionStatus.equalsIgnoreCase("DISPENSED")
				|| prescriptionStatus.equalsIgnoreCase("PARTIAL_DISPENSED")
				|| prescriptionStatus.equalsIgnoreCase("PENDING") || prescriptionStatus.equalsIgnoreCase("INVALID")
				|| prescriptionStatus.equalsIgnoreCase("FAILED")) {
			prescriptionRequest.setCanCancel(false);
			prescriptionRequest.setCanFollowUp(false);
		} else if (prescriptionStatus.equalsIgnoreCase("APPROVED")
				|| prescriptionStatus.equalsIgnoreCase("PARTIAL_APPROVED")) {
			prescriptionRequest.setCanCancel(true);
			prescriptionRequest.setCanFollowUp(true);
		}
	}

	private PrescriptionRequest checkEPrescriptionRefNumberIsValid(String ePrescriptionReferenceNumber,
			String providerId) throws PrescriptionException {
		Optional<PrescriptionRequest> prescriptionRequestOptional = prescriptionRequestRepository
				.findByePrescriptionReferenceNumberAndProviderId(ePrescriptionReferenceNumber, providerId);
		if (prescriptionRequestOptional.isPresent()) {
			return prescriptionRequestOptional.get();
		}
		throw new PrescriptionException(new PrescriptionResponseModel(400, "This prescription doesn't exist."));
	}

}
