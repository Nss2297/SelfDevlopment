package com.waseel.prescription.prescriptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
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
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.dss.DssResponse;
import com.waseel.prescription.model.dss.Error;
import com.waseel.prescription.model.dss.Result;
import com.waseel.prescription.model.eligibility.EligibilityResponseModel;
import com.waseel.prescription.model.enums.BenefitCaseType;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.DssPayerTransactionType;
import com.waseel.prescription.model.enums.EligibilityStatus;
import com.waseel.prescription.model.enums.GenderType;
import com.waseel.prescription.model.enums.PbmRequestType;
import com.waseel.prescription.model.enums.PhysicianCategory;
import com.waseel.prescription.model.enums.PolicyConsumptionStatus;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.enums.SourceType;
import com.waseel.prescription.model.enums.UnitType;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionResponseModel;
import com.waseel.prescription.model.pbmpayerapis.MemberDemographicDataResponseModel;
import com.waseel.prescription.model.pbmpayerapis.PolicyInformationModel;
import com.waseel.prescription.model.policyconsumption.DrugListModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.model.prescription.DrugList;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.persist.businessrules.Department;
import com.waseel.prescription.persist.businessrules.PhysicianInfo;
import com.waseel.prescription.persist.mdss.DrugService;
import com.waseel.prescription.persist.prescriptionservice.InvalidPrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.MappingPayerId;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.Physician;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionApprovalAssc;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.businessrules.DeptSpecPhyscAsscRepository;
import com.waseel.prescription.repository.businessrules.PhysicianInfoRepository;
import com.waseel.prescription.repository.hira.DrugListServiceRepository;
import com.waseel.prescription.repository.hira.SwitchAccountRepository;
import com.waseel.prescription.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.prescription.repository.mdss.DrugServiceRepository;
import com.waseel.prescription.repository.prescriptionservice.DiagnosisRepository;
import com.waseel.prescription.repository.prescriptionservice.InvalidPrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.MappingPayerIdRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberPolicyUsageRepository;
import com.waseel.prescription.repository.prescriptionservice.PhysicianRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionApprovalAsscRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.TransactionLogRepository;
import com.waseel.prescription.service.clienthandler.PbmPayerApisRestHandler;
import com.waseel.prescription.service.clienthandler.RestHandler;
import com.waseel.prescription.service.management.BusinessRuleService;
import com.waseel.prescription.service.management.TransactionLogService;
import com.waseel.prescription.service.prescriptions.FollowUpPrescriptionService;
import com.waseel.prescription.service.prescriptions.PrescriptionService;
import com.waseel.prescription.service.validation.TechnicalValidationService;
import com.waseel.prescription.util.UserInfoUtil;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class FollowUpPrescriptionTests {

	@Autowired
	private FollowUpPrescriptionService followUpPrescriptionService;

	@MockBean
	private DiagnosisRepository diagnosisRepository;

	@MockBean
	public TransactionLogRepository transactionLogRepository;

	@MockBean
	private PrescriptionRequestRepository prescriptionRequestRepository;

	@MockBean
	private ServiceInfoRepository serviceInfoRepository;

	@MockBean
	private ServiceResponseInfoRepository serviceResponseInfoRepository;

	@MockBean
	private ServiceRejectionRepository serviceRejectionRepository;

	@MockBean
	private DrugListServiceRepository drugListServiceRepository;

	@Autowired
	private TransactionLogService transactionLogService;

	@MockBean
	private RestHandler restHandler;

	@Autowired
	private PrescriptionService prescriptionService;

	@MockBean
	private PhysicianRepository physicianRepository;

	@MockBean
	private MemberInfoRepository memberInfoRepository;

	@MockBean
	private InvalidPrescriptionRequestRepository invalidPrescriptionRequestRepository;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	private TechnicalValidationService technicalValidationService;

	@MockBean
	private BusinessRuleService businessRuleService;

	@MockBean
	private SwitchAccountRepository switchAccountRepository;

	@MockBean
	private MappingPayerIdRepository mappingPayerIdRepository;

	@MockBean
	private DrugServiceRepository drugServiceRepository;
	
	@MockBean
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	private PrescriptionRequest prescriptionRequest;
	private Optional<MemberInfo> memberInfo;
	private Optional<Physician> physician;

	private final MockHttpServletRequest request = new MockHttpServletRequest();
	protected static final int CONTENT_CACHE_LIMIT = 13;
	ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(this.request, CONTENT_CACHE_LIMIT);
	private PrescriptionRequestModel prescriptionRequestModel;
	private TransactionLog transactionLog;
	private String requestId;
	String ePrescriptionReferenceNum;
	private static final String MSG_ISCANCELLED = "Request is already cancelled. You can't do FollowUp";
	private static final String MSG_DISPENSED = "Not allowed to do FollowUp because this request is already Dispensed.";
	private static final String MSG_CANNOTFOLLOWUP = "Not allowed to do FollowUp with this request.";
	private Timestamp timestamp = (new Timestamp(Calendar.getInstance().getTimeInMillis()));
	private String providerId = "801";
	private static final String memberName = "Salim";
	private static final String payerId = "102";
	private EPrescriptionResponseModel ePrescriptionResponseModel;
	private String statusDesc = "Refill Too Soon, last refilled on 12/12/2023 by Provider : testProvider for drug : 51-277-98";
	private Date date = new Date();
	private static final String currency = Currency.SAR.value();
	private static final String classCode = "1-VVIP-Network Gold";
	private static final String mappedPayerId = "102_" + DssPayerTransactionType.PRESCRIPTION.value();
	private static final Long mappingId = 1L;
	private static final String drugListId = "1";
	private final String memberNationality = "Saudi Arabia";

	@MockBean
	private PbmPayerApisRestHandler pbmPayerApisRestHandler;

	@MockBean
	private MemberPolicyUsageRepository memberPolicyUsageRepository;

	@MockBean
	private PhysicianInfoRepository physicianInfoRepository;

	@MockBean
	private DeptSpecPhyscAsscRepository deptSpecPhyscAsscRepository;

	@MockBean
	private PrescriptionApprovalAsscRepository prescriptionApprovalAsscRepository;

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
	}

	@BeforeEach
	void setUpData() {
		ePrescriptionReferenceNum = "2023-01";
		requestId = "8890e044-a1ba-44c7-b0c7-86393cc5773b";
		prescriptionRequestModel = getPrescriptionRequestModel();
		transactionLog = generateTransactionLog();
		prescriptionRequest = generatePrescriptionRequest();
		memberInfo = Optional.of(generateMemberInfo());
		physician = Optional.of(generatePhysician());
		requestWrapper = getContentCachingRequestWrapper();
		Mockito.when(transactionLogRepository.save(Mockito.any())).thenReturn(transactionLog);
		Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(prescriptionRequest);
		Mockito.when(memberInfoRepository.save(Mockito.any())).thenReturn(memberInfo);
		Mockito.when(physicianRepository.save(Mockito.any())).thenReturn(physician);
		Mockito.when(invalidPrescriptionRequestRepository.save(Mockito.any()))
				.thenReturn(generateInvalidPrescriptionRequest());
		Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(ePrescriptionReferenceNum))
				.thenReturn(Optional.of(prescriptionRequest));
		assertNotNull(prescriptionRequest);
		assertNotNull(memberInfo);
		assertNotNull(physician);
		Mockito.when(pbmPayerApisRestHandler.sendRequestToGetMemberDemographicData(Mockito.any()))
				.thenReturn(generateMemberDemographicDataResponseModel());
		Mockito.when(mappingPayerIdRepository.findByPayerIdAndTransactionTypeAndIsEnabled(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(Optional.of(generateMappingPayerId()));
		Mockito.when(drugServiceRepository.findByOtherCodesValue(Mockito.any()))
				.thenReturn(Optional.of(generateDrugService()));
	}

	@Test
	@DisplayName("Invalid response[ throw Exception ] from FollowUp.")
	void followUpServiceTestsInvalidCase() throws IOException {
		try {
			prescriptionRequest.setCancelled(true);
			Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(prescriptionRequest);
			Mockito.when(transactionLogRepository.findByePrescriptionReferenceNumberWithValidStatus(
					prescriptionRequestModel.getePrescriptionReferenceNumber()))
					.thenReturn(Optional.of(transactionLog));
			prescriptionRequest.setPayerId(mappedPayerId);
			Mockito.when(prescriptionRequestRepository.findByRequestId(requestId))
					.thenReturn(Optional.of(prescriptionRequest));
			mockDrugServiceData();
			prescriptionService.manageNewOrFollowUpPrescriptionRequest(prescriptionRequestModel, requestWrapper,
					SourceType.INTEGRATION.value(), payerId);
		} catch (PrescriptionException e) {
			assertThat(e.getInvalidResponse().getHttpStatusDescription())
					.isEqualTo("Invalid EPrescriptionReferenceNumber.");
		}
	}

	@Test
	@DisplayName("Invalid response Can Not Do FollowUp.")
	void followUpServiceTestsCanNotFollowUpCase() throws IOException {
		try {
			prescriptionRequest.setCanFollowUp(false);
			Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(prescriptionRequest);
			Mockito.when(transactionLogRepository.findByePrescriptionReferenceNumberWithValidStatus(
					prescriptionRequestModel.getePrescriptionReferenceNumber()))
					.thenReturn(Optional.of(transactionLog));
			prescriptionRequest.setPayerId(mappedPayerId);
			Mockito.when(prescriptionRequestRepository.findByRequestId(requestId))
					.thenReturn(Optional.of(prescriptionRequest));
			Mockito.when(transactionLogRepository.findByePrescriptionReferenceNumberAndTransactionType(
					prescriptionRequestModel.getePrescriptionReferenceNumber(), RequestType.NEW.name()))
					.thenReturn(Optional.of(transactionLog));
			mockDrugServiceData();
			PrescriptionResponseModel model = prescriptionService.manageNewOrFollowUpPrescriptionRequest(
					prescriptionRequestModel, requestWrapper, SourceType.INTEGRATION.value(), payerId);
			assertThat(model.getStatus()).isEqualTo("Invalid");
			assertThat(model.getHttpStatusCode()).isEqualTo(HttpStatus.OK.value());
			assertThat(model.getStatusDescription()).isEqualTo(MSG_CANNOTFOLLOWUP);
			assertEquals(payerId, prescriptionRequestModel.getPayerId());
		} catch (PrescriptionException e) {
			assertThat(e.getInvalidResponse().getHttpStatusDescription())
					.isEqualTo("Invalid EPrescriptionReferenceNumber.");
		}
	}

	@Test
	@DisplayName("Invalid response Can Not Do FollowUp.")
	void followUpServiceTestsIsCancelledCase() throws IOException {
		try {
			prescriptionRequest.setCancelled(true);
			Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(prescriptionRequest);
			Mockito.when(transactionLogRepository.findByePrescriptionReferenceNumberWithValidStatus(
					prescriptionRequestModel.getePrescriptionReferenceNumber()))
					.thenReturn(Optional.of(transactionLog));
			prescriptionRequest.setPayerId(mappedPayerId);
			Mockito.when(prescriptionRequestRepository.findByRequestId(requestId))
					.thenReturn(Optional.of(prescriptionRequest));
			Mockito.when(transactionLogRepository.findByePrescriptionReferenceNumberAndTransactionType(
					prescriptionRequestModel.getePrescriptionReferenceNumber(), RequestType.NEW.name()))
					.thenReturn(Optional.of(transactionLog));
			mockDrugServiceData();
			PrescriptionResponseModel model = prescriptionService.manageNewOrFollowUpPrescriptionRequest(
					prescriptionRequestModel, requestWrapper, SourceType.INTEGRATION.value(), payerId);
			assertThat(model.getStatus()).isEqualTo("Invalid");
			assertThat(model.getHttpStatusCode()).isEqualTo(HttpStatus.OK.value());
			assertThat(model.getStatusDescription()).isEqualTo(MSG_ISCANCELLED);
			assertEquals(payerId, prescriptionRequestModel.getPayerId());
		} catch (PrescriptionException e) {
			assertThat(e.getInvalidResponse().getHttpStatusDescription())
					.isEqualTo("Invalid EPrescriptionReferenceNumber.");
		}
	}

	@Test
	@DisplayName("Invalid response Can Not Do FollowUp Because of Dispensed request.")
	void validateCannotFollowUpDispensedRequest() throws IOException {
		try {
			prescriptionRequest.setCanFollowUp(false);
			prescriptionRequest.setStatusCode(RequestStatusType.DISPENSED.value());
			Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(prescriptionRequest);
			Mockito.when(transactionLogRepository.findByePrescriptionReferenceNumberWithValidStatus(
					prescriptionRequestModel.getePrescriptionReferenceNumber()))
					.thenReturn(Optional.of(transactionLog));
			prescriptionRequest.setPayerId(mappedPayerId);
			Mockito.when(prescriptionRequestRepository.findByRequestId(requestId))
					.thenReturn(Optional.of(prescriptionRequest));
			Mockito.when(transactionLogRepository.findByePrescriptionReferenceNumberAndTransactionType(
					prescriptionRequestModel.getePrescriptionReferenceNumber(), RequestType.NEW.name()))
					.thenReturn(Optional.of(transactionLog));
			mockDrugServiceData();
			PrescriptionResponseModel model = prescriptionService.manageNewOrFollowUpPrescriptionRequest(
					prescriptionRequestModel, requestWrapper, SourceType.INTEGRATION.value(), payerId);
			assertThat(model.getStatus()).isEqualTo("Invalid");
			assertThat(model.getHttpStatusCode()).isEqualTo(HttpStatus.OK.value());
			assertThat(model.getStatusDescription()).isEqualTo(MSG_DISPENSED);
			assertEquals(payerId, prescriptionRequestModel.getPayerId());
		} catch (PrescriptionException e) {
			assertThat(e.getInvalidResponse().getHttpStatusDescription())
					.isEqualTo("Invalid EPrescriptionReferenceNumber.");
		}
	}

	@Test
	@DisplayName("Invalid response[400] from FollowUp.")
	void followUpServiceTestsBeanValidationCase() {
		try {
			PrescriptionResponseModel invalidResponse = technicalValidationService
					.populateInvalidPrescriptionResponse(getMethodArgumentNotValidException(), requestWrapper);
			assertNotNull(invalidResponse);
			assertThat(invalidResponse.getStatus()).isEqualTo("Invalid");
			assertThat(invalidResponse.getHttpStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
			assertThat(invalidResponse.getePrescriptionReferenceNumber()).isEqualTo(ePrescriptionReferenceNum);
			assertEquals(payerId, prescriptionRequestModel.getPayerId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("Failed response[500] from FollowUp.")
	void followUpServiceTestsFailedValidationCase() {
		try {
			PrescriptionResponseModel invalidResponse = technicalValidationService
					.populateFailedPrescriptionResponse(requestWrapper);
			assertNotNull(invalidResponse);
			assertThat(invalidResponse.getStatus()).isEqualTo("Failed");
			assertThat(invalidResponse.getHttpStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			assertThat(invalidResponse.getHttpStatusDescription()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.name());
			assertThat(invalidResponse.getePrescriptionReferenceNumber()).isEqualTo(ePrescriptionReferenceNum);
			assertEquals(payerId, prescriptionRequestModel.getPayerId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MethodArgumentNotValidException getMethodArgumentNotValidException() {
		final Method method = new Object() {
		}.getClass().getEnclosingMethod();
		final MethodParameter parameter = Mockito.mock(MethodParameter.class);
		Mockito.when(parameter.getMethod()).thenReturn(method);
		final BindingResult bindingResult = Mockito.mock(BindingResult.class);
		bindingResult.rejectValue("unitType", "unitType should be one of these values : PACKAGE or UNIT");
		bindingResult.getAllErrors();
		final MethodArgumentNotValidException exception = new MethodArgumentNotValidException(parameter, bindingResult);
		Mockito.when(bindingResult.getAllErrors()).thenReturn(Lists.newArrayList());
		return exception;
	}

	private ContentCachingRequestWrapper getContentCachingRequestWrapper() {
		ContentCachingRequestWrapper cachingRequestWrapper = null;
		try {
			MockHttpServletRequest hRequest = new MockHttpServletRequest();
			String req = this.mapper.writeValueAsString(prescriptionRequestModel);
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

	@Test
	@DisplayName("Valid response[200] from FollowUp.")
	void followUpServiceTestsValidCase() {
		try {
			Mockito.when(restHandler.handleFollowupPrescriptionRequest(Mockito.any()))
					.thenReturn(getDSSResponseModel());
			Mockito.when(transactionLogRepository.findByePrescriptionReferenceNumberAndTransactionType(
					prescriptionRequestModel.getePrescriptionReferenceNumber(), RequestType.NEW.name()))
					.thenReturn(Optional.of(transactionLog));
			Mockito.when(transactionLogService.addTransaction(RequestType.FOLLOWUP,
					prescriptionRequestModel.getPayerId(), providerId, requestId,
					prescriptionRequestModel.getePrescriptionReferenceNumber(), SourceType.INTEGRATION.value()))
					.thenReturn(transactionLog);
			Mockito.when(
					businessRuleService.eligibilityCheck(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
					.thenReturn(generateEligibilityResponse(EligibilityStatus.ELIGIBLE.getValue()));
			Mockito.when(businessRuleService.policyConsumptionCheck(Mockito.any(), Mockito.any(), Mockito.any(),
					Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
					.thenReturn(generatePolicyResponseModel(PolicyConsumptionStatus.APPROVED.getValue()));
			Mockito.when(prescriptionRequestRepository.findByRequestId(Mockito.any()))
					.thenReturn(Optional.of(generatePrescriptionRequest()));
			Mockito.when(serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId))
					.thenReturn(generateServiceInfoListForApproval());
			Mockito.when(serviceInfoRepository.save(Mockito.any())).thenReturn(generateServiceInfoForApproval());
			Mockito.when(serviceResponseInfoRepository.save(Mockito.any()))
					.thenReturn(generateServiceResponseInfoForApproval());
			mockDataForEPrescriptionApprovalTest();
			Mockito.when(serviceResponseInfoRepository.save(Mockito.any()))
					.thenReturn(generateServiceResponseInfoForApproval());
			PrescriptionResponseModel prescriptionResponseModel = followUpPrescriptionService.manageFollowUpRequest(
					prescriptionRequestModel, requestWrapper, generatePrescriptionRequest(), providerId,
					SourceType.INTEGRATION.value());
			assertNotNull(prescriptionResponseModel);
			assertThat(prescriptionResponseModel.getHttpStatusCode()).isEqualTo(HttpStatus.OK.value());
			assertThat(prescriptionResponseModel.getStatus()).isEqualTo(RequestStatusType.PARTIAL_APPROVED.value());
			assertEquals(payerId, prescriptionRequestModel.getPayerId());
		} catch (PrescriptionException e) {
			assertThat(e.getInvalidResponse().getStatusDescription())
					.isEqualTo("Invalid EPrescriptionReferenceNumber.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("Success response from EPrescription Approval.")
	void successResponseFromEPrescriptionApprovalTest() {
		try {
			mockDataForEligiblityAndPolicyConsumption();
			ePrescriptionResponseModel = generateSuccessEPrescriptionResponseModel();
			mockDataForEPrescriptionApprovalTest();
			PrescriptionResponseModel prescriptionResponseAfterApproval = followUpPrescriptionService
					.manageFollowUpRequest(prescriptionRequestModel, requestWrapper, generatePrescriptionRequest(),
							providerId, SourceType.INTEGRATION.value());
			assertNotNull(prescriptionResponseAfterApproval);
			assertNotNull(prescriptionResponseAfterApproval.getResults());
			assertThat(prescriptionResponseAfterApproval.getHttpStatusCode()).isEqualTo(HttpStatus.OK.value());
			assertThat(prescriptionResponseAfterApproval.getStatus())
					.isEqualTo(RequestStatusType.PARTIAL_APPROVED.value());
			assertThat(prescriptionResponseAfterApproval.getResults()).hasSize(2);
			assertEquals(payerId, prescriptionRequestModel.getPayerId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("Failed response from EPrescription Approval.")
	void failedResponseFromEPrescriptionApprovalTest() {
		try {
			mockDataForEligiblityAndPolicyConsumption();
			ePrescriptionResponseModel = generateFailedEPrescriptionResponseModel();
			mockDataForEPrescriptionApprovalTest();
			PrescriptionResponseModel prescriptionResponseAfterApproval = followUpPrescriptionService
					.manageFollowUpRequest(prescriptionRequestModel, requestWrapper, generatePrescriptionRequest(),
							providerId, SourceType.INTEGRATION.value());
			assertNotNull(prescriptionResponseAfterApproval);
			assertThat(prescriptionResponseAfterApproval.getStatus()).isEqualTo(RequestStatusType.FAILED.value());
			assertNull(prescriptionResponseAfterApproval.getResults());
			assertEquals("Not able to call Tawuniya server", prescriptionResponseAfterApproval.getStatusDescription());
			assertEquals(payerId, prescriptionRequestModel.getPayerId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void mockDataForEPrescriptionApprovalTest() {
		Mockito.when(prescriptionApprovalAsscRepository.save(Mockito.any()))
				.thenReturn(generatePrescriptionApprovalAssc());
		Mockito.when(pbmPayerApisRestHandler.sendRequestToGetEPrescriptionApproval(Mockito.any()))
				.thenReturn(ePrescriptionResponseModel);
		Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(generatePrescriptionRequest());
		Mockito.when(prescriptionRequestRepository
				.findByePrescriptionReferenceNumber(prescriptionRequestModel.getePrescriptionReferenceNumber()))
				.thenReturn(Optional.of(generatePrescriptionRequest()));
		Mockito.when(serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId))
				.thenReturn(generateServiceInfoListForApproval());
	}

	private List<ServiceInfo> generateServiceInfoListForApproval() {
		List<ServiceInfo> list = new ArrayList<>();
		list.add(new ServiceInfo(2L, "31-277-98", "package", 318.00, new BigDecimal(3), new BigDecimal(31.0), "test",
				date, date, 8L, "test", "test", requestId));
		return list;
	}

	private PrescriptionApprovalAssc generatePrescriptionApprovalAssc() {
		return new PrescriptionApprovalAssc("1022332", new Date(), ePrescriptionReferenceNum,
				PbmRequestType.FOLLOWUP.value());
	}

	private EPrescriptionResponseModel generateSuccessEPrescriptionResponseModel() {
		return new EPrescriptionResponseModel(prescriptionRequestModel.getePrescriptionReferenceNumber(), "101222122",
				null, null, null);
	}

	private EPrescriptionResponseModel generateFailedEPrescriptionResponseModel() {
		return new EPrescriptionResponseModel(null, null, "FAILED", "Not able to call Tawuniya server", null);
	}

	private void mockDataForEligiblityAndPolicyConsumption() {
		Mockito.when(physicianInfoRepository.save(Mockito.any())).thenReturn(generatePhysicianInfo());
		Mockito.when(deptSpecPhyscAsscRepository.save(Mockito.any())).thenReturn(generateDepartment());
		Mockito.when(serviceResponseInfoRepository.save(Mockito.any()))
				.thenReturn(generateServiceResponseInfoForApproval());
		Mockito.when(physicianInfoRepository.findByRegistrationNumber(Mockito.any()))
				.thenReturn(Optional.of(generatePhysicianInfo()));
		Mockito.when(deptSpecPhyscAsscRepository.findByPhysicianInfoId(Mockito.any()))
				.thenReturn(Optional.of(generateDepartment()));
		Mockito.when(restHandler.handleFollowupPrescriptionRequest(Mockito.any())).thenReturn(getDSSResponseModel());
		Mockito.when(transactionLogRepository.findByePrescriptionReferenceNumberAndTransactionType(
				prescriptionRequestModel.getePrescriptionReferenceNumber(), RequestType.NEW.name()))
				.thenReturn(Optional.of(transactionLog));
		Mockito.when(transactionLogService.addTransaction(RequestType.FOLLOWUP, prescriptionRequestModel.getPayerId(),
				providerId, requestId, prescriptionRequestModel.getePrescriptionReferenceNumber(),
				SourceType.INTEGRATION.value())).thenReturn(transactionLog);
		Mockito.when(businessRuleService.eligibilityCheck(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(generateEligibilityResponse(EligibilityStatus.ELIGIBLE.getValue()));
		Mockito.when(businessRuleService.policyConsumptionCheck(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(generatePolicyResponseModel(PolicyConsumptionStatus.APPROVED.getValue()));
		Mockito.when(prescriptionRequestRepository.findByRequestId(Mockito.anyString()))
				.thenReturn(Optional.of(generatePrescriptionRequest()));
		Mockito.when(serviceInfoRepository.save(Mockito.any())).thenReturn(generateServiceInfoForApproval());
		Mockito.when(serviceInfoRepository.findByRequestIdAndDrugCode(requestId, "31-277-98"))
				.thenReturn(Optional.of(generateServiceInfoForApproval()));
	}

	private Department generateDepartment() {
		return new Department(1L, "Cardiology", date, false);
	}

	private PhysicianInfo generatePhysicianInfo() {
		com.waseel.prescription.persist.businessrules.PhysicianCategory category = new com.waseel.prescription.persist.businessrules.PhysicianCategory();
		category.setPhysicianCategoryName("Anesthesia Cardiology");
		category.setCategoryDescription("Anesthesia Cardiology");
		return new PhysicianInfo(1L, 99999L, "31-277-98", "TEST", category);

	}

	private ServiceInfo generateServiceInfoForApproval() {
		return new ServiceInfo(2L, "31-277-98", "package", 318.00, new BigDecimal(3), new BigDecimal(31.0), "test",
				date, date, 8L, "test", "test", requestId);
	}

	private ServiceResponseInfo generateServiceResponseInfoForApproval() {
		return new ServiceResponseInfo(2l, requestId, new BigDecimal(30.2), new BigDecimal(30.2), 15.3,
				new BigDecimal(10.2), new BigDecimal(30.2), ServiceStatus.APPROVED.name(), null, 2L);
	}

	private PrescriptionRequestModel getPrescriptionRequestModel() {
		List<DiagnosisCodes> diagnosisCodesList = new ArrayList<>();
		DiagnosisCodes code1 = new DiagnosisCodes("F31.6", "PRIMARY");
		DiagnosisCodes code2 = new DiagnosisCodes("R25.2", "SECONDARY");
		diagnosisCodesList.add(code1);
		diagnosisCodesList.add(code2);

		List<DrugList> drugLists = new ArrayList<>();
		setDrugListData(drugLists);

		List<DrugListModel> policyConsumptionDrugList = new ArrayList<>();
		DrugListModel drugListModel = new DrugListModel("51-277-98", new BigDecimal(50), null, null, null, null);
		policyConsumptionDrugList.add(drugListModel);

		PrescriptionRequestModel request = new PrescriptionRequestModel("102", "123", "1234578901", "22/02/2000",
				"123456", GenderType.MALE.value(), BigDecimal.valueOf(51), BigDecimal.valueOf(146), "32", "Dr.Test",
				PhysicianCategory.CONSULTANT.value(), diagnosisCodesList, drugLists, memberName, "inpatient",
				policyConsumptionDrugList);
		request.setePrescriptionReferenceNumber(ePrescriptionReferenceNum);
		return request;
	}
	
	private void setDrugListData(List<DrugList> drugLists) {
		DrugList drugList1 = new DrugList("31-277-98", UnitType.UNIT.value(), new BigDecimal(5), 10D, "HealthCare", "5",
				"once-daily", "others", "23/02/2023", "25/02/2023", drugListId);
		DrugList drugList2 = new DrugList("51-277-98", UnitType.PACKAGE.value(), new BigDecimal(4), 40D,
				"HealthCare Online", "15", "twice-daily", "others", "24/03/2023", "25/02/2023", drugListId);
		drugLists.add(drugList1);
		drugLists.add(drugList2);
	}

	private DssResponse getDSSResponseModel() {
		List<Error> errors = new ArrayList<>();
		Error error1 = new Error("Drug 51-277-98 is inconsistent with the patient's age", "FDB_CPAGE902");
		Error error2 = new Error("Medication 51-277-98 is not indicated with diagnosis code R25.2", "IDF_CPINDI001");
		errors.add(error1);
		errors.add(error2);

		List<Result> results = new ArrayList<>();
		Result result1 = new Result("31-277-98", new BigDecimal(5), new BigDecimal(50D), "5",
				ServiceStatus.APPROVED.name(), null);
		Result result2 = new Result("51-277-98", new BigDecimal(4), new BigDecimal(160D), "15",
				ServiceStatus.REJECTED.name(), errors);
		results.add(result1);
		results.add(result2);

		List<String> errorsList = new ArrayList<>();
		errorsList.add(error1.getDescription());
		errorsList.add(error2.getDescription());

		DssResponse response = new DssResponse(requestId, RequestStatusType.PARTIAL_APPROVED.value(), errorsList,
				results, HttpStatus.OK.value(), HttpStatus.OK.name());

		return response;
	}

	private TransactionLog generateTransactionLog() {
		return new TransactionLog(1L, requestId, 51.11, RequestType.NEW.name(), "102", "12", "Received",
				ePrescriptionReferenceNum, null, null, new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), null, "200", null);
	}

	private PrescriptionRequest generatePrescriptionRequest() {
		PrescriptionRequest request = new PrescriptionRequest(requestId, "102", "12",
				new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), RequestStatusType.APPROVED.value(), "",
				ePrescriptionReferenceNum, new BigDecimal(0), new BigDecimal(0), BenefitCaseType.INPATIENT.value(),
				currency, currency);
		return request;
	}

	private MemberInfo generateMemberInfo() {
		return new MemberInfo("123", 1234578901L, "123456", convertStringToDate("22/02/2000"), 51D, 146D,
				GenderType.MALE.value(), requestId, memberName, memberNationality);
	}

	private Physician generatePhysician() {
		return new Physician("32", requestId, "Dr.Test", PhysicianCategory.CONSULTANT.value(), "Test");
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

	private InvalidPrescriptionRequest generateInvalidPrescriptionRequest() {
		return new InvalidPrescriptionRequest(1L, requestId, prescriptionRequest.getePrescriptionReferenceNumber(),
				timestamp, timestamp, null, null, null, 0, null, prescriptionRequest.getPayerId(),
				prescriptionRequest.getProviderId());
	}

	public void generateMockUserInfo() {
		Map<String, Object> details = new HashMap<String, Object>();
		details.put("accId", "12");
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

	private EligibilityResponseModel generateEligibilityResponse(String value) {
		return new EligibilityResponseModel(value,
				"Physician consultation is covered subject to Tawuniya's Policy Terms and Conditions.", "",
				ePrescriptionReferenceNum, requestId, HttpStatus.OK.value());
	}

	private PolicyResponseModel generatePolicyResponseModel(String value) {
		List<DrugListModel> policyConsumptiondrugList = new ArrayList<>();
		DrugListModel drug = new DrugListModel("45-56-78", new BigDecimal("100"), null, null, null, null);
		policyConsumptiondrugList.add(drug);
		return new PolicyResponseModel("APPROVED", "APPROVED", "20", "100", "", "", String.valueOf(HttpStatus.OK.value()), "147954268",
				"789465", "", new BigDecimal("15000"), "SAR", "SAR", "APPROVED", "100", "123456789",
				policyConsumptiondrugList, currency, currency);
	}

	private ResponseEntity<MemberDemographicDataResponseModel> generateMemberDemographicDataResponseModel() {
		List<PolicyInformationModel> policyInformationList = new ArrayList<>();
		policyInformationList
				.add(new PolicyInformationModel("24735173", "TEST", "001014523658001", classCode, classCode));
		MemberDemographicDataResponseModel response = new MemberDemographicDataResponseModel("TEST", 1234567890L,
				GenderType.FEMALE.value(), new Date(), "", "saudi", "00966504875128", "test@hotmail.com", "32 Years",
				policyInformationList);
		return ResponseEntity.ok(response);
	}

	private MappingPayerId generateMappingPayerId() {
		MappingPayerId mappingPayerId = new MappingPayerId(mappingId, DssPayerTransactionType.PRESCRIPTION.value(),
				payerId, mappedPayerId, true);
		return mappingPayerId;
	}

	private DrugService generateDrugService() {
		DrugService drugService = new DrugService();
		drugService.setCategory("PHARMACEUTICAL");
		drugService.setCode("06285147014149");
		drugService.setDisplay("FLAZOL 500MG TAB");
		drugService.setDosageForm("TABLETS");
		drugService.setDrugListId(1l);
		drugService.setGranularUnit("20");
		drugService.setIngredients("METRONIDAZOLE");
		drugService.setLastUpdatedDate(date);
		drugService.setManufacturer("TABUK PHARMACEUTICAL MANUFACTURING CO.,SAUDI ARABIA");
		drugService.setOtherCodesType("SFDA");
		drugService.setOtherCodesValue("31-277-98");
		drugService.setPackageSize("20'S");
		drugService.setPackageType("BLISTER PACK");
		drugService.setPrice("16.55");
		drugService.setReceivedDate(date);
		drugService.setRegOwner("TABUK PHARMACEUTICAL MANUFACTURING CO.");
		drugService.setReleaseDate(timestamp);
		drugService.setRoaSuggested("ORAL");
		drugService.setStrength("500 MG");
		drugService.setUnitType("TABLET");
		drugService.setWaseelDrugId(10007769L);
		return drugService;
	}
	
	private void mockDrugServiceData() {
		Mockito.when(drugServiceMetaDataRepository.getActiveDrugServiceList(any())).thenReturn(Optional.ofNullable(1L));
		List<DrugService> drugLists = prescriptionRequestModel.getDrugList().stream().map(drug -> {
			DrugService drugService = generateDrugService();
			drugService.setOtherCodesValue(drug.getDrugCode());
			return drugService;
		}).collect(Collectors.toList());
		Mockito.when(drugServiceRepository.findByDrugListId(1L)).thenReturn(Optional.of(drugLists));
	}
}
