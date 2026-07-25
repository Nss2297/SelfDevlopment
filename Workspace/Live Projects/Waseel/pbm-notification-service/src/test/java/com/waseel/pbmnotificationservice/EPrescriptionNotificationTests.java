package com.waseel.pbmnotificationservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.pbmnotificationservice.exceptions.NotificationException;
import com.waseel.pbmnotificationservice.model.eprescription.notification.NotificationRequestModel;
import com.waseel.pbmnotificationservice.model.eprescription.notification.NotificationResponseModel;
import com.waseel.pbmnotificationservice.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.pbmnotificationservice.repository.prescriptionservice.PayerNotificationsRepository;
import com.waseel.pbmnotificationservice.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.pbmnotificationservice.service.InvalidResponseService;
import com.waseel.pbmnotificationservice.service.eprescription.notification.NotificationService;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;

@SpringBootTest
@ActiveProfiles("test")
class EPrescriptionNotificationTests {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private InvalidResponseService invalidResponseService;

	@Autowired
	private Validator validator;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private MessageSource messageSource;

	@MockBean
	private PayerNotificationsRepository payerNotificationsRepository;

	@MockBean
	private PrescriptionRequestRepository prescriptionRequestRepository;

	private PrescriptionRequest prescriptionRequest;
	private NotificationRequestModel notificationRequestModel;

	private String ePrescriptionReferenceNumber = "2023-1";
	private String approvalReferenceNumber = "101222222";
	private String ePrescriptionStatus = "APPROVED";
	private String payerId = "102";
	private String requestId = "8890e048-a1ba-44c7-b0c7-86393cc5773b";
	private String strFailed = "FAILED";
	private String strReceived = "RECEIVED";
	private String strEPrescriptionNotFound = "EPrescriptionReferenceNumber is not found or exists.";
	private Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());

	@BeforeEach
	void setupData() {
		prescriptionRequest = generatePrescriptionRequest();
		notificationRequestModel = generateNotificationRequestModel(ePrescriptionReferenceNumber,
				approvalReferenceNumber, ePrescriptionStatus);
		assertNotNull(prescriptionRequest);
	}

	@Test
	@DisplayName("EPrescription Notification RECEIVED Response")
	void ePrescriptionNotificationReceivedResponse() {
		try {
			Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber))
					.thenReturn(Optional.of(prescriptionRequest));
			NotificationResponseModel responseModel = notificationService
					.receiveNotificationFromTawuniya(notificationRequestModel, payerId);
			assertNotNull(responseModel);
			assertThat(responseModel.getePrescriptionReferenceNumber())
					.isEqualTo(notificationRequestModel.getePrescriptionReferenceNumber());
			assertThat(responseModel.getApprovalReferenceNumber())
					.isEqualTo(notificationRequestModel.getApprovalReferenceNumber());
			assertThat(responseModel.getStatus()).isEqualTo(strReceived);
		} catch (NotificationException e) {
			assertThat(e.getMessage()).isEqualTo(strEPrescriptionNotFound);
		}
	}

	@Test
	@DisplayName("Validation for EPrescriptionReferenceNumber")
	void ePrescriptionReferenceNumberValidation() {
		try {
			Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber("46566"))
					.thenReturn(Optional.of(prescriptionRequest));
			NotificationResponseModel responseModel = notificationService
					.receiveNotificationFromTawuniya(notificationRequestModel, payerId);
			assertNotNull(responseModel);
			assertThat(responseModel.getePrescriptionReferenceNumber())
					.isEqualTo(notificationRequestModel.getePrescriptionReferenceNumber());
			assertThat(responseModel.getApprovalReferenceNumber())
					.isEqualTo(notificationRequestModel.getApprovalReferenceNumber());
			assertThat(responseModel.getStatus()).isEqualTo(strReceived);
			assertNotNull(responseModel.getStatusDescription());
		} catch (NotificationException e) {
			assertThat(e.getMessage()).isEqualTo(strEPrescriptionNotFound);
		}
	}

	@Test
	@DisplayName("EPrescription Notification FAILED Response from NotificationException")
	void ePrescriptionNotificationFailedResponse() {
		NotificationException exception = new NotificationException(strEPrescriptionNotFound);
		NotificationResponseModel responseModel = invalidResponseService.populateInvalidFailedResponse(exception,
				getContentCachingRequestWrapper(), new MockHttpServletRequest());
		assertNotNull(responseModel);
		assertThat(responseModel.getePrescriptionReferenceNumber())
				.isEqualTo(notificationRequestModel.getePrescriptionReferenceNumber());
		assertThat(responseModel.getApprovalReferenceNumber())
				.isEqualTo(notificationRequestModel.getApprovalReferenceNumber());
		assertThat(responseModel.getStatus()).isEqualTo(strFailed);
		assertThat(responseModel.getStatusDescription()).isEqualTo(strEPrescriptionNotFound);
	}

	@Test
	@DisplayName("EPrescription Notification FAILED Response From MethodArgumentNotValidException")
	void beanLenghtFieldValidationTest() {
		NotificationResponseModel responseModel = invalidResponseService.populateInvalidFailedResponse(
				getMethodArgumentNotValidExceptionLengthField(), getContentCachingRequestWrapper(),
				new MockHttpServletRequest());
		Assertions.assertNotNull(responseModel);
		assertThat(responseModel.getePrescriptionReferenceNumber())
				.isEqualTo(notificationRequestModel.getePrescriptionReferenceNumber());
		assertThat(responseModel.getApprovalReferenceNumber())
				.isEqualTo(notificationRequestModel.getApprovalReferenceNumber());
		assertThat(responseModel.getStatus()).isEqualTo(strFailed);
		assertThat(responseModel.getStatusDescription()).isEqualTo("ePrescriptionStatus shouldn't be more than 60, "
				+ "approvalReferenceNumber should not be null or empty");
	}

	@Test
	@DisplayName("Request model Not Empty validations")
	void requestModelNotEmptyValidation() {
		notificationRequestModel = generateNotificationRequestModel(null, null, null);
		List<ConstraintViolation<NotificationRequestModel>> sortedViolations = getSortedViolations(
				notificationRequestModel);
		Assertions.assertFalse(sortedViolations.isEmpty());
		Assertions.assertEquals(4, sortedViolations.size());
		List<String> messages = new ArrayList<>();
		for (ConstraintViolation<NotificationRequestModel> violation : sortedViolations) {
			String propertyPath = violation.getPropertyPath().toString() + " ";
			String expectedMessage = (violation.getMessage().contains("APPROVED, REJECTED, PARTIAL_APPROVED, DISPENSED or PARTIAL_DISPENSED")
					? messageSource.getMessage("ePrescriptionStatusTypeValidation", null, Locale.getDefault())
					: propertyPath + messageSource.getMessage("notEmptyValidation", null, Locale.getDefault()));
			messages.add(expectedMessage);
		}
		Assertions.assertEquals(
				"approvalReferenceNumber should not be null or empty, "
						+ "ePrescriptionReferenceNumber should not be null or empty, "
						+ "ePrescriptionStatus should be one of these values : APPROVED, REJECTED, PARTIAL_APPROVED, DISPENSED or PARTIAL_DISPENSED, "
						+ "ePrescriptionStatus should not be null or empty",
				StringUtils.strip(messages.toString(), "[]"));
	}

	@Test
	@DisplayName("Request model Length validations")
	void requestModelLengthValidation() {
		String msg = getStringOf100Length();
		notificationRequestModel = generateNotificationRequestModel(msg, msg, msg);
		List<ConstraintViolation<NotificationRequestModel>> sortedViolations = getSortedViolations(
				notificationRequestModel);
		Assertions.assertFalse(sortedViolations.isEmpty());
		Assertions.assertEquals(4, sortedViolations.size());
		List<String> messages = new ArrayList<>();
		for (ConstraintViolation<NotificationRequestModel> violation : sortedViolations) {
			String propertyPath = violation.getPropertyPath().toString();
			String expectedMessage;
			if (propertyPath.equals("ePrescriptionStatus")) {
				expectedMessage = (violation.getMessage().contains("APPROVED, REJECTED, PARTIAL_APPROVED, DISPENSED or PARTIAL_DISPENSED")
						? messageSource.getMessage("ePrescriptionStatusTypeValidation", null, Locale.getDefault())
						: propertyPath + " "
								+ messageSource.getMessage("noMoreThan60LengthValidation", null, Locale.getDefault()));
			} else {
				expectedMessage = propertyPath + " "
						+ messageSource.getMessage("noMoreThan100LengthValidation", null, Locale.getDefault());
			}
			messages.add(expectedMessage);
		}
		Assertions.assertEquals(
				"approvalReferenceNumber shouldn't be more than 100, "
						+ "ePrescriptionReferenceNumber shouldn't be more than 100,"
						+ " ePrescriptionStatus should be one of these values : APPROVED, REJECTED, PARTIAL_APPROVED, DISPENSED or PARTIAL_DISPENSED,"
						+ " ePrescriptionStatus shouldn't be more than 60",
				StringUtils.strip(messages.toString(), "[]"));
	}

	private List<ConstraintViolation<NotificationRequestModel>> getSortedViolations(
			NotificationRequestModel requestModel) {
		Set<ConstraintViolation<NotificationRequestModel>> violations = validator.validate(requestModel);
		List<ConstraintViolation<NotificationRequestModel>> sortedViolations = new ArrayList<>(violations);
		Collections.sort(sortedViolations, Comparator.comparing(violation -> violation.getPropertyPath().toString()));
		Collections.sort(sortedViolations, Comparator.comparing(violation -> violation.getMessage().toString()));
		return sortedViolations;
	}

	private NotificationRequestModel generateNotificationRequestModel(String ePrescriptionReferenceNumber,
			String approvalReferenceNumber, String ePrescriptionStatus) {
		return new NotificationRequestModel(ePrescriptionReferenceNumber, approvalReferenceNumber, ePrescriptionStatus);
	}

	private PrescriptionRequest generatePrescriptionRequest() {
		PrescriptionRequest prescriptionRequest = new PrescriptionRequest(requestId, payerId, "12", timestamp,
				timestamp, "APPROVED", null, true, true, false, ePrescriptionReferenceNumber, "INPATIENT",
				new BigDecimal(0), new BigDecimal(0));
		return prescriptionRequest;
	}

	private String getStringOf100Length() {
		return "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123";
	}

	private MethodArgumentNotValidException getMethodArgumentNotValidExceptionLengthField() {
		FieldError fieldError1 = new FieldError("ePrescriptionStatus", "ePrescriptionStatus", getStringOf100Length(),
				false, null, new Object[] {}, "ePrescriptionStatus "
						+ messageSource.getMessage("noMoreThan60LengthValidation", null, Locale.ENGLISH));
		FieldError fieldError2 = new FieldError("approvalReferenceNumber", "approvalReferenceNumber", null, false, null,
				new Object[] {},
				"approvalReferenceNumber " + messageSource.getMessage("notEmptyValidation", null, Locale.ENGLISH));
		FieldError[] errors = { fieldError1, fieldError2 };
		return createExceptionWithFieldErrors(errors);
	}

	private MethodArgumentNotValidException createExceptionWithFieldErrors(FieldError... fieldErrors) {
		BindingResult bindingResult = new BeanPropertyBindingResult(notificationRequestModel, "");
		for (FieldError fieldError : fieldErrors) {
			bindingResult.addError(fieldError);
		}
		return new MethodArgumentNotValidException(mock(MethodParameter.class), bindingResult);
	}

	private ContentCachingRequestWrapper getContentCachingRequestWrapper() {
		ContentCachingRequestWrapper cachingRequestWrapper = null;
		try {
			MockHttpServletRequest hRequest = new MockHttpServletRequest();
			String req = this.mapper.writeValueAsString(notificationRequestModel);
			hRequest.setRequestURI("/eprescription/notifications");
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
