package com.waseel.prescription.prescriptions;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.prescription.clients.AuthenticationServiceClient;
import com.waseel.prescription.clients.PbmNotificationServiceClient;
import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.authentication.JwtResponse;
import com.waseel.prescription.model.authentication.OneTimeAccessTokenRequest;
import com.waseel.prescription.model.common.CommonPrescriptionUpdationResponseModel;
import com.waseel.prescription.model.enums.BenefitCaseType;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.FrequencyType;
import com.waseel.prescription.model.enums.GenderType;
import com.waseel.prescription.model.enums.PhysicianCategory;
import com.waseel.prescription.model.enums.PolicyConsumptionStatus;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.enums.UnitType;
import com.waseel.prescription.model.modifydecision.InvalidResPrescriptionUpdationService;
import com.waseel.prescription.model.modifydecision.ModifyDecisionDrugList;
import com.waseel.prescription.model.modifydecision.ModifyDecisionRequestModel;
import com.waseel.prescription.model.modifydecision.ModifyDecisionResponseModel;
import com.waseel.prescription.model.notification.EmailNotificationResponseModel;
import com.waseel.prescription.model.notification.SmsNotificationResponseModel;
import com.waseel.prescription.model.pbmpayerapis.MemberDemographicDataResponseModel;
import com.waseel.prescription.model.pbmpayerapis.PolicyInformationModel;
import com.waseel.prescription.model.policyconsumption.DrugListModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.model.prescription.ServiceDetailsModel;
import com.waseel.prescription.persist.businessrules.Department;
import com.waseel.prescription.persist.businessrules.PhysicianInfo;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.MemberPolicyUsage;
import com.waseel.prescription.persist.prescriptionservice.Physician;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.persist.prescriptionservice.ServiceRejection;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.repository.businessrules.DeptSpecPhyscAsscRepository;
import com.waseel.prescription.repository.businessrules.PhysicianInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberPolicyUsageRepository;
import com.waseel.prescription.repository.prescriptionservice.PhysicianRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;
import com.waseel.prescription.service.clienthandler.PbmPayerApisRestHandler;
import com.waseel.prescription.service.management.BusinessRuleService;
import com.waseel.prescription.service.modifydecision.ModifyDecisionDMLService;
import com.waseel.prescription.service.modifydecision.ModifyDecisionService;
import com.waseel.prescription.util.SourceTypeUtil;

@SpringBootTest
@ActiveProfiles("test")
class ModifyDecisionPrescriptionTests {
	@Autowired
	private ModifyDecisionService modifyDecisionService;
	@Autowired
	private InvalidResPrescriptionUpdationService invalidResPrescriptionUpdationService;
	@MockBean
	private PrescriptionRequestRepository prescriptionRequestRepository;
	@MockBean
	private ServiceInfoRepository serviceInfoRepository;
	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private ServiceRejectionRepository serviceRejectionRepository;
	@MockBean
	private ServiceResponseInfoRepository serviceResponseInfoRepository;
	@MockBean
	private PbmNotificationServiceClient notificationServiceClient;
	@MockBean
	private PbmPayerApisRestHandler pbmPayerApisRestHandler;
	@MockBean
	private AuthenticationServiceClient authenticationServiceClient;
	@MockBean
	private MemberInfoRepository memberInfoRepository;
	@MockBean
	private PhysicianInfoRepository physicianInfoRepository;
	@MockBean
	private DeptSpecPhyscAsscRepository deptSpecPhyscAsscRepository;
	@MockBean
	private PhysicianRepository physicianRepository;
	@MockBean
	private BusinessRuleService businessRuleService;
	@MockBean
	private MemberPolicyUsageRepository memberPolicyUsageRepository;
	@Mock
	private ContentCachingRequestWrapper requestWrapper;
	@Mock
	private ModifyDecisionDMLService modifyDecisionDMLService;

	private ModifyDecisionRequestModel modifyDecisionRequestModel;
	private PrescriptionRequest prescriptionRequest;
	private List<ServiceInfo> serviceInfoList;
	private ServiceInfo serviceInfo;
	private ServiceResponseInfo serviceResponseInfo;
	List<ServiceDetailsModel> serviceDetails;
	private List<ServiceRejection> serviceRejections;
	private Date date = new Date();
	private String ePrescriptionRefNum = "2023-01";
	private String payerId = "102";
	private String requestId = "8890e048-a1ba-44c7-b0c7-86393cc5773b";
	private String strInvalid = "INVALID";
	private String strFailed = "FAILED";
	private String statusDesc = "46-172-05 is an expensive drug - this request requires a manual review from the payer.";
	private String drugCode1 = "46-172-05";
	private String drugCode2 = "123-277-02";
	private String idNumber = "1234567893";
	private static final String currency = Currency.SAR.value();
	private static final String classCode = "1-VVIP-Network Gold";
	private static final String scientificCode1 = "7000000226-75-100000073649";
	private static final String scientificCode2 = "7000000226-75-100000073650";
	private final String memberNationality = "Saudi Arabia";

	@BeforeEach
	public void setUpCommonData() {
		generateMockUserInfo();
		modifyDecisionRequestModel = generateModifyDecisionRequestModel();
		prescriptionRequest = generatePrescriptionRequest();
		serviceInfoList = generateListOfServiceInfo();
		requestWrapper = getContentCachingRequestWrapper();
		serviceDetails = generateServiceDetails();
		prescriptionRequest.setMemberInfo(new MemberInfo("Test", 12l, "Test", new Date(), 55.1, 5.1, "Male", requestId,
				"Test", memberNationality));
		Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(prescriptionRequest);
		Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(ePrescriptionRefNum))
				.thenReturn(Optional.of(prescriptionRequest));
		Mockito.when(serviceInfoRepository.saveAll(Mockito.any())).thenReturn(serviceInfoList);
		Mockito.when(serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId)).thenReturn(serviceInfoList);
	}

	@Test
	@Rollback(false)
	@DisplayName("Success Response")
	void successResponseTest() {
		try {
			serviceInfo = generateServiceInfo();
			serviceResponseInfo = generateServiceResponseInfo();
			serviceRejections = generateServiceRejectionList();
			Mockito.when(serviceResponseInfoRepository.getIsNotDeletedDrugAndRequestId(Mockito.any(), Mockito.any()))
					.thenReturn(serviceDetails);
			Mockito.when(serviceInfoRepository.save(Mockito.any())).thenReturn(serviceInfo);
			Mockito.when(serviceResponseInfoRepository.save(Mockito.any())).thenReturn(serviceResponseInfo);
			Mockito.when(serviceRejectionRepository.saveAll(Mockito.any())).thenReturn(serviceRejections);
			Mockito.when(
					serviceInfoRepository.findByRequestIdAndDrugCodeAndIsDeleted(requestId, ePrescriptionRefNum, false))
					.thenReturn(Optional.of(serviceInfo));
			Mockito.when(serviceResponseInfoRepository.findByRequestIdAndServiceID(requestId, serviceInfo.getId()))
					.thenReturn(Optional.of(serviceResponseInfo));
			Mockito.when(serviceRejectionRepository.findByRequestIdAndServiceResponseId(requestId,
					serviceResponseInfo.getId())).thenReturn(Optional.of(serviceRejections));
			Mockito.when(serviceResponseInfoRepository.findByIsNotDeletedDrugAndRequestId(requestId))
					.thenReturn(generateServiceResponseInfoList());
			emailSmsMock();
			mockPolicyConsumptionDetails();
			ModifyDecisionResponseModel response = modifyDecisionService.modifyDecisionByPayer(
					modifyDecisionRequestModel, ePrescriptionRefNum, payerId, requestWrapper,
					SourceTypeUtil.getSourceTypeBasedOnHeaderOrigin(""));
			assertThat(response.getePrescriptionReferenceNumber()).isNotNull();
			assertThat(response.getePrescriptionReferenceNumber()).isEqualTo(ePrescriptionRefNum);
			assertThat(prescriptionRequest.getStatusCode()).isNotEqualTo(RequestStatusType.PENDING.value());
		} catch (Exception e) {
			if (e instanceof PrescriptionException) {
				assertThat(e.getMessage()).isEqualTo("EPrescriptionReferenceNumber is not found or exists.");
			}
		}
	}

//	@Test
	@DisplayName("Invalid Response")
	void invalidResponseTest() {
		PrescriptionException exception = new PrescriptionException(
				"EPrescriptionReferenceNumber is not found or exists.");
		CommonPrescriptionUpdationResponseModel response = invalidResPrescriptionUpdationService
				.populateInvalidFailedResponse(exception, requestWrapper);
		assertThat(response.getePrescriptionReferenceNumber()).isNotNull();
		assertThat(response.getePrescriptionReferenceNumber()).isEqualTo(ePrescriptionRefNum);
		assertThat(response.getErrorCode()).isEqualTo(strInvalid);
		assertThat(response.getErrorDescription()).isEqualTo(exception.getMessage());
	}

//	@Test
	@DisplayName("Failed Response")
	void failedResponseTest() {
		CommonPrescriptionUpdationResponseModel response = invalidResPrescriptionUpdationService
				.populateInvalidFailedResponse(new Exception(), requestWrapper);
		assertThat(response.getePrescriptionReferenceNumber()).isNotNull();
		assertThat(response.getePrescriptionReferenceNumber()).isEqualTo(ePrescriptionRefNum);
		assertThat(response.getErrorCode()).isEqualTo(strFailed);
		assertThat(response.getErrorDescription()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.name());
	}

	private List<ServiceDetailsModel> generateServiceDetails() {
		List<ServiceDetailsModel> list = new ArrayList<>();
		list.add(new ServiceDetailsModel(drugCode2, ServiceStatus.APPROVED.name(), scientificCode1));
		list.add(new ServiceDetailsModel(drugCode1, ServiceStatus.REJECTED.name(), scientificCode2));
		return list;
	}

	private void emailSmsMock() {
		Mockito.when(pbmPayerApisRestHandler.sendRequestToGetMemberDemographicData(Mockito.any()))
				.thenReturn(generateMemberDemographicDataResponseModel());
		Mockito.when(
				authenticationServiceClient.generateAccessTokenForPatientUrl(generateOneTimeAccessTokenRequestModel(),
						"Basic cHJlc2NyaXB0aW9uLXNlcnZpY2U6cHJlc2NyaXB0aW9uLXNlcnZpY2Utc2VjcmV0"))
				.thenReturn(generateJwtResponse());
		Mockito.when(notificationServiceClient.sentNotificationToPatient(Mockito.any()))
				.thenReturn(ResponseEntity.ok(generateSmsNotificationResponse()));
		Mockito.when(notificationServiceClient.sendEmailNotification(Mockito.any()))
				.thenReturn(ResponseEntity.ok(generateEmailNotificationResponse()));
	}

	private Map<String, String> generatePathVariables() {
		Map<String, String> pathVariables = new HashMap<>();
		pathVariables.put("ePrescriptionReferenceNumber", ePrescriptionRefNum);
		pathVariables.put("payerId", payerId);
		return pathVariables;
	}

	private List<ServiceRejection> generateServiceRejectionList() {
		List<ServiceRejection> list = new ArrayList<>();
		list.add(new ServiceRejection(drugCode1, "BR_EXLHCDF03", statusDesc));
		return list;
	}

	private List<ServiceResponseInfo> generateServiceResponseInfoList() {
		List<ServiceResponseInfo> serviceResInfoList = new ArrayList<>();
		serviceResInfoList.add(new ServiceResponseInfo(1l, requestId, new BigDecimal(30.2), new BigDecimal(30.2), 15.3,
				new BigDecimal(10.2), new BigDecimal(30.2), ServiceStatus.APPROVED.name(), null, 1L));
		serviceResInfoList.add(new ServiceResponseInfo(2l, requestId, new BigDecimal(30.2), new BigDecimal(30.2), 15.3,
				new BigDecimal(10.2), new BigDecimal(30.2), ServiceStatus.REJECTED.name(), statusDesc, 2L));
		return serviceResInfoList;
	}

	private ServiceResponseInfo generateServiceResponseInfo() {
		return new ServiceResponseInfo(1l, requestId, new BigDecimal(30.2), new BigDecimal(30.2), 15.3,
				new BigDecimal(10.2), new BigDecimal(30.2), ServiceStatus.REJECTED.name(), statusDesc, 1L);
	}

	private ServiceInfo generateServiceInfo() {
		return new ServiceInfo(1L, "46-172-05", UnitType.PACKAGE.value(), 318.00, new BigDecimal(3),
				new BigDecimal(31.0), "test", date, date, 8L, "test", "test", requestId);
	}

	private PrescriptionRequest generatePrescriptionRequest() {
		PrescriptionRequest prescriptionRequest = new PrescriptionRequest(requestId, payerId, "55",
				new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), RequestStatusType.PENDING.value(), statusDesc,
				ePrescriptionRefNum, new BigDecimal(0), new BigDecimal(0), BenefitCaseType.INPATIENT.value(), currency,
				currency);
		prescriptionRequest.setCanCancel(true);
		prescriptionRequest.setCanFollowUp(true);
		return prescriptionRequest;
	}

	private ModifyDecisionRequestModel generateModifyDecisionRequestModel() {
		ModifyDecisionRequestModel requestModel = new ModifyDecisionRequestModel();
		requestModel.setDrugList(generateModifyDecisionDrugList());
		return requestModel;
	}

	private List<ModifyDecisionDrugList> generateModifyDecisionDrugList() {
		List<ModifyDecisionDrugList> modifyDecisionDrugList = new ArrayList<>();
		modifyDecisionDrugList.add(new ModifyDecisionDrugList(drugCode1, UnitType.PACKAGE.value(), new BigDecimal(10),
				10D, 10D, FrequencyType.AS_NEEDED.value(), null, 5L, new BigDecimal(10), new BigDecimal(10),
				ServiceStatus.APPROVED.name(), "Payer has approved this drug 46-172-05"));
		modifyDecisionDrugList.add(new ModifyDecisionDrugList(drugCode2, UnitType.PACKAGE.value(), new BigDecimal(10),
				10D, 10D, FrequencyType.AS_NEEDED.value(), null, 5L, new BigDecimal(10), new BigDecimal(10),
				ServiceStatus.REJECTED.name(), "Payer has rejected this drug 123-277-02"));
		return modifyDecisionDrugList;
	}

	private List<ServiceInfo> generateListOfServiceInfo() {
		List<ServiceInfo> serviceInfoList = new ArrayList<>();
		serviceInfoList.add(new ServiceInfo(1L, drugCode1, UnitType.PACKAGE.value(), 318.00, new BigDecimal(3),
				new BigDecimal(31.0), "test", date, date, 8L, "test", "test", requestId));
		serviceInfoList.add(new ServiceInfo(1L, drugCode2, UnitType.UNIT.value(), 318.00, new BigDecimal(3),
				new BigDecimal(31.0), "test", date, date, 8L, "test", "test", requestId));
		return serviceInfoList;
	}

	private ContentCachingRequestWrapper getContentCachingRequestWrapper() {
		ContentCachingRequestWrapper cachingRequestWrapper = null;
		try {
			MockHttpServletRequest hRequest = new MockHttpServletRequest();
			String req = this.mapper.writeValueAsString(modifyDecisionRequestModel);
			hRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
			hRequest.setContent(req.getBytes(StandardCharsets.UTF_8.name()));
			hRequest.setRequestURI("payers/102/prescriptions/2023-01/modify-decision");
			hRequest.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, generatePathVariables());
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

	private SmsNotificationResponseModel generateSmsNotificationResponse() {
		return new SmsNotificationResponseModel("SUCCESS", "Sms sent", "42000348806924", "2022-09-06 14:06:25.979");
	}

	private EmailNotificationResponseModel generateEmailNotificationResponse() {
		return new EmailNotificationResponseModel("SUCCESS", "Email sent");
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

	private OneTimeAccessTokenRequest generateOneTimeAccessTokenRequestModel() {
		List<String> ePrescriptionReferenceNumbers = new ArrayList<>();
		ePrescriptionReferenceNumbers.add(ePrescriptionRefNum);
		return new OneTimeAccessTokenRequest(idNumber, ePrescriptionReferenceNumbers, BigInteger.valueOf(604800000));
	}

	private JwtResponse generateJwtResponse() {
		String accessToken = "eyJ0eXBlIjoibGltaXRlZF9hY2Nlc3NfdG9rZW4iLCJhbGciOiJIUzUxMiJ9.ey"
				+ "JzdWIiOiIyMzkyMDE5NTY0IiwiZXhwIjoxNjkwODEyMzM3LCJpYXQiOjE2OTAyMDc"
				+ "1MzcsInJvbCI6W3siYXV0aG9yaXR5IjoicHJlc2NyaXB0aW9uLXNlcnZpY2V8MjAyMy"
				+ "0zMzAwIn1dfQ.W2AFXpF8eE_03BmWIz_51kX4zdNZE0rMu0HUC0IIWKaXfB8esFCIpQmqoj0KI_XtDBXGlbKgZ7kFNNcEslSmkA";
		return new JwtResponse(accessToken, date);
	}

	public void generateMockUserInfo() {
		Map<String, Object> details = new HashMap<String, Object>();
		details.put("accId", "55");
		details.put("accName", "Test");
		details.put("accCode", "accCode");
		details.put("username", "username");
		details.put("email", "email");
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(authentication.getPrincipal()).thenReturn(details);
	}

	private void mockPolicyConsumptionDetails() {
		Mockito.when(memberInfoRepository.findByRequestId(Mockito.any()))
				.thenReturn(Optional.of(prescriptionRequest.getMemberInfo()));
		Mockito.when(physicianRepository.findByRequestId(Mockito.any()))
				.thenReturn(Optional.of(generatePhysicianDetails()));
		Mockito.when(physicianInfoRepository.findByRegistrationNumber(Mockito.any()))
				.thenReturn(Optional.of(generatePhysicianInfo()));
		Mockito.when(deptSpecPhyscAsscRepository.findByPhysicianInfoId(Mockito.any()))
				.thenReturn(Optional.of(generateDepartment()));
		Mockito.when(businessRuleService.policyConsumptionCheck(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(generatePolicyResponseModel(PolicyConsumptionStatus.APPROVED.getValue()));
		Mockito.when(memberPolicyUsageRepository.save(Mockito.any())).thenReturn(generateMemberPolicyUsage());
	}

	private Physician generatePhysicianDetails() {
		return new Physician(1L, "32", requestId, "Dr.Test", PhysicianCategory.CONSULTANT.value(), "Test");
	}

	private PhysicianInfo generatePhysicianInfo() {
		return new PhysicianInfo(1L, 801L, "78654321", "Dr. Khan", null);
	}

	private Department generateDepartment() {
		return new Department(1L, "department");
	}

	private PolicyResponseModel generatePolicyResponseModel(String value) {
		List<DrugListModel> policyConsumptionDrugList = new ArrayList<>();
		DrugListModel drug = new DrugListModel("45-56-78", new BigDecimal("100"), null, null, null, null);
		policyConsumptionDrugList.add(drug);
		return new PolicyResponseModel("APPROVED", "APPROVED", "20", "100", "", "", String.valueOf(HttpStatus.OK.value()), "147954268",
				"789465", "", new BigDecimal("15000"), "SAR", "SAR", "APPROVED", "100", "123456789",
				policyConsumptionDrugList, currency, currency);
	}

	private MemberPolicyUsage generateMemberPolicyUsage() {
		MemberPolicyUsage memberPolicyUsage = new MemberPolicyUsage(payerId, "801", "1234568", Long.valueOf(1234567890),
				"789456", "7894195", "benefit", new BigDecimal(0), "SAR", new BigDecimal(0), "SAR", "2023-01",
				"APPROVED");
		return memberPolicyUsage;
	}
}
