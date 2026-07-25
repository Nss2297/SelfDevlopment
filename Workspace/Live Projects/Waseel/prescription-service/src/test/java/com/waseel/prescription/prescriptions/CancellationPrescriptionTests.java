package com.waseel.prescription.prescriptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import com.waseel.prescription.clients.PBMPayerApisServiceClient;
import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.cancellation.PrescriptionCancellationRequestModel;
import com.waseel.prescription.model.cancellation.PrescriptionCancellationResponseModel;
import com.waseel.prescription.model.dss.DssResponse;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.DssPayerTransactionType;
import com.waseel.prescription.model.enums.GenderType;
import com.waseel.prescription.model.enums.PolicyConsumptionStatus;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.enums.SourceType;
import com.waseel.prescription.model.enums.TransactionStatusType;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionResponseModel;
import com.waseel.prescription.model.pbmpayerapis.MemberDemographicDataResponseModel;
import com.waseel.prescription.model.pbmpayerapis.PolicyInformationModel;
import com.waseel.prescription.model.policyconsumption.DrugListModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.persist.businessrules.BenefitCodePhyscSpecAssc;
import com.waseel.prescription.persist.businessrules.BenefitCodes;
import com.waseel.prescription.persist.businessrules.Department;
import com.waseel.prescription.persist.businessrules.PhysicianInfo;
import com.waseel.prescription.persist.businessrules.Speciality;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.MemberPolicyUsage;
import com.waseel.prescription.persist.prescriptionservice.Physician;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionApprovalAssc;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.businessrules.BenefitCodePhyscSpecAsscRepository;
import com.waseel.prescription.repository.businessrules.DeptSpecPhyscAsscRepository;
import com.waseel.prescription.repository.businessrules.PhysicianInfoRepository;
import com.waseel.prescription.repository.businessrules.SpecialityRepository;
import com.waseel.prescription.repository.hira.SwitchAccountRepository;
import com.waseel.prescription.repository.prescriptionservice.DiagnosisRepository;
import com.waseel.prescription.repository.prescriptionservice.MappingPayerIdRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberPolicyUsageRepository;
import com.waseel.prescription.repository.prescriptionservice.PhysicianRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionApprovalAsscRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.TransactionLogRepository;
import com.waseel.prescription.service.clienthandler.PbmPayerApisRestHandler;
import com.waseel.prescription.service.clienthandler.RestHandler;
import com.waseel.prescription.service.management.BusinessRuleService;
import com.waseel.prescription.service.prescriptions.CancellationPrescriptionService;
import com.waseel.prescription.service.prescriptions.PrescriptionService;
import com.waseel.prescription.service.validation.TechnicalValidationService;
import com.waseel.prescription.util.UserInfoUtil;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles({ "test" })
class CancellationPrescriptionTests {

	private static final Logger log = LoggerFactory.getLogger(TechnicalValidationService.class);

	@Autowired
	private CancellationPrescriptionService cancellationPrescriptionService;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private TechnicalValidationService technicalValidationService;

	@MockBean
	PrescriptionRequestRepository prescriptionRequestRepository;

	@MockBean
	ServiceResponseInfoRepository serviceResponseInfoRepository;

	@MockBean
	public TransactionLogRepository transactionLogRepository;

	@Autowired
	private PrescriptionService prescriptionService;

	@MockBean
	private BusinessRuleService businessRuleService;

	@MockBean
	private MemberInfoRepository memberInfoRepository;

	@MockBean
	private MemberPolicyUsageRepository memberPolicyUsageRepository;

	@MockBean
	private RestHandler restHandler;

	@MockBean
	private PrescriptionApprovalAsscRepository prescriptionApprovalAsscRepository;

	@MockBean
	private PBMPayerApisServiceClient pbmPayerApisServiceClient;

	@MockBean
	private ServiceInfoRepository serviceInfoRepository;

	@MockBean
	private DiagnosisRepository diagnosisRepository;

	@MockBean
	private PhysicianRepository physicianRepository;

	@MockBean
	private PhysicianInfoRepository physicianInfoRepository;

	@MockBean
	private DeptSpecPhyscAsscRepository deptSpecPhyscAsscRepository;

	@MockBean
	private PbmPayerApisRestHandler pbmPayerApisRestHandler;

	@MockBean
	private SwitchAccountRepository switchAccountRepository;

	@MockBean
	private MappingPayerIdRepository mappingPayerIdRepository;

	@MockBean
	private BenefitCodePhyscSpecAsscRepository benefitCodePhyscSpecAsscRepository;
	
	@MockBean
	private SpecialityRepository specialityRepository;

	private String payerId = "102";
	private String providerId = "12";
	private String requestId = "0fb78ee7-9e62-4d9e-aee7-18f099e126f1";
	private String currentYear = Year.now().toString();
	String ePrescriptionReferenceNumber = currentYear + "-1";
	private Timestamp timestamp = (new Timestamp(Calendar.getInstance().getTimeInMillis()));
	String msgNotAllowCancel = "Not allowed to do Cancellation because ";
	private String msgCancelledBefore = msgNotAllowCancel + "this request is already Cancelled before.";
	private String msgRejectedRequest = msgNotAllowCancel + "Service code(s) already Rejected.";
	private String msgDispensedRequest = msgNotAllowCancel + "this request is already Dispensed.";
	private String msgCancelledSuccess = "Transaction cancelled successfully.";
	private String msgInvalid = "Invalid";
	private String msgCancelled = "Cancelled";
	private Date date = new Date();
	private static final String memberName = "Salim";
	private final String benefitCode = "DENTAL";
	private String physcianName = "Dr. test";
	private PrescriptionCancellationRequestModel cancellationRequestModel = null;
	private final MockHttpServletRequest request = new MockHttpServletRequest();
	private ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
	private TransactionLog transactionLog;
	private PrescriptionRequest prescriptionRequest;
	private EPrescriptionResponseModel ePrescriptionResponseModel;
	private static final String currency = Currency.SAR.value();
	private static final String classCode = "1-VVIP-Network Gold";
	private static final String mappedPayerId = "102_" + DssPayerTransactionType.PRESCRIPTION.value();
	private final String memberNationality = "Saudi Arabia";
	private BenefitCodePhyscSpecAssc benefitCodePhyscSpecAssc = null;
	private static final Long pkid = 1L;
	private Speciality speciality = null;
	private static final String specialityName = "Anesthesia Cardiology";

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
	}

	@BeforeEach
	public void setUpData() {
		cancellationRequestModel = generatePrescriptionCancellationRequestModel();
		Mockito.when(transactionLogRepository.generateEPrescriptionReferenceNumber())
				.thenReturn(ePrescriptionReferenceNumber);
		Mockito.when(transactionLogRepository.save(Mockito.any())).thenReturn(generateTransactionLogWithRequest());
		cachingRequestWrapper = getContentCachingRequestWrapper();
		Mockito.when(pbmPayerApisRestHandler.sendRequestToGetMemberDemographicData(Mockito.any()))
				.thenReturn(generateMemberDemographicDataResponseModel());
		benefitCodePhyscSpecAssc = populateBenefitCodePhyscSpecAssc();
		speciality = populateSpeciality();
	}

	@Test
	@DisplayName("Already cancelled request")
	void isCancelledRequest() {
		PrescriptionCancellationResponseModel responseModel;
		try {
			responseModel = cancellationPrescriptionService.managePrescriptionCancellationRequest(
					ePrescriptionReferenceNumber,
					generatePrescriptionRequest(true, RequestStatusType.APPROVED.value(), false), cachingRequestWrapper,
					false);
			assertNotNull(responseModel);
			assertEquals(responseModel.getStatusDescription(), msgCancelledBefore);
			assertEquals(responseModel.getStatus(), msgInvalid);
		} catch (PrescriptionException e) {
			e.printStackTrace();
			assertEquals("Not allowed to do Cancellation with this request.",
					e.getInvalidResponse().getStatusDescription());
		}
	}

	@Test
	@DisplayName("Rejected request")
	void isRejectedRequest() {
		PrescriptionCancellationResponseModel responseModel;
		try {
			responseModel = cancellationPrescriptionService.managePrescriptionCancellationRequest(
					ePrescriptionReferenceNumber,
					generatePrescriptionRequest(false, RequestStatusType.REJECTED.value(), false),
					cachingRequestWrapper, false);
			assertNotNull(responseModel);
			assertEquals(responseModel.getStatusDescription(), msgRejectedRequest);
			assertEquals(responseModel.getStatus(), msgInvalid);
		} catch (PrescriptionException e) {
			e.printStackTrace();
			assertEquals("Not allowed to do Cancellation with this request.",
					e.getInvalidResponse().getStatusDescription());
		}
	}

	@Test
	@DisplayName("Dispensed request")
	void isDispensedRequest() {
		Mockito.when(prescriptionRequestRepository.save(Mockito.any()))
				.thenReturn(generatePrescriptionRequestEntity(RequestStatusType.DISPENSED.value()));
		PrescriptionCancellationResponseModel responseModel;
		try {
			responseModel = cancellationPrescriptionService.managePrescriptionCancellationRequest(
					ePrescriptionReferenceNumber,
					generatePrescriptionRequest(false, RequestStatusType.DISPENSED.value(), false),
					cachingRequestWrapper, false);
			assertNotNull(responseModel);
			assertEquals(responseModel.getStatusDescription(), msgDispensedRequest);
			assertEquals(responseModel.getStatus(), msgInvalid);
			assertEquals(false, responseModel.isCanCancel());
		} catch (PrescriptionException e) {
			e.printStackTrace();
			assertEquals("Not allowed to do Cancellation with this request.",
					e.getInvalidResponse().getStatusDescription());
		}
	}

	@Test
	@DisplayName("Successfully Cancelled Prescription")
	void successfullyCancelPrescriptionTest() {
		List<ServiceResponseInfo> list = generateServiceResponseInfo();
		Mockito.when(serviceResponseInfoRepository.findByRequestId(Mockito.any())).thenReturn(list);
		Mockito.when(serviceResponseInfoRepository.saveAll(Mockito.any()))
				.thenReturn(generateServiceResponseInfoEntity(list));
		Mockito.when(prescriptionRequestRepository.save(Mockito.any()))
				.thenReturn(generatePrescriptionRequestEntity(RequestStatusType.REJECTED.value()));
		PrescriptionCancellationResponseModel responseModel;
		try {
			responseModel = cancellationPrescriptionService.managePrescriptionCancellationRequest(
					ePrescriptionReferenceNumber,
					generatePrescriptionRequest(false, RequestStatusType.APPROVED.value(), true), cachingRequestWrapper,
					false);
			assertNotNull(responseModel);
			assertEquals(responseModel.getStatusDescription(), msgCancelledSuccess);
			assertEquals(responseModel.getStatus(), msgCancelled);
		} catch (PrescriptionException e) {
			e.printStackTrace();
			assertEquals("Not allowed to do Cancellation with this request.",
					e.getInvalidResponse().getStatusDescription());
		}
	}

	@Test
	@DisplayName("Invalid response[400].")
	void cancelServiceTestsBeanValidationCase() {
		try {
			PrescriptionResponseModel invalidResponse = technicalValidationService
					.populateInvalidPrescriptionResponse(getMethodArgumentNotValidException(), cachingRequestWrapper);
			assertNotNull(invalidResponse);
			assertEquals("Invalid", invalidResponse.getStatus());
			assertEquals(HttpStatus.BAD_REQUEST.value(), invalidResponse.getHttpStatusCode());
			assertEquals(ePrescriptionReferenceNumber, invalidResponse.getePrescriptionReferenceNumber());
		} catch (Exception e) {
			log.error("Exception:-", e);
		}
	}

	@Test
	@DisplayName("Failed Response[500].")
	void cancelServiceTestsFailedValidationCase() {
		try {
			PrescriptionResponseModel invalidResponse = technicalValidationService
					.populateFailedPrescriptionResponse(cachingRequestWrapper);
			assertNotNull(invalidResponse);
			assertEquals("Failed", invalidResponse.getStatus());
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), invalidResponse.getHttpStatusCode());
			assertEquals(ePrescriptionReferenceNumber, invalidResponse.getePrescriptionReferenceNumber());
			assertThat(invalidResponse.getHttpStatusDescription()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.name());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("Success Response from EPrescription Approval")
	void successResponseFromEPrescriptionApproval() {
		try {
			ePrescriptionResponseModel = generateSuccessEPrescriptionResponseModel();
			mockDataForPolicyCheckAndValidationAndDss();
			mockDataForEPrescriptionApproval();
			PrescriptionCancellationResponseModel responseAfterApproval = prescriptionService
					.managePrescriptionCancellationRequest(cancellationRequestModel, cachingRequestWrapper,
							SourceType.INTEGRATION.value());
			assertNotNull(responseAfterApproval);
			assertEquals(msgCancelledSuccess, responseAfterApproval.getStatusDescription());
			assertEquals(msgCancelled, responseAfterApproval.getStatus());
			assertEquals(ePrescriptionReferenceNumber, responseAfterApproval.getePrescriptionReferenceNumber());
		} catch (PrescriptionException e) {
			e.printStackTrace();
			assertEquals("Not allowed to do Cancellation with this request.",
					e.getInvalidResponse().getStatusDescription());
		}
	}

	@Test
	@DisplayName("Failed Response from EPrescription Approval")
	void failedResponseFromEPrescriptionApproval() {
		Mockito.when(physicianRepository.findByRequestId(Mockito.any()))
				.thenReturn(Optional.of(generatePhysicianModel()));
		Mockito.when(physicianInfoRepository.findByRegistrationNumber(Mockito.any()))
				.thenReturn(Optional.of(generatePhysicianInfoModle()));
		Mockito.when(deptSpecPhyscAsscRepository.findByPhysicianInfoId(Mockito.any()))
				.thenReturn(Optional.of(generateDepartmentModle()));
		Mockito.when(specialityRepository.findBySpecialityNameAndIsDeleted(Mockito.any(), Mockito.anyBoolean()))
		.thenReturn(Optional.of(speciality));
		try {
			ePrescriptionResponseModel = generateFailedEPrescriptionResponseModel();
			mockDataForPolicyCheckAndValidationAndDss();
			mockDataForEPrescriptionApproval();
			PrescriptionCancellationResponseModel responseAfterApproval = prescriptionService
					.managePrescriptionCancellationRequest(cancellationRequestModel, cachingRequestWrapper,
							SourceType.INTEGRATION.value());
			assertNotNull(responseAfterApproval);
			assertEquals("Not able to call Tawuniya server", responseAfterApproval.getStatusDescription());
			assertEquals("FAILED", responseAfterApproval.getStatus());
			assertEquals(ePrescriptionReferenceNumber, responseAfterApproval.getePrescriptionReferenceNumber());
		} catch (PrescriptionException e) {
			e.printStackTrace();
			assertEquals("Not allowed to do Cancellation with this request.",
					e.getInvalidResponse().getStatusDescription());
		}
	}

	private EPrescriptionResponseModel generateSuccessEPrescriptionResponseModel() {
		return new EPrescriptionResponseModel(ePrescriptionReferenceNumber, "101222122", null, null, null);
	}

	private EPrescriptionResponseModel generateFailedEPrescriptionResponseModel() {
		return new EPrescriptionResponseModel(null, null, "FAILED", "Not able to call Tawuniya server", null);
	}

	private void mockDataForEPrescriptionApproval() {
		Mockito.when(pbmPayerApisRestHandler.sendRequestToGetEPrescriptionApproval(Mockito.any()))
				.thenReturn(ePrescriptionResponseModel);
		Mockito.when(prescriptionApprovalAsscRepository.save(Mockito.any())).thenReturn(new PrescriptionApprovalAssc());
		Mockito.when(pbmPayerApisServiceClient.getEPrescriptionApproval(Mockito.any()))
				.thenReturn(ResponseEntity.of(Optional.of(ePrescriptionResponseModel)));
		Mockito.when(diagnosisRepository.findByRequestIdAndIsNotDeleted(requestId)).thenReturn(new ArrayList<>());
		Mockito.when(serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId)).thenReturn(new ArrayList<>());
		Mockito.when(physicianRepository.findByRequestId(requestId)).thenReturn(Optional.of(generatePhysicianModel()));
	}

	private void mockDataForPolicyCheckAndValidationAndDss() {
		transactionLog = generateTransactionLog(51.13, RequestType.CANCELLATION.name());
		prescriptionRequest = generatePrescriptionRequest(false, RequestStatusType.APPROVED.value(), true);
		Mockito.when(transactionLogRepository.save(Mockito.any())).thenReturn(transactionLog);
		Mockito.when(transactionLogRepository.findByePrescriptionReferenceNumberWithValidStatus(Mockito.any()))
				.thenReturn(Optional.of(transactionLog));
		Mockito.when(transactionLogRepository.generateEPrescriptionReferenceNumber())
				.thenReturn(ePrescriptionReferenceNumber);
		Mockito.when(prescriptionRequestRepository.save(Mockito.any()))
				.thenReturn(generatePrescriptionRequest(false, RequestStatusType.APPROVED.value(), true));
		Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(Mockito.any()))
				.thenReturn(Optional.of(prescriptionRequest));
		Mockito.when(prescriptionRequestRepository.findByRequestId(Mockito.any()))
				.thenReturn(Optional.of(prescriptionRequest));
		Mockito.when(prescriptionRequestRepository.save(Mockito.any()))
				.thenReturn(generatePrescriptionRequestEntity(RequestStatusType.REJECTED.value()));
		Mockito.when(memberInfoRepository.save(Mockito.any())).thenReturn(new MemberInfo());
		Mockito.when(memberInfoRepository.findByRequestId(Mockito.any())).thenReturn(generateMemberInfo());
		Mockito.when(memberPolicyUsageRepository.save(Mockito.any())).thenReturn(generateMemberPolicyUsage());
		Mockito.when(businessRuleService.policyConsumptionCheckForCancellation(Mockito.any(), Mockito.any()))
				.thenReturn(generatePolicyResponseModel(PolicyConsumptionStatus.APPROVED.getValue()));
		Mockito.when(restHandler.handleCancelPrescriptionRequest(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(generateDssResponse(RequestStatusType.REJECTED.value()));
		List<ServiceResponseInfo> list = generateServiceResponseInfo();
		Mockito.when(serviceResponseInfoRepository.findByRequestId(Mockito.any())).thenReturn(list);
		Mockito.when(serviceResponseInfoRepository.saveAll(Mockito.any()))
				.thenReturn(generateServiceResponseInfoEntity(list));
		Mockito.when(
				benefitCodePhyscSpecAsscRepository.findBySpecialityIdAndIsEnabled(Mockito.any(), Mockito.anyBoolean()))
				.thenReturn(Optional.of(benefitCodePhyscSpecAssc));
	}

	private DssResponse generateDssResponse(String status) {
		DssResponse response = new DssResponse();
		response.setCode(HttpStatus.OK.value());
		response.setMessage(msgCancelledSuccess);
		return response;
	}

	private MemberPolicyUsage generateMemberPolicyUsage() {
		MemberPolicyUsage memberPolicyUsage = new MemberPolicyUsage(payerId, providerId, "1234568",
				Long.valueOf(1234567890), "789456", "7894195", "benefit", new BigDecimal(0), "SAR", new BigDecimal(0),
				"SAR", ePrescriptionReferenceNumber, "APPROVED");
		return memberPolicyUsage;
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

	private PolicyResponseModel generatePolicyResponseModel(String value) {
		List<DrugListModel> drugList = new ArrayList<>();
		DrugListModel drugListModel = new DrugListModel("45-78-56", new BigDecimal("100"), null, null, null, null);
		drugList.add(drugListModel);
		return new PolicyResponseModel("APPROVED", "APPROVED", "20", "100", "", "", String.valueOf(HttpStatus.OK.value()), "147954268",
				"789465", "", new BigDecimal("15000"), "SAR", "SAR", "APPROVED", "100", "123456789", drugList, currency,
				currency);
	}

	private TransactionLog generateTransactionLog(Double transactionID, String transactionType) {
		return new TransactionLog(1L, requestId, null != transactionID ? transactionID : 51.12,
				null != transactionType && !transactionType.isEmpty() ? transactionType : RequestType.FOLLOWUP.name(),
				payerId, providerId, TransactionStatusType.RECEIVED.value(), ePrescriptionReferenceNumber, null, null,
				timestamp, timestamp, null, null, null);
	}

	private PrescriptionRequest generatePrescriptionRequest(boolean isCancelled, String statusCode, boolean canCancel) {
		PrescriptionRequest prescriptionRequest = new PrescriptionRequest(requestId, mappedPayerId, providerId,
				timestamp, timestamp, statusCode, null, canCancel, true, isCancelled);
		prescriptionRequest.setMemberInfo(new MemberInfo("Test", 12l, "Test", new Date(), 55.1, 5.1, "Male", requestId,
				"Test", memberNationality));
		return prescriptionRequest;
	}

	private List<ServiceResponseInfo> generateServiceResponseInfo() {
		List<ServiceResponseInfo> list = new ArrayList<>();
		ServiceResponseInfo serviceResponseInfo = new ServiceResponseInfo(1l, requestId, new BigDecimal(636.0),
				new BigDecimal(636.0), 15.3, new BigDecimal(10.2), new BigDecimal(636.0),
				RequestStatusType.APPROVED.value(), null, 40L);
		list.add(serviceResponseInfo);
		return list;

	}

	private PrescriptionRequest generatePrescriptionRequestEntity(String statusCode) {
		return new PrescriptionRequest(requestId, payerId, providerId, timestamp, timestamp, statusCode, null, false,
				true, false);
	}

	private List<ServiceResponseInfo> generateServiceResponseInfoEntity(List<ServiceResponseInfo> list) {
		list.forEach(serviceResponseInfo -> serviceResponseInfo.setStatus(ServiceStatus.REJECTED.name()));
		return list;
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
			String req = this.mapper.writeValueAsString(cancellationRequestModel);
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

	private PrescriptionCancellationRequestModel generatePrescriptionCancellationRequestModel() {
		return new PrescriptionCancellationRequestModel(payerId, ePrescriptionReferenceNumber);
	}

	private TransactionLog generateTransactionLogWithRequest() {
		return new TransactionLog(2L, requestId, 51.11, RequestType.NEW.name(), payerId, providerId, "Received",
				currentYear + "-1", null, null, timestamp, timestamp, null, null, null);
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

	private Department generateDepartmentModle() {
		return new Department(1L, benefitCode, date, false);
	}

	private PhysicianInfo generatePhysicianInfoModle() {
		return new PhysicianInfo(1L, Long.valueOf(providerId), "4589", physcianName);
	}

	private Physician generatePhysicianModel() {
		return new Physician(1L, "33", requestId, physcianName, "SPECIALIST", "Adult ENT");
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
