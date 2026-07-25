package com.waseel.prescription.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Year;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.cancellation.PrescriptionCancellationRequestModel;
import com.waseel.prescription.model.enums.BenefitCaseType;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.DssPayerTransactionType;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.SourceType;
import com.waseel.prescription.model.enums.TransactionStatusType;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.TransactionLogRepository;
import com.waseel.prescription.service.validation.TechnicalValidationService;
import com.waseel.prescription.util.UserInfoUtil;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles({ "test" })
class CancellationValidationTests {

	@Autowired
	private TechnicalValidationService technicalValidationService;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	public TransactionLogRepository transactionLogRepository;

	@MockBean
	private PrescriptionRequestRepository prescriptionRequestRepository;

	private String payerId = "102";
	private String providerId = "12";
	private String currentYear = Year.now().toString();
	String ePrescriptionReferenceNumber = currentYear + "-1";
	private String requestId = "0fb78ee7-9e62-4d9e-aee7-18f099e126f1";
	private Timestamp timestamp = (new Timestamp(Calendar.getInstance().getTimeInMillis()));
	protected static final int CONTENT_CACHE_LIMIT = 3;
	private final MockHttpServletRequest request = new MockHttpServletRequest();
	ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request, CONTENT_CACHE_LIMIT);
	private PrescriptionCancellationRequestModel cancellationRequestModel = new PrescriptionCancellationRequestModel(
			payerId, ePrescriptionReferenceNumber);
	Optional<TransactionLog> transactionLogOp = Optional.empty();
	Optional<PrescriptionRequest> prescriptionRequestOp = Optional.empty();
	private static final String currency = Currency.SAR.value();
	private static final String mappedPayerId = "102_" + DssPayerTransactionType.PRESCRIPTION.value();

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
	}

	@BeforeEach
	void setUpData() {
		requestWrapper = getContentCachingRequestWrapper(cancellationRequestModel);
		transactionLogOp = Optional.of(generateTransactionLog(null, null));
		Mockito.when(transactionLogRepository.findByePrescriptionReferenceNumberWithValidStatus(Mockito.any()))
				.thenReturn(transactionLogOp);
		prescriptionRequestOp = Optional.of(generatePrescriptionRequest());
		Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(Mockito.any()))
				.thenReturn(prescriptionRequestOp);
		Mockito.when(transactionLogRepository.generateEPrescriptionReferenceNumber())
				.thenReturn(ePrescriptionReferenceNumber);
	}

	@SuppressWarnings("unused")
	@Test
	@DisplayName("Invalid EPrescriptionReferenceNumber")
	void invalidEPrescriptionReferenceNumber() {
		Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(Mockito.any()))
				.thenReturn(Optional.empty());
		try {
			PrescriptionRequest request = technicalValidationService.validateCancellationRequest(
					cancellationRequestModel, requestWrapper, providerId, SourceType.INTEGRATION.value());
		} catch (PrescriptionException prescriptionException) {
			assertNotNull(prescriptionException);
			assertEquals("EPrescriptionReferenceNumber is not found or exists.",
					prescriptionException.getInvalidResponse().getStatusDescription());
		}
	}

	@SuppressWarnings("unused")
	@Test
	@DisplayName("Invalid PayerId")
	void invalidPayerId() {
		prescriptionRequestOp.get().setPayerId("103");
		Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(Mockito.any()))
				.thenReturn(prescriptionRequestOp);
		Mockito.when(prescriptionRequestRepository.findByRequestId(Mockito.any()))
		.thenReturn(prescriptionRequestOp);
		try {
			PrescriptionRequest request = technicalValidationService.validateCancellationRequest(
					cancellationRequestModel, requestWrapper, providerId, SourceType.INTEGRATION.value());
		} catch (PrescriptionException prescriptionException) {
			assertNotNull(prescriptionException);
			assertEquals("PayerId is not matching with ePrescriptionReferenceNumber",
					prescriptionException.getInvalidResponse().getStatusDescription());
		}
	}

	@SuppressWarnings("unused")
	@Test
	@DisplayName("Invalid ProviderId")
	void invalidProviderId() {
		prescriptionRequestOp.get().setProviderId("801");
		Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(Mockito.any()))
				.thenReturn(prescriptionRequestOp);
		Mockito.when(prescriptionRequestRepository.findByRequestId(Mockito.any()))
		.thenReturn(prescriptionRequestOp);
		try {
			PrescriptionRequest request = technicalValidationService.validateCancellationRequest(
					cancellationRequestModel, requestWrapper, providerId, SourceType.INTEGRATION.value());
		} catch (PrescriptionException prescriptionException) {
			assertNotNull(prescriptionException);
			assertEquals("ProviderId is not matching with ePrescriptionReferenceNumber",
					prescriptionException.getInvalidResponse().getStatusDescription());
		}
	}

	@Test
	@DisplayName("Invalid prescription to Cancel")
	void invalidPrescriptionToCancel() {
		prescriptionRequestOp.get().setCanCancel(false);
		Mockito.when(prescriptionRequestRepository.findByRequestId(Mockito.any())).thenReturn(prescriptionRequestOp);
		try {
			technicalValidationService.validateCancellationRequest(cancellationRequestModel, requestWrapper, providerId,
					SourceType.INTEGRATION.value());
		} catch (PrescriptionException prescriptionException) {
			assertNotNull(prescriptionException);
			assertEquals("Not allowed to do Cancellation with this request.",
					prescriptionException.getInvalidResponse().getStatusDescription());
		}
	}

	@Test
	@DisplayName("Valid prescription to Cancel")
	void validPrescriptionToCancel() throws PrescriptionException {
		prescriptionRequestOp.get().setCanCancel(true);
		Mockito.when(prescriptionRequestRepository.findByRequestId(Mockito.any())).thenReturn(prescriptionRequestOp);
		Mockito.when(transactionLogRepository.save(Mockito.any()))
				.thenReturn(generateTransactionLog(51.13, RequestType.CANCELLATION.name()));
		PrescriptionRequest request = technicalValidationService.validateCancellationRequest(cancellationRequestModel,
				requestWrapper, providerId, SourceType.INTEGRATION.value());
		assertNotNull(request);
		assertEquals(request.getStatusCode(), RequestStatusType.APPROVED.value());
	}

	private TransactionLog generateTransactionLog(Double transactionID, String transactionType) {
		return new TransactionLog(1L, requestId, null != transactionID ? transactionID : 51.12,
				null != transactionType && !transactionType.isEmpty() ? transactionType : RequestType.FOLLOWUP.name(),
				payerId, providerId, TransactionStatusType.RECEIVED.value(), ePrescriptionReferenceNumber, null, null,
				timestamp, timestamp, null, null, null);
	}

	private PrescriptionRequest generatePrescriptionRequest() {
		return new PrescriptionRequest(requestId, mappedPayerId, providerId, timestamp, timestamp,
				RequestStatusType.APPROVED.value(), null, ePrescriptionReferenceNumber, new BigDecimal(0),
				new BigDecimal(0), BenefitCaseType.INPATIENT.value(), currency, currency);
	}

	public void generateMockUserInfo() {
		Map<String, Object> details = new HashMap<String, Object>();
		details.put("accId", "accId");
		details.put("accName", "accName");
		details.put("accCode", "accCode");
		details.put("username", "username");
		details.put("email", "email");

		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(authentication.getPrincipal()).thenReturn(details);
	}

	private ContentCachingRequestWrapper getContentCachingRequestWrapper(
			PrescriptionCancellationRequestModel cancellationRequestModel) {
		ContentCachingRequestWrapper cachingRequestWrapper = null;
		try {
			MockHttpServletRequest hRequest = new MockHttpServletRequest();
			String req = mapper.writeValueAsString(cancellationRequestModel);
			hRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
			hRequest.setContent(req.getBytes(StandardCharsets.UTF_8.name()));
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
}
