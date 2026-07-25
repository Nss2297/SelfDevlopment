package com.waseel.prescription.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.dispense.DispensableDrugs;
import com.waseel.prescription.model.dispense.PrescriptionDispenseRequestModel;
import com.waseel.prescription.model.dispense.PrescriptionDispenseResponseModel;
import com.waseel.prescription.model.enums.CommonWords;
import com.waseel.prescription.model.enums.PrescriptionUrl;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.TransactionLogRepository;
import com.waseel.prescription.service.management.SessionService;
import com.waseel.prescription.service.management.TransactionLogService;
import com.waseel.prescription.service.mapper.MapperService;
import com.waseel.prescription.util.SourceTypeUtil;
import com.waseel.prescription.util.UserInfoUtil;

@Service
public class DispenseTechnicalValidationService {

	private static final String STR_INVALID = "Invalid";
	private static final String STR_INVALID_FORMAT = " has invalid format.It should not have space, special characters or alphabets and should be a number or a decimal up to 2 digits.";
	private static final String STR_UNAUTHORIZED = "Unauthorized";

	@Autowired
	private MapperService mapperService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private PrescriptionRequestRepository prescriptionRequestRepository;

	@Autowired
	private ServiceInfoRepository serviceInfoRepository;

	public PrescriptionRequest validateDispenseRequest(String ePrescriptionReferenceNumber,
			ContentCachingRequestWrapper requestWrapper, String providerId, String payerId,
			List<DispensableDrugs> drugList) throws PrescriptionException {
		Optional<PrescriptionRequest> prescriptionRequest = prescriptionRequestRepository
				.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		if (prescriptionRequest.isPresent()) {
			String requestId = prescriptionRequest.get().getRequestId();
			if (drugList != null && !drugList.isEmpty()) {
				validateDrugCode(drugList, requestId, providerId, payerId, ePrescriptionReferenceNumber,
						requestWrapper);
			}
			return prescriptionRequest.get();
		}
		throw new PrescriptionException(
				populateInvalidDispenseResponse(requestWrapper, "EPrescriptionReferenceNumber is not found or exists.",
						null, ePrescriptionReferenceNumber, providerId, payerId));
	}

	private void validateDrugCode(List<DispensableDrugs> drugList, String requestId, String providerId, String payerId,
			String ePrescriptionReferenceNumber, ContentCachingRequestWrapper requestWrapper)
			throws PrescriptionException {
		List<ServiceInfo> serviceInfoList = serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId);
		if (serviceInfoList != null && !serviceInfoList.isEmpty() && drugList != null) {
			List<String> notFoundDrugs = serviceInfoList.stream()
					.filter(serviceInfo -> !StringUtils.isBlank(serviceInfo.getScientificCode()))
					.flatMap(serviceInfo -> drugList.stream()
							.filter(dispensableDrugs -> isValidDrug(serviceInfo, dispensableDrugs))
							.map(DispensableDrugs::getDrugCode))
					.collect(Collectors.toList());
			if (!notFoundDrugs.isEmpty()) {
				throw new PrescriptionException(
						populateInvalidDispenseResponse(requestWrapper, "DrugCode " + notFoundDrugs + " not found.",
								null, ePrescriptionReferenceNumber, providerId, payerId));
			}
		}
	}

	private boolean isValidDrug(ServiceInfo serviceInfo, DispensableDrugs dispensableDrugs) {
		String drugCode = serviceInfo.getDrugCode();
		String scientificCode = serviceInfo.getScientificCode();
		return serviceInfo.getServiceResponseInfo().getStatus().equalsIgnoreCase(ServiceStatus.PENDING.name())
				&& !StringUtils.isBlank(scientificCode) && dispensableDrugs.getScientificCode().equals(scientificCode)
				&& (!StringUtils.isBlank(drugCode) && !drugCode.equalsIgnoreCase(CommonWords.UNDEFINED.value())
						&& !drugCode.equalsIgnoreCase(dispensableDrugs.getDrugCode()));
	}

	public PrescriptionDispenseResponseModel populateInvalidDispenseResponse(
			ContentCachingRequestWrapper requestWrapper, String errorMessage, String requestId,
			String ePrescriptionReferenceNumber, String providerId, String payerId) {
		PrescriptionDispenseResponseModel invalidResponse = new PrescriptionDispenseResponseModel();
		invalidResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		invalidResponse.setStatus(STR_INVALID);
		invalidResponse.setStatusDescription(errorMessage);
		invalidResponse.setPayerId(payerId);
		invalidResponse.setProviderId(providerId);
		if (requestWrapper.getRequestURI().endsWith(PrescriptionUrl.DISPENSE.getValue())) {
			populateTransactionLogForDispense(requestWrapper, payerId, providerId, requestId,
					ePrescriptionReferenceNumber);
		}
		return invalidResponse;
	}

	public PrescriptionDispenseResponseModel populateInvalidPrescriptionResponse(MethodArgumentNotValidException ex,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		String ePrescriptionReferenceNumber;
		if (requestWrapper.getRequestURI().endsWith(PrescriptionUrl.DISPENSE.getValue())) {
			PrescriptionDispenseRequestModel dispenseRequest = mapperService
					.mapPrescriptionDispenseRequest(requestWrapper);
			ePrescriptionReferenceNumber = dispenseRequest.getePrescriptionReferenceNumber();
		} else {
			ePrescriptionReferenceNumber = fetchEPrescriptionRefNumberFromUri(request);
		}
		PrescriptionDispenseResponseModel invalidResponse = new PrescriptionDispenseResponseModel();
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
		String error = errors.toString().replace("[", "").replace("]", "");
		invalidResponse.setStatus(STR_INVALID);
		invalidResponse.setStatusDescription(error);
		invalidResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		String payerId = fetchPayerIdFromUri(request);
		invalidResponse.setPayerId(payerId);
		invalidResponse.setProviderId(providerId);
		populateTransactionLogForDispense(requestWrapper, payerId, providerId,
				getRequestId(ePrescriptionReferenceNumber), ePrescriptionReferenceNumber);
		return invalidResponse;
	}

	public PrescriptionDispenseResponseModel populateFailedPrescriptionResponse(
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		String ePrescriptionReferenceNumber;
		if (requestWrapper.getRequestURI().endsWith(PrescriptionUrl.DISPENSE.getValue())) {
			PrescriptionDispenseRequestModel dispenseRequest = mapperService
					.mapPrescriptionDispenseRequest(requestWrapper);
			ePrescriptionReferenceNumber = dispenseRequest.getePrescriptionReferenceNumber();
		} else {
			ePrescriptionReferenceNumber = fetchEPrescriptionRefNumberFromUri(request);
		}
		PrescriptionDispenseResponseModel invalidResponse = new PrescriptionDispenseResponseModel();
		invalidResponse.setStatus("Failed");
		invalidResponse.setStatusDescription(HttpStatus.INTERNAL_SERVER_ERROR.name());
		invalidResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		String payerId = fetchPayerIdFromUri(request);
		invalidResponse.setPayerId(payerId);
		invalidResponse.setProviderId(providerId);
		populateTransactionLogForDispense(requestWrapper, payerId, providerId,
				getRequestId(ePrescriptionReferenceNumber), ePrescriptionReferenceNumber);
		return invalidResponse;
	}

	public PrescriptionDispenseResponseModel populateInvalidPrescriptionResponse(HttpMessageNotReadableException ex,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		JSONObject requestModel = mapperService.mapBadPrescriptionRequest(requestWrapper);
		String payerId = fetchPayerIdFromUri(request);
		String ePrescriptionReferenceNumber;
		if (requestWrapper.getRequestURI().endsWith(PrescriptionUrl.DISPENSABLE_DRUGS.getValue())) {
			ePrescriptionReferenceNumber = fetchEPrescriptionRefNumberFromUri(request);
		} else {
			ePrescriptionReferenceNumber = requestModel.getString("ePrescriptionReferenceNumber");
		}
		List<String> errors = new ArrayList<>();
		if (ex.getCause() instanceof InvalidFormatException) {
			InvalidFormatException iex = (InvalidFormatException) ex.getCause();
			iex.getPath().forEach(reference -> {
				if (StringUtils.isNotBlank(reference.getFieldName())) {
					errors.add(reference.getFieldName());
				}
			});
		}
		String error = errors.toString().replace("[", "").replace("]", "").concat(STR_INVALID_FORMAT);
		PrescriptionDispenseResponseModel invalidResponse = new PrescriptionDispenseResponseModel();
		invalidResponse.setStatus(STR_INVALID);
		invalidResponse.setStatusDescription(error);
		invalidResponse.setPayerId(payerId);
		invalidResponse.setProviderId(providerId);
		invalidResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		populateTransactionLogForDispense(requestWrapper, invalidResponse.getPayerId(), invalidResponse.getProviderId(),
				getRequestId(ePrescriptionReferenceNumber), ePrescriptionReferenceNumber);
		return invalidResponse;
	}

	private void populateTransactionLogForDispense(ContentCachingRequestWrapper requestWrapper, String payerId,
			String providerId, String requestId, String ePrescriptionReferenceNumber) {
		String transactionLogId = sessionService.getTransactionLogIdFromSession(requestWrapper);
		if (StringUtils.isBlank(transactionLogId)) {
			String sourceType = SourceTypeUtil
					.getSourceTypeBasedOnHeaderOrigin(requestWrapper.getHeader(HttpHeaders.ORIGIN));
			TransactionLog transactionLog = transactionLogService.addTransaction(RequestType.DISPENSED, payerId,
					providerId, requestId, ePrescriptionReferenceNumber, sourceType);
			if (transactionLog != null) {
				sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
			}
		}
	}

	private String getRequestId(String ePrescriptionReferenceNumber) {
		if (!StringUtils.isBlank(ePrescriptionReferenceNumber)) {
			Optional<TransactionLog> transactionLog = transactionLogRepository
					.findByePrescriptionReferenceNumberWithValidStatus(ePrescriptionReferenceNumber);
			if (transactionLog.isPresent()) {
				return transactionLog.get().getRequestId();
			}
		}
		return null;
	}

	public PrescriptionDispenseResponseModel populateUnautorizedPrescriptionResponse(AccessDeniedException ex,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		String ePrescriptionReferenceNumber;
		if (requestWrapper.getRequestURI().endsWith(PrescriptionUrl.DISPENSABLE_DRUGS.getValue())) {
			ePrescriptionReferenceNumber = fetchEPrescriptionRefNumberFromUri(request);
		} else {
			PrescriptionDispenseRequestModel dispenseRequest = mapperService
					.mapPrescriptionDispenseRequest(requestWrapper);
			ePrescriptionReferenceNumber = dispenseRequest.getePrescriptionReferenceNumber();
		}
		PrescriptionDispenseResponseModel invalidResponse = new PrescriptionDispenseResponseModel();
		String error = ex.getMessage();
		invalidResponse.setStatus(STR_UNAUTHORIZED);
		invalidResponse.setStatusDescription(error);
		invalidResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		String payerId = fetchPayerIdFromUri(request);
		invalidResponse.setPayerId(payerId);
		invalidResponse.setProviderId(providerId);
		populateTransactionLogForDispense(requestWrapper, payerId, providerId,
				getRequestId(ePrescriptionReferenceNumber), ePrescriptionReferenceNumber);
		return invalidResponse;
	}

	private String fetchPayerIdFromUri(HttpServletRequest request) {
		Map<?, ?> map = new TreeMap<>(
				(Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
		return (String) map.get("payerId");
	}

	private String fetchEPrescriptionRefNumberFromUri(HttpServletRequest request) {
		Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		return (String) pathVariables.get("ePrescriptionReferenceNumber");
	}

	public PrescriptionRequest validateEPrescriptionReferenceNumber(String ePrescriptionReferenceNumber, String payerId,
			ContentCachingRequestWrapper requestWrapper) throws PrescriptionException {
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		Optional<PrescriptionRequest> prescriptionRequestOptional = prescriptionRequestRepository
				.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		if (prescriptionRequestOptional.isPresent()) {
			return prescriptionRequestOptional.get();
		}
		throw new PrescriptionException(
				populateInvalidDispenseResponse(requestWrapper, "EPrescriptionReferenceNumber is not found or exists.",
						null, ePrescriptionReferenceNumber, providerId, payerId));
	}
}
