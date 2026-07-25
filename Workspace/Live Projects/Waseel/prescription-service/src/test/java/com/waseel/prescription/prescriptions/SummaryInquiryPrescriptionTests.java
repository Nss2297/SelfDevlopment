package com.waseel.prescription.prescriptions;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.enums.DomainName;
import com.waseel.prescription.model.enums.PrescriptionUrl;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.SourceType;
import com.waseel.prescription.model.enums.TransactionStatusType;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryRequestModel;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryResponseModel;
import com.waseel.prescription.persist.prescriptionservice.InvalidPrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.prescriptionservice.InvalidPrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.TransactionLogRepository;
import com.waseel.prescription.service.inquiry.PrescriptionSummaryInquiryService;
import com.waseel.prescription.service.management.TransactionLogService;
import com.waseel.prescription.util.DateUtil;

@SpringBootTest
@ActiveProfiles("test")
public class SummaryInquiryPrescriptionTests {

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private TransactionLogService transactionLogService;

	@MockBean
	private InvalidPrescriptionRequestRepository invalidPrescriptionRequestRepository;
	@MockBean
	private TransactionLogRepository transactionLogRepository;
	@MockBean
	private MemberInfoRepository memberInfoRepository;
	@Autowired
	private PrescriptionSummaryInquiryService prescriptionSummaryInquiryService;

	private PrescriptionSummaryRequestModel prescriptionSummaryRequestModel;
	private ContentCachingRequestWrapper contentCachingRequestWrapper;
	private TransactionLog transactionLog;
	private List<MemberInfo> memberInfo;
	private List<InvalidPrescriptionRequest> invalidPrescriptionRequest;
	private String requestId = UUID.randomUUID().toString();
	private String payerId = "102";
	private String providerId = "12";
	private String idNumber = "1234567890";
	private String memberId = "198";
	private String policyNumber = "5666";
	private String startDate = "01-03-2023";
	private String endDate = "30-03-2023";
	private static final String DataFound = "Data fetched successfully.";
	private static final String START_DATE = "StartDate";
	private static final String END_DATE = "EndDate";
	private Map<String, Timestamp> startAndEndDates;

	@BeforeEach
	void setupData() throws ParseException {
		prescriptionSummaryRequestModel = generateSummaryInquiryRequestModel();
		contentCachingRequestWrapper = getContentCachingRequestWrapper();
		transactionLog = generateTransactionLogWithvalidStatus();
		MockHttpServletRequest hRequest = new MockHttpServletRequest();
		startAndEndDates = getStartAndEndDates(prescriptionSummaryRequestModel);
		memberInfo = generateMemberInfoList(startAndEndDates);
		invalidPrescriptionRequest = generateInvalidPrescription();

		Mockito.when(transactionLogRepository.save(Mockito.any())).thenReturn(transactionLog);
		Mockito.when(transactionLogService.updateTransactionLog(transactionLog.getTransactionLogId(), null, DataFound,
				hRequest, null)).thenReturn(transactionLog);
		Mockito.when(transactionLogService.addInquiryTransaction(RequestType.SUMMARY_INQUIRY, payerId, providerId,
				requestId, null, SourceType.INTEGRATION.value())).thenReturn(transactionLog);
		Mockito.when(memberInfoRepository
				.findSummaryInquiryByIdNumberAndPrescriptionRequest_ProviderIdAndPrescriptionRequestPayerId_AndPrescriptionRequest_SendDateTimeGreaterThanEqualAndPrescriptionRequest_SendDateTimeLessThanEqual(
						Long.parseLong(prescriptionSummaryRequestModel.getIdNumber()), providerId, payerId,
						startAndEndDates.get(START_DATE), startAndEndDates.get(END_DATE)))
				.thenReturn(Optional.of(memberInfo));
		Mockito.when(invalidPrescriptionRequestRepository
				.findByIdNumberAndProviderIdAndPayerIdAndSendDateTimeGreaterThanEqualAndSendDateTimeLessThanEqual(
						Long.parseLong(prescriptionSummaryRequestModel.getIdNumber()), providerId, payerId,
						startAndEndDates.get(START_DATE), startAndEndDates.get(END_DATE)))
				.thenReturn(Optional.of(invalidPrescriptionRequest));

		assertNotNull(memberInfo);
		assertNotNull(invalidPrescriptionRequest);
	}

	@Test
	void validPrescriptionSummaryInquiryResponseTest() throws PrescriptionException, ParseException {
		try {
			PrescriptionSummaryResponseModel response = prescriptionSummaryInquiryService
					.managePrescriptionSummaryRequest(payerId, prescriptionSummaryRequestModel,
							contentCachingRequestWrapper,DomainName.WASEEL.value());
			if (response != null)
				assertResponseIsValid(response);
		} catch (PrescriptionException invalid) {
			if (invalid.getInvalidInquiryResponse() != null) {
				System.out.print("InvalidException: Status -> " + invalid.getInvalidInquiryResponse().getErrorStatus()
						+ " Description -> " + invalid.getInvalidInquiryResponse().getErrorDescription());
				assertNotNull(invalid.getInvalidInquiryResponse().getErrorDescription());
				assertNotNull(invalid.getInvalidInquiryResponse().getErrorStatus());
			}
		} catch (Exception e) {
			System.out.print("Exception:---" + e);
		}
	}

	private List<InvalidPrescriptionRequest> generateInvalidPrescription() {
		List<InvalidPrescriptionRequest> prescriptionList = new ArrayList<InvalidPrescriptionRequest>();
		InvalidPrescriptionRequest prescriptionRequest = new InvalidPrescriptionRequest();
		prescriptionRequest.setePrescriptionReferenceNumber("2023-1223");
		prescriptionRequest.setStatus("REJECTED");
		prescriptionRequest
				.setStatusDescription("Drug Not Found For Code : 23-8873-19, Drug Not Found For Code : 23-88d3-19");
		prescriptionRequest.setSendDateTime(startAndEndDates.get(START_DATE));
		prescriptionList.add(prescriptionRequest);
		return prescriptionList;
	}

	void assertResponseIsValid(PrescriptionSummaryResponseModel response) {
		assertNotNull(response.getRequestId());
		assertNotNull(response.getRequestStatus());
		if (response.getPrescriptionSummary() != null && !response.getPrescriptionSummary().isEmpty()) {
			response.getPrescriptionSummary().forEach(r -> {
				assertNotNull(r.getePrescriptionReferenceNumber());
				assertNotNull(r.getStatusDescription());
			});
		}
	}

	private List<MemberInfo> generateMemberInfoList(Map<String, Timestamp> startAndEndDates) {
		List<MemberInfo> memberInfoList = new ArrayList<MemberInfo>();
		Set<PrescriptionRequest> prescriptionRequestSet = new HashSet<PrescriptionRequest>();
		PrescriptionRequest ePrescription = new PrescriptionRequest();
		ePrescription.setReceivedDateTime(startAndEndDates.get(START_DATE));
		ePrescription.setStatusCode("REJECTED");
		ePrescription
				.setStatusDescription("Drug Not Found For Code : 23-8873-19, Drug Not Found For Code : 23-88d3-19");
		ePrescription.setePrescriptionReferenceNumber("2023-1223");
		prescriptionRequestSet.add(ePrescription);
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setIdNumber(Long.parseLong(idNumber));
		memberInfo.setMemberId(memberId);
		memberInfo.setPolicyNumber(policyNumber);
		memberInfo.setRequestId(requestId);
		memberInfo.setPrescriptionRequest(prescriptionRequestSet);
		memberInfoList.add(memberInfo);
		return memberInfoList;
	}

	private ContentCachingRequestWrapper getContentCachingRequestWrapper() {
		ContentCachingRequestWrapper cachingRequestWrapper = null;
		try {
			MockHttpServletRequest hRequest = new MockHttpServletRequest();
			String req = this.mapper.writeValueAsString(prescriptionSummaryRequestModel);
			hRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
			hRequest.setContent(req.getBytes(StandardCharsets.UTF_8.name()));
			hRequest.setRequestURI(PrescriptionUrl.SUMMARY_INQUIRY.getValue());
			cachingRequestWrapper = new ContentCachingRequestWrapper(hRequest);
			cachingRequestWrapper.setRequest(hRequest);
			FileCopyUtils.copyToByteArray(cachingRequestWrapper.getInputStream());
			return cachingRequestWrapper;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cachingRequestWrapper;
	}

	private PrescriptionSummaryRequestModel generateSummaryInquiryRequestModel() {
		PrescriptionSummaryRequestModel prescriptionSummaryRequestModel = new com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryRequestModel();
		prescriptionSummaryRequestModel.setIdNumber(idNumber);
		prescriptionSummaryRequestModel.setMemberID(memberId);
		prescriptionSummaryRequestModel.setPolicyNumber(policyNumber);
		prescriptionSummaryRequestModel.setStartDate(startDate);
		prescriptionSummaryRequestModel.setEndDate(endDate);
		return prescriptionSummaryRequestModel;
	}

	private TransactionLog generateTransactionLogWithvalidStatus() {
		return new TransactionLog(1L, requestId, 51.11, RequestType.SUMMARY_INQUIRY.name(), payerId, providerId,
				TransactionStatusType.SENT.name(), null, null, null,
				new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), "PBM", String.valueOf(HttpStatus.OK.value()),
				RequestStatusType.APPROVED.value());
	}

	private Map<String, Timestamp> getStartAndEndDates(PrescriptionSummaryRequestModel prescriptionSummaryRequestModel)
			throws ParseException {
		Map<String, Timestamp> dates = new HashMap<String, Timestamp>();
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

}
