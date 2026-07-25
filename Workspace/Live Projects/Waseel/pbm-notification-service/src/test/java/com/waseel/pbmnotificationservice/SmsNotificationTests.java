package com.waseel.pbmnotificationservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.waseel.pbmnotificationservice.clients.SsoClient;
import com.waseel.pbmnotificationservice.clients.WaseelSmsServiceClient;
import com.waseel.pbmnotificationservice.model.common.CommonRequestModel;
import com.waseel.pbmnotificationservice.model.common.CommonResponseModel;
import com.waseel.pbmnotificationservice.model.enums.ResponseStatusType;
import com.waseel.pbmnotificationservice.model.enums.TransactionStatusType;
import com.waseel.pbmnotificationservice.model.sms.SmsNotificationRequestModel;
import com.waseel.pbmnotificationservice.model.sms.SmsNotificationResponseModel;
import com.waseel.pbmnotificationservice.model.sso.JwtResponse;
import com.waseel.pbmnotificationservice.model.unifonic.UnifonicDataModel;
import com.waseel.pbmnotificationservice.model.unifonic.UnifonicReponseModel;
import com.waseel.pbmnotificationservice.persist.businessrules.EmailSmsAuditLog;
import com.waseel.pbmnotificationservice.repository.businessrules.EmailSmsAuditlogRepository;
import com.waseel.pbmnotificationservice.service.InvalidResponseService;
import com.waseel.pbmnotificationservice.service.PbmSmsService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class SmsNotificationTests {

	@Autowired
	private PbmSmsService pbmSmsService;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private SsoClient ssoClient;

	@MockBean
	private WaseelSmsServiceClient waseelSmsServiceClient;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private InvalidResponseService invalidResponseService;

	@MockBean
	private EmailSmsAuditlogRepository emailSmsAuditlogRepository;

	private SmsNotificationResponseModel smsNotificationResponseModel = null;
	private SmsNotificationRequestModel smsNotificationRequestModel = null;
	private CommonRequestModel commonRequestModel = null;
	private EmailSmsAuditLog emailSmsAuditLog = null;
	private final MockHttpServletRequest request = new MockHttpServletRequest();
	protected static final int CONTENT_CACHE_LIMIT = 13;
	private ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(this.request,
			CONTENT_CACHE_LIMIT);
	private Date date = new Date();
	private final String status = ResponseStatusType.SUCCESS.value();
	private final String mobileNumber = "966505987591";
	private final String timeCreated = "2022-09-06 14:06:25.979";
	private final Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());

	@BeforeAll
	void prepareCommonData() {
		commonRequestModel = populateCommonRequestModel();
		smsNotificationRequestModel = populateSmsNotificationRequestModel();
		requestWrapper = getContentCachingRequestWrapper();
		smsNotificationResponseModel = populateSmsNotificationResponseModel();
		emailSmsAuditLog = populateEmailSmsAuditLog();
	}

	@BeforeEach
	void prepareCommonDataBeforeEachUnitTest() {
		Mockito.when(ssoClient.fetchAccessToken(Mockito.any(), Mockito.any())).thenReturn(populateJwtResponse());
		Mockito.when(waseelSmsServiceClient.sendMemberDetailsToUnifonic(Mockito.any()))
				.thenReturn(ResponseEntity.ok(populateUnifonicReponseModel()));
		Mockito.when(emailSmsAuditlogRepository.save(Mockito.any())).thenReturn(emailSmsAuditLog);
		Mockito.when(emailSmsAuditlogRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(emailSmsAuditLog));
	}

	@Order(1)
	@Test
	@DisplayName("Sent sms successfully")
	void sentSmsSuccessfully() {
		SmsNotificationResponseModel notificationRequestModel = pbmSmsService
				.sendSmsNotificationToMember(smsNotificationRequestModel, requestWrapper);
		assertNotNull(notificationRequestModel);
		assertNotNull(notificationRequestModel.getMessageId());
		assertNotNull(notificationRequestModel.getStatus());
		assertEquals(notificationRequestModel.getStatus(), status);
		assertEquals("", notificationRequestModel.getStatusDescription());
		assertNotNull(notificationRequestModel.getTimeCreated());
	}

	@Order(2)
	@Test
	@DisplayName("Invalid response")
	void invalidResponse() {
		MethodArgumentNotValidException exception = getMethodArgumentNotValidException();
		commonRequestModel = new CommonRequestModel();
		smsNotificationRequestModel = new SmsNotificationRequestModel();
		CommonResponseModel commonResponseModel = invalidResponseService
				.populateInvalidFailedResponseForEmailAndSMS(exception, requestWrapper);
		assertNotNull(commonResponseModel);
		assertEquals(commonResponseModel.getStatus(), ResponseStatusType.INVALID.value());
		assertEquals(
				"url should not be null or empty, requestType should not be null or empty, requestId should not be null or empty, ePrescriptionReferenceNumber should not be null or empty, mobileNumber should not be null or empty, message should not be null or empty",
				commonResponseModel.getStatusDescription());
		smsNotificationRequestModel = populateSmsNotificationRequestModel();
		commonRequestModel = populateCommonRequestModel();
	}

	@Order(3)
	@Test
	@DisplayName("Failed response")
	void failedResponse() {
		Exception exception = new Exception();
		CommonResponseModel commonResponseModel = invalidResponseService
				.populateInvalidFailedResponseForEmailAndSMS(exception, requestWrapper);
		assertNotNull(commonResponseModel);
		assertEquals(commonResponseModel.getStatus(), ResponseStatusType.FAILED.value());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), commonResponseModel.getStatusDescription());
	}

	private SmsNotificationResponseModel populateSmsNotificationResponseModel() {
		return new SmsNotificationResponseModel(status, "", mobileNumber, timeCreated);
	}

	private CommonRequestModel populateCommonRequestModel() {
		return new CommonRequestModel(
				"https://qa-pbm-admin.waseel.com/patient-prescription?access_token=eyJ0eXBlIjoibGltaXRlZF9hY2Nlc3NfdG9rZW4iLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjE2NTQ5ODcyIiwiZXhwIjoxNjg5OTI0MDEwLCJpYXQiOjE2ODkzMTkyMTAsInJvbCI6W3siYXV0aG9yaXR5IjoicHJlc2NyaXB0aW9uLXNlcnZpY2V8MjAyMy00MzIwIn1dfQ.QanFlDiFeL9A0OeaZy_2kYlIk6LNoRbrbIoeLJ4iIZj0on0P64k820iKR-K9LNCua3dpoANya1DTrRIxc_MTrg",
				"NEW", "8890e048-a1ba-44c7-b0c7-86393cc5773b", "10085-9578");
	}

	private SmsNotificationRequestModel populateSmsNotificationRequestModel() {
		return new SmsNotificationRequestModel(mobileNumber,
				"Hello Hashim , We have received your prescription(Ref. No. 547458) that created/updated b Dallah Hospital.Please click the link to view details. https://qa-pbm-admin.waseel.com patient-prescription?access_token=iSFM1MTIifQ.eyJhY2Nf.80z4LVn");
	}

	private ContentCachingRequestWrapper getContentCachingRequestWrapper() {
		ContentCachingRequestWrapper cachingRequestWrapper = null;
		try {
			MockHttpServletRequest hRequest = new MockHttpServletRequest();
			String req = this.mapper.writeValueAsString(smsNotificationRequestModel);
			hRequest.setRequestURI("/notifications/sms");
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

	private JwtResponse populateJwtResponse() {
		return new JwtResponse(
				"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJRM2o5NFJmRUhiUExLQURsVjFIcGVkWDBZSFk2eU1TbkhIdGZwajUzT3o4In0.eyJleHAiOjE2ODkzNDc2NjEsImlhdCI6MTY4OTM0NzM2MSwianRpIjoiZTdjMzc4YzAtNTJhOC00ZGNkLTk2YWItM2U4MGM3ZWMwNzM0IiwiaXNzIjoiaHR0cHM6Ly9xYS1pYW0ud2FzZWVsLmNvbS9yZWFsbXMvd2FzZWVsLXFhIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImYwZDNlNmNkLWU2MTctNGM3Zi1iNjExLTY2OGMyNDk1ZDlmMyIsInR5cCI6IkJlYXJlciIsImF6cCI6InBibS1ub3RpZmljYXRpb24tc2VydmljZSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy13YXNlZWwtcWEiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoic21zLXNlbmRlciBwcm9maWxlIG9wZW5pZCBlbWFpbC1zZW5kZXIgZW1haWwiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImNsaWVudEhvc3QiOiIxMC4xMDAuMTAwLjUwIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50LXBibS1ub3RpZmljYXRpb24tc2VydmljZSIsImNsaWVudEFkZHJlc3MiOiIxMC4xMDAuMTAwLjUwIiwiY2xpZW50X2lkIjoicGJtLW5vdGlmaWNhdGlvbi1zZXJ2aWNlIn0.VBiJGyD3HIC_Vy-oC-IuCOJ9csDhkFfvKS3qxTiVo-M62ji4m_Ih1xpPjC-FEWls9cuwrKUGhEpokOcYNtWaluuPaJlLFneoFowVOGSV9W6HBUVW5sBBwsVTHmyvsGMePzdqze9UHZD_loJM3XdEG9oFq4B9ZObHuWnKq9Lk6de42nRsjVXZqWe-4SErkTFkIe3Y7vG6hT-PT6tR8BpLQDWvllSWvS0LNrpaQTkOBT5UenNSvlPb5KB0pjX0sac6RkQVQPiHKmQ2wK7SlwDVEdQwariAspgfh7ybn4A9cvS5XBb5zCQBrBhiVL-_EFjXAA-gahCnfTW7J8fst_NF9w",
				date, date, "0", "sms-sender profile openid email-sender email");
	}

	private UnifonicReponseModel populateUnifonicReponseModel() {
		UnifonicDataModel unifoniDataModel = populateUnifonicDataModel();
		return new UnifonicReponseModel(true, "", status, "ER-00", unifoniDataModel);
	}

	private UnifonicDataModel populateUnifonicDataModel() {
		return new UnifonicDataModel("42000348806924", "CorrelationID", "Sent", "0", "0", "0", mobileNumber,
				timeCreated, "");
	}

	private MethodArgumentNotValidException getMethodArgumentNotValidException() {
		FieldError fieldError1 = new FieldError("url", "url", "", false, null, new Object[] {},
				"url " + messageSource.getMessage("notEmptyValidation", null, Locale.ENGLISH));
		FieldError fieldError2 = new FieldError("requestType", "requestType", "", false, null, new Object[] {},
				"requestType " + messageSource.getMessage("notEmptyValidation", null, Locale.ENGLISH));
		FieldError fieldError3 = new FieldError("requestId", "requestId", "", false, null, new Object[] {},
				"requestId " + messageSource.getMessage("notEmptyValidation", null, Locale.ENGLISH));
		FieldError fieldError4 = new FieldError("ePrescriptionReferenceNumber", "ePrescriptionReferenceNumber", "",
				false, null, new Object[] {},
				"ePrescriptionReferenceNumber " + messageSource.getMessage("notEmptyValidation", null, Locale.ENGLISH));
		FieldError fieldError5 = new FieldError("mobileNumber", "mobileNumber", "", false, null, new Object[] {},
				"mobileNumber " + messageSource.getMessage("notEmptyValidation", null, Locale.ENGLISH));
		FieldError fieldError6 = new FieldError("message", "message", "", false, null, new Object[] {},
				"message " + messageSource.getMessage("notEmptyValidation", null, Locale.ENGLISH));
		FieldError[] errors = { fieldError1, fieldError2, fieldError3, fieldError4, fieldError5, fieldError6 };
		return createExceptionWithFieldErrors(errors);
	}

	private MethodArgumentNotValidException createExceptionWithFieldErrors(FieldError... fieldErrors) {
		BindingResult bindingResult = new BeanPropertyBindingResult(smsNotificationRequestModel, "");
		for (FieldError fieldError : fieldErrors) {
			bindingResult.addError(fieldError);
		}
		return new MethodArgumentNotValidException(mock(MethodParameter.class), bindingResult);
	}

	private EmailSmsAuditLog populateEmailSmsAuditLog() {
		return new EmailSmsAuditLog(1L, mobileNumber, "", "NEW", "SUCCESS", "", TransactionStatusType.RECEIVED.value(),
				timestamp, timestamp, "f0a5e239-a891-48ee-b14e-816a29c8651d", "2023-3300", "timeCreated", timeCreated,
				"https://qa-pbm-admin.waseel.com/patient-prescription?access_token=eyJ0eXBlIjoibGltaXRlZF9hY2Nlc3NfdG9rZW4iLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjE2NTQ5ODcyIiwiZXhwIjoxNjg5OTI0MDEwLCJpYXQiOjE2ODkzMTkyMTAsInJvbCI6W3siYXV0aG9yaXR5IjoicHJlc2NyaXB0aW9uLXNlcnZpY2V8MjAyMy00MzIwIn1dfQ.QanFlDiFeL9A0OeaZy_2kYlIk6LNoRbrbIoeLJ4iIZj0on0P64k820iKR-K9LNCua3dpoANya1DTrRIxc_MTrg");
	}
}
