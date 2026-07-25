package com.waseel.prescription.prescriptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.prescription.clients.EligibilityServiceClient;
import com.waseel.prescription.clients.PBMPayerApisServiceClient;
import com.waseel.prescription.clients.PolicyConsumptionClient;
import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.dispense.PrescriptionDispenseRequestModel;
import com.waseel.prescription.model.dispense.PrescriptionDispenseResponseModel;
import com.waseel.prescription.model.eligibility.EligibilityResponseModel;
import com.waseel.prescription.model.enums.BenefitCaseType;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.DomainName;
import com.waseel.prescription.model.enums.EligibilityStatus;
import com.waseel.prescription.model.enums.GenderType;
import com.waseel.prescription.model.enums.PolicyConsumptionStatus;
import com.waseel.prescription.model.enums.PrescriptionUrl;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.enums.TransactionStatusType;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionResponseModel;
import com.waseel.prescription.model.pbmpayerapis.MemberDemographicDataResponseModel;
import com.waseel.prescription.model.pbmpayerapis.PolicyInformationModel;
import com.waseel.prescription.model.policyconsumption.CancellAndDispensePolicyRequestModel;
import com.waseel.prescription.model.policyconsumption.DrugListModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.persist.businessrules.BenefitCodePhyscSpecAssc;
import com.waseel.prescription.persist.businessrules.BenefitCodes;
import com.waseel.prescription.persist.businessrules.Department;
import com.waseel.prescription.persist.businessrules.PhysicianInfo;
import com.waseel.prescription.persist.businessrules.Speciality;
import com.waseel.prescription.persist.hira.AccountToAccountAssociation;
import com.waseel.prescription.persist.hira.AccountToAccountAssociationId;
import com.waseel.prescription.persist.prescriptionservice.DispensedPrescription;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.MemberPolicyUsage;
import com.waseel.prescription.persist.prescriptionservice.Physician;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionApprovalAssc;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.businessrules.BenefitCodePhyscSpecAsscRepository;
import com.waseel.prescription.repository.businessrules.DeptSpecPhyscAsscRepository;
import com.waseel.prescription.repository.businessrules.PhysicianInfoRepository;
import com.waseel.prescription.repository.businessrules.SpecialityRepository;
import com.waseel.prescription.repository.hira.AccountToAccountAssociationRepository;
import com.waseel.prescription.repository.hira.SwitchAccountRepository;
import com.waseel.prescription.repository.prescriptionservice.DiagnosisRepository;
import com.waseel.prescription.repository.prescriptionservice.DispensedPrescriptionRepository;
import com.waseel.prescription.repository.prescriptionservice.DispensedServiceRepository;
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
import com.waseel.prescription.service.prescriptions.DispensePrescriptionService;
import com.waseel.prescription.util.UserInfoUtil;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class DispensePrescriptionTests {

	private final String INVALID = "Invalid";
	private final String requestId = "8890e048-a1ba-44c7-b0c7-86393cc5773b";
	private final String validEPrescriptionReferenceNumber = "2023-1";
	private final String invalidEPrescriptionReferenceNumber = "abc";
	private final String payerId = "102";
	private final Long idNumber = 123L;
	private String providerId = "12";
	private final String msgDispensedSuccess = "Dispensed successfully";
	private final String msgRejected = "Already rejected can't dispensed";
	private final String msgCancelled = "Already cancelled ,can't dispensed";
	private final String msgAlreadyDispensed = "Already dispensed.";
	private final String benefitCode = "DENTAL";
	private final String benefitCase = "INPATIENT";
	private final Date date = new Date();
	private static final String classCode = "1-VVIP-Network Gold";
	private final String memberNationality = "Saudi Arabia";
	private BenefitCodePhyscSpecAssc benefitCodePhyscSpecAssc = null;
	private static final Long pkid = 1L;

	@Autowired
	private DispensePrescriptionService dispensePrescriptionService;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private EligibilityServiceClient eligibilityServiceClient;

	@MockBean
	private PolicyConsumptionClient policyConsumptionClient;

	@MockBean
	private PBMPayerApisServiceClient pbmPayerApisServiceClient;

	@MockBean
	private TransactionLogRepository transactionLogRepository;

	@MockBean
	private PrescriptionRequestRepository prescriptionRequestRepository;

	@MockBean
	private DispensedPrescriptionRepository dispensedPrescriptionRepository;

	@MockBean
	private DispensedServiceRepository dispensedServiceRepository;

	@MockBean
	private ServiceResponseInfoRepository serviceResponseInfoRepository;

	@MockBean
	private AccountToAccountAssociationRepository accountToAccountAssociationRepository;

	@MockBean
	private MemberInfoRepository memberInfoRepository;

	@MockBean
	private MemberPolicyUsageRepository memberPolicyUsageRepository;

	@MockBean
	private ServiceInfoRepository serviceInfoRepository;

	@MockBean
	private DiagnosisRepository diagnosisRepository;

	@MockBean
	private PhysicianRepository physicianRepository;

	@MockBean
	PrescriptionApprovalAsscRepository prescriptionApprovalAsscRepository;

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

	private PrescriptionDispenseRequestModel dispenseRequestModel;
	private TransactionLog transactionLog;
	private PrescriptionRequest prescriptionRequest;
	private ContentCachingRequestWrapper contentCachingRequestWrapper;
	private List<ServiceResponseInfo> serviceResponseInfoList;
	private List<ServiceInfo> serviceInfoList;
	private AccountToAccountAssociation accountToAccountAssociation;
	private MemberInfo memberInfo;
	private EligibilityResponseModel eligibilityResponseModel;
	private PolicyResponseModel policyResponseModel;
	private DispensedPrescription dispensedPrescription;
	private EPrescriptionResponseModel ePrescriptionResponseModel;
	private static final String currency = Currency.SAR.value();
	private Speciality speciality = null;
	private static final String specialityName = "Anesthesia Cardiology";

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
	}

	@BeforeEach
	void setupData() {
		dispenseRequestModel = generatePrescriptionDispenseRequestModel();
		transactionLog = generateTransactionLogWithValidStatus();
		prescriptionRequest = generatePrescriptionRequest();
		memberInfo = generateMemberInfo();
		contentCachingRequestWrapper = getContentCachingRequestWrapper();
		serviceResponseInfoList = generateServiceResponseInfoEntity();
		serviceInfoList = generateServiceInfoList();
		accountToAccountAssociation = generateAccountToAccountAssociation();
		eligibilityResponseModel = generateEligibilityResponseModel();
		policyResponseModel = generatePolicyResponseModel();
		dispensedPrescription = generateDispensedPrescription();
		ePrescriptionResponseModel = generateEPrescriptionResponseModel();
		benefitCodePhyscSpecAssc = populateBenefitCodePhyscSpecAssc();
		Mockito.when(transactionLogRepository.save(Mockito.any())).thenReturn(transactionLog);
		Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(prescriptionRequest);
		assertNotNull(prescriptionRequest);
		assertNotNull(transactionLog);
		assertNotNull(dispenseRequestModel);
		assertNotNull(accountToAccountAssociation);
		Mockito.when(transactionLogRepository
				.findByePrescriptionReferenceNumberWithValidStatus(validEPrescriptionReferenceNumber))
				.thenReturn(Optional.of(transactionLog));
		Mockito.when(serviceResponseInfoRepository.saveAll(Mockito.any())).thenReturn(serviceResponseInfoList);
		Mockito.when(serviceResponseInfoRepository.findByRequestId(requestId)).thenReturn(serviceResponseInfoList);
		Mockito.when(prescriptionRequestRepository.findByRequestId(requestId))
				.thenReturn(Optional.of(prescriptionRequest));
		Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(Mockito.any()))
				.thenReturn(Optional.of(prescriptionRequest));
		Mockito.when(memberInfoRepository.findByRequestId(requestId)).thenReturn(Optional.of(memberInfo));
		Mockito.when(eligibilityServiceClient.checkMemberEligibility(idNumber + "", payerId, providerId, requestId))
				.thenReturn(ResponseEntity.of(Optional.of(eligibilityResponseModel)));
		Mockito.when(
				policyConsumptionClient.checkPolicyConsumptionForDispensePrescription(Mockito.any(), Mockito.any()))
				.thenReturn(ResponseEntity.of(Optional.of(policyResponseModel)));
		Mockito.when(memberPolicyUsageRepository.save(Mockito.any())).thenReturn(new MemberPolicyUsage());
		Mockito.when(
				serviceInfoRepository.findByIsNotDeletedAndRequestIdAndStatus(requestId, ServiceStatus.APPROVED.name()))
				.thenReturn(serviceInfoList);
		Mockito.when(serviceResponseInfoRepository.findByServiceIds(Mockito.any())).thenReturn(serviceResponseInfoList);
		Mockito.when(dispensedPrescriptionRepository.save(Mockito.any())).thenReturn(dispensedPrescription);
		Mockito.when(dispensedServiceRepository.saveAll(Mockito.any())).thenReturn(new ArrayList<>());
		Mockito.when(pbmPayerApisServiceClient.getEPrescriptionApproval(Mockito.any()))
				.thenReturn(ResponseEntity.of(Optional.of(ePrescriptionResponseModel)));
		Mockito.when(diagnosisRepository.findByRequestIdAndIsNotDeleted(requestId)).thenReturn(new ArrayList<>());
		Mockito.when(serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId)).thenReturn(new ArrayList<>());
		Mockito.when(physicianRepository.findByRequestId(requestId)).thenReturn(Optional.of(new Physician()));
		Mockito.when(physicianInfoRepository.findByRegistrationNumber(Mockito.any()))
				.thenReturn(Optional.of(generatePhysicianInfoModle()));
		Mockito.when(deptSpecPhyscAsscRepository.findByPhysicianInfoId(Mockito.any()))
				.thenReturn(Optional.of(generateDepartmentModle()));
		prescriptionRequest.setMemberInfo(new MemberInfo("Test", 12l, "Test", new Date(), 55.1, 5.1, "Male", requestId,
				"Test", memberNationality));
		Mockito.when(prescriptionApprovalAsscRepository.save(Mockito.any())).thenReturn(new PrescriptionApprovalAssc());
		Mockito.when(pbmPayerApisRestHandler.sendRequestToGetMemberDemographicData(Mockito.any()))
				.thenReturn(generateMemberDemographicDataResponseModel());
		Mockito.when(
				benefitCodePhyscSpecAsscRepository.findBySpecialityIdAndIsEnabled(Mockito.any(), Mockito.anyBoolean()))
				.thenReturn(Optional.of(benefitCodePhyscSpecAssc));
		speciality = populateSpeciality();
		Mockito.when(specialityRepository.findBySpecialityNameAndIsDeleted(Mockito.any(), Mockito.anyBoolean()))
				.thenReturn(Optional.of(speciality));
	}

	@Test
	@DisplayName("CheckEPrescriptionApproval With ApprovalReferenceNumber")
	void successCheckEPrescriptionApprovalApiTest() {
		try {
			Mockito.when(pbmPayerApisRestHandler.sendRequestToGetEPrescriptionApproval(Mockito.any()))
					.thenReturn(ePrescriptionResponseModel);
			PrescriptionDispenseResponseModel response = dispensePrescriptionService.managePrescriptionDispensedRequest(
					dispenseRequestModel, contentCachingRequestWrapper, payerId, DomainName.WASEEL.value());
			assertNotNull(response);
			assertThat(response.getPayerId()).isEqualTo(payerId);
			assertThat(response.getProviderId()).isEqualTo(providerId);
			assertThat(response.getePrescriptionReferenceNumber())
					.isEqualTo(dispenseRequestModel.getePrescriptionReferenceNumber());
			assertThat(response.getStatus()).isEqualTo(RequestStatusType.DISPENSED.value());
			assertThat(response.getStatusDescription()).isEqualTo(msgDispensedSuccess);
			assertThat(prescriptionRequest.getStatusCode()).isEqualTo(RequestStatusType.DISPENSED.value());
			assertThat(serviceResponseInfoList.get(0).getStatus()).isEqualTo(ServiceStatus.DISPENSED.name());
		} catch (PrescriptionException e) {
			assertThat(e.getDispensedResponseModel().getStatus()).isEqualTo(INVALID);
		}
	}

	@Test
	@DisplayName("CheckEPrescriptionApproval Without ApprovalReferenceNumber")
	void failCheckEPrescriptionApprovalApiTest() {
		try {
			ePrescriptionResponseModel.setApprovalReferenceNumber(null);
			ePrescriptionResponseModel.setStatus("FAILED");
			Mockito.when(pbmPayerApisRestHandler.sendRequestToGetEPrescriptionApproval(Mockito.any()))
					.thenReturn(ePrescriptionResponseModel);
			PrescriptionDispenseResponseModel response = dispensePrescriptionService.managePrescriptionDispensedRequest(
					dispenseRequestModel, contentCachingRequestWrapper, payerId, DomainName.WASEEL.value());
			assertNotNull(response);
			assertThat(response.getPayerId()).isEqualTo(payerId);
			assertThat(response.getProviderId()).isEqualTo(providerId);
			assertThat(response.getePrescriptionReferenceNumber())
					.isEqualTo(dispenseRequestModel.getePrescriptionReferenceNumber());
			assertThat(response.getStatus()).isEqualTo("FAILED");
			assertNull(response.getStatusDescription());
		} catch (PrescriptionException e) {
			assertThat(e.getDispensedResponseModel().getStatus()).isEqualTo(INVALID);
		}
	}

	@Test
	@DisplayName("Success Response - REJECTED request")
	void successResponseWithInvalidRejectedStatus() {
		try {
			prescriptionRequest.setStatusCode(RequestStatusType.REJECTED.value());
			prescriptionRequest
					.setStatusDescription("Medication 2402221767 is not indicated with diagnosis code F31.6");
			Mockito.when(prescriptionRequestRepository.findByRequestId(requestId))
					.thenReturn(Optional.of(prescriptionRequest));
			PrescriptionDispenseResponseModel response = dispensePrescriptionService.managePrescriptionDispensedRequest(
					dispenseRequestModel, contentCachingRequestWrapper, payerId, DomainName.WASEEL.value());
			assertNotNull(response);
			assertThat(response.getPayerId()).isEqualTo(payerId);
			assertThat(response.getProviderId()).isEqualTo(providerId);
			assertThat(response.getePrescriptionReferenceNumber())
					.isEqualTo(dispenseRequestModel.getePrescriptionReferenceNumber());
			assertThat(response.getStatus()).isEqualTo(INVALID);
			assertThat(response.getStatusDescription()).isEqualTo(msgRejected);
			assertThat(prescriptionRequest.getStatusCode()).isNotEqualTo(RequestStatusType.DISPENSED.value());
		} catch (PrescriptionException e) {
			assertThat(e.getDispensedResponseModel().getStatus()).isEqualTo(INVALID);
		}
	}

	@Test
	@DisplayName("Success Response - CANCELLED request")
	void successResponseWithInvalidCancelledStatus() {
		try {
			prescriptionRequest.setStatusCode(RequestStatusType.CANCELLED.value());
			prescriptionRequest.setStatusDescription("Transaction cancelled successfully.");
			Mockito.when(prescriptionRequestRepository.findByRequestId(requestId))
					.thenReturn(Optional.of(prescriptionRequest));
			PrescriptionDispenseResponseModel response = dispensePrescriptionService.managePrescriptionDispensedRequest(
					dispenseRequestModel, contentCachingRequestWrapper, payerId, DomainName.WASEEL.value());
			assertNotNull(response);
			assertThat(response.getPayerId()).isEqualTo(payerId);
			assertThat(response.getProviderId()).isEqualTo(providerId);
			assertThat(response.getePrescriptionReferenceNumber())
					.isEqualTo(dispenseRequestModel.getePrescriptionReferenceNumber());
			assertThat(response.getStatus()).isEqualTo(INVALID);
			assertThat(response.getStatusDescription()).isEqualTo(msgCancelled);
			assertThat(prescriptionRequest.getStatusCode()).isNotEqualTo(RequestStatusType.DISPENSED.value());
		} catch (PrescriptionException e) {
			assertThat(e.getDispensedResponseModel().getStatus()).isEqualTo(INVALID);
		}
	}

	@Test
	@DisplayName("Success Response - DISPENSED request")
	void successResponseWithInvalidDispensedStatus() {
		try {
			prescriptionRequest.setStatusCode(RequestStatusType.DISPENSED.value());
			prescriptionRequest.setStatusDescription(msgDispensedSuccess);
			Mockito.when(prescriptionRequestRepository.findByRequestId(requestId))
					.thenReturn(Optional.of(prescriptionRequest));
			PrescriptionDispenseResponseModel response = dispensePrescriptionService.managePrescriptionDispensedRequest(
					dispenseRequestModel, contentCachingRequestWrapper, payerId, DomainName.WASEEL.value());
			assertNotNull(response);
			assertThat(response.getPayerId()).isEqualTo(payerId);
			assertThat(response.getProviderId()).isEqualTo(providerId);
			assertThat(response.getePrescriptionReferenceNumber())
					.isEqualTo(dispenseRequestModel.getePrescriptionReferenceNumber());
			assertThat(response.getStatus()).isEqualTo(INVALID);
			assertThat(response.getStatusDescription()).isEqualTo(msgAlreadyDispensed);
			assertThat(prescriptionRequest.getStatusCode()).isEqualTo(RequestStatusType.DISPENSED.value());
		} catch (PrescriptionException e) {
			assertThat(e.getDispensedResponseModel().getStatus()).isEqualTo(INVALID);
		}
	}

	@Test
	@DisplayName("Invalid response[400] PrescriptionException from Dispense Api.")
	void invalidPrescriptionDetailInquiryResponseTest() {
		String invalidEPrescriptionRefNum = "abc";
		try {
			dispenseRequestModel.setePrescriptionReferenceNumber(invalidEPrescriptionReferenceNumber);
			Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(Mockito.any()))
					.thenReturn(Optional.empty());
			dispensePrescriptionService.managePrescriptionDispensedRequest(dispenseRequestModel,
					contentCachingRequestWrapper, payerId, DomainName.WASEEL.value());
		} catch (PrescriptionException e) {
			PrescriptionDispenseResponseModel invalidResponse = e.getDispensedResponseModel();
			assertThat(e.getDispensedResponseModel().getStatusDescription())
					.isEqualTo("EPrescriptionReferenceNumber is not found or exists.");
			assertThat(invalidResponse.getStatus()).isEqualTo(INVALID);
			assertThat(invalidResponse.getPayerId()).isEqualTo(payerId);
			assertThat(invalidResponse.getProviderId()).isEqualTo(providerId);
			assertThat(invalidResponse.getePrescriptionReferenceNumber()).isEqualTo(invalidEPrescriptionRefNum);
			dispenseRequestModel.setePrescriptionReferenceNumber(validEPrescriptionReferenceNumber);
		}
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

	private List<ServiceResponseInfo> generateServiceResponseInfoEntity() {
		List<ServiceResponseInfo> list = new ArrayList<>();
		list.add(new ServiceResponseInfo(1l, requestId, new BigDecimal(30D), new BigDecimal(30D), null, null, null,
				RequestStatusType.APPROVED.value(), null, 1L));
		list.add(new ServiceResponseInfo(2l, requestId, new BigDecimal(30D), new BigDecimal(0D), null, null, null,
				RequestStatusType.REJECTED.value(), "Medication 2402221767 is not indicated with diagnosis code F31.6",
				2L));
		return list;
	}

	private List<ServiceInfo> generateServiceInfoList() {
		List<ServiceInfo> list = new ArrayList<>();
		list.add(new ServiceInfo("Test", "Test", 25.0, BigDecimal.valueOf(12), BigDecimal.valueOf(10), "Test",
				new Date(), new Date(), 5l, "Test", "Test", "Test"));
		list.add(new ServiceInfo("Test", "Test", 5.0, BigDecimal.valueOf(2), BigDecimal.valueOf(1), "Test", new Date(),
				new Date(), 55l, "Test", "Test", "Test"));
		return list;
	}

	private PrescriptionDispenseRequestModel generatePrescriptionDispenseRequestModel() {
		return new PrescriptionDispenseRequestModel(validEPrescriptionReferenceNumber);
	}

	private EligibilityResponseModel generateEligibilityResponseModel() {
		return new EligibilityResponseModel(EligibilityStatus.ELIGIBLE.getValue(), "Eligible", "Test", "Test",
				requestId, 200);
	}

	private PolicyResponseModel generatePolicyResponseModel() {
		PolicyResponseModel policyResponseModel = new PolicyResponseModel();
		policyResponseModel.setStatus(PolicyConsumptionStatus.APPROVED.getValue());
		policyResponseModel.setRemainingLimit("100");
		return policyResponseModel;
	}

	private DispensedPrescription generateDispensedPrescription() {
		DispensedPrescription dispensedPrescription = new DispensedPrescription();
		dispensedPrescription.setId(123l);
		return dispensedPrescription;
	}

	private EPrescriptionResponseModel generateEPrescriptionResponseModel() {
		EPrescriptionResponseModel responseModel = new EPrescriptionResponseModel();
		responseModel.setApprovalReferenceNumber("12345");
		return responseModel;
	}

	private TransactionLog generateTransactionLogWithValidStatus() {
		return new TransactionLog(1L, requestId, 51.11, RequestType.NEW.name(), payerId, providerId,
				TransactionStatusType.SENT.name(), validEPrescriptionReferenceNumber, ServiceStatus.APPROVED.name(),
				null, new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), "PBM", String.valueOf(HttpStatus.OK.value()),
				RequestStatusType.APPROVED.value());
	}

	private PrescriptionRequest generatePrescriptionRequest() {
		return new PrescriptionRequest(requestId, payerId, providerId,
				new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), RequestStatusType.APPROVED.value(), "",
				validEPrescriptionReferenceNumber, new BigDecimal(0), new BigDecimal(0),
				BenefitCaseType.INPATIENT.value(), currency, currency);
	}

	private MemberInfo generateMemberInfo() {
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setIdNumber(idNumber);
		return memberInfo;
	}

	private AccountToAccountAssociation generateAccountToAccountAssociation() {
		AccountToAccountAssociationId id = new AccountToAccountAssociationId(
				BigDecimal.valueOf(Double.valueOf(providerId)), BigDecimal.valueOf(Double.valueOf(payerId)));
		return new AccountToAccountAssociation(id, true, true, "123");
	}

	private ContentCachingRequestWrapper getContentCachingRequestWrapper() {
		ContentCachingRequestWrapper cachingRequestWrapper = null;
		try {
			MockHttpServletRequest hRequest = new MockHttpServletRequest();
			String req = this.mapper.writeValueAsString(dispenseRequestModel);
			hRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
			hRequest.setContent(req.getBytes(StandardCharsets.UTF_8.name()));
			hRequest.setRequestURI(PrescriptionUrl.DISPENSE.getValue());
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

	private CancellAndDispensePolicyRequestModel generateCancellAndDispensePolicyRequestModel() {
		List<DrugListModel> drugListModels = new ArrayList<DrugListModel>();
		DrugListModel drugListModel = new DrugListModel("54-78-896", new BigDecimal(45), null, null, null, null);
		drugListModels.add(drugListModel);
		return new CancellAndDispensePolicyRequestModel(benefitCode, benefitCase, payerId, requestId, drugListModels,
				providerId, RequestType.DISPENSED.value());
	}

	private Department generateDepartmentModle() {
		return new Department(1L, benefitCode, date, false);
	}

	private PhysicianInfo generatePhysicianInfoModle() {
		return new PhysicianInfo(1L, Long.valueOf(providerId), "4589", "Dr.test");
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
