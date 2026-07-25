package com.waseel.prescription.service.inquiry;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryRequestModel;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryResponseModel;
import com.waseel.prescription.persist.prescriptionservice.InvalidPrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.prescriptionservice.InvalidPrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberInfoRepository;
import com.waseel.prescription.service.management.SessionService;
import com.waseel.prescription.service.management.TransactionLogService;
import com.waseel.prescription.service.mapper.SummaryInquiryMapperService;
import com.waseel.prescription.service.validation.InquiryTechnicalValidationService;
import com.waseel.prescription.util.DateUtil;
import com.waseel.prescription.util.SourceTypeUtil;
import com.waseel.prescription.util.UserInfoUtil;

@Service
public class PrescriptionSummaryInquiryService {

	@Autowired
	MemberInfoRepository memberInfoRepository;
	@Autowired
	SummaryInquiryMapperService summaryInquiryMapperService;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	InquiryTechnicalValidationService inquiryTechnicalValidationService;
	@Autowired
	private TransactionLogService transactionLogService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	InvalidPrescriptionRequestRepository invalidPrescriptionRequestRepository;

	private static final String START_DATE = "StartDate";
	private static final String END_DATE = "EndDate";

	public PrescriptionSummaryResponseModel managePrescriptionSummaryRequest(String payerId,
			PrescriptionSummaryRequestModel prescriptionSummaryRequestModel,
			ContentCachingRequestWrapper requestWrapper, String headerOrigin) throws PrescriptionException, ParseException {
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		String requestId = UUID.randomUUID().toString();
		inquiryTechnicalValidationService.validatePrescriptionInquirySummaryRequest(prescriptionSummaryRequestModel,
				requestWrapper, null);
		String sourceType = SourceTypeUtil.getSourceTypeBasedOnHeaderOrigin(headerOrigin);
		TransactionLog transactionLog = transactionLogService.addInquiryTransaction(RequestType.SUMMARY_INQUIRY,
				payerId, providerId, requestId, null, sourceType);
		if (transactionLog != null && transactionLog.getTransactionLogId() != null) {
			sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
		}
		Optional<List<MemberInfo>> memberInfo = Optional.empty();
		Optional<List<InvalidPrescriptionRequest>> invalidRequests = Optional.empty();
		Map<String, Timestamp> startAndEndDates = getStartAndEndDates(prescriptionSummaryRequestModel);
		if (queryByIdNumber(prescriptionSummaryRequestModel)) {
			memberInfo = memberInfoRepository
					.findSummaryInquiryByIdNumberAndPrescriptionRequest_ProviderIdAndPrescriptionRequestPayerId_AndPrescriptionRequest_SendDateTimeGreaterThanEqualAndPrescriptionRequest_SendDateTimeLessThanEqual(
							Long.parseLong(prescriptionSummaryRequestModel.getIdNumber()), providerId, payerId,
							startAndEndDates.get(START_DATE), startAndEndDates.get(END_DATE));
			invalidRequests = invalidPrescriptionRequestRepository
					.findByIdNumberAndProviderIdAndPayerIdAndSendDateTimeGreaterThanEqualAndSendDateTimeLessThanEqual(
							Long.parseLong(prescriptionSummaryRequestModel.getIdNumber()), providerId, payerId,
							startAndEndDates.get(START_DATE), startAndEndDates.get(END_DATE));
		} else if (queryByMemberIdAndPolicyNumber(prescriptionSummaryRequestModel)) {
			memberInfo = memberInfoRepository
					.findSummaryInquiryByMemberIdAndPolicyNumberAndPrescriptionRequest_ProviderIdAndPrescriptionRequestPayerId_AndPrescriptionRequest_SendDateTimeGreaterThanEqualAndPrescriptionRequest_SendDateTimeLessThanEqual(
							prescriptionSummaryRequestModel.getMemberID(),
							prescriptionSummaryRequestModel.getPolicyNumber(), providerId, payerId,
							startAndEndDates.get(START_DATE), startAndEndDates.get(END_DATE));
			invalidRequests = invalidPrescriptionRequestRepository
					.findByMemberIdAndPolicyNumberAndProviderIdAndPayerIdAndSendDateTimeGreaterThanEqualAndSendDateTimeLessThanEqual(
							prescriptionSummaryRequestModel.getMemberID(),
							prescriptionSummaryRequestModel.getPolicyNumber(), providerId, payerId,
							startAndEndDates.get(START_DATE), startAndEndDates.get(END_DATE));
		}
		return (memberInfo.isPresent() && !memberInfo.get().isEmpty())
				|| (invalidRequests.isPresent() && !invalidRequests.get().isEmpty())
						? manageSummaryInquiryMapping(memberInfo, invalidRequests, requestId)
						: manageDataNotFoundMapping(requestId);
	}

	private PrescriptionSummaryResponseModel manageDataNotFoundMapping(String requestId) {
		return summaryInquiryMapperService.mapNoDatafoundResponse(requestId);
	}

	private PrescriptionSummaryResponseModel manageSummaryInquiryMapping(Optional<List<MemberInfo>> memberInfo,
			Optional<List<InvalidPrescriptionRequest>> invalidRequests, String requestId) {
		return summaryInquiryMapperService.mapSummryInquiryResponse(memberInfo.isPresent() ? memberInfo.get() : null,
				invalidRequests.isPresent() ? invalidRequests.get() : null, requestId);
	}

	private Map<String, Timestamp> getStartAndEndDates(PrescriptionSummaryRequestModel prescriptionSummaryRequestModel)
			throws ParseException {
		Map<String, Timestamp> dates = new HashMap<>();
		if (prescriptionSummaryRequestModel != null && prescriptionSummaryRequestModel.getStartDate() != null
				&& prescriptionSummaryRequestModel.getEndDate() != null) {
			dates.put(START_DATE,
					DateUtil.getTimestampFromString(prescriptionSummaryRequestModel.getStartDate(), "dd-MM-yyyy"));
			dates.put(END_DATE, getEndOfDay(
					DateUtil.getTimestampFromString(prescriptionSummaryRequestModel.getEndDate(), "dd-MM-yyyy")));

		} else {
			setDefaultDate(dates);
		}
		return dates;
	}

	private void setDefaultDate(Map<String, Timestamp> dates) {
		dates.put(START_DATE, DateUtil.getTimestampOneWeekAgo());
		dates.put(END_DATE, getEndOfDay(DateUtil.getTimestampFromDate(new Date())));
	}

	@SuppressWarnings("deprecation")
	private Timestamp getEndOfDay(Timestamp timestamp) {
		timestamp.setHours(23);
		timestamp.setMinutes(59);
		timestamp.setSeconds(59);
		timestamp.setNanos(999999999);
		return timestamp;
	}

	private boolean queryByIdNumber(PrescriptionSummaryRequestModel prescriptionSummaryRequestModel) {
		return prescriptionSummaryRequestModel.getIdNumber() != null
				&& !prescriptionSummaryRequestModel.getIdNumber().isEmpty();
	}

	private boolean queryByMemberIdAndPolicyNumber(PrescriptionSummaryRequestModel prescriptionSummaryRequestModel) {
		return prescriptionSummaryRequestModel.getMemberID() != null
				&& !prescriptionSummaryRequestModel.getMemberID().isEmpty()
				&& prescriptionSummaryRequestModel.getPolicyNumber() != null
				&& !prescriptionSummaryRequestModel.getPolicyNumber().isEmpty();
	}
}
