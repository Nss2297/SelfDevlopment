package com.waseel.prescription.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
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
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.dispense.PrescriptionDispenseRequestModel;
import com.waseel.prescription.model.dispense.PrescriptionDispenseResponseModel;
import com.waseel.prescription.model.enums.BenefitCaseType;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.PrescriptionUrl;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.enums.TransactionStatusType;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.TransactionLogRepository;
import com.waseel.prescription.service.validation.DispenseTechnicalValidationService;
import com.waseel.prescription.util.UserInfoUtil;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class DispenseValidationTests {

	private final String INVALID = "Invalid";
	private final String requestId = "8890e048-a1ba-44c7-b0c7-86393cc5773b";
	private final String validEPrescriptionReferenceNumber = "2023-1";
	private final String invalidEPrescriptionReferenceNumber = "abc";
	private final String payerId = "102";
	private String providerId = "12";
	MockHttpServletRequest request = new MockHttpServletRequest();
	private static final String currency = Currency.SAR.value();

	@Autowired
	private DispenseTechnicalValidationService dispenseTechnicalValidationService;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private MessageSource messageSource;

	@MockBean
	private PrescriptionRequestRepository prescriptionRequestRepository;

	@MockBean
	private TransactionLogRepository transactionLogRepository;

	private TransactionLog transactionLog;
	private PrescriptionRequest prescriptionRequest;
	private PrescriptionDispenseRequestModel dispenseRequestModel;

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		Map<String, String> pathVariableMap = new HashMap<>();
		pathVariableMap.put("payerId", "102");
		request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, pathVariableMap);
	}

	@BeforeEach
	void setupData() {
		dispenseRequestModel = generatePrescriptionDispenseRequestModel();
		transactionLog = generateTransactionLogWithvalidStatus();
		prescriptionRequest = generatePrescriptionRequest();
		Mockito.when(transactionLogRepository.save(Mockito.any())).thenReturn(transactionLog);
		Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(prescriptionRequest);
		assertNotNull(transactionLog);
		assertNotNull(prescriptionRequest);
		assertNotNull(dispenseRequestModel);
	}

	@Test
	@DisplayName("Validation for EPrescriptionReferenceNumber")
	void ePrescriptionReferenceNumberNotFoundValidation() {
		try {
			dispenseRequestModel.setePrescriptionReferenceNumber(invalidEPrescriptionReferenceNumber);
			Mockito.when(prescriptionRequestRepository
					.findByePrescriptionReferenceNumber(invalidEPrescriptionReferenceNumber))
					.thenReturn(Optional.of(prescriptionRequest));
			dispenseTechnicalValidationService.validateDispenseRequest(invalidEPrescriptionReferenceNumber,
					getContentCachingRequestWrapper(), providerId, payerId,null);
		} catch (PrescriptionException e) {
			PrescriptionDispenseResponseModel invalidResponse = e.getDispensedResponseModel();
			assertThat(invalidResponse.getStatusDescription())
					.isEqualTo("EPrescriptionReferenceNumber is not found or exists.");
			assertThat(invalidResponse.getStatus()).isEqualTo(INVALID);
			assertThat(invalidResponse.getPayerId()).isEqualTo(payerId);
			assertThat(invalidResponse.getePrescriptionReferenceNumber())
					.isEqualTo(invalidEPrescriptionReferenceNumber);
			assertThat(invalidResponse.getProviderId()).isEqualTo(providerId);
		}
	}

	@Test
	@DisplayName("Bean/Technical Empty field validation for requestModel of Dispense Api.")
	void beanEmptyFieldValidationTest() {
		dispenseRequestModel.setePrescriptionReferenceNumber(null);
		PrescriptionDispenseResponseModel invalidReponse = dispenseTechnicalValidationService
				.populateInvalidPrescriptionResponse(getMethodArgumentNotValidExceptionEmptyField(),
						getContentCachingRequestWrapper(), request);
		assertNotNull(invalidReponse);
		assertNull(invalidReponse.getePrescriptionReferenceNumber());
		assertThat(invalidReponse.getStatus()).isEqualTo(INVALID);
		assertThat(invalidReponse.getStatusDescription()).isEqualTo("providerId should not be null or empty,"
				+ " ePrescriptionReferenceNumber should not be null or empty");
		assertThat(invalidReponse.getePrescriptionReferenceNumber()).isNull();
	}

	@Test
	@DisplayName("Bean/Technical Length field validation for requestModel of Dispense Api.")
	void beanLenghtFieldValidationTest() {
		dispenseRequestModel.setePrescriptionReferenceNumber(validEPrescriptionReferenceNumber);
		PrescriptionDispenseResponseModel invalidReponse = dispenseTechnicalValidationService
				.populateInvalidPrescriptionResponse(getMethodArgumentNotValidExceptionLengthField(),
						getContentCachingRequestWrapper(), request);
		assertNotNull(invalidReponse);
		assertThat(invalidReponse.getStatus()).isEqualTo(INVALID);
		assertThat(invalidReponse.getStatusDescription()).isEqualTo("providerId shouldn't be more than 20");
		assertThat(invalidReponse.getePrescriptionReferenceNumber()).isEqualTo(validEPrescriptionReferenceNumber);
	}

	private MethodArgumentNotValidException getMethodArgumentNotValidExceptionLengthField() {
		String msg = messageSource.getMessage("noMoreThanTwentyLengthValidation", null, Locale.ENGLISH);
		FieldError fieldError = new FieldError("providerId", "12345678901234567890123", null, false, null,
				new Object[] {}, "providerId " + msg);
		FieldError[] errors = { fieldError };
		return createExceptionWithFieldErrors(errors);
	}

	private MethodArgumentNotValidException getMethodArgumentNotValidExceptionEmptyField() {
		FieldError fieldError1 = new FieldError("providerId", "providerId", null, false, null, new Object[] {},
				"providerId should not be null or empty");
		FieldError fieldError2 = new FieldError("ePrescriptionReferenceNumber", "ePrescriptionReferenceNumber", null,
				false, null, new Object[] {}, "ePrescriptionReferenceNumber should not be null or empty");
		FieldError[] errors = { fieldError1, fieldError2 };
		return createExceptionWithFieldErrors(errors);
	}

	private MethodArgumentNotValidException createExceptionWithFieldErrors(FieldError... fieldErrors) {
		BindingResult bindingResult = new BeanPropertyBindingResult(dispenseRequestModel, "");
		for (FieldError fieldError : fieldErrors) {
			bindingResult.addError(fieldError);
		}
		return new MethodArgumentNotValidException(mock(MethodParameter.class), bindingResult);
	}

	private PrescriptionRequest generatePrescriptionRequest() {
		return new PrescriptionRequest(requestId, payerId, providerId,
				new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), RequestStatusType.APPROVED.value(), "",
				validEPrescriptionReferenceNumber, new BigDecimal(0), new BigDecimal(0),
				BenefitCaseType.INPATIENT.value(), currency, currency);
	}

	private PrescriptionDispenseRequestModel generatePrescriptionDispenseRequestModel() {
		return new PrescriptionDispenseRequestModel(validEPrescriptionReferenceNumber);
	}

	private TransactionLog generateTransactionLogWithvalidStatus() {
		return new TransactionLog(1L, requestId, 51.11, RequestType.NEW.name(), payerId, providerId,
				TransactionStatusType.SENT.name(), validEPrescriptionReferenceNumber, ServiceStatus.APPROVED.name(),
				null, new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), "PBM", String.valueOf(HttpStatus.OK.value()),
				RequestStatusType.APPROVED.value());
	}

	private ContentCachingRequestWrapper getContentCachingRequestWrapper() {
		ContentCachingRequestWrapper cachingRequestWrapper = null;
		try {
			MockHttpServletRequest hRequest = new MockHttpServletRequest();
			String req = this.mapper.writeValueAsString(dispenseRequestModel);
			hRequest.setRequestURI(PrescriptionUrl.DISPENSE.getValue());
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

	public void generateMockUserInfo() {
		Map<String, Object> details = new HashMap<String, Object>();
		details.put("accId", "801");
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
}
