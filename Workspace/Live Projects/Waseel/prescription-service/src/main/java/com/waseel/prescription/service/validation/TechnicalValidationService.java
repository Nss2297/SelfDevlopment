package com.waseel.prescription.service.validation;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.waseel.prescription.model.br.SensitiveDrugResponseModel;
import com.waseel.prescription.model.cancellation.PrescriptionCancellationRequestModel;
import com.waseel.prescription.model.cancellation.PrescriptionCancellationResponseModel;
import com.waseel.prescription.model.dss.DssResponse;
import com.waseel.prescription.model.enums.DssPayerTransactionType;
import com.waseel.prescription.model.enums.PrescriptionExceptionConstants;
import com.waseel.prescription.model.enums.PrescriptionExceptionMessages;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.ServiceName;
import com.waseel.prescription.model.exclusion.DrugExclusionResponseModel;
import com.waseel.prescription.model.formulary.DrugFormularyResponseModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.persist.businessrules.PhysicianInfo;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.Physician;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.businessrules.PhysicianInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.PhysicianRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.TransactionLogRepository;
import com.waseel.prescription.service.management.SessionService;
import com.waseel.prescription.service.management.TransactionLogService;
import com.waseel.prescription.service.mapper.MapperService;
import com.waseel.prescription.util.SourceTypeUtil;
import com.waseel.prescription.util.UserInfoUtil;

import feign.Request.HttpMethod;

@Service
public class TechnicalValidationService {

	private static final Logger log = LoggerFactory.getLogger(TechnicalValidationService.class);

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private MapperService mapperService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	@Autowired
	private PrescriptionRequestRepository prescriptionRequestRepository;

	@Autowired
	private MemberInfoRepository memberInfoRepository;

	@Autowired
	private PhysicianRepository physicianRepository;

	@Autowired
	private PhysicianInfoRepository physicianInfoRepository;

	private static final String STR_DO_NOT_UPDATE = "Don't allow to update ";
	private static final String STR_INVALID = "Invalid";
	private static final String STR_INVALID_FORMAT = " has invalid format.It should not have space, special characters or alphabets and should be a number or a decimal.";
	private static final String STR_UNAUTHORIZED = "Unauthorized";
	private static final String E_PRESCRIPTION_REFERENCE_NUMBER_STRING = "ePrescriptionReferenceNumber";
	private static final String E_PRESCRIPTION_REFERENCE_NUMBER_NOT_EXIST_STRING = "EPrescriptionReferenceNumber is not found or exists.";
	private static final String FAILED_STRING = "Failed";

	public boolean identifyNewFollowUpRequest(String ePrescriptionReferenceNumber) {
		/*
		 * if ePrescriptionReferenceNumber is exist in request means we have to do
		 * FOLLOWUP, otherwise it is NEW request
		 */
		return ePrescriptionReferenceNumber != null;
	}

	public PrescriptionRequest validateFollowUpRequest(PrescriptionRequestModel request,
			ContentCachingRequestWrapper requestWrapper, String providerId) throws PrescriptionException {
		if (StringUtils.isBlank(request.getePrescriptionReferenceNumber())) {
			throw new PrescriptionException(populateInvalidPrescriptionResponse(requestWrapper,
					"EPrescriptionReferenceNumber can't be empty.", request.getePrescriptionReferenceNumber()));
		}
		validateMemberIdAndIdNumber(request.getIdNumber(), request.getMemberId(), request.getPolicyNumber(),
				requestWrapper);
		Optional<PrescriptionRequest> prescriptionRequestOp = prescriptionRequestRepository
				.findByePrescriptionReferenceNumber(request.getePrescriptionReferenceNumber());
		if (prescriptionRequestOp.isPresent()) {
			checkIsRequestValid(requestWrapper, prescriptionRequestOp.get().getRequestId(), request,
					prescriptionRequestOp.get().getePrescriptionReferenceNumber(), providerId);
			return prescriptionRequestOp.get();
		} else {
			throw new PrescriptionException(populateInvalidPrescriptionResponse(requestWrapper,
					E_PRESCRIPTION_REFERENCE_NUMBER_NOT_EXIST_STRING, request.getePrescriptionReferenceNumber()));
		}
	}

	private void checkIsRequestValid(ContentCachingRequestWrapper requestWrapper, String requestId,
			PrescriptionRequestModel request, String ePrescriptionReferenceNumber, String providerId)
			throws PrescriptionException {
		checkNoUpdatableFields(requestWrapper, requestId, request, ePrescriptionReferenceNumber, providerId);
	}

	private void checkNoUpdatableFields(ContentCachingRequestWrapper requestWrapper, String requestId,
			PrescriptionRequestModel request, String ePrescriptionReferenceNumber, String providerId)
			throws PrescriptionException {
		List<String> errorMsg = new ArrayList<>();
		checkPrescriptionRequestFields(request, requestId, errorMsg, providerId);
		checkMemberInfoFields(request, requestId, errorMsg);
		checkPhysicianFields(request, requestId, errorMsg);
		if (!errorMsg.isEmpty()) {
			throw new PrescriptionException(populateInvalidPrescriptionResponse(requestWrapper, errorMsg.toString(),
					ePrescriptionReferenceNumber));
		}
	}

	private void checkPrescriptionRequestFields(PrescriptionRequestModel request, String requestId,
			List<String> errorMsg, String providerId) {
		Optional<PrescriptionRequest> prescriptionRequest = prescriptionRequestRepository.findByRequestId(requestId);
		if (prescriptionRequest.isPresent()) {
			PrescriptionRequest pReq = prescriptionRequest.get();
			String prescriptionPayerId = request.getPayerId() + "_" + DssPayerTransactionType.PRESCRIPTION.value();
			if (!prescriptionPayerId.equals(pReq.getPayerId())) {
				errorMsg.add(STR_DO_NOT_UPDATE + "PayerId");
			}

			if (!providerId.equals(pReq.getProviderId())) {
				errorMsg.add(STR_DO_NOT_UPDATE + "ProviderId");
			}
		}
	}

	private void checkPhysicianFields(PrescriptionRequestModel request, String requestId, List<String> errorMsg) {
		Optional<Physician> physicianDetails = physicianRepository.findByRequestId(requestId);
		if (physicianDetails.isPresent()) {
			Physician physician = physicianDetails.get();
			if (!StringUtils.isBlank(request.getPhysicianName()) && !StringUtils.isBlank(physician.getPhysicianName())
					&& !request.getPhysicianName().equals(physician.getPhysicianName().replace(" ", " "))) {
				errorMsg.add(STR_DO_NOT_UPDATE + "PhysicianName");
			}
			if (!StringUtils.isBlank(request.getPhysicianCategory())
					&& !StringUtils.isBlank(physician.getPhysicianCategory())
					&& !request.getPhysicianCategory().equals(physician.getPhysicianCategory())) {
				errorMsg.add(STR_DO_NOT_UPDATE + "PhysicianCategory");
			}
			if (!StringUtils.isBlank(request.getPhysicianLicenseNumber())
					&& !StringUtils.isBlank(physician.getPhysicianLicenseNumber())
					&& !request.getPhysicianLicenseNumber().equals(physician.getPhysicianLicenseNumber())) {
				errorMsg.add(STR_DO_NOT_UPDATE + "PhysicianLicenseNumber");
			}
			if (!StringUtils.isBlank(request.getPhysicianSpeciality())
					&& !StringUtils.isBlank(physician.getPhysicianSpeciality())
					&& !request.getPhysicianSpeciality().equals(physician.getPhysicianSpeciality())) {
				errorMsg.add(STR_DO_NOT_UPDATE + "PhysicianSpeciality");
			}
		}
	}

	private void checkMemberInfoFields(PrescriptionRequestModel request, String requestId, List<String> errorMsg) {
		Optional<MemberInfo> memberInfo = memberInfoRepository.findByRequestId(requestId);
		if (memberInfo.isPresent()) {
			checkMemberInfoDetails(memberInfo.get(), request, errorMsg);
		}
	}

	private void checkMemberInfoDetails(MemberInfo mInfo, PrescriptionRequestModel request, List<String> errorMsg) {
		checkMemberIdOrPolicyNumberOrIdNumber(mInfo, request, errorMsg);
		Timestamp dob = convertStringToDate(request.getDateOfBirth());
		if (dob != null && !dob.equals(mInfo.getDob())) {
			errorMsg.add(STR_DO_NOT_UPDATE + "DateOfBirth");
		}
		if (request.getMemberWeight() != null && mInfo.getWeight() != null
				&& request.getMemberWeight().doubleValue() != mInfo.getWeight()) {
			errorMsg.add(STR_DO_NOT_UPDATE + "MemberWeight");
		}
		if (request.getMemberHeight() != null && mInfo.getHeight() != null
				&& request.getMemberHeight().doubleValue() != mInfo.getHeight()) {
			errorMsg.add(STR_DO_NOT_UPDATE + "MemberHeight");
		}
		if (!StringUtils.isBlank(request.getMemberGender()) && !StringUtils.isBlank(mInfo.getGender())
				&& !request.getMemberGender().equalsIgnoreCase(mInfo.getGender())) {
			errorMsg.add(STR_DO_NOT_UPDATE + "MemberGender");
		}
		if (!StringUtils.isBlank(request.getMemberName()) && !StringUtils.isBlank(mInfo.getMemberName())
				&& !request.getMemberName().equals(mInfo.getMemberName().replace(" ", " "))) {
			errorMsg.add(STR_DO_NOT_UPDATE + "MemberName");
		}
		if (StringUtils.isNotBlank(request.getMemberNationality()) && StringUtils.isNotBlank(mInfo.getNationality())
				&& !request.getMemberNationality().equals(mInfo.getNationality())) {
			errorMsg.add(STR_DO_NOT_UPDATE + "MemberNationality");
		}
	}

	private void checkMemberIdOrPolicyNumberOrIdNumber(MemberInfo mInfo, PrescriptionRequestModel request,
			List<String> errorMsg) {
		if (!StringUtils.isBlank(request.getMemberId()) && !request.getMemberId().equals(mInfo.getMemberId())) {
			errorMsg.add(STR_DO_NOT_UPDATE + "MemberId");
		}
		String idNumber = request.getIdNumber();
		String memberInfoIdNumber = mInfo.getIdNumber() == null ? "" : mInfo.getIdNumber().toString();
		if (!StringUtils.isBlank(idNumber) && StringUtils.equals(idNumber, memberInfoIdNumber)) {
			if (StringUtils.isNotBlank(idNumber) && StringUtils.isNotBlank(memberInfoIdNumber)
					&& Long.parseLong(idNumber) != mInfo.getIdNumber()) {
				errorMsg.add(STR_DO_NOT_UPDATE + "IdNumber");
			}
		} else {
			errorMsg.add(STR_DO_NOT_UPDATE + "IdNumber");
		}
		if (!StringUtils.isBlank(request.getPolicyNumber()) && !StringUtils.isBlank(mInfo.getPolicyNumber())
				&& !request.getPolicyNumber().equals(mInfo.getPolicyNumber())) {
			errorMsg.add(STR_DO_NOT_UPDATE + "PolicyNumber");
		}
	}

	public PrescriptionResponseModel populateInvalidPrescriptionResponse(ContentCachingRequestWrapper requestWrapper,
			String errorMessage, String ePrescriptionReferenceNumber) {
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		PrescriptionResponseModel invalidResponse = new PrescriptionResponseModel();
		List<String> errors = new ArrayList<>();
		errors.add(errorMessage);
		invalidResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		invalidResponse.setStatus(STR_INVALID);
		String error = errors.toString().replace("[", "").replace("]", "");
		invalidResponse.setStatusDescription(error);
		invalidResponse.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
		invalidResponse.setHttpStatusDescription(error);
		populateTransactionLog(requestWrapper, invalidResponse, ePrescriptionReferenceNumber, false, providerId);
		return invalidResponse;
	}

	public PrescriptionResponseModel populateInvalidPrescriptionResponse(MethodArgumentNotValidException ex,
			ContentCachingRequestWrapper requestWrapper) {
		String requestId = UUID.randomUUID().toString();
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		PrescriptionRequestModel prescriptionRequest = mapperService.mapPrescriptionRequest(requestWrapper);
		PrescriptionResponseModel invalidResponse = new PrescriptionResponseModel();
		invalidResponse.setStatus(STR_INVALID);
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
		String error = errors.toString().replace("[", "").replace("]", "");
		invalidResponse.setStatusDescription(error);
		invalidResponse.setRequestId(requestId);
		invalidResponse.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
		invalidResponse.setHttpStatusDescription(error);
		populateTransactionLog(requestWrapper, invalidResponse, prescriptionRequest.getePrescriptionReferenceNumber(),
				false, providerId);
		return invalidResponse;
	}

	public PrescriptionResponseModel populateInvalidPrescriptionResponse(MethodArgumentNotValidException ex,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
		String requestId = UUID.randomUUID().toString();
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		PrescriptionRequestModel prescriptionRequest = mapperService.mapPrescriptionRequest(requestWrapper);
		String ePrescriptionReferenceNumber = null;
		if (prescriptionRequest != null) {
			ePrescriptionReferenceNumber = prescriptionRequest.getePrescriptionReferenceNumber();
		}
		if (ePrescriptionReferenceNumber == null) {
			ePrescriptionReferenceNumber = fetchValueFromUri(request, E_PRESCRIPTION_REFERENCE_NUMBER_STRING);
		}
		PrescriptionResponseModel invalidResponse = new PrescriptionResponseModel();
		invalidResponse.setStatus(STR_INVALID);
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
		String error = errors.toString().replace("[", "").replace("]", "");
		invalidResponse.setStatusDescription(error);
		invalidResponse.setRequestId(requestId);
		invalidResponse.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
		invalidResponse.setHttpStatusDescription(error);
		populateTransactionLog(requestWrapper, invalidResponse, ePrescriptionReferenceNumber, false, providerId);
		return invalidResponse;
	}

	private String fetchValueFromUri(HttpServletRequest request, String valueName) {
		try {
			Map<String, String> map = new TreeMap<>(
					(Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
			return map.get(valueName);
		} catch (Exception ex) {
			log.error(ex.toString());
		}
		return null;
	}

	private TransactionLog generateTransactionLogData(ContentCachingRequestWrapper requestWrapper, String requestId,
			String ePrescriptionReferenceNumber, boolean isUnreadableRequest, String providerId) {
		String payerId = "";
		if (isUnreadableRequest) {
			JSONObject prescriptionRequestModel = mapperService.mapBadPrescriptionRequest(requestWrapper);
			payerId = prescriptionRequestModel.getString("payerId");
			if (prescriptionRequestModel.has(E_PRESCRIPTION_REFERENCE_NUMBER_STRING)) {
				ePrescriptionReferenceNumber = prescriptionRequestModel
						.getString(E_PRESCRIPTION_REFERENCE_NUMBER_STRING);
			}
		} else {
			if (requestWrapper.getMethod().equals(HttpMethod.DELETE.name())
					|| requestWrapper.getMethod().equals(HttpMethod.GET.name())) {
				// Cancellation & Payer Member
				Map<?, ?> pathVariables = (Map<?, ?>) requestWrapper
						.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
				payerId = (String) pathVariables.get("payerId");
				ePrescriptionReferenceNumber = (String) pathVariables.get(E_PRESCRIPTION_REFERENCE_NUMBER_STRING);
			} else {
				PrescriptionRequestModel prescriptionRequestModel = this.mapperService
						.mapPrescriptionRequest(requestWrapper);
				if (prescriptionRequestModel != null) {
					payerId = prescriptionRequestModel.getPayerId();
					ePrescriptionReferenceNumber = prescriptionRequestModel.getePrescriptionReferenceNumber();
				}
			}
		}
		if (StringUtils.isNotBlank(ePrescriptionReferenceNumber)) {
			Optional<PrescriptionRequest> prescriptionRequestOp = prescriptionRequestRepository
					.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
			requestId = prescriptionRequestOp.isPresent() ? prescriptionRequestOp.get().getRequestId() : requestId;
		} else {
			requestId = requestWrapper.getMethod().equals(HttpMethod.DELETE.name()) ? null : requestId;
		}
		String sourceType = SourceTypeUtil
				.getSourceTypeBasedOnHeaderOrigin(requestWrapper.getHeader(HttpHeaders.ORIGIN));
		TransactionLog transactionLog = transactionLogService.addTransaction(
				getRequestType(requestWrapper, ePrescriptionReferenceNumber), payerId, providerId, requestId,
				ePrescriptionReferenceNumber, sourceType);
		if (null != transactionLog && null != transactionLog.getTransactionLogId()
				&& !transactionLog.getTransactionLogId().toString().isEmpty()) {
			log.info("Data saved for TransactionLogId [{}]", transactionLog.getTransactionLogId());
			return transactionLog;
		}
		return null;
	}

	public PrescriptionResponseModel populateFailedPrescriptionResponse(ContentCachingRequestWrapper requestWrapper,
			HttpServletRequest request) {
		String ePrescriptionReferenceNumber = null;
		if (!requestWrapper.getMethod().equals(HttpMethod.DELETE.name())) {
			PrescriptionRequestModel prescriptionRequest = mapperService.mapPrescriptionRequest(requestWrapper);
			if (prescriptionRequest != null) {
				ePrescriptionReferenceNumber = prescriptionRequest.getePrescriptionReferenceNumber();
			}
		}
		if (ePrescriptionReferenceNumber == null) {
			ePrescriptionReferenceNumber = fetchValueFromUri(request, E_PRESCRIPTION_REFERENCE_NUMBER_STRING);
		}
		String requestId = UUID.randomUUID().toString();
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		PrescriptionResponseModel invalidResponse = new PrescriptionResponseModel();
		invalidResponse.setStatus(FAILED_STRING);
		invalidResponse.setRequestId(requestId);
		invalidResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		invalidResponse.setHttpStatusDescription(HttpStatus.INTERNAL_SERVER_ERROR.name());
		invalidResponse.setStatusDescription(HttpStatus.INTERNAL_SERVER_ERROR.name());
		populateTransactionLog(requestWrapper, invalidResponse, ePrescriptionReferenceNumber, false, providerId);
		return invalidResponse;
	}

	public PrescriptionResponseModel populateFailedPrescriptionResponse(ContentCachingRequestWrapper requestWrapper) {
		String requestId = UUID.randomUUID().toString();
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		PrescriptionRequestModel prescriptionRequest = mapperService.mapPrescriptionRequest(requestWrapper);
		PrescriptionResponseModel invalidResponse = new PrescriptionResponseModel();
		invalidResponse.setStatus(FAILED_STRING);
		invalidResponse.setRequestId(requestId);
		invalidResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		invalidResponse.setHttpStatusDescription(HttpStatus.INTERNAL_SERVER_ERROR.name());
		invalidResponse.setStatusDescription(HttpStatus.INTERNAL_SERVER_ERROR.name());
		populateTransactionLog(requestWrapper, invalidResponse, prescriptionRequest.getePrescriptionReferenceNumber(),
				false, providerId);
		return invalidResponse;
	}

	private void populateTransactionLog(ContentCachingRequestWrapper requestWrapper,
			PrescriptionResponseModel invalidResponse, String ePrescriptionReferenceNumber, boolean isUnreadableRequest,
			String providerId) {
		HttpSession session = requestWrapper.getSession();
		Long transactionLogId = (Long) session.getAttribute("TransactionLogId");
		TransactionLog transactionLog = null;
		if (transactionLogId != null) {
			log.info("TransactionLogId => [{}]", transactionLogId);
			Optional<TransactionLog> transactionLogOp = transactionLogRepository.findById(transactionLogId);
			transactionLog = !transactionLogOp.isPresent() ? null : transactionLogOp.get();
		} else {
			transactionLog = generateTransactionLogData(requestWrapper, invalidResponse.getRequestId(),
					ePrescriptionReferenceNumber, isUnreadableRequest, providerId);
		}
		if (null != transactionLog) {
			sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
			invalidResponse.setePrescriptionReferenceNumber(null != transactionLog.getTransactionLogId()
					&& !transactionLog.getTransactionLogId().toString().isEmpty()
							? transactionLog.getePrescriptionReferenceNumber()
							: Year.now().toString());
			invalidResponse.setRequestId(transactionLog.getRequestId());
		}
	}

	public PrescriptionRequest validateCancellationRequest(
			PrescriptionCancellationRequestModel prescriptionCancellationRequestModel,
			ContentCachingRequestWrapper requestWrapper, String providerId, String sourceType)
			throws PrescriptionException {
		String ePrescriptionReferenceNumber = prescriptionCancellationRequestModel.getePrescriptionReferenceNumber();
		Optional<PrescriptionRequest> prescriptionRequestOptional = prescriptionRequestRepository
				.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		if (prescriptionRequestOptional.isPresent()) {
			PrescriptionRequest prescriptionRequest = prescriptionRequestOptional.get();
			String requestId = prescriptionRequest.getRequestId();
			TransactionLog transaction = transactionLogService.addTransaction(RequestType.CANCELLATION,
					prescriptionCancellationRequestModel.getPayerId(), providerId, requestId,
					ePrescriptionReferenceNumber, sourceType);
			if (null != transaction) {
				sessionService.setTransactionLogIdInSession(requestWrapper, transaction.getTransactionLogId());
			}
			return checkIsRequestCanCancel(requestWrapper, prescriptionCancellationRequestModel, requestId,
					ePrescriptionReferenceNumber, providerId);
		}
		throw new PrescriptionException(populateInvalidPrescriptionResponse(requestWrapper,
				E_PRESCRIPTION_REFERENCE_NUMBER_NOT_EXIST_STRING, ePrescriptionReferenceNumber));
	}

	private RequestType getRequestType(ContentCachingRequestWrapper requestWrapper,
			String ePrescriptionReferenceNumber) {
		if (requestWrapper.getMethod().equals(HttpMethod.DELETE.name())) {
			return RequestType.CANCELLATION;
		} else {
			return ePrescriptionReferenceNumber != null && !ePrescriptionReferenceNumber.isEmpty()
					? RequestType.FOLLOWUP
					: RequestType.NEW;
		}
	}

	private Timestamp convertStringToDate(String dateStr) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = format.parse(dateStr);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private PrescriptionRequest checkIsRequestCanCancel(ContentCachingRequestWrapper requestWrapper,
			PrescriptionCancellationRequestModel requestModel, String requestId, String ePrescriptionReferenceNumber,
			String providerId) throws PrescriptionException {
		Optional<PrescriptionRequest> prescriptionRequestOptional = prescriptionRequestRepository
				.findByRequestId(requestId);
		if (prescriptionRequestOptional.isPresent()) {
			PrescriptionRequest prescriptionRequest = prescriptionRequestOptional.get();
			if (!prescriptionRequest.getPayerId()
					.equals(requestModel.getPayerId() + "_" + DssPayerTransactionType.PRESCRIPTION.value())) {
				throw new PrescriptionException(populateInvalidPrescriptionResponse(requestWrapper,
						"PayerId is not matching with ePrescriptionReferenceNumber", ePrescriptionReferenceNumber));
			}

			if (!prescriptionRequest.getProviderId().equals(providerId)) {
				throw new PrescriptionException(populateInvalidPrescriptionResponse(requestWrapper,
						"ProviderId is not matching with ePrescriptionReferenceNumber", ePrescriptionReferenceNumber));
			}
			return prescriptionRequest;
		}
		return null;
	}

	public PrescriptionResponseModel populateInvalidPrescriptionResponse(HttpMessageNotReadableException ex,
			ContentCachingRequestWrapper requestWrapper) {
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		List<String> errors = new ArrayList<>();
		if (ex.getCause() instanceof InvalidFormatException) {
			InvalidFormatException iex = (InvalidFormatException) ex.getCause();
			iex.getPath().forEach(reference -> {
				if (!reference.getDescription().contains("ArrayList")
						&& !reference.getDescription().contains("drugList")
						&& StringUtils.isNotBlank(reference.getFieldName())) {
					errors.add(reference.getFieldName());
				}
			});
		}
		String requestId = UUID.randomUUID().toString();
		PrescriptionResponseModel invalidResponse = new PrescriptionResponseModel();
		invalidResponse.setStatus(STR_INVALID);
		String error = errors.toString().replace("[", "").replace("]", "").concat(STR_INVALID_FORMAT);
		invalidResponse.setStatusDescription(error);
		invalidResponse.setRequestId(requestId);
		invalidResponse.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
		invalidResponse.setHttpStatusDescription(error);
		populateTransactionLog(requestWrapper, invalidResponse, null, true, providerId);
		return invalidResponse;
	}

	public void validateFields(ContentCachingRequestWrapper requestWrapper,
			PrescriptionRequestModel prescriptionRequest, String providerId) throws PrescriptionException {
		validateMemberIdAndIdNumber(prescriptionRequest.getIdNumber(), prescriptionRequest.getMemberId(),
				prescriptionRequest.getPolicyNumber(), requestWrapper);
		validatePhysicianDetails(prescriptionRequest.getPhysicianLicenseNumber(), requestWrapper, providerId);
	}

	private void validateMemberIdAndIdNumber(String idNumber, String memberId, String policyNumber,
			ContentCachingRequestWrapper requestWrapper) throws PrescriptionException {
		if (StringUtils.isBlank(idNumber) && StringUtils.isBlank(memberId)) {
			throw new PrescriptionException(
					populateInvalidPrescriptionResponse(requestWrapper, "IDNumber or MemberID is mandatory", null));
		}
		if (StringUtils.isNotBlank(memberId) && StringUtils.isBlank(policyNumber)) {
			throw new PrescriptionException(populateInvalidPrescriptionResponse(requestWrapper,
					"PolicyNumber is mandatory with MemberID", null));
		}
	}

	private void validatePhysicianDetails(String physicianLicenseNumber, ContentCachingRequestWrapper requestWrapper,
			String providerId) throws NumberFormatException, PrescriptionException {
		PhysicianInfo physicianInfo = physicianInfoRepository
				.findByRegistrationNumberAndProviderId(physicianLicenseNumber, Long.valueOf(providerId))
				.orElseThrow(() -> new PrescriptionException(populateInvalidPrescriptionResponse(requestWrapper,
						PrescriptionExceptionMessages.PHYSICIAN_LICENSE_NUMBER_NOT_FOUND.value()
								.replace(PrescriptionExceptionConstants.PHYSICIAN_LICENSE_NUMBER_FIELD.value(),
										physicianLicenseNumber)
								.replace(PrescriptionExceptionConstants.PROVIDER_ID_FIELD.value(), providerId),
						null)));
	}

	public void validateDiagnosisType(List<DiagnosisCodes> diagnosisCodesList,
			ContentCachingRequestWrapper requestWrapper) throws PrescriptionException {
		if (diagnosisCodesList != null && diagnosisCodesList.stream()
				.filter(diagnosisCodes -> diagnosisCodes.getDiagnosisType().equalsIgnoreCase("primary")).count() != 1) {
			throw new PrescriptionException(populateInvalidPrescriptionResponse(requestWrapper,
					"At most one Primary diagnosis must be present.", null));
		}
	}

	public PrescriptionResponseModel populateUnautorizedPrescriptionResponse(AccessDeniedException ex,
			ContentCachingRequestWrapper requestWrapper) {
		String requestId = UUID.randomUUID().toString();
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		PrescriptionRequestModel prescriptionRequest = mapperService.mapPrescriptionRequest(requestWrapper);
		PrescriptionResponseModel invalidResponse = new PrescriptionResponseModel();
		invalidResponse.setStatus(STR_UNAUTHORIZED);
		String error = ex.getMessage();
		invalidResponse.setStatusDescription(error);
		invalidResponse.setRequestId(requestId);
		invalidResponse.setHttpStatusCode(HttpStatus.UNAUTHORIZED.value());
		invalidResponse.setHttpStatusDescription(error);
		populateTransactionLog(requestWrapper, invalidResponse, prescriptionRequest.getePrescriptionReferenceNumber(),
				false, providerId);
		return invalidResponse;
	}

	public PrescriptionResponseModel populateUnautorizedPrescriptionResponse(AccessDeniedException ex,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
		String requestId = UUID.randomUUID().toString();
		String ePrescriptionReferenceNumber = null;
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		if (!requestWrapper.getMethod().equals(HttpMethod.DELETE.name())) {
			PrescriptionRequestModel prescriptionRequest = mapperService.mapPrescriptionRequest(requestWrapper);
			if (prescriptionRequest != null) {
				ePrescriptionReferenceNumber = prescriptionRequest.getePrescriptionReferenceNumber();
			}
		}
		if (ePrescriptionReferenceNumber == null) {
			ePrescriptionReferenceNumber = fetchValueFromUri(request, E_PRESCRIPTION_REFERENCE_NUMBER_STRING);
		}
		PrescriptionResponseModel invalidResponse = new PrescriptionResponseModel();
		invalidResponse.setStatus(STR_UNAUTHORIZED);
		String error = ex.getMessage();
		invalidResponse.setStatusDescription(error);
		invalidResponse.setRequestId(requestId);
		invalidResponse.setHttpStatusCode(HttpStatus.UNAUTHORIZED.value());
		invalidResponse.setHttpStatusDescription(error);
		populateTransactionLog(requestWrapper, invalidResponse, ePrescriptionReferenceNumber, false, providerId);
		return invalidResponse;
	}

	public PrescriptionRequest getPrescriptionRequestObject(String ePrescriptionReferenceNumber,
			ContentCachingRequestWrapper requestWrapper) throws PrescriptionException {
		Optional<PrescriptionRequest> prescriptionRequestOptional = prescriptionRequestRepository
				.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		if (prescriptionRequestOptional.isPresent()) {
			return prescriptionRequestOptional.get();
		}
		throw new PrescriptionException(populateInvalidPrescriptionResponse(requestWrapper,
				E_PRESCRIPTION_REFERENCE_NUMBER_NOT_EXIST_STRING, ePrescriptionReferenceNumber));
	}

	public Object populateInvalidResForConnectionIssue(String errorMessage, String id, String serviceName) {
		List<String> errors = new ArrayList<>();
		errors.add(errorMessage);
		String error = errors.toString().replace("[", "").replace("]", "");
		if (StringUtils.equals(ServiceName.DSS_SERVICE.getValue(), serviceName)) {
			return prepareDssResponseForConnectionIssue(id, errors, error);
		} else if (StringUtils.equals(ServiceName.POLICY_CONSUMPTION_SERVICE.getValue(), serviceName)) {
			return preparePolicyConsumptionResponseForConnectionIssue(error);
		} else if (StringUtils.equals(ServiceName.DRUG_EXCLUSION_SERVICE.getValue(), serviceName)) {
			return prepareSpecialityExclusionResponseForConnectionIssue(id, error);
		} else if (StringUtils.equals(ServiceName.DRUG_FORMULARY_SERVICE.getValue(), serviceName)) {
			return prepareDrugFormularyResponseForConnectionIssue(error);
		} else if (StringUtils.equals(ServiceName.BR_SERVICE.getValue(), serviceName)) {
			return prepareBrResponseForConnectionIssue(id, error);
		}
		return null;
	}

	private DssResponse prepareDssResponseForConnectionIssue(String requestId, List<String> errors, String error) {
		DssResponse invalidResponse = new DssResponse();
		invalidResponse.setRequestId(requestId);
		invalidResponse.setStatus(FAILED_STRING);
		invalidResponse.setErrors(errors);
		invalidResponse.setHttpStatusCode(HttpStatus.REQUEST_TIMEOUT.value());
		invalidResponse.setHttpStatusDescription(error);
		return invalidResponse;
	}

	private DrugExclusionResponseModel prepareSpecialityExclusionResponseForConnectionIssue(String requestId,
			String error) {
		return new DrugExclusionResponseModel(requestId, FAILED_STRING, error);
	}

	private DrugFormularyResponseModel prepareDrugFormularyResponseForConnectionIssue(String error) {
		return new DrugFormularyResponseModel(FAILED_STRING, error);
	}

	private PolicyResponseModel preparePolicyConsumptionResponseForConnectionIssue(String error) {
		PolicyResponseModel policyResponseModel = new PolicyResponseModel();
		policyResponseModel.setStatus(FAILED_STRING);
		policyResponseModel.setStatusDescription(error);
		policyResponseModel.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		return policyResponseModel;
	}

	public PrescriptionCancellationResponseModel prepareCancellationDssResponseForConnectionIssue(String errorMessage,
			String ePrescriptionReferenceNumber) {
		List<String> errors = new ArrayList<>();
		errors.add(errorMessage);
		String error = errors.toString().replace("[", "").replace("]", "");
		PrescriptionCancellationResponseModel invalidResponse = new PrescriptionCancellationResponseModel();
		invalidResponse.setCanCancel(false);
		invalidResponse.setCanFollowUp(false);
		invalidResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		invalidResponse.setHttpStatusCode(HttpStatus.REQUEST_TIMEOUT.value());
		invalidResponse.setStatus(FAILED_STRING);
		invalidResponse.setStatusDescription(error);
		return invalidResponse;
	}

	private SensitiveDrugResponseModel prepareBrResponseForConnectionIssue(String requestId, String error) {
		SensitiveDrugResponseModel invalidResponse = new SensitiveDrugResponseModel();
		invalidResponse.setRequestId(requestId);
		invalidResponse.setErrorDescription(Arrays.asList(error));
		invalidResponse.setErrorCode(FAILED_STRING);
		return invalidResponse;
	}
}
