package com.waseel.prescription.prescriptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.assertj.core.util.Lists;
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
import com.waseel.prescription.clients.PbmNotificationServiceClient;
import com.waseel.prescription.model.dss.DssResponse;
import com.waseel.prescription.model.dss.Error;
import com.waseel.prescription.model.dss.Result;
import com.waseel.prescription.model.eligibility.EligibilityResponseModel;
import com.waseel.prescription.model.enums.BenefitCaseType;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.DssPayerTransactionType;
import com.waseel.prescription.model.enums.EligibilityStatus;
import com.waseel.prescription.model.enums.GenderType;
import com.waseel.prescription.model.enums.PolicyConsumptionStatus;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.enums.SourceType;
import com.waseel.prescription.model.notification.EmailNotificationResponseModel;
import com.waseel.prescription.model.notification.SmsNotificationResponseModel;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionResponseModel;
import com.waseel.prescription.model.pbmpayerapis.MemberDemographicDataResponseModel;
import com.waseel.prescription.model.pbmpayerapis.PolicyInformationModel;
import com.waseel.prescription.model.policyconsumption.DrugListModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.model.prescription.DrugList;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.persist.businessrules.BenefitCodePhyscSpecAssc;
import com.waseel.prescription.persist.businessrules.BenefitCodes;
import com.waseel.prescription.persist.businessrules.Department;
import com.waseel.prescription.persist.businessrules.PhysicianInfo;
import com.waseel.prescription.persist.businessrules.Speciality;
import com.waseel.prescription.persist.mdss.DrugService;
import com.waseel.prescription.persist.prescriptionservice.Diagnosis;
import com.waseel.prescription.persist.prescriptionservice.InvalidPrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.MappingPayerId;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.MemberPolicyUsage;
import com.waseel.prescription.persist.prescriptionservice.Physician;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.persist.prescriptionservice.ServiceRejection;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.businessrules.BenefitCodePhyscSpecAsscRepository;
import com.waseel.prescription.repository.businessrules.DeptSpecPhyscAsscRepository;
import com.waseel.prescription.repository.businessrules.PhysicianInfoRepository;
import com.waseel.prescription.repository.businessrules.SpecialityRepository;
import com.waseel.prescription.repository.hira.DrugListServiceRepository;
import com.waseel.prescription.repository.hira.SwitchAccountRepository;
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
import com.waseel.prescription.service.prescriptions.NewPrescriptionService;
import com.waseel.prescription.service.validation.TechnicalValidationService;
import com.waseel.prescription.util.UserInfoUtil;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles({ "test" })
class NewPrescriptionTests {

	private static final Logger log = LoggerFactory.getLogger(TechnicalValidationService.class);

	@Autowired
	private NewPrescriptionService prescriptionService;

	@Autowired
	private TechnicalValidationService technicalValidationService;

	@MockBean
	public TransactionLogRepository transactionLogRepository;

	@MockBean
	private RestHandler restHandler;

	@MockBean
	private PrescriptionRequestRepository prescriptionRequestRepository;

	@MockBean
	private PhysicianRepository physicianRepository;

	@MockBean
	private MemberInfoRepository memberInfoRepository;

	@MockBean
	private DiagnosisRepository diagnosisRepository;

	@MockBean
	private ServiceInfoRepository serviceInfoRepository;

	@MockBean
	private ServiceResponseInfoRepository serviceResponseInfoRepository;

	@MockBean
	private ServiceRejectionRepository serviceRejectionRepository;

	@MockBean
	private DrugListServiceRepository drugListServiceRepository;

	@MockBean
	private InvalidPrescriptionRequestRepository invalidPrescriptionRequestRepository;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private BusinessRuleService businessRuleService;

	@MockBean
	private MemberPolicyUsageRepository memberPolicyUsageRepository;

	@MockBean
	private PhysicianInfoRepository physicianInfoRepository;

	@MockBean
	private DeptSpecPhyscAsscRepository deptSpecPhyscAsscRepository;

	@MockBean
	private SwitchAccountRepository switchAccountRepository;

	@MockBean
	private PbmNotificationServiceClient notificationServiceClient;

	@MockBean
	private DrugServiceRepository drugServiceRepository;

	@MockBean
	private MappingPayerIdRepository mappingPayerIdRepository;

	@MockBean
	private BenefitCodePhyscSpecAsscRepository benefitCodePhyscSpecAsscRepository;

	@MockBean
	private SpecialityRepository specialityRepository;

	private String payerId = "102";
	private String providerId = "801";
	private String requestId = "0fb78ee7-9e62-4d9e-aee7-18f099e126f1";
	private String currentYear = Year.now().toString();
	String ePrescriptionReferenceNumber = currentYear + "-1";
	private Timestamp timestamp = (new Timestamp(Calendar.getInstance().getTimeInMillis()));
	protected static final int CONTENT_CACHE_LIMIT = 3;
	private PrescriptionRequestModel prescriptionRequestModel;
	private TransactionLog requestTransactionLog;
	private final MockHttpServletRequest request = new MockHttpServletRequest();
	ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(this.request, CONTENT_CACHE_LIMIT);
	private Date date = new Date();
	private static final String memberName = "Salim";
	private static final String eligibilityReferenceNumber = "47984222";
	private static final String currency = Currency.SAR.value();
	private static final String classCode = "1-VVIP-Network Gold";
	private static final String mappedPayerId = "102_" + DssPayerTransactionType.PRESCRIPTION.value();
	private static final Long mappingId = 1L;
	private static final String drugListId = "1";
	private BenefitCodePhyscSpecAssc benefitCodePhyscSpecAssc = null;
	private static final Long pkid = 1L;
	private static final String benefitCode = "Dental Benefit";
	private Speciality speciality = null;
	private static final String specialityName = "Anesthesia Cardiology";
	private EPrescriptionResponseModel ePrescriptionResponseModel;

	@MockBean
	private PbmPayerApisRestHandler pbmPayerApisRestHandler;

	@MockBean
	private PrescriptionApprovalAsscRepository prescriptionApprovalAsscRepository;

	private String statusDesc = "Refill Too Soon, last refilled on 12/12/2023 by Provider : testProvider for drug : 51-277-98";

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		benefitCodePhyscSpecAssc = populateBenefitCodePhyscSpecAssc();
	}

	@BeforeEach
	public void setUpData() {
		prescriptionRequestModel = generatePrescriptionRequestModel();
		requestTransactionLog = generateTransactionLogWithRequest();
		generateDssRejectionErrors();
		speciality = populateSpeciality();
		Mockito.when(transactionLogRepository.generateEPrescriptionReferenceNumber())
				.thenReturn(ePrescriptionReferenceNumber);
		Mockito.when(transactionLogRepository.save(Mockito.any())).thenReturn(requestTransactionLog);
		Mockito.when(physicianRepository.save(Mockito.any())).thenReturn(new Physician());
		Mockito.when(memberInfoRepository.save(Mockito.any())).thenReturn(new MemberInfo());
		List<Diagnosis> diagnosisList = new ArrayList<>();
		Mockito.when(diagnosisRepository.saveAll(Mockito.any())).thenReturn(diagnosisList);
		Mockito.when(serviceInfoRepository.save(Mockito.any())).thenReturn(generateServiceInfoEntity());
		Mockito.when(pbmPayerApisRestHandler.sendRequestToGetMemberDemographicData(Mockito.any()))
				.thenReturn(generateMemberDemographicDataResponseModel());
		Mockito.when(notificationServiceClient.sentNotificationToPatient(Mockito.any()))
				.thenReturn(ResponseEntity.ok(generateSmsNotificationResponse()));
		Mockito.when(notificationServiceClient.sendEmailNotification(Mockito.any()))
				.thenReturn(ResponseEntity.ok(generateEmailNotificationResponse()));
		Mockito.when(mappingPayerIdRepository.findByPayerIdAndTransactionTypeAndIsEnabled(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(Optional.of(generateMappingPayerId()));
		Mockito.when(
				benefitCodePhyscSpecAsscRepository.findBySpecialityIdAndIsEnabled(Mockito.any(), Mockito.anyBoolean()))
				.thenReturn(Optional.of(benefitCodePhyscSpecAssc));
		Mockito.when(specialityRepository.findBySpecialityNameAndIsDeleted(Mockito.any(), Mockito.anyBoolean()))
				.thenReturn(Optional.of(speciality));
	}

	@Test
	@DisplayName("Rejected status response from DSS.")
	void rejectedServiceFromPrescriptionTest() throws IOException {
		Mockito.when(businessRuleService.eligibilityCheck(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(generateEligibilityResponse(EligibilityStatus.ELIGIBLE.getValue()));
		Mockito.when(businessRuleService.policyConsumptionCheck(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(generatePolicyResponseModel(PolicyConsumptionStatus.APPROVED.getValue()));
		Mockito.when(businessRuleService.eligibilityCheck(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(generateEligibilityResponse(EligibilityStatus.ELIGIBLE.getValue()));
		Mockito.when(restHandler.handlePrescriptionRequest(Mockito.any()))
				.thenReturn(generateDssResponse(RequestStatusType.REJECTED.value()));
		Mockito.when(prescriptionRequestRepository.save(Mockito.any()))
				.thenReturn(generatePrescriptionRequest(RequestStatusType.REJECTED.value()));
		Mockito.when(serviceResponseInfoRepository.save(Mockito.any()))
				.thenReturn(generateServiceResponseInfoEntity(RequestStatusType.REJECTED.value()));
		Mockito.when(serviceRejectionRepository.saveAll(Mockito.any())).thenReturn(generateServiceRejections());
		PrescriptionResponseModel prescriptionResponseModel = prescriptionService.newSubmissionController(
				prescriptionRequestModel, requestWrapper, requestId, providerId, SourceType.INTEGRATION.value(),
				payerId);
		assertNotNull(prescriptionResponseModel);
		assertThat(getResponseStatus(prescriptionResponseModel, RequestStatusType.REJECTED.value())).isTrue();
		assertEquals(prescriptionRequestModel.getPayerId(), payerId);
	}

	@Test
	@DisplayName("Approved status response from DSS.")
	void approvedServiceFromPrescriptionTest() throws IOException {
		Mockito.when(businessRuleService.eligibilityCheck(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(generateEligibilityResponse(EligibilityStatus.ELIGIBLE.getValue()));
		Mockito.when(businessRuleService.policyConsumptionCheck(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(generatePolicyResponseModel(PolicyConsumptionStatus.APPROVED.getValue()));
		Mockito.when(restHandler.handlePrescriptionRequest(Mockito.any()))
				.thenReturn(generateDssResponse(RequestStatusType.APPROVED.value()));
		Mockito.when(prescriptionRequestRepository.save(Mockito.any()))
				.thenReturn(generatePrescriptionRequest(RequestStatusType.APPROVED.value()));
		Mockito.when(serviceResponseInfoRepository.save(Mockito.any()))
				.thenReturn(generateServiceResponseInfoEntity(RequestStatusType.APPROVED.value()));
		Mockito.when(physicianInfoRepository.findByRegistrationNumber(Mockito.any()))
				.thenReturn(Optional.of(generatePhysicianInfo()));
		Mockito.when(deptSpecPhyscAsscRepository.findByPhysicianInfoId(Mockito.any()))
				.thenReturn(Optional.of(generateDepartment()));
		PrescriptionResponseModel prescriptionResponseModel = prescriptionService.newSubmissionController(
				prescriptionRequestModel, requestWrapper, requestId, providerId, SourceType.INTEGRATION.value(),
				payerId);
		assertNotNull(prescriptionResponseModel);
		assertThat(getResponseStatus(prescriptionResponseModel, RequestStatusType.APPROVED.value())).isTrue();
		assertEquals(prescriptionRequestModel.getPayerId(), payerId);
	}

	@Test
	@DisplayName("Partially Approved status response from DSS.")
	void partiallyApprovedServiceFromPrescriptionTest() throws IOException {
		Mockito.when(businessRuleService.eligibilityCheck(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(generateEligibilityResponse(EligibilityStatus.ELIGIBLE.getValue()));
		Mockito.when(businessRuleService.policyConsumptionCheck(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(generatePolicyResponseModel(PolicyConsumptionStatus.APPROVED.getValue()));
		Mockito.when(memberInfoRepository.findByRequestId(Mockito.any())).thenReturn(generateMemberInfo());
		Mockito.when(memberPolicyUsageRepository.save(Mockito.any())).thenReturn(generateMemberPolicyUsage());
		Mockito.when(restHandler.handlePrescriptionRequest(Mockito.any()))
				.thenReturn(generateDssResponse(RequestStatusType.PARTIAL_APPROVED.value()));
		Mockito.when(prescriptionRequestRepository.save(Mockito.any()))
				.thenReturn(generatePrescriptionRequest(RequestStatusType.PARTIAL_APPROVED.value()));
		Mockito.when(serviceResponseInfoRepository.save(Mockito.any()))
				.thenReturn(generateServiceResponseInfoEntity(RequestStatusType.PARTIAL_APPROVED.value()));
		Mockito.when(serviceRejectionRepository.saveAll(Mockito.any())).thenReturn(generateServiceRejections());
		Mockito.when(drugServiceRepository.findByOtherCodesValue(Mockito.any()))
				.thenReturn(Optional.of(generateDrugService()));
		PrescriptionResponseModel prescriptionResponseModel = prescriptionService.newSubmissionController(
				prescriptionRequestModel, requestWrapper, requestId, providerId, SourceType.INTEGRATION.value(),
				payerId);
		assertNotNull(prescriptionResponseModel);
		assertThat(getResponseStatus(prescriptionResponseModel, RequestStatusType.PARTIAL_APPROVED.value())).isTrue();
		assertEquals(prescriptionRequestModel.getPayerId(), payerId);
	}

	@Test
	@DisplayName("Success response from EPrescription Approval.")
	void successResponseFromEPrescriptionApprovalTest() {
		ePrescriptionResponseModel = generateSuccessEPrescriptionResponseModel();
		mockDataForEligiblityAndPolicyConsumption();
		mockDataForEPrescriptionApprovalTest();
		PrescriptionResponseModel prescriptionResponseAfterApproval = prescriptionService.newSubmissionController(
				prescriptionRequestModel, requestWrapper, requestId, providerId, SourceType.INTEGRATION.value(),
				payerId);
		assertNotNull(prescriptionResponseAfterApproval);
		assertThat(prescriptionResponseAfterApproval.getStatus()).isEqualTo(RequestStatusType.PARTIAL_APPROVED.value());
		assertThat(prescriptionResponseAfterApproval.getResults()).hasSize(2);
		assertEquals(prescriptionRequestModel.getPayerId(), payerId);
	}

	@Test
	@DisplayName("Failed response from EPrescription Approval.")
	void failedResponseFromEPrescriptionApprovalTest() {
		ePrescriptionResponseModel = generateFailedEPrescriptionResponseModel();
		mockDataForEligiblityAndPolicyConsumption();
		mockDataForEPrescriptionApprovalTest();
		PrescriptionResponseModel prescriptionResponseAfterApproval = prescriptionService.newSubmissionController(
				prescriptionRequestModel, requestWrapper, requestId, providerId, SourceType.INTEGRATION.value(),
				payerId);
		assertNotNull(prescriptionResponseAfterApproval);
		assertThat(prescriptionResponseAfterApproval.getStatus()).isEqualTo(RequestStatusType.FAILED.value());
		assertNull(prescriptionResponseAfterApproval.getResults());
		assertEquals("Not able to call Tawuniya server", prescriptionResponseAfterApproval.getStatusDescription());
		assertEquals(prescriptionRequestModel.getPayerId(), payerId);
	}

	private void mockDataForEPrescriptionApprovalTest() {
		Mockito.when(pbmPayerApisRestHandler.sendRequestToGetEPrescriptionApproval(Mockito.any()))
				.thenReturn(ePrescriptionResponseModel);
		Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber))
				.thenReturn(Optional.of(generatePrescriptionRequest(RequestStatusType.PARTIAL_APPROVED.value())));
		Mockito.when(serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId))
				.thenReturn(generateServiceInfoForApproval());
	}

	private void mockDataForEligiblityAndPolicyConsumption() {
		Mockito.when(businessRuleService.eligibilityCheck(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(generateEligibilityResponse(EligibilityStatus.ELIGIBLE.getValue()));
		Mockito.when(businessRuleService.policyConsumptionCheck(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(generatePolicyResponseModel(PolicyConsumptionStatus.APPROVED.getValue()));
		Mockito.when(memberInfoRepository.findByRequestId(Mockito.any())).thenReturn(generateMemberInfo());
		Mockito.when(memberPolicyUsageRepository.save(Mockito.any())).thenReturn(generateMemberPolicyUsage());
		Mockito.when(restHandler.handlePrescriptionRequest(Mockito.any())).thenReturn(generateDssResponseForApproval());
		Mockito.when(prescriptionRequestRepository.save(Mockito.any()))
				.thenReturn(generatePrescriptionRequest(RequestStatusType.PARTIAL_APPROVED.value()));
		Mockito.when(serviceInfoRepository.save(Mockito.any())).thenReturn(generateServiceInfoForApproval());
		Mockito.when(serviceResponseInfoRepository.save(Mockito.any()))
				.thenReturn(generateServiceResponseInfoForApproval());
		Mockito.when(serviceRejectionRepository.saveAll(Mockito.any()))
				.thenReturn(generateServiceRejectionsForApproval());
	}

	private EPrescriptionResponseModel generateSuccessEPrescriptionResponseModel() {
		return new EPrescriptionResponseModel(ePrescriptionReferenceNumber, "101222122", null, null, null);
	}

	private EPrescriptionResponseModel generateFailedEPrescriptionResponseModel() {
		return new EPrescriptionResponseModel(null, null, "FAILED", "Not able to call Tawuniya server", null);
	}

	private MemberPolicyUsage generateMemberPolicyUsage() {
		MemberPolicyUsage memberPolicyUsage = new MemberPolicyUsage(payerId, providerId, "1234568",
				Long.valueOf(1234567890), "789456", "7894195", "benefit", new BigDecimal(0), "SAR", new BigDecimal(0),
				"SAR", ePrescriptionReferenceNumber, "APPROVED");
		return memberPolicyUsage;
	}

	@Test
	@DisplayName("Invalid response[400].")
	void invalidDssResponseTest() {
		Mockito.when(invalidPrescriptionRequestRepository.save(Mockito.any()))
				.thenReturn(generateInvalidPrescriptionRequest());
		PrescriptionResponseModel invalidDssResponse = technicalValidationService.populateInvalidPrescriptionResponse(
				getMethodArgumentNotValidException(), getContentCachingRequestWrapper());
		assertNotNull(invalidDssResponse);
		assertEquals("Invalid", invalidDssResponse.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST.value(), invalidDssResponse.getHttpStatusCode());
		assertEquals(ePrescriptionReferenceNumber, invalidDssResponse.getePrescriptionReferenceNumber());
	}

	@Test
	@DisplayName("Failed Response[500].")
	void failedDssResponseTest() {
		PrescriptionResponseModel invalidDssResponse = technicalValidationService
				.populateFailedPrescriptionResponse(getContentCachingRequestWrapper());
		assertNotNull(invalidDssResponse);
		assertEquals("Failed", invalidDssResponse.getStatus());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), invalidDssResponse.getHttpStatusCode());
		assertEquals(ePrescriptionReferenceNumber, invalidDssResponse.getePrescriptionReferenceNumber());
		assertThat(invalidDssResponse.getHttpStatusDescription()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.name());
	}

	private PrescriptionRequestModel generatePrescriptionRequestModel() {
		DiagnosisCodes primaryCode = new DiagnosisCodes("F31.6", "PRIMARY");
		DiagnosisCodes secondaryCode = new DiagnosisCodes("R25.2", "SECONDARY");
		List<DiagnosisCodes> diagnosisCodes = new ArrayList<>();
		diagnosisCodes.add(secondaryCode);
		diagnosisCodes.add(primaryCode);
		DrugList drugList1 = new DrugList("51-277-98", "package", new BigDecimal(3), 3.0, "test", "5", "test", "test",
				"21/12/2019", "01/10/2025", drugListId);
		DrugList drugList2 = new DrugList("23-883-19", "package", new BigDecimal(3), 3.0, "test", "5", "test", "test",
				"22/12/2019", "02/10/2025", drugListId);
		List<DrugList> drugLists = new ArrayList<DrugList>();
		drugLists.add(drugList1);
		drugLists.add(drugList2);
		List<DrugListModel> policyConsumptionDrugList = new ArrayList<>();
		DrugListModel drugListModel = new DrugListModel("51-277-98", new BigDecimal(50), null, null, null, null);
		policyConsumptionDrugList.add(drugListModel);
		PrescriptionRequestModel prescriptionRequestModel = new PrescriptionRequestModel(payerId, "1233", "123",
				"05/01/2023", "5666", "MALE", new BigDecimal(50.5), new BigDecimal(100.10), "test", "Dr. test", "test",
				diagnosisCodes, drugLists, memberName, "inpatient", policyConsumptionDrugList);
		return prescriptionRequestModel;
	}

	private TransactionLog generateTransactionLogWithRequest() {
		return new TransactionLog(2L, requestId, 51.11, RequestType.NEW.name(), payerId, providerId, "Received",
				currentYear + "-1", null, null, timestamp, timestamp, null, null, null);
	}

	private DssResponse generateDssResponse(String status) {
		return new DssResponse(requestId, status,
				status.equalsIgnoreCase(RequestStatusType.APPROVED.value()) ? null : generateDssRejectionErrors(),
				generateResult(status), 200, null, 1L);
	}

	private DssResponse generateDssResponseForApproval() {
		return new DssResponse(requestId, RequestStatusType.PARTIAL_APPROVED.value(),
				generateDssRejectionErrorsForApproval(), generateDssResultForApproval(), HttpStatus.OK.value(), null,
				1L);
	}

	private List<String> generateDssRejectionErrorsForApproval() {
		List<String> errors = new ArrayList<>();
		errors.add(statusDesc);
		return errors;
	}

	private List<String> generateDssRejectionErrors() {
		List<String> errors = new ArrayList<>();
		errors.add("CPREF390");
		return errors;
	}

	private List<Result> generateDssResultForApproval() {
		List<Result> results = new ArrayList<>();
		Error error = new Error("CPREF390", statusDesc);
		List<Error> errorList = new ArrayList<>();
		errorList.add(error);
		results.add(new Result("51-277-98", new BigDecimal(3), new BigDecimal(40.5), "3",
				RequestStatusType.REJECTED.value(), errorList));
		results.add(new Result("23-883-19", new BigDecimal(3), new BigDecimal(40.5), "3",
				RequestStatusType.APPROVED.value(), null));
		return results;
	}

	private List<Result> generateResult(String status) {
		List<Result> results = new ArrayList<>();
		if (status.equalsIgnoreCase(RequestStatusType.APPROVED.value())) {
			results.add(new Result("69-892-19", new BigDecimal(3), new BigDecimal(40.5), "3",
					RequestStatusType.APPROVED.value(), null));
		} else {
			com.waseel.prescription.model.dss.Error error = new Error("CPREF390",
					"Refill Too Soon, last refilled on 12/12/2023 by Provider : testProvider for drug : 51-227-58");
			List<com.waseel.prescription.model.dss.Error> errorList = new ArrayList<>();
			errorList.add(error);
			Result result = new Result("69-892-19", new BigDecimal(3), new BigDecimal(40.5), "3",
					RequestStatusType.REJECTED.value(), errorList);
			results.add(result);
		}
		return results;
	}

	private PrescriptionRequest generatePrescriptionRequest(String status) {
		return new PrescriptionRequest(requestId, payerId, providerId, timestamp, timestamp, status,
				status.equalsIgnoreCase(RequestStatusType.APPROVED.value()) ? null
						: "Refill Too Soon, last refilled on 12/12/2023 by Provider : testProvider for drug : 51-227-58",
				ePrescriptionReferenceNumber, new BigDecimal(0), new BigDecimal(0), BenefitCaseType.INPATIENT.value(),
				currency, currency);
	}

	private List<ServiceInfo> generateServiceInfoForApproval() {
		List<ServiceInfo> list = new ArrayList<>();
		list.add(new ServiceInfo(1L, "51-277-98", "package", 318.00, new BigDecimal(3), new BigDecimal(31.0), "test",
				date, date, 8L, "test", "test", requestId));
		list.add(new ServiceInfo(2L, "23-883-19", "package", 318.00, new BigDecimal(3), new BigDecimal(31.0), "test",
				date, date, 8L, "test", "test", requestId));
		return list;
	}

	private List<ServiceResponseInfo> generateServiceResponseInfoForApproval() {
		List<ServiceResponseInfo> list = new ArrayList<>();
		list.add(new ServiceResponseInfo(1l, requestId, new BigDecimal(30.2), new BigDecimal(30.2), 15.3,
				new BigDecimal(10.2), new BigDecimal(30.2), ServiceStatus.REJECTED.name(), statusDesc, 1L));
		list.add(new ServiceResponseInfo(2l, requestId, new BigDecimal(30.2), new BigDecimal(30.2), 15.3,
				new BigDecimal(10.2), new BigDecimal(30.2), ServiceStatus.APPROVED.name(), null, 2L));
		return list;
	}

	private List<ServiceRejection> generateServiceRejectionsForApproval() {
		List<ServiceRejection> serviceRejections = new ArrayList<ServiceRejection>();
		serviceRejections.add(new ServiceRejection(1L, "51-227-58", "CPREF390", statusDesc, requestId, 198L));
		return serviceRejections;
	}

	private ServiceInfo generateServiceInfoEntity() {
		return new ServiceInfo(1L, "51-277-98", "package", 318.00, new BigDecimal(3), new BigDecimal(31.0), "test",
				date, date, 8L, "test", "test", requestId);
	}

	private ServiceResponseInfo generateServiceResponseInfoEntity(String status) {
		return new ServiceResponseInfo(1l, requestId, new BigDecimal(30.2), new BigDecimal(30.2), 15.3,
				new BigDecimal(10.2), new BigDecimal(30.2), status,
				status.equalsIgnoreCase(RequestStatusType.APPROVED.value()) ? null
						: "Refill Too Soon, last refilled on 12/12/2023 by Provider : testProvider for drug : 51-227-58",
				40L);
	}

	private boolean getResponseStatus(PrescriptionResponseModel prescriptionResponseModel, String status) {
		return null != prescriptionResponseModel && !prescriptionResponseModel.getRequestId().isEmpty()
				&& prescriptionResponseModel.getStatus().equalsIgnoreCase(status) ? true : false;
	}

	private List<ServiceRejection> generateServiceRejections() {
		List<ServiceRejection> serviceRejections = new ArrayList<ServiceRejection>();
		serviceRejections.add(new ServiceRejection(1L, "31-277-98", "CPREF390",
				"Refill Too Soon, last refilled on Fri Nov 11 2022 by Provider :  for drug : 31-277-98", requestId,
				198L));
		return serviceRejections;
	}

	private MethodArgumentNotValidException getMethodArgumentNotValidException() {
		final Method method = new Object() {
		}.getClass().getEnclosingMethod();
		final MethodParameter parameter = Mockito.mock(MethodParameter.class);
		Mockito.when(parameter.getMethod()).thenReturn(method);
		final BindingResult bindingResult = Mockito.mock(BindingResult.class);
		bindingResult.rejectValue("payerId", "payerId should not be null or empty");
		bindingResult.getAllErrors();
		final MethodArgumentNotValidException exception = new MethodArgumentNotValidException(parameter, bindingResult);
		Mockito.when(bindingResult.getAllErrors()).thenReturn(Lists.newArrayList());
		return exception;
	}

	private ContentCachingRequestWrapper getContentCachingRequestWrapper() {
		ContentCachingRequestWrapper cachingRequestWrapper = null;
		try {
			MockHttpServletRequest hRequest = new MockHttpServletRequest();
			String req = mapper.writeValueAsString(prescriptionRequestModel);
			hRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
			hRequest.setContent(req.getBytes(StandardCharsets.UTF_8.name()));
			cachingRequestWrapper = new ContentCachingRequestWrapper(hRequest);
			cachingRequestWrapper.setRequest(hRequest);
			FileCopyUtils.copyToByteArray(cachingRequestWrapper.getInputStream());
			return cachingRequestWrapper;
		} catch (JsonProcessingException e) {
			log.error("", e);
		} catch (UnsupportedEncodingException e) {
			log.error("", e);
		} catch (IOException e) {
			log.error("", e);
		}
		return cachingRequestWrapper;
	}

	private InvalidPrescriptionRequest generateInvalidPrescriptionRequest() {
		return new InvalidPrescriptionRequest(1L, requestId, ePrescriptionReferenceNumber, timestamp, timestamp, null,
				null, null, 0, null, payerId, providerId);
	}

	public void generateMockUserInfo() {
		Map<String, Object> details = new HashMap<String, Object>();
		details.put("accId", providerId);
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
				eligibilityReferenceNumber, requestId, HttpStatus.OK.value());
	}

	private PolicyResponseModel generatePolicyResponseModel(String value) {
		List<DrugListModel> policyConsumptiondrugList = new ArrayList<>();
		DrugListModel drug = new DrugListModel("45-56-78", new BigDecimal("100"), null, null, null, null);
		policyConsumptiondrugList.add(drug);
		return new PolicyResponseModel("APPROVED", "APPROVED", "20", "100", "", "",
				String.valueOf(HttpStatus.OK.value()), "147954268", "789465", "", new BigDecimal("15000"), "SAR", "SAR",
				"APPROVED", "100", "123456789", policyConsumptiondrugList, currency, currency);
	}

	private Optional<MemberInfo> generateMemberInfo() {
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setDob(date);
		memberInfo.setGender(currentYear);
		memberInfo.setHeight(12.3D);
		memberInfo.setId(1L);
		memberInfo.setIdNumber(1234567890L);
		memberInfo.setMemberId("4894561");
		memberInfo.setMemberName(memberName);
		memberInfo.setPolicyNumber(currentYear);
		memberInfo.setRequestId(requestId);
		memberInfo.setWeight(12.3D);
		return Optional.of(memberInfo);
	}

	private Department generateDepartment() {
		return new Department(1L, "department");
	}

	private PhysicianInfo generatePhysicianInfo() {
		return new PhysicianInfo(1L, 801L, "78654321", "Dr. Khan", null);
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

	private SmsNotificationResponseModel generateSmsNotificationResponse() {
		return new SmsNotificationResponseModel("SUCCESS", "Sms sent", "42000348806924", "2022-09-06 14:06:25.979");
	}

	private EmailNotificationResponseModel generateEmailNotificationResponse() {
		return new EmailNotificationResponseModel("SUCCESS", "Email sent");
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

	private MappingPayerId generateMappingPayerId() {
		MappingPayerId mappingPayerId = new MappingPayerId(mappingId, DssPayerTransactionType.PRESCRIPTION.value(),
				payerId, mappedPayerId, true);
		return mappingPayerId;
	}

	private BenefitCodePhyscSpecAssc populateBenefitCodePhyscSpecAssc() {
		BenefitCodePhyscSpecAssc benefitCodePhyscSpecAssc = new BenefitCodePhyscSpecAssc(pkid, BigDecimal.ONE, pkid,
				true);
		benefitCodePhyscSpecAssc.setBenefitCodes(new BenefitCodes(pkid, benefitCode));
		return benefitCodePhyscSpecAssc;
	}

	private Speciality populateSpeciality() {
		return new Speciality(BigDecimal.ONE, specialityName, date, Boolean.FALSE);
	}
}
