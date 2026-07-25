package com.waseel.prescription.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
import com.waseel.prescription.model.common.ValidInvalidRequestModel;
import com.waseel.prescription.model.enums.PrescriptionUrl;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.inquiry.InquiryInvalidResponseModel;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryRequestModel;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryRequestModel;
import com.waseel.prescription.persist.prescriptionservice.InvalidPrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.prescriptionservice.InvalidPrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.service.management.SessionService;
import com.waseel.prescription.service.management.TransactionLogService;
import com.waseel.prescription.service.mapper.MapperService;
import com.waseel.prescription.util.SourceTypeUtil;
import com.waseel.prescription.util.UserInfoUtil;

@Service
public class InquiryTechnicalValidationService {

	private static final String STR_INVALID = "Invalid";
	private static final String STR_INVALID_FORMAT = " has invalid format.It should not have space, special characters or alphabets and should be a number or a decimal up to 2 digits.";
	private static final String STR_UNAUTHORIZED = "Unauthorized";

	@Autowired
	private MapperService mapperService;

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private PrescriptionRequestRepository prescriptionRequestRepository;

	@Autowired
	private InvalidPrescriptionRequestRepository invalidPrescriptionRequestRepository;

	public void validatePrescriptionInquirySummaryRequest(PrescriptionSummaryRequestModel summaryRequestModel,
			ContentCachingRequestWrapper requestWrapper, String requestId) throws PrescriptionException {
		List<String> errors = new ArrayList<>();
		validateMemberIdAndIdNumber(summaryRequestModel, errors);
		validateDates(summaryRequestModel, errors);
		if (summaryRequestModel.getIdNumber() != null && !summaryRequestModel.getIdNumber().isEmpty())
			validateIdNumber(summaryRequestModel, errors);
		if (errors.size() > 0) {
			throw new PrescriptionException(
					populateSummaryInquiryInvalidResponseModel(requestId, requestWrapper, errors, summaryRequestModel));
		}
	}

	private void validateIdNumber(PrescriptionSummaryRequestModel summaryRequestModel, List<String> errors) {
		if (!summaryRequestModel.getIdNumber().matches("[0-9]+")
				|| summaryRequestModel.getIdNumber().trim().length() != 10) {
			errors.add("idNumber must be 10 digits");
		}
	}

	private void validateMemberIdAndIdNumber(PrescriptionSummaryRequestModel summaryRequestModel, List<String> error) {

		if (StringUtils.isEmpty(summaryRequestModel.getIdNumber().trim())
				&& StringUtils.isEmpty(summaryRequestModel.getMemberID().trim())) {
			error.add("IDNumber or MemberID is mandatory");
		}
		if (StringUtils.isNotEmpty(summaryRequestModel.getMemberID().trim())
				&& StringUtils.isEmpty(summaryRequestModel.getPolicyNumber().trim())) {
			error.add("PolicyNumber is mandatory with MemberID");
		}
	}

	private void validateDates(PrescriptionSummaryRequestModel summaryRequestModel, List<String> error) {
		if (null != summaryRequestModel.getStartDate() && null == summaryRequestModel.getEndDate()) {
			error.add("EndDate is mandatory with startDate");
		}
		if (null == summaryRequestModel.getStartDate() && null != summaryRequestModel.getEndDate()) {
			error.add("StartDate is mandatory with endDate");
		}
	}

	private InquiryInvalidResponseModel populateSummaryInquiryInvalidResponseModel(String requestId,
			ContentCachingRequestWrapper requestWrapper, List<String> errors,
			PrescriptionSummaryRequestModel summaryRequestModel) {
		InquiryInvalidResponseModel invalidInquiryResponse = new InquiryInvalidResponseModel();
		invalidInquiryResponse.setErrorStatus(STR_INVALID);
		String errorStr = errors.toString().replace("[", "").replace("]", "");
		invalidInquiryResponse.setErrorDescription(errorStr);
		invalidInquiryResponse.setRequestId(requestId);
		return invalidInquiryResponse;
	}

	public InquiryInvalidResponseModel populateInvalidInquiryPrescriptionResponse(MethodArgumentNotValidException ex,
			ContentCachingRequestWrapper requestWrapper) {
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		InquiryInvalidResponseModel invalidResponse = new InquiryInvalidResponseModel();
		invalidResponse.setErrorStatus(STR_INVALID);
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
		String error = errors.toString().replace("[", "").replace("]", "");
		invalidResponse.setErrorDescription(error);
		if (requestWrapper.getRequestURI().contains(PrescriptionUrl.DETAIL_INQUIRY.getValue())) {
			PrescriptionDetailInquiryRequestModel detailInquiryModel = mapperService
					.mapPrescriptionDetailInquiryRequest(requestWrapper);
			String ePrescriptionReferenceNum = detailInquiryModel.getePrescriptionReferenceNumber();
			String requestId = getRequestId(ePrescriptionReferenceNum);
			invalidResponse.setRequestId(requestId);
			invalidResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNum);
			populateTransactionLog(requestWrapper, detailInquiryModel.getPayerId(), providerId, requestId,
					ePrescriptionReferenceNum);
		}
		return invalidResponse;
	}

	private RequestType getRequestType(ContentCachingRequestWrapper requestWrapper) {
		if (requestWrapper.getRequestURI().contains(PrescriptionUrl.DETAIL_INQUIRY.getValue())) {
			return RequestType.DETAIL_INQUIRY;
		} else {
			return RequestType.SUMMARY_INQUIRY;
		}
	}

	private void populateTransactionLog(ContentCachingRequestWrapper requestWrapper, String payerId, String providerId,
			String requestId, String ePrescriptionReferenceNumber) {
		String transactionLogId = sessionService.getTransactionLogIdFromSession(requestWrapper);
		if (StringUtils.isBlank(transactionLogId)) {
			String sourceType = SourceTypeUtil
					.getSourceTypeBasedOnHeaderOrigin(requestWrapper.getHeader(HttpHeaders.ORIGIN));
			TransactionLog transactionLog = transactionLogService.addInquiryTransaction(getRequestType(requestWrapper),
					payerId, providerId, requestId, ePrescriptionReferenceNumber, sourceType);
			if (transactionLog != null) {
				sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
			}
		}
	}

	public InquiryInvalidResponseModel populateFailedInquiryPrescriptionResponse(
			ContentCachingRequestWrapper requestWrapper) {
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		InquiryInvalidResponseModel invalidResponse = new InquiryInvalidResponseModel();
		invalidResponse.setErrorStatus("Failed");
		invalidResponse.setErrorDescription(HttpStatus.INTERNAL_SERVER_ERROR.name());
		if (requestWrapper.getRequestURI().contains(PrescriptionUrl.DETAIL_INQUIRY.getValue())) {
			PrescriptionDetailInquiryRequestModel detailInquiryModel = mapperService
					.mapPrescriptionDetailInquiryRequest(requestWrapper);
			String ePrescriptionReferenceNum = detailInquiryModel.getePrescriptionReferenceNumber();
			String requestId = getRequestId(ePrescriptionReferenceNum);
			invalidResponse.setRequestId(requestId);
			invalidResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNum);
			populateTransactionLog(requestWrapper, detailInquiryModel.getPayerId(), providerId, requestId,
					ePrescriptionReferenceNum);
		}
		return invalidResponse;
	}

	public InquiryInvalidResponseModel populateInvalidInquiryPrescriptionResponse(HttpMessageNotReadableException ex,
			ContentCachingRequestWrapper requestWrapper) {
		JSONObject requestModel = mapperService.mapBadPrescriptionRequest(requestWrapper);
		List<String> errors = new ArrayList<>();
		if (ex.getCause() instanceof InvalidFormatException) {
			InvalidFormatException iex = (InvalidFormatException) ex.getCause();
			iex.getPath().forEach(reference -> {
				if (StringUtils.isNotBlank(reference.getFieldName())) {
					errors.add(reference.getFieldName());
				}
			});
		}
		InquiryInvalidResponseModel invalidResponse = new InquiryInvalidResponseModel();
		invalidResponse.setErrorStatus(STR_INVALID);
		String error = errors.toString().replace("[", "").replace("]", "").concat(STR_INVALID_FORMAT);
		invalidResponse.setErrorDescription(error);
		if (requestWrapper.getRequestURI().contains(PrescriptionUrl.DETAIL_INQUIRY.getValue())) {
			String payerId = requestModel.getString("payerId");
			String providerId = requestModel.getString("providerId");
			String ePrescriptionReferenceNumber = requestModel.getString("ePrescriptionReferenceNumber");
			String requestId = getRequestId(ePrescriptionReferenceNumber);
			invalidResponse.setRequestId(requestId);
			invalidResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
			populateTransactionLog(requestWrapper, payerId, providerId, requestId, ePrescriptionReferenceNumber);
		}
		return invalidResponse;
	}

	private InquiryInvalidResponseModel populateInvalidDetailInquiryResponse(String errorMessage, String requestId,
			String ePrescriptionReferenceNumber, ContentCachingRequestWrapper requestWrapper, String providerId) {
		PrescriptionDetailInquiryRequestModel detailInquiryModel = mapperService
				.mapPrescriptionDetailInquiryRequest(requestWrapper);
		InquiryInvalidResponseModel invalidResponse = new InquiryInvalidResponseModel();
		List<String> errors = new ArrayList<>();
		errors.add(errorMessage);
		invalidResponse.setRequestId(requestId);
		invalidResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		invalidResponse.setErrorStatus(STR_INVALID);
		String error = errors.toString().replace("[", "").replace("]", "");
		invalidResponse.setErrorDescription(error);
		populateTransactionLog(requestWrapper, detailInquiryModel.getPayerId(), providerId, requestId,
				ePrescriptionReferenceNumber);
		return invalidResponse;
	}

	public ValidInvalidRequestModel validateDetailInquiryRequest(PrescriptionDetailInquiryRequestModel requestModel,
			ContentCachingRequestWrapper requestWrapper, String providerId) throws PrescriptionException {
		String ePrescriptionReferenceNumber = requestModel.getePrescriptionReferenceNumber();
		Optional<PrescriptionRequest> prescriptionRequest = prescriptionRequestRepository
				.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		if (prescriptionRequest.isPresent()) {
			return new ValidInvalidRequestModel(prescriptionRequest.get());
		} else {
			Optional<InvalidPrescriptionRequest> invalidPrescriptionRequest = invalidPrescriptionRequestRepository
					.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
			if (invalidPrescriptionRequest.isPresent()) {
				return new ValidInvalidRequestModel(invalidPrescriptionRequest.get());
			} else {
				throw new PrescriptionException(
						populateInvalidDetailInquiryResponse("EPrescriptionReferenceNumber is not found or exists.",
								null, ePrescriptionReferenceNumber, requestWrapper, providerId));
			}
		}
	}

	private String getRequestId(String ePrescriptionReferenceNumber) {
		Optional<PrescriptionRequest> prescriptionRequest = prescriptionRequestRepository
				.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		if (prescriptionRequest.isPresent()) {
			return prescriptionRequest.get().getRequestId();
		} else {
			Optional<InvalidPrescriptionRequest> invalidPrescriptionRequest = invalidPrescriptionRequestRepository
					.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
			if (invalidPrescriptionRequest.isPresent()) {
				return invalidPrescriptionRequest.get().getRequestId();
			}
		}
		return null;
	}

	public InquiryInvalidResponseModel populateUnautorizedPrescriptionResponse(AccessDeniedException ex,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		String payerId = "";
		String requestId = UUID.randomUUID().toString();
		String ePrescriptionReferenceNum = "";
		InquiryInvalidResponseModel invalidResponse = new InquiryInvalidResponseModel();
		invalidResponse.setErrorStatus(STR_UNAUTHORIZED);
		String error = ex.getMessage();
		invalidResponse.setErrorDescription(error);
		invalidResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNum);
		if (requestWrapper.getRequestURI().contains(PrescriptionUrl.DETAIL_INQUIRY.getValue())) {
			PrescriptionDetailInquiryRequestModel detailInquiryModel = mapperService
					.mapPrescriptionDetailInquiryRequest(requestWrapper);
			ePrescriptionReferenceNum = detailInquiryModel.getePrescriptionReferenceNumber();
			requestId = getRequestId(ePrescriptionReferenceNum);
			payerId = detailInquiryModel.getPayerId();
			invalidResponse.setePrescriptionReferenceNumber(ePrescriptionReferenceNum);
			invalidResponse.setRequestId(requestId);
		} else {
			Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			payerId = (String) pathVariables.get("payerId");
		}
		invalidResponse.setRequestId(requestId);
		populateTransactionLog(requestWrapper, payerId, providerId, requestId, ePrescriptionReferenceNum);
		return invalidResponse;
	}

}
