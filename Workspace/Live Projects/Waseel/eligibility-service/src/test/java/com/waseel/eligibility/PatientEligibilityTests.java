package com.waseel.eligibility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.eligibility.client.portal.SOAPConnector;
import com.waseel.eligibility.client.portal.model.EligibilitySubmissionReponseCT;
import com.waseel.eligibility.client.portal.model.StatusCT;
import com.waseel.eligibility.client.portal.model.StatusCodeST;
import com.waseel.eligibility.client.portal.model.TransactionWrapper;
import com.waseel.eligibility.enums.EligibilityDenialCode;
import com.waseel.eligibility.enums.EligibilityStatusType;
import com.waseel.eligibility.enums.TransactionStatusType;
import com.waseel.eligibility.exception.EligibilityException;
import com.waseel.eligibility.model.EligibilityRequestModel;
import com.waseel.eligibility.model.EligibilityResponseModel;
import com.waseel.eligibility.persist.businessrules.CommonDenials;
import com.waseel.eligibility.persist.businessrules.TransactionLog;
import com.waseel.eligibility.repository.businessrules.CommonDenialsRepository;
import com.waseel.eligibility.repository.businessrules.TransactionLogRepository;
import com.waseel.eligibility.service.EligibilityService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles({ "test" })
class PatientEligibilityTests {

	private static final Logger log = LoggerFactory.getLogger(PatientEligibilityTests.class);

	@Autowired
	private EligibilityService eligibilityService;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private TransactionLogRepository transactionLogRepository;

	@MockBean
	private SOAPConnector soapConnector;

	@MockBean
	private CommonDenialsRepository commonDenialsRepository;

	private String payerId = "102";
	private String providerId = "1200";
	private String requestId = "0fb78ee7-9e62-4d9e-aee7-18f099e126f1";
	private String idNumber = "2392019564";
	private String transactionReferenceNumber = "47985082";
	private Timestamp timestamp = (new Timestamp(Calendar.getInstance().getTimeInMillis()));
	private String eligibleStatus = "Physician consultation is covered subject to Tawuniya's Policy Terms and Conditions.";
	private String inEligibleStatus = "Provider not Active.";
	private String invalidStatus = "No response received from portal. Please try again later.";
	private String failedStatus = "Eligibility submission failed.";
	private String denialCode = "W-09";
	private EligibilityRequestModel eligibilityRequestModel = null;
	private MockHttpServletRequest request = new MockHttpServletRequest();
	private ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
	private MockHttpServletResponse response = new MockHttpServletResponse();;
	private ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(response);

	@BeforeAll
	private void commonData() {
		eligibilityRequestModel = populateEligibilityRequestModel();
	}

	@BeforeEach
	private void setUpDate() {
		cachingRequestWrapper = getContentCachingRequestWrapper();
		Mockito.when(transactionLogRepository.save(Mockito.any())).thenReturn(generateTransactionLog());
	}

	@Test
	@DisplayName("ELIGIBLE patient.")
	void eligiblePatientTest() throws EligibilityException {
		Mockito.when(soapConnector.callWebService(Mockito.any(), Mockito.any()))
				.thenReturn(generateEligibleTransactionWrapper());
		EligibilityResponseModel eligibilityResponseModel = eligibilityService.eligibilityController(idNumber,
				eligibilityRequestModel, cachingRequestWrapper, cachingResponseWrapper);
		assertNotNull(eligibilityResponseModel);
		assertNull(eligibilityResponseModel.getDenialCode());
		assertEquals(eligibilityResponseModel.getStatus(), EligibilityStatusType.ELIGIBLE.value());
		assertNull(eligibilityResponseModel.getDescription());
		assertEquals(eligibilityResponseModel.getHttpStatusCode(), HttpStatus.OK.value());
		assertEquals(eligibilityResponseModel.getRequestId(), requestId);
		assertEquals(eligibilityResponseModel.getStatusDescription(), eligibleStatus);
	}

	@Test
	@DisplayName("INELIGIBLE patient.")
	void inEligiblePatientTest() throws EligibilityException {
		Mockito.when(soapConnector.callWebService(Mockito.any(), Mockito.any()))
				.thenReturn(generateInEligibleTransactionWrapper());
		Mockito.when(commonDenialsRepository.findByDenialCode(Mockito.any()))
				.thenReturn(generateIneligibleDenialCode());
		EligibilityResponseModel eligibilityResponseModel = eligibilityService.eligibilityController(idNumber,
				eligibilityRequestModel, cachingRequestWrapper, cachingResponseWrapper);
		assertNotNull(eligibilityResponseModel);
		assertNotNull(eligibilityResponseModel.getDenialCode());
		assertEquals(eligibilityResponseModel.getDenialCode(), EligibilityDenialCode.INELIGIBLE.value());
		assertEquals(eligibilityResponseModel.getStatus(), EligibilityStatusType.INELIGIBLE.value());
		assertNotNull(eligibilityResponseModel.getDescription());
		assertEquals(eligibilityResponseModel.getDescription(), inEligibleStatus);
		assertEquals(eligibilityResponseModel.getHttpStatusCode(), HttpStatus.OK.value());
		assertEquals(eligibilityResponseModel.getRequestId(), requestId);
		assertEquals(eligibilityResponseModel.getStatusDescription(), inEligibleStatus);
	}

	@Test
	@DisplayName("INVALID patient.")
	void invalidPatientTest() throws EligibilityException {
		Mockito.when(soapConnector.callWebService(Mockito.any(), Mockito.any()))
				.thenReturn(generateInvalidTransactionWrapper());
		Mockito.when(commonDenialsRepository.findByDenialCode(Mockito.any())).thenReturn(generateInvalidDenialCode());
		EligibilityResponseModel eligibilityResponseModel = eligibilityService.eligibilityController(idNumber,
				eligibilityRequestModel, cachingRequestWrapper, cachingResponseWrapper);
		assertNotNull(eligibilityResponseModel);
		assertNotNull(eligibilityResponseModel.getDenialCode());
		assertEquals(eligibilityResponseModel.getDenialCode(), EligibilityDenialCode.INVALID.value());
		assertEquals(eligibilityResponseModel.getStatus(), EligibilityStatusType.INVALID.value());
		assertNotNull(eligibilityResponseModel.getDescription());
		assertEquals(eligibilityResponseModel.getDescription(), invalidStatus);
		assertEquals(eligibilityResponseModel.getHttpStatusCode(), HttpStatus.OK.value());
		assertEquals(eligibilityResponseModel.getRequestId(), requestId);
		assertEquals(eligibilityResponseModel.getStatusDescription(), invalidStatus);
	}

	private EligibilityException generateEligibilityException() {
		EligibilityResponseModel responseModel = new EligibilityResponseModel();
		responseModel.setDenialCode(EligibilityDenialCode.FAILED.value());
		responseModel.setDescription(failedStatus);
		responseModel.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		responseModel.setReferenceNumber(transactionReferenceNumber);
		responseModel.setRequestId(requestId);
		responseModel.setStatus(failedStatus);
		responseModel.setStatusDescription(failedStatus);
		EligibilityException eligibilityException = new EligibilityException(responseModel);
		return eligibilityException;
	}

	private EligibilityRequestModel populateEligibilityRequestModel() {
		return new EligibilityRequestModel(payerId, providerId, requestId);
	}

	private ContentCachingRequestWrapper getContentCachingRequestWrapper() {
		ContentCachingRequestWrapper cachingRequestWrapper = null;
		try {
			MockHttpServletRequest hRequest = new MockHttpServletRequest();
			String req = this.mapper.writeValueAsString(eligibilityRequestModel);
			hRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
			hRequest.setContent(req.getBytes(StandardCharsets.UTF_8.name()));
			cachingRequestWrapper = new ContentCachingRequestWrapper(hRequest);
			cachingRequestWrapper.setRequest(hRequest);
			FileCopyUtils.copyToByteArray(cachingRequestWrapper.getInputStream());
			return cachingRequestWrapper;
		} catch (JsonProcessingException e) {
			log.error("Exception:-", e);
		} catch (UnsupportedEncodingException e) {
			log.error("Exception:-", e);
		} catch (IOException e) {
			log.error("Exception:-", e);
		}
		return cachingRequestWrapper;
	}

	private TransactionLog generateTransactionLog() {
		return new TransactionLog(1L, Double.valueOf(52.1), requestId, transactionReferenceNumber, payerId, providerId,
				EligibilityStatusType.ELIGIBLE.value(), eligibleStatus, timestamp, timestamp,
				String.valueOf(HttpStatus.OK.value()), eligibleStatus, TransactionStatusType.SENT.value(), null);
	}

	private TransactionWrapper generateEligibleTransactionWrapper() {
		StatusCT statusCT = new StatusCT();
		statusCT.setReferenceNumber(transactionReferenceNumber);
		statusCT.setStatusCode(StatusCodeST.ELIGIBLE);
		statusCT.setStatusDescription(eligibleStatus);
		EligibilitySubmissionReponseCT submissionReponseCT = new EligibilitySubmissionReponseCT();
		submissionReponseCT.setStatus(statusCT);
		TransactionWrapper wrapper = new TransactionWrapper();
		wrapper.setEligibilitySubmissionResponse(submissionReponseCT);
		return wrapper;
	}

	private Optional<CommonDenials> generateIneligibleDenialCode() {
		CommonDenials commonDenials = new CommonDenials(43L, EligibilityDenialCode.INELIGIBLE.value(),
				inEligibleStatus);
		return Optional.of(commonDenials);
	}

	private TransactionWrapper generateInEligibleTransactionWrapper() {
		StatusCT statusCT = new StatusCT();
		statusCT.setReferenceNumber(transactionReferenceNumber);
		statusCT.setDenialCode(denialCode);
		statusCT.setStatusCode(StatusCodeST.INELIGIBLE);
		statusCT.setStatusDescription(inEligibleStatus);
		EligibilitySubmissionReponseCT submissionReponseCT = new EligibilitySubmissionReponseCT();
		submissionReponseCT.setStatus(statusCT);
		TransactionWrapper wrapper = new TransactionWrapper();
		wrapper.setEligibilitySubmissionResponse(submissionReponseCT);
		return wrapper;
	}

	private Optional<CommonDenials> generateInvalidDenialCode() {
		CommonDenials commonDenials = new CommonDenials(41L, EligibilityDenialCode.INVALID.value(), invalidStatus);
		return Optional.of(commonDenials);
	}

	private TransactionWrapper generateInvalidTransactionWrapper() {
		StatusCT statusCT = new StatusCT();
		statusCT.setReferenceNumber(transactionReferenceNumber);
		statusCT.setDenialCode(denialCode);
		statusCT.setStatusCode(StatusCodeST.INVALID);
		statusCT.setStatusDescription(invalidStatus);
		EligibilitySubmissionReponseCT submissionReponseCT = new EligibilitySubmissionReponseCT();
		submissionReponseCT.setStatus(statusCT);
		TransactionWrapper wrapper = new TransactionWrapper();
		wrapper.setEligibilitySubmissionResponse(submissionReponseCT);
		return wrapper;
	}
}
