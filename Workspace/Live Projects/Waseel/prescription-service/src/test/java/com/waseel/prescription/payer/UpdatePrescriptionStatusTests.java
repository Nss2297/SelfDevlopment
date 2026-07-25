package com.waseel.prescription.payer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import com.waseel.prescription.clients.PbmNotificationServiceClient;
import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.enums.BenefitCaseType;
import com.waseel.prescription.model.enums.CommonDenialsCode;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.FrequencyType;
import com.waseel.prescription.model.enums.GenderType;
import com.waseel.prescription.model.enums.PhysicianCategory;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.enums.UnitType;
import com.waseel.prescription.model.inquiry.eprescription.EPrescriptionInquiryDrugList;
import com.waseel.prescription.model.inquiry.eprescription.EPrescriptionInquiryError;
import com.waseel.prescription.model.inquiry.eprescription.EPrescriptionInquiryRequestModel;
import com.waseel.prescription.model.inquiry.eprescription.InsuranceCompanyDecision;
import com.waseel.prescription.model.inquiry.eprescription.PbmValidationResult;
import com.waseel.prescription.model.notification.EmailNotificationResponseModel;
import com.waseel.prescription.model.pbmpayerapis.DiagnosisCodes;
import com.waseel.prescription.model.pbmpayerapis.MemberDemographicDataResponseModel;
import com.waseel.prescription.model.pbmpayerapis.PolicyInformationModel;
import com.waseel.prescription.model.prescription.ServiceRejectionDTO;
import com.waseel.prescription.persist.mdss.DrugService;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionApprovalDrug;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.repository.mdss.DrugServiceRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionApprovalDrugRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.TransactionLogRepository;
import com.waseel.prescription.service.clienthandler.PbmPayerApisRestHandler;
import com.waseel.prescription.service.policyconsumption.PolicyConsumptionService;
import com.waseel.prescription.service.prescriptions.EmailAndSmsNotificationService;
import com.waseel.prescription.service.prescriptions.PrescriptionUpdationService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles({ "test" })
@TestMethodOrder(OrderAnnotation.class)
class UpdatePrescriptionStatusTests {

	@Autowired
	private PrescriptionUpdationService prescriptionUpdationService;

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
	private PolicyConsumptionService policyConsumptionService;

	@MockBean
	private EmailAndSmsNotificationService emailAndSmsNotificationService;

	@MockBean
	private DrugServiceRepository drugServiceRepository;

	@MockBean
	private PrescriptionApprovalDrugRepository prescriptionApprovalDrugRepository;

	@MockBean
	private PbmPayerApisRestHandler pbmPayerApisRestHandler;

	@MockBean
	private PbmNotificationServiceClient notificationServiceClient;

	private EPrescriptionInquiryRequestModel ePrescriptionInquiryRequestModel = null;
	private ServiceResponseInfo serviceResponseInfo = null;
	private PrescriptionRequest prescriptionRequest = null;
	private static final String requestType = "NEW";
	private static final String caseType = BenefitCaseType.INPATIENT.value();
	private static final String currentYear = Year.now().toString();
	private static final String ePrescriptionReferenceNumber = currentYear + "-1";
	private static final String ePrescriptionStatus = RequestStatusType.APPROVED.value();
	private static final String ePrescriptionStatusDescription = "";
	private static final String payerId = "102";
	private static final String providerId = "801";
	private static final String memberName = "Salim";
	private static final Long idNumber = 2392019564L;
	private static final Date date = new Date();
	private static final String policyNumber = "24735173";
	private static final String memberGender = GenderType.MALE.value();
	private static final BigDecimal memberWeight = new BigDecimal("55");
	private static final BigDecimal memberHeight = new BigDecimal("5.5");
	private static final String physicianLicenseNumber = "55";
	private static final String physicianSpeciality = "Anesthesia Cardiology";
	private static final String physicianName = "Dr. Khan";
	private static final String physicianCategory = PhysicianCategory.GP.value();
	private static final Boolean value = Boolean.TRUE;
	private static final BigDecimal totalPrescriptionPrice = new BigDecimal("1000");
	private static final BigDecimal totalPatientShareValue = new BigDecimal("100");
	private static final String currency = Currency.SAR.value();
	private static final BigDecimal totalPayerShareValue = new BigDecimal("900");
	private static final String diagnosisCode = "F31.6";
	private static final String diagnosisType = "PRIMARY";
	private static final String drugCode1 = "1501233101";
	private static final String drugCode2 = "380-149-09";
	private static final String scientificCode1 = "7000000687-6000000-200000016494";
	private static final String scientificCode2 = "7000000687-6000000-200000016495";
	private static final String drugName1 = "REBIF 22 MCG PRE-FILLED SYRINGE";
	private static final String drugName2 = "Laroza";
	private static final String scientificName1 = "INTERFERON BETA-1A";
	private static final String scientificName2 = "INTERFERON BETA-2A";
	private static final String unitType = UnitType.PACKAGE.value();
	private static final BigDecimal quantity = BigDecimal.TEN;
	private static final Double unitPrice = 5D;
	private static final String orderingClinician = "33";
	private static final BigDecimal duration = BigDecimal.ONE;
	private static final BigDecimal useUnitValue = BigDecimal.ONE;
	private static final String frequency = FrequencyType.EVERY_12_HOURS.value();
	private static final String frequencyOthersDescription = "";
	private static final BigDecimal requestedAmount = new BigDecimal("50");
	private static final BigDecimal approvedAmount = new BigDecimal("50");
	private static final String status = RequestStatusType.APPROVED.value();
	private static final String requestId = "0fb78ee7-9e62-4d9e-aee7-18f099e126f1";
	private Timestamp timestamp = (new Timestamp(Calendar.getInstance().getTimeInMillis()));
	private static final String classCode = "1-VVIP-Network Gold";
	private static final Long id = 1L;
	private static final Long drugListId = 7L;
	private static final String rejectionReason = "Medication " + drugCode1
			+ " is not indicated with diagnosis code R25.2";
	private static final String E_PRESCRIPTION_REFERENCE_NUMBER_NOT_EXIST_STRING = "EPrescriptionReferenceNumber is not found or exists.";

	@BeforeAll
	public void commonDataForAllUnitTest() {
		ePrescriptionInquiryRequestModel = generateEPrescriptionInquiryRequestModel();
	}

	@BeforeEach
	public void commonDataBeforeEachUnitTest() {
		commonMockData();
	}

	@Order(1)
	@Test
	@DisplayName("Prescription not found.")
	void prescriptionNotFoundTest() {
		try {
			Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(Mockito.any()))
					.thenReturn(Optional.empty());
			prescriptionUpdationService.updatePrescriptionStatus(ePrescriptionInquiryRequestModel);
		} catch (PrescriptionException prescriptionException) {
			String message = prescriptionException.getMessage();
			assertNotNull(message);
			assertEquals(E_PRESCRIPTION_REFERENCE_NUMBER_NOT_EXIST_STRING, message);
			verify(serviceRejectionRepository, times(0)).deleteByRequestIdAndServiceResponseId(requestId, id);
			verify(serviceRejectionRepository, times(0)).deleteByRequestIdAndId(requestId, id);
		}
	}

	@Order(2)
	@Test
	@DisplayName("Prescription rejected drug with approved by Payer.")
	void rejectedDrugCodeApprovedByPayerTest() throws PrescriptionException {
		prescriptionUpdationService.updatePrescriptionStatus(ePrescriptionInquiryRequestModel);
		verify(serviceRejectionRepository, times(1)).deleteByRequestIdAndServiceResponseId(requestId, id);
	}

	@Order(3)
	@Test
	@DisplayName("Prescription drug with scientific code Scientific code approved by Payer.")
	void rejectedScientificCodeDrugApprovedByPayerTest() throws PrescriptionException {
		Mockito.when(serviceInfoRepository.findByRequestIdAndDrugCode(Mockito.any(), Mockito.any()))
				.thenReturn(Optional.empty());
		prescriptionUpdationService.updatePrescriptionStatus(ePrescriptionInquiryRequestModel);
		verify(serviceRejectionRepository, times(1)).deleteByRequestIdAndServiceResponseId(requestId, id);
	}

	@Order(4)
	@Test
	@DisplayName("Pending drugCode rejected by Payer.")
	void pendingDrugCodeRejectedByPayerTest() throws PrescriptionException {
		setRejectedStatusForDrugByPayer();
		prescriptionUpdationService.updatePrescriptionStatus(ePrescriptionInquiryRequestModel);
		verify(serviceRejectionRepository, times(1)).deleteByRequestIdAndId(requestId, id);
		ePrescriptionInquiryRequestModel = generateEPrescriptionInquiryRequestModel();
		serviceResponseInfo = generateServiceResponseInfo();
	}

	@Order(5)
	@Test
	@DisplayName("Pending Scientific Code rejected by Payer.")
	void pendingScientificCodeRejectedByPayerTest() throws PrescriptionException {
		setRejectedStatusForDrugByPayer();
		Mockito.when(serviceInfoRepository.findByRequestIdAndDrugCode(Mockito.any(), Mockito.any()))
				.thenReturn(Optional.empty());
		prescriptionUpdationService.updatePrescriptionStatus(ePrescriptionInquiryRequestModel);
		verify(serviceRejectionRepository, times(1)).deleteByRequestIdAndId(requestId, id);
		ePrescriptionInquiryRequestModel = generateEPrescriptionInquiryRequestModel();
		serviceResponseInfo = generateServiceResponseInfo();
	}

	private void commonMockData() {
		ReflectionTestUtils.setField(prescriptionUpdationService, "featureToggleEnabled", true);
		prescriptionRequest = generatePrescriptionRequest();
		serviceResponseInfo = generateServiceResponseInfo();
		Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(Mockito.any()))
				.thenReturn(Optional.of(prescriptionRequest));
		Mockito.when(serviceInfoRepository.findByRequestIdAndDrugCode(Mockito.any(), Mockito.any()))
				.thenReturn(Optional.of(generateServiceInfo(drugCode1)));
		Mockito.when(serviceInfoRepository.findByRequestIdAndIsDeletedAndScientificCodeNotNull(Mockito.any(),
				Mockito.anyBoolean())).thenReturn(Optional.of(generatePrescriptionServiceWithScientificCode()));
		Mockito.when(drugServiceRepository.findByOtherCodesValueAndDrugListIdIn(Mockito.any(), Mockito.any()))
				.thenReturn(Optional.of(generateDrugServices()));
		Mockito.when(serviceResponseInfoRepository.findByRequestIdAndServiceIDAndStatusNot(Mockito.any(),
				Mockito.anyLong(), Mockito.any())).thenReturn(Optional.of(serviceResponseInfo));
		Mockito.when(serviceResponseInfoRepository.save(Mockito.any())).thenReturn(serviceResponseInfo);
		Mockito.when(serviceRejectionRepository.findByRequestIdAndDenialCodeAndServiceResponseId(Mockito.any(),
				Mockito.any(), Mockito.anyLong())).thenReturn(Optional.of(generateServiceRejectionDTO()));
		Mockito.when(serviceRejectionRepository.fetchByRequestIdAndServiceResponseId(Mockito.any(), Mockito.anyLong()))
				.thenReturn(Optional.of(generateServiceRejectionDTOList()));
		Mockito.when(prescriptionApprovalDrugRepository
				.findByEprescriptionReferenceNumberAndScientificCodeAndSuggestedDrugCode(Mockito.any(), Mockito.any(),
						Mockito.any()))
				.thenReturn(Optional.of(generatePrescriptionApprovalDrug()));
		Mockito.when(prescriptionApprovalDrugRepository.save(Mockito.any()))
				.thenReturn(generatePrescriptionApprovalDrug());
		Mockito.when(drugServiceRepository.findByOtherCodesValueAndDrugListId(Mockito.any(), Mockito.anyLong()))
				.thenReturn(Optional.of(getDrugDetails(drugCode1, drugName1, scientificCode1, scientificName1)));
		Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(prescriptionRequest);
		Mockito.when(pbmPayerApisRestHandler.sendRequestToGetMemberDemographicData(Mockito.any()))
				.thenReturn(generateMemberDemographicDataResponseModel());
		Mockito.when(serviceResponseInfoRepository.fetchPrescriptionStatusCodeByRequestId(Mockito.any()))
				.thenReturn(status);
		Mockito.when(serviceRejectionRepository.getAllRejectionsByRequestId(Mockito.any()))
				.thenReturn(new ArrayList<>());
		Mockito.when(notificationServiceClient.sendEmailNotification(Mockito.any()))
				.thenReturn(ResponseEntity.ok(generateEmailNotificationResponse()));
	}

	private EPrescriptionInquiryRequestModel generateEPrescriptionInquiryRequestModel() {
		List<DiagnosisCodes> diagnosisCodes = generateListDiagnosisCodes();
		List<EPrescriptionInquiryDrugList> drugList = generateEPrescriptionInquiryDrugList();
		return new EPrescriptionInquiryRequestModel(requestType, caseType, ePrescriptionReferenceNumber,
				ePrescriptionStatus, ePrescriptionStatusDescription, payerId, providerId, memberName, idNumber, date,
				policyNumber, memberGender, memberWeight, memberHeight, physicianLicenseNumber, physicianSpeciality,
				physicianName, physicianCategory, value, value, totalPrescriptionPrice, totalPatientShareValue,
				currency, totalPayerShareValue, payerId, diagnosisCodes, drugList);
	}

	private List<DiagnosisCodes> generateListDiagnosisCodes() {
		List<DiagnosisCodes> diagnosisCodes = new ArrayList<>();
		diagnosisCodes.add(new DiagnosisCodes(diagnosisCode, diagnosisType));
		return diagnosisCodes;
	}

	private List<EPrescriptionInquiryDrugList> generateEPrescriptionInquiryDrugList() {
		List<EPrescriptionInquiryDrugList> drugList = new ArrayList<>();
		List<EPrescriptionInquiryError> prescriptionInquiryError = new ArrayList<>();
		prescriptionInquiryError
				.add(new EPrescriptionInquiryError(CommonDenialsCode.MODIFY_BY_PAYER_CODE.value(), caseType));
		PbmValidationResult pbmValidationResult = new PbmValidationResult(requestedAmount, approvedAmount,
				ePrescriptionStatus, prescriptionInquiryError);
		InsuranceCompanyDecision insuranceCompanyDecision = new InsuranceCompanyDecision(requestedAmount,
				approvedAmount, ePrescriptionStatus, prescriptionInquiryError);
		EPrescriptionInquiryDrugList inquiryDrugList = new EPrescriptionInquiryDrugList(drugCode1, unitType, quantity,
				unitPrice, orderingClinician, duration, useUnitValue, frequency, frequencyOthersDescription, date, date,
				totalPatientShareValue, currency, totalPayerShareValue, currency, pbmValidationResult,
				insuranceCompanyDecision, totalPatientShareValue, totalPayerShareValue);
		drugList.add(inquiryDrugList);
		return drugList;
	}

	private PrescriptionRequest generatePrescriptionRequest() {
		return new PrescriptionRequest(requestId, payerId, providerId, timestamp, timestamp,
				RequestStatusType.PARTIAL_APPROVED.value(), null, ePrescriptionReferenceNumber, new BigDecimal(0),
				new BigDecimal(0), caseType, currency, currency);
	}

	private ResponseEntity<MemberDemographicDataResponseModel> generateMemberDemographicDataResponseModel() {
		List<PolicyInformationModel> policyInformationList = new ArrayList<>();
		policyInformationList
				.add(new PolicyInformationModel(policyNumber, "TEST", "001014523658001", classCode, classCode));
		MemberDemographicDataResponseModel response = new MemberDemographicDataResponseModel("TEST", 1234567890L,
				GenderType.FEMALE.value(), new Date(), "", "saudi", "00966504875128", "test@hotmail.com", "32 Years",
				policyInformationList);
		return ResponseEntity.ok(response);
	}

	private EmailNotificationResponseModel generateEmailNotificationResponse() {
		return new EmailNotificationResponseModel("SUCCESS", "Email sent");
	}

	private List<ServiceInfo> generatePrescriptionServiceWithScientificCode() {
		List<ServiceInfo> list = new ArrayList<>();
		ServiceInfo serviceInfo1 = generateServiceInfo("");
		serviceInfo1.setDrugListId(drugListId);
		serviceInfo1.setScientificCode(scientificCode1);
		list.add(serviceInfo1);
		ServiceInfo serviceInfo2 = generateServiceInfo("");
		serviceInfo2.setDrugListId(drugListId);
		serviceInfo2.setScientificCode(scientificCode2);
		list.add(serviceInfo2);
		return list;
	}

	private ServiceInfo generateServiceInfo(String drugcode) {
		return new ServiceInfo(id, drugcode, unitType, unitPrice, quantity, requestedAmount, orderingClinician, date,
				date, id, frequency, frequencyOthersDescription, requestId);
	}

	private List<DrugService> generateDrugServices() {
		List<DrugService> drugServices = new ArrayList<>();
		DrugService drugService1 = getDrugDetails(drugCode1, drugName1, scientificCode1, scientificName1);
		DrugService drugService2 = getDrugDetails(drugCode2, drugName2, scientificCode2, scientificName2);
		drugServices.add(drugService1);
		drugServices.add(drugService2);
		return drugServices;
	}

	private DrugService getDrugDetails(String drugCode, String drugName, String scientificCode, String scientificName) {
		DrugService drugService = new DrugService();
		drugService.setCategory("PHARMACEUTICAL");
		drugService.setCode("06285147014149");
		drugService.setDisplay(drugName);
		drugService.setDosageForm("TABLETS");
		drugService.setDrugListId(drugListId);
		drugService.setGranularUnit("20");
		drugService.setIngredients(scientificName);
		drugService.setLastUpdatedDate(date);
		drugService.setManufacturer("TABUK PHARMACEUTICAL MANUFACTURING CO.,SAUDI ARABIA");
		drugService.setOtherCodesType("SFDA");
		drugService.setOtherCodesValue(drugCode);
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
		drugService.setScientificCode(scientificCode);
		return drugService;
	}

	private ServiceResponseInfo generateServiceResponseInfo() {
		return new ServiceResponseInfo(id, requestId, memberHeight, memberHeight, 15.3, memberWeight, memberWeight,
				ServiceStatus.REJECTED.name(), "", id);
	}

	private List<ServiceRejectionDTO> generateServiceRejectionDTOList() {
		List<ServiceRejectionDTO> list = new ArrayList<>();
		list.add(generateServiceRejectionDTO());
		return list;
	}

	private ServiceRejectionDTO generateServiceRejectionDTO() {
		ServiceRejectionDTO serviceRejectionDTO = new ServiceRejectionDTO() {
			private static final long serialVersionUID = 5000064387677606418L;

			@Override
			public Long getServiceResponseId() {
				return id;
			}

			@Override
			public String getScientificCode() {
				return scientificCode1;
			}

			@Override
			public String getRequestId() {
				return requestId;
			}

			@Override
			public String getRejectionReason() {
				return rejectionReason;
			}

			@Override
			public String getIsModifiedByPayer() {
				return "0";
			}

			@Override
			public Long getId() {
				return id;
			}

			@Override
			public String getEligibilityReferenceNumber() {
				return ePrescriptionReferenceNumber;
			}

			@Override
			public String getDrugCode() {
				return drugCode1;
			}

			@Override
			public String getDenialCode() {
				return "FDB_CPINDI001";
			}
		};
		return serviceRejectionDTO;
	};

	private PrescriptionApprovalDrug generatePrescriptionApprovalDrug() {
		return new PrescriptionApprovalDrug(id, ePrescriptionReferenceNumber, timestamp, scientificCode1, status,
				drugCode1);
	}

	private void setRejectedStatusForDrugByPayer() {
		List<EPrescriptionInquiryError> prescriptionInquiryError = new ArrayList<>();
		prescriptionInquiryError
				.add(new EPrescriptionInquiryError(CommonDenialsCode.REQUIRED_PAYER_APPROVAL.value(), rejectionReason));
		InsuranceCompanyDecision insuranceCompanyDecision = new InsuranceCompanyDecision(requestedAmount,
				BigDecimal.ZERO, RequestStatusType.REJECTED.value(), prescriptionInquiryError);
		ePrescriptionInquiryRequestModel.getDrugList().get(0).setInsuranceCompanyDecision(insuranceCompanyDecision);
		serviceResponseInfo.setStatus(RequestStatusType.PENDING.value());
		;
	}
}
