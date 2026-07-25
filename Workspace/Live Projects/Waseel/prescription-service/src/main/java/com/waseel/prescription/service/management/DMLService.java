package com.waseel.prescription.service.management;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.eligibility.EligibilityResponseModel;
import com.waseel.prescription.model.enums.BusinessRulesType;
import com.waseel.prescription.model.enums.CommonWords;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.PolicyConsumptionStatus;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.prescription.BusinessRuleValidations;
import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.model.prescription.DrugList;
import com.waseel.prescription.model.prescription.MedicalValidations;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.model.prescription.ServiceResponse;
import com.waseel.prescription.persist.mdss.DrugService;
import com.waseel.prescription.persist.prescriptionservice.Diagnosis;
import com.waseel.prescription.persist.prescriptionservice.DiagnosisId;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.Physician;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.persist.prescriptionservice.ServiceRejection;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.repository.businessrules.CommonDenialsRepository;
import com.waseel.prescription.repository.mdss.DrugServiceRepository;
import com.waseel.prescription.repository.prescriptionservice.DiagnosisRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.PhysicianRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;

@Service
public class DMLService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DMLService.class);

	@Autowired
	private PrescriptionRequestRepository prescriptionRequestRepository;

	@Autowired
	private PhysicianRepository physicianRepository;

	@Autowired
	private MemberInfoRepository memberInfoRepository;

	@Autowired
	private DiagnosisRepository diagnosisRepository;

	@Autowired
	private ServiceInfoRepository serviceInfoRepository;

	@Autowired
	private ServiceResponseInfoRepository serviceResponseInfoRepository;

	@Autowired
	private ServiceRejectionRepository serviceRejectionRepository;

	@Autowired
	private DrugServiceRepository drugServiceRepository;

	@Autowired
	private CommonDenialsRepository commonDenialsRepository;

	public PrescriptionRequest savePrescriptionRequest(PrescriptionRequestModel request,
			PrescriptionResponseModel response, Timestamp sendingDateTime, String providerId, String checkType,
			String eligibilityReferenceNumber) {
		PrescriptionRequest preRequestEntity = null;
		try {
			BigDecimal patientShare = response.getPatientShare() != null ? response.getPatientShare() : BigDecimal.ZERO;
			BigDecimal payerShare = response.getPayerShare() != null ? response.getPayerShare() : BigDecimal.ZERO;
			response.setPatientShare(patientShare);
			response.setPayerShare(payerShare);
			if (StringUtils.isNotBlank(checkType)
					&& (StringUtils.equals(checkType, BusinessRulesType.ELIGIBILITY_CHECK.value())
							|| StringUtils.equals(checkType, BusinessRulesType.POLICY_CONSUMPTION_CHECK.value()))) {
				preRequestEntity = prescriptionRequestRepository.save(populatePrescriptionRequestAfterBusinessRuleCheck(
						request, response, sendingDateTime, providerId));
				populateServiceInfoAfterBusinessRuleCheck(request, response, eligibilityReferenceNumber);
			} else {
				preRequestEntity = prescriptionRequestRepository
						.save(populatePrescriptionRequest(request, response, sendingDateTime, providerId));
				populateServiceInfo(request, response);
			}
			String requestId = preRequestEntity.getRequestId();
			physicianRepository.save(populatePhysician(request, requestId));
			memberInfoRepository.save(populateMemberInfo(request, requestId));
			diagnosisRepository.saveAll(populateDiagnosis(request.getDiagnosisCodes(), requestId));
			LOGGER.info("Request {} has been saved successfully", preRequestEntity.getRequestId());
			return preRequestEntity;
		} catch (Exception e) {
			LOGGER.error("Saving Request {} has been failed ", response.getRequestId(), e);
			e.printStackTrace();
		}
		return preRequestEntity;
	}

	public PrescriptionRequest updatePrescriptionRequest(PrescriptionRequest prescriptionRequest,
			PrescriptionRequestModel request, PrescriptionResponseModel response, String eligibilityReferenceNumber) {
		PrescriptionRequest preRequestEntity = null;
		try {
			prescriptionRequest.setStatusCode(response.getStatus());
			prescriptionRequest.setStatusDescription(response.getStatusDescription());
			prescriptionRequest.setCaseType(request.getCaseType().toUpperCase());
			prescriptionRequest.setCanCancel(false);
			prescriptionRequest.setCancelled(false);
			preRequestEntity = prescriptionRequestRepository.save(prescriptionRequest);
			populateServiceInfoAfterEligibilityCheckForFollowUp(request, response, eligibilityReferenceNumber);
			LOGGER.info("Request {} has been saved successfully", preRequestEntity.getRequestId());
			return preRequestEntity;
		} catch (Exception e) {
			LOGGER.error("Saving Request {} has been failed ", response.getRequestId(), e);
			e.printStackTrace();
		}
		return preRequestEntity;
	}

	public PrescriptionRequest updatePrescriptionRequestForDispense(PrescriptionRequest prescriptionRequest,
			List<String> drugList, EligibilityResponseModel responseModel, String requestId) {
		PrescriptionRequest preRequestEntity = null;
		try {
			prescriptionRequest.setStatusCode(RequestStatusType.REJECTED.value());
			prescriptionRequest.setStatusDescription(responseModel.getStatusDescription());
			prescriptionRequest.setCanCancel(false);
			prescriptionRequest.setCanFollowUp(false);
			prescriptionRequest.setCancelled(false);
			preRequestEntity = prescriptionRequestRepository.save(prescriptionRequest);
			populateServiceInfoAfterEligibilityCheckForDispense(drugList, responseModel, requestId);
			LOGGER.info("Request {} has been saved successfully", preRequestEntity.getRequestId());
			return preRequestEntity;
		} catch (Exception e) {
			LOGGER.error("Saving Request {} has been failed ", requestId);
			e.printStackTrace();
		}
		return preRequestEntity;
	}

	private PrescriptionRequest populatePrescriptionRequest(PrescriptionRequestModel request,
			PrescriptionResponseModel response, Timestamp sendingDateTime, String providerId) {
		String currency = Currency.SAR.value();
		PrescriptionRequest prescriptionRequest = new PrescriptionRequest(response.getRequestId(), request.getPayerId(),
				providerId, sendingDateTime, (new Timestamp(Calendar.getInstance().getTimeInMillis())),
				response.getStatus(), response.getStatusDescription(), response.getePrescriptionReferenceNumber(),
				response.getPatientShare(), response.getPayerShare(), request.getCaseType(),
				StringUtils.isNotBlank(response.getPatientShareCurrency()) ? response.getPatientShareCurrency()
						: currency,
				StringUtils.isNotBlank(response.getPayerShareCurrency()) ? response.getPayerShareCurrency() : currency);
		if (response.getStatus().equals(RequestStatusType.REJECTED.value())) {
			prescriptionRequest.setCanCancel(false);
		}
		prescriptionRequest.setLastUpdateDate(new Date());
		return prescriptionRequest;
	}

	private Physician populatePhysician(PrescriptionRequestModel request, String requestId) {
		return new Physician(request.getPhysicianLicenseNumber(), requestId, request.getPhysicianName(),
				request.getPhysicianCategory(), request.getPhysicianSpeciality());
	}

	private MemberInfo populateMemberInfo(PrescriptionRequestModel request, String requestId) {
		Double memberWeight = request.getMemberWeight() != null ? request.getMemberWeight().doubleValue() : null;
		Double memberHeight = request.getMemberHeight() != null ? request.getMemberHeight().doubleValue() : null;
		return new MemberInfo(request.getMemberId(),
				StringUtils.isBlank(request.getIdNumber()) ? null : Long.parseLong(request.getIdNumber()),
				request.getPolicyNumber(), convertStringToDate(request.getDateOfBirth()), memberWeight, memberHeight,
				request.getMemberGender(), requestId, request.getMemberName(), request.getMemberNationality().strip());
	}

	private List<Diagnosis> populateDiagnosis(List<DiagnosisCodes> diagnosisCodes, String requestId) {
		List<Diagnosis> diagnosisList = new ArrayList<>();
		if (diagnosisCodes != null && !diagnosisCodes.isEmpty()) {
			diagnosisCodes.forEach(code -> {
				Diagnosis diagnosis = new Diagnosis();
				DiagnosisId diagnosisId = new DiagnosisId();
				diagnosisId.setDiagnosisCode(code.getDiagnosisCode());
				diagnosisId.setRequestId(requestId);
				diagnosis.setDiagnosisId(diagnosisId);
				diagnosis.setDiagnosisType(code.getDiagnosisType());
				diagnosisList.add(diagnosis);
			});
		}
		return diagnosisList;
	}

	private void populateServiceInfo(PrescriptionRequestModel request, PrescriptionResponseModel response) {
		if (request.getDrugList() != null) {
			String requestId = response.getRequestId();
			for (int i = 0; i < request.getDrugList().size(); i++) {
				DrugList drug = request.getDrugList().get(i);
				ServiceInfo serviceInfo = new ServiceInfo();
				setServiceInfoData(serviceInfo, drug, requestId);
				ServiceInfo newServiceInfo = serviceInfoRepository.save(serviceInfo);
				if ((drug.getDrugCode() != null && drug.getDrugCode().equals(newServiceInfo.getDrugCode()))
						|| (drug.getScientificCode() != null
								&& drug.getScientificCode().equals(newServiceInfo.getScientificCode()))) {
					ServiceResponse serviceResponse = response.getResults().get(i);
					newServiceInfo.setRequestedAmount(serviceResponse.getRequestedAmount());
					serviceInfoRepository.save(serviceInfo);
					populateServiceResponseInfo(serviceResponse, requestId, newServiceInfo);
				}
			}
		}
	}

	private void setServiceInfoData(ServiceInfo serviceInfo, DrugList drug, String requestId) {
		if (!StringUtils.isBlank(drug.getDrugCode())) {
			serviceInfo.setDrugCode(drug.getDrugCode());
			Optional<DrugService> drugServiceOptional = drugServiceRepository.findByOtherCodesValue(drug.getDrugCode());
			drugServiceOptional.ifPresent(drugService -> serviceInfo.setUseUnitType(drugService.getUnitType()));
		} else {
			serviceInfo.setDrugCode(CommonWords.UNDEFINED.value());
		}
		if (!StringUtils.isBlank(drug.getDuration()))
			serviceInfo.setDuration(Long.parseLong(drug.getDuration()));
		if (drug.getQuantity() != null)
			serviceInfo.setQuantity(drug.getQuantity());
		serviceInfo.setUnitPrice(drug.getUnitPrice() != null ? drug.getUnitPrice() : 0);
		serviceInfo.setUnitType(drug.getUnitType());
		serviceInfo.setOrderingClinician(drug.getOrderingClinician());
		serviceInfo.setServiceStartDate(convertStringToDate(drug.getServiceStartDate()));
		if (!StringUtils.isBlank(drug.getServiceEndDate())) {
			serviceInfo.setServiceEndDate(convertStringToDate(drug.getServiceEndDate()));
		}
		serviceInfo.setFrequency(drug.getFrequency());
		serviceInfo.setFrequencyOthersDescription(drug.getFrequencyOthersDescription());
		if (!StringUtils.isBlank(drug.getUseUnitValue())) {
			serviceInfo.setUseUnitValue(Double.valueOf(drug.getUseUnitValue()));
		}
		serviceInfo.setScientificCode(drug.getScientificCode());
		serviceInfo.setRequestId(requestId);
		serviceInfo.setDrugListId(Long.valueOf(drug.getDrugListId()));
	}

	private void populateServiceResponseInfo(ServiceResponse result, String requestId, ServiceInfo serviceInfo) {
		String currency = Currency.SAR.value();
		ServiceResponseInfo serviceResponseInfo = new ServiceResponseInfo(requestId, serviceInfo.getRequestedAmount(),
				result.getApprovedAmount(), result.getDiscount(), result.getPatientShare(), result.getNet(),
				result.getStatus(), result.getStatusDescription(), serviceInfo.getId(),
				StringUtils.isNotBlank(result.getPatientShareCurrency()) ? result.getPatientShareCurrency() : currency,
				StringUtils.isNotBlank(result.getNetCurrency()) ? result.getNetCurrency() : currency);
		ServiceResponseInfo newServiceResponseInfo = serviceResponseInfoRepository.save(serviceResponseInfo);
		populateServiceRejection(newServiceResponseInfo.getId(), requestId, result.getErrors(),
				serviceInfo.getScientificCode());
	}

	private void populateServiceRejection(Long serviceResponseInfoId, String requestId, List<MedicalValidations> errors,
			String scientificCode) {
		List<ServiceRejection> errorList = new ArrayList<>();
		if (errors != null) {
			errors.forEach(error -> {
				ServiceRejection serviceRejection = new ServiceRejection(error.getDrugCode(), error.getDenialCode(),
						error.getRejectionReason(), requestId, scientificCode, serviceResponseInfoId);
				errorList.add(serviceRejection);
			});
		}
		if (!errorList.isEmpty()) {
			serviceRejectionRepository.saveAll(errorList);
		}
	}

	private Timestamp convertStringToDate(String dateStr) {
		try {
			return new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(dateStr).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private PrescriptionRequest populatePrescriptionRequestAfterBusinessRuleCheck(PrescriptionRequestModel request,
			PrescriptionResponseModel response, Timestamp sendingDateTime, String providerId) {
		String status = response.getStatus();
		boolean canFollowup = status.equals(PolicyConsumptionStatus.REJECTED.getValue())
				|| status.equals(RequestStatusType.REJECTED.value());
		String currency = Currency.SAR.value();
		PrescriptionRequest prescriptionRequest = new PrescriptionRequest(response.getRequestId(), request.getPayerId(),
				providerId, sendingDateTime, (new Timestamp(Calendar.getInstance().getTimeInMillis())), status,
				response.getStatusDescription(), false, canFollowup, false, response.getePrescriptionReferenceNumber(),
				request.getCaseType().toUpperCase(), response.getPatientShare(), response.getPayerShare(),
				StringUtils.isNotBlank(response.getPatientShareCurrency()) ? response.getPatientShareCurrency()
						: currency,
				StringUtils.isNotBlank(response.getPayerShareCurrency()) ? response.getPayerShareCurrency() : currency);
		prescriptionRequest.setLastUpdateDate(new Date());
		return prescriptionRequest;
	}

	private void populateServiceInfoAfterBusinessRuleCheck(PrescriptionRequestModel request,
			PrescriptionResponseModel response, String eligibilityReferenceNumber) {
		List<DrugList> drugLists = request.getDrugList();
		if (drugLists != null) {
			String requestId = response.getRequestId();
			for (int i = 0; i < drugLists.size(); i++) {
				DrugList drug = drugLists.get(i);
				String drugCode = drug.getDrugCode();
				ServiceInfo serviceInfo = new ServiceInfo();
				setServiceInfoData(serviceInfo, drug, requestId);
				ServiceInfo newServiceInfo = serviceInfoRepository.save(serviceInfo);
				if (drugCode.equals(newServiceInfo.getDrugCode())) {
					ServiceResponse serviceResponse = response.getResults().get(i);
					serviceInfo.setRequestedAmount(serviceResponse.getRequestedAmount());
					serviceInfoRepository.save(serviceInfo);
					populateServiceResponseAfterBusinessRuleCheck(serviceResponse, requestId, newServiceInfo,
							response.getStatus(), eligibilityReferenceNumber);
				}
			}
		}
	}

	private void populateServiceInfoAfterEligibilityCheckForFollowUp(PrescriptionRequestModel request,
			PrescriptionResponseModel response, String eligibilityReferenceNumber) {
		List<DrugList> drugLists = request.getDrugList();
		if (drugLists != null) {
			String requestId = response.getRequestId();
			for (int i = 0; i < drugLists.size(); i++) {
				DrugList drug = drugLists.get(i);
				String drugCode = drug.getDrugCode();
				Optional<ServiceInfo> serviceInfoOp = serviceInfoRepository.findByRequestIdAndDrugCode(requestId,
						drugCode);
				ServiceInfo serviceInfo = serviceInfoOp.isPresent() ? serviceInfoOp.get() : new ServiceInfo();
				serviceInfo.setDeleted(false);
				setServiceInfoData(serviceInfo, drug, requestId);
				ServiceResponse serviceResponse = response.getResults().get(i);
				serviceInfo.setRequestedAmount(serviceResponse.getRequestedAmount());
				ServiceInfo newServiceInfo = serviceInfoRepository.save(serviceInfo);
				populateServiceResponseInfoForEligibilityCheckForFollowUp(serviceResponse, requestId, newServiceInfo,
						response.getStatus(), eligibilityReferenceNumber);
			}
		}
	}

	private void populateServiceInfoAfterEligibilityCheckForDispense(List<String> drugList,
			EligibilityResponseModel responseModel, String requestId) {
		if (drugList != null) {
			for (int i = 0; i < drugList.size(); i++) {
				String drugCode = drugList.get(i);
				Optional<ServiceInfo> serviceInfoOp = serviceInfoRepository.findByRequestIdAndDrugCode(requestId,
						drugCode);
				if (serviceInfoOp.isPresent()) {
					ServiceInfo serviceInfo = serviceInfoOp.get();
					populateServiceResponseInfoForEligibilityCheckForDispense(responseModel, requestId, serviceInfo,
							RequestStatusType.REJECTED.value(), drugCode);
				}
			}
		}
	}

	private BigDecimal calculateRequestedAmount(Double unitPrice, BigDecimal quantity) {
		return quantity.multiply(BigDecimal.valueOf(unitPrice));
	}

	private void populateServiceResponseAfterBusinessRuleCheck(ServiceResponse result, String requestId,
			ServiceInfo serviceInfo, String status, String eligibilityReferenceNumber) {
		String currency = Currency.SAR.value();
		ServiceResponseInfo serviceResponseInfo = new ServiceResponseInfo(requestId, serviceInfo.getRequestedAmount(),
				result.getApprovedAmount(), result.getDiscount(), result.getPatientShare(), result.getNet(),
				result.getStatus(), result.getStatusDescription(), serviceInfo.getId(),
				StringUtils.isNotBlank(result.getPatientShareCurrency()) ? result.getPatientShareCurrency() : currency,
				StringUtils.isNotBlank(result.getNetCurrency()) ? result.getNetCurrency() : currency);
		ServiceResponseInfo newServiceResponseInfo = serviceResponseInfoRepository.save(serviceResponseInfo);
		if (status.equals("REJECTED")) {
			populateServiceRejectionForIneligiblePrescription(newServiceResponseInfo.getId(), requestId,
					result.getBusinessRuleError(), eligibilityReferenceNumber);
		}
	}

	private void populateServiceResponseInfoForEligibilityCheckForFollowUp(ServiceResponse result, String requestId,
			ServiceInfo serviceInfo, String status, String eligibilityReferenceNumber) {
		Optional<ServiceResponseInfo> serviceResponseInfoOptional = serviceResponseInfoRepository
				.findByRequestIdAndServiceID(requestId, serviceInfo.getId());
		ServiceResponseInfo serviceResponseInfo = serviceResponseInfoOptional.isPresent()
				? serviceResponseInfoOptional.get()
				: new ServiceResponseInfo(requestId, serviceInfo.getRequestedAmount(), result.getApprovedAmount(),
						result.getDiscount(), result.getPatientShare(), result.getNet(), result.getStatus(),
						result.getStatusDescription(), serviceInfo.getId());
		serviceResponseInfo.setStatus(result.getStatus());
		serviceResponseInfo.setStatusDescription(result.getStatusDescription());
		ServiceResponseInfo newServiceResponseInfo = serviceResponseInfoRepository.save(serviceResponseInfo);
		if (status.equals("REJECTED")) {
			populateServiceRejectionForIneligiblePrescriptionForFollowUp(newServiceResponseInfo.getId(), requestId,
					result.getBusinessRuleError(), eligibilityReferenceNumber);
		}
	}

	private void populateServiceResponseInfoForEligibilityCheckForDispense(EligibilityResponseModel responseModel,
			String requestId, ServiceInfo serviceInfo, String status, String drugCode) {
		Optional<ServiceResponseInfo> serviceResponseInfoOptional = serviceResponseInfoRepository
				.findByRequestIdAndServiceID(requestId, serviceInfo.getId());
		if (serviceResponseInfoOptional.isPresent()) {
			ServiceResponseInfo serviceResponseInfo = serviceResponseInfoOptional.get();
			serviceResponseInfo.setStatus(status);
			serviceResponseInfo.setStatusDescription(responseModel.getStatusDescription());
			ServiceResponseInfo newServiceResponseInfo = serviceResponseInfoRepository.save(serviceResponseInfo);
			if (status.equals("REJECTED")) {
				populateServiceRejectionForIneligiblePrescriptionDispense(newServiceResponseInfo.getId(), requestId,
						responseModel, drugCode);
			}
		}
	}

	private void populateServiceRejectionForIneligiblePrescription(Long serviceResponseInfoId, String requestId,
			BusinessRuleValidations businessRuleValidation, String eligibilityReferenceNumber) {
		ServiceRejection serviceRejection = new ServiceRejection(businessRuleValidation.getDrugCode(),
				businessRuleValidation.getDenialCode(), businessRuleValidation.getRejectionReason(), requestId,
				serviceResponseInfoId, eligibilityReferenceNumber);
		serviceRejectionRepository.save(serviceRejection);
	}

	private void populateServiceRejectionForIneligiblePrescriptionForFollowUp(Long serviceResponseInfoId,
			String requestId, BusinessRuleValidations businessRuleValidation, String eligibilityReferenceNumber) {
		Optional<ServiceRejection> serviceRejectionOptional = serviceRejectionRepository
				.findByServiceResponseId(serviceResponseInfoId);
		ServiceRejection serviceRejection = serviceRejectionOptional.isPresent() ? serviceRejectionOptional.get()
				: new ServiceRejection(businessRuleValidation.getDrugCode(), businessRuleValidation.getDenialCode(),
						businessRuleValidation.getRejectionReason(), requestId, serviceResponseInfoId,
						eligibilityReferenceNumber);
		serviceRejection.setDenialCode(businessRuleValidation.getDenialCode());
		serviceRejection.setRejectionReason(businessRuleValidation.getRejectionReason());
		serviceRejection.setEligibilityReferenceNumber(eligibilityReferenceNumber);
		serviceRejectionRepository.save(serviceRejection);
	}

	private void populateServiceRejectionForIneligiblePrescriptionDispense(Long serviceResponseInfoId, String requestId,
			EligibilityResponseModel responseModel, String drugCode) {
		Optional<ServiceRejection> serviceRejectionOptional = serviceRejectionRepository
				.findByServiceResponseId(serviceResponseInfoId);
		ServiceRejection serviceRejection = serviceRejectionOptional.isPresent() ? serviceRejectionOptional.get()
				: new ServiceRejection(drugCode, responseModel.getDenialCode(), responseModel.getDescription(),
						requestId, serviceResponseInfoId, responseModel.getReferenceNumber());
		serviceRejection.setDenialCode(responseModel.getDenialCode());
		serviceRejection.setRejectionReason(responseModel.getDescription());
		serviceRejection.setEligibilityReferenceNumber(responseModel.getReferenceNumber());
		serviceRejectionRepository.save(serviceRejection);
	}
}
