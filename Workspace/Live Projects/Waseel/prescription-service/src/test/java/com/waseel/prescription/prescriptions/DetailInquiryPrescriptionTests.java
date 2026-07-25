package com.waseel.prescription.prescriptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import com.waseel.prescription.model.enums.BenefitCaseType;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.DomainName;
import com.waseel.prescription.model.enums.PrescriptionUrl;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.enums.SourceType;
import com.waseel.prescription.model.enums.TransactionStatusType;
import com.waseel.prescription.model.enums.UnitType;
import com.waseel.prescription.model.inquiry.InquiryInvalidResponseModel;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryRequestModel;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryResponseModel;
import com.waseel.prescription.model.prescription.MedicalValidations;
import com.waseel.prescription.model.prescription.ServiceResponse;
import com.waseel.prescription.persist.prescriptionservice.InvalidPrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.prescriptionservice.InvalidPrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.TransactionLogRepository;
import com.waseel.prescription.service.inquiry.PrescriptionDetailInquiryService;
import com.waseel.prescription.service.management.TransactionLogService;
import com.waseel.prescription.service.validation.InquiryTechnicalValidationService;

@SpringBootTest
@ActiveProfiles("test")
class DetailInquiryPrescriptionTests {

	private static final String INVALID = "Invalid";

	@Autowired
	private PrescriptionDetailInquiryService detailInquiryService;

	@Autowired
	private InquiryTechnicalValidationService inquiryTechnicalValidationService;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private TransactionLogService transactionLogService;

	@MockBean
	private TransactionLogRepository transactionLogRepository;

	@MockBean
	private PrescriptionRequestRepository prescriptionRequestRepository;

	@MockBean
	private ServiceInfoRepository serviceInfoRepository;

	@MockBean
	private ServiceResponseInfoRepository serviceResponseInfoRepository;

	@MockBean
	private ServiceRejectionRepository serviceRejectionRepository;

	@MockBean
	private InvalidPrescriptionRequestRepository invalidPrescriptionRequestRepository;

	private PrescriptionDetailInquiryRequestModel detailInquiryRequestModel;
	private String ePrescriptionReferenceNumber = "2023-1";
	private String invalidEPrescriptionReferenceNumber = "abc";
	private String payerId = "102";
	private String providerId = "12";
	private String requestId = "8890e048-a1ba-44c7-b0c7-86393cc5773b";
	private String drugCode1 = "2402221767";
	private String drugCode2 = "31-277-98";
	private String statusDesc;
	private ContentCachingRequestWrapper contentCachingRequestWrapper;
	private TransactionLog transactionLog;
	private PrescriptionRequest prescriptionRequest;
	private InvalidPrescriptionRequest invalidPrescriptionRequest;
	private static final String currency = Currency.SAR.value();

	@BeforeEach
	void setupData() {
		detailInquiryRequestModel = generateDetailInquiryRequestModel();
		contentCachingRequestWrapper = getContentCachingRequestWrapper();
		transactionLog = generateTransactionLogWithvalidStatus();
		prescriptionRequest = generatePrescriptionRequest();
		invalidPrescriptionRequest = generateInvalidPrescriptionRequest();
		Mockito.when(transactionLogRepository.save(Mockito.any())).thenReturn(transactionLog);
		Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(prescriptionRequest)
				.thenReturn(invalidPrescriptionRequest);
		Mockito.when(transactionLogRepository
				.findByePrescriptionReferenceNumberWithValidStatus(ePrescriptionReferenceNumber))
				.thenReturn(Optional.of(transactionLog));
		Mockito.when(transactionLogService.addTransaction(RequestType.DETAIL_INQUIRY, payerId, providerId, requestId,
				ePrescriptionReferenceNumber, SourceType.INTEGRATION.value())).thenReturn(transactionLog);
		Mockito.when(
				prescriptionRequestRepository.findByePrescriptionReferenceNumber(invalidEPrescriptionReferenceNumber))
				.thenReturn(Optional.of(prescriptionRequest));
		Mockito.when(invalidPrescriptionRequestRepository
				.findByePrescriptionReferenceNumber(invalidEPrescriptionReferenceNumber))
				.thenReturn(Optional.of(invalidPrescriptionRequest));

		assertNotNull(transactionLog);
		assertNotNull(prescriptionRequest);
	}

	@Test
	@DisplayName("Valid REJECTED response of Detail Api")
	void validRejectedPrescriptionDetailInquiryResponse() {
		try {
			Mockito.when(serviceInfoRepository.getDetailsOfInquiry(requestId))
					.thenReturn(generateServiceResponse(RequestStatusType.REJECTED.value()));
			Mockito.when(serviceRejectionRepository.findByRequestIdAndDrugCode(requestId, drugCode1))
					.thenReturn(generateMedicalValidations());
			PrescriptionDetailInquiryResponseModel response = detailInquiryService
					.managePrescriptionDetailInquiryRequest(detailInquiryRequestModel, contentCachingRequestWrapper,
							DomainName.WASEEL.name());
			assertRejectedRequestCases(response);
		} catch (PrescriptionException e) {
			assertThat(e.getInvalidInquiryResponse().getErrorStatus()).isEqualTo(INVALID);
		}
	}

	@Test
	@DisplayName("Valid PARTIALLY APPROVED response of Detail Api")
	void validPrescriptionDetailInquiryResponseTest() {
		try {
			prescriptionRequest.setStatusCode(RequestStatusType.PARTIAL_APPROVED.value());
			Mockito.when(serviceInfoRepository.getDetailsOfInquiry(requestId))
					.thenReturn(generateServiceResponse(RequestStatusType.PARTIAL_APPROVED.value()));
			Mockito.when(serviceRejectionRepository.findByRequestIdAndDrugCode(requestId, drugCode1))
					.thenReturn(generateMedicalValidations());
			PrescriptionDetailInquiryResponseModel response = detailInquiryService
					.managePrescriptionDetailInquiryRequest(detailInquiryRequestModel, contentCachingRequestWrapper,
							DomainName.WASEEL.name());
			assertPartiallyApprovedCases(response);
		} catch (PrescriptionException e) {
			assertThat(e.getInvalidInquiryResponse().getErrorStatus()).isEqualTo(INVALID);
		}
	}

	@Test
	@DisplayName("Valid APPROVED response of Detail Api")
	void validApprovedPrescriptionDetailInquiryResponseTest() {
		try {
			prescriptionRequest.setStatusCode(RequestStatusType.APPROVED.value());
			prescriptionRequest.setStatusDescription(null);
			Mockito.when(prescriptionRequestRepository.findByRequestId(requestId))
					.thenReturn(Optional.of(prescriptionRequest));
			Mockito.when(serviceInfoRepository.getDetailsOfInquiry(requestId))
					.thenReturn(generateApprovedServiceResponse());
			Mockito.when(serviceRejectionRepository.findByRequestIdAndDrugCode(requestId, drugCode1))
					.thenReturn(generateMedicalValidations());
			PrescriptionDetailInquiryResponseModel response = detailInquiryService
					.managePrescriptionDetailInquiryRequest(detailInquiryRequestModel, contentCachingRequestWrapper,
							DomainName.WASEEL.name());
			assertApprovedCases(response);
		} catch (PrescriptionException e) {
			assertThat(e.getInvalidInquiryResponse().getErrorStatus()).isEqualTo(INVALID);
		}
	}

	@Test
	@DisplayName("Invalid response[400] PrescriptionException from Detail Api.")
	void invalidPrescriptionDetailInquiryResponseTest() {
		String invalidEPrescriptionRefNum = "test";
		try {
			detailInquiryRequestModel.setePrescriptionReferenceNumber(invalidEPrescriptionRefNum);
			detailInquiryService.managePrescriptionDetailInquiryRequest(detailInquiryRequestModel,
					contentCachingRequestWrapper, DomainName.WASEEL.name());
		} catch (PrescriptionException e) {
			InquiryInvalidResponseModel invalidResponse = e.getInvalidInquiryResponse();
			assertThat(e.getInvalidInquiryResponse().getErrorDescription())
					.isEqualTo("EPrescriptionReferenceNumber is not found or exists.");
			assertThat(invalidResponse.getErrorStatus()).isEqualTo(INVALID);
			assertThat(invalidResponse.getRequestId()).isNull();
			assertThat(invalidResponse.getePrescriptionReferenceNumber()).isEqualTo(invalidEPrescriptionRefNum);
		}
	}

	@Test
	@DisplayName("Failed response[500] from Detail Api.")
	void failedValidationTest() {
		try {
			Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(prescriptionRequest);
			Mockito.when(prescriptionRequestRepository
					.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber))
					.thenReturn(Optional.of(prescriptionRequest));
			InquiryInvalidResponseModel invalidReponse = inquiryTechnicalValidationService
					.populateFailedInquiryPrescriptionResponse(contentCachingRequestWrapper);
			assertNotNull(invalidReponse);
			assertThat(invalidReponse.getErrorStatus()).isEqualTo("Failed");
			assertThat(invalidReponse.getErrorDescription()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.name());
			assertThat(invalidReponse.getePrescriptionReferenceNumber()).isEqualTo(ePrescriptionReferenceNumber);
			assertThat(invalidReponse.getRequestId()).isEqualTo(requestId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void assertRejectedRequestCases(PrescriptionDetailInquiryResponseModel response) {
		assertCommonCases(response);
		assertThat(response.getStatusDescription()).isNotNull();
		assertThat(response.getStatusDescription()).isEqualTo(statusDesc);
		assertThat(response.getStatus()).isEqualTo(RequestStatusType.REJECTED.value());
		ServiceResponse serviceResponse = response.getResults().get(0);
		assertThat(response.getResults()).hasSize(1);
		assertThat(serviceResponse.getStatus()).isEqualTo(ServiceStatus.REJECTED.name());
		List<MedicalValidations> medicalValidations = serviceResponse.getErrors();
		assertNotNull(medicalValidations);
		assertThat(medicalValidations).isNotEmpty();
		assertThat(medicalValidations).hasSize(3);
		assertThat(medicalValidations).hasSameSizeAs(generateMedicalValidations());
		MedicalValidations mv = medicalValidations.get(0);
		assertThat(mv.getDrugCode()).isEqualTo(drugCode1);
		assertThat(mv.getRejectionReason())
				.isEqualTo("Medication 2402221767 is not indicated with diagnosis code F31.6");
	}

	void assertPartiallyApprovedCases(PrescriptionDetailInquiryResponseModel response) {
		assertCommonCases(response);
		assertThat(response.getStatusDescription()).isNotNull();
		assertThat(response.getStatusDescription()).isEqualTo(statusDesc);
		assertThat(response.getStatus()).isEqualTo(RequestStatusType.PARTIAL_APPROVED.value());
		assertThat(response.getResults()).hasSize(2);
		ServiceResponse serviceResponse1 = response.getResults().get(0);
		ServiceResponse serviceResponse2 = response.getResults().get(1);
		assertThat(serviceResponse1.getStatus()).isEqualTo(ServiceStatus.REJECTED.name());
		assertThat(serviceResponse2.getStatus()).isEqualTo(ServiceStatus.APPROVED.name());
		assertThat(serviceResponse1.getErrors()).isNotNull();
		assertThat(serviceResponse2.getErrors()).isNull();
	}

	void assertApprovedCases(PrescriptionDetailInquiryResponseModel response) {
		assertCommonCases(response);
		assertThat(response.getStatusDescription()).isNull();
		assertThat(response.getStatus()).isEqualTo(RequestStatusType.APPROVED.value());
		assertThat(response.getResults()).hasSize(1);
		ServiceResponse serviceResponse = response.getResults().get(0);
		assertThat(serviceResponse.getErrors()).isNull();
		assertThat(serviceResponse.getStatus()).isEqualTo(ServiceStatus.APPROVED.name());

	}

	void assertCommonCases(PrescriptionDetailInquiryResponseModel response) {
		assertNotNull(response);
		assertNotNull(response.getResults());
		assertThat(response.isCanCancel()).isTrue();
		assertThat(response.isCanFollowUp()).isTrue();
		assertThat(response.getePrescriptionReferenceNumber()).isEqualTo(ePrescriptionReferenceNumber);
		assertThat(response.getRequestId()).isEqualTo(requestId);
	}

	private TransactionLog generateTransactionLogWithvalidStatus() {
		return new TransactionLog(1L, requestId, 51.11, RequestType.NEW.name(), payerId, providerId,
				TransactionStatusType.SENT.name(), ePrescriptionReferenceNumber, ServiceStatus.APPROVED.name(), null,
				new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), "PBM", String.valueOf(HttpStatus.OK.value()),
				RequestStatusType.APPROVED.value());
	}

	private PrescriptionRequest generatePrescriptionRequest() {
		statusDesc = "Medication 2402221767 is not indicated with diagnosis code F31.6,"
				+ "Refill Too Soon, last refilled on Tue Feb 15 2028 by Provider :  for drug : 2402221767,"
				+ "Drug 2402221767 is inconsistent with the patient's age";
		PrescriptionRequest prescriptionRequest = new PrescriptionRequest(requestId, payerId, providerId,
				new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), RequestStatusType.REJECTED.value(), statusDesc,
				ePrescriptionReferenceNumber, new BigDecimal(0), new BigDecimal(0), BenefitCaseType.INPATIENT.value(),
				currency, currency);
		prescriptionRequest.setCanCancel(true);
		prescriptionRequest.setCanFollowUp(true);
		return prescriptionRequest;
	}

	private PrescriptionDetailInquiryRequestModel generateDetailInquiryRequestModel() {
		return new PrescriptionDetailInquiryRequestModel(payerId, ePrescriptionReferenceNumber);
	}

	private InvalidPrescriptionRequest generateInvalidPrescriptionRequest() {
		return new InvalidPrescriptionRequest(requestId, invalidEPrescriptionReferenceNumber,
				new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), INVALID,
				"IDNumber and MemberID should not be null or empty", null, null, payerId, providerId);
	}

	private List<ServiceResponse> generateServiceResponse(String status) {
		List<ServiceResponse> serviceResponseList = new ArrayList<>();
		ServiceResponse serviceResponse = new ServiceResponse(drugCode1, UnitType.PACKAGE.value(), 10D,
				new BigDecimal(3L), new BigDecimal(100D), new BigDecimal(0D), null, null, null,
				RequestStatusType.REJECTED.value(), null);
		serviceResponseList.add(serviceResponse);
		if (status.equals(RequestStatusType.PARTIAL_APPROVED.value())) {
			ServiceResponse serviceResponse2 = new ServiceResponse(drugCode2, UnitType.UNIT.value(), 10D,
					new BigDecimal(3L), new BigDecimal(100D), new BigDecimal(100D), null, null, null,
					RequestStatusType.APPROVED.value(), null);
			serviceResponseList.add(serviceResponse2);
		}
		return serviceResponseList;
	}

	private List<ServiceResponse> generateApprovedServiceResponse() {
		List<ServiceResponse> serviceResponseList = new ArrayList<>();
		ServiceResponse serviceResponse2 = new ServiceResponse(drugCode2, UnitType.UNIT.value(), 10D,
				new BigDecimal(3L), new BigDecimal(100D), new BigDecimal(100D), null, null, null,
				RequestStatusType.APPROVED.value(), null);
		serviceResponseList.add(serviceResponse2);
		return serviceResponseList;
	}

	private List<MedicalValidations> generateMedicalValidations() {
		List<MedicalValidations> list = new ArrayList<>();
		list.add(new MedicalValidations(drugCode1, "FDB_CPINDI001",
				"Medication 2402221767 is not indicated with diagnosis code F31.6"));
		list.add(new MedicalValidations(drugCode1, "CPREF390",
				"Refill Too Soon, last refilled on Tue Feb 15 2028 by Provider :  for drug : 2402221767"));
		list.add(new MedicalValidations(drugCode1, "FDB_CPAGE902",
				"Drug 2402221767 is inconsistent with the patient's age"));
		return list;
	}

	private ContentCachingRequestWrapper getContentCachingRequestWrapper() {
		ContentCachingRequestWrapper cachingRequestWrapper = null;
		try {
			MockHttpServletRequest hRequest = new MockHttpServletRequest();
			String req = this.mapper.writeValueAsString(detailInquiryRequestModel);
			hRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
			hRequest.setContent(req.getBytes(StandardCharsets.UTF_8.name()));
			hRequest.setRequestURI(PrescriptionUrl.DETAIL_INQUIRY.getValue());
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
