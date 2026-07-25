package com.waseel.prescription.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import com.waseel.prescription.model.dispense.DispensableDrugs;
import com.waseel.prescription.model.dispense.DispenseDrugsRequestModel;
import com.waseel.prescription.model.enums.BenefitCaseType;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.FrequencyType;
import com.waseel.prescription.model.enums.PhysicianCategory;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.enums.UnitType;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionDrugList;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionRequestModel;
import com.waseel.prescription.model.pbmpayerapis.PbmValidationResult;
import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.Physician;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.repository.businessrules.CommonDenialsRepository;
import com.waseel.prescription.repository.prescriptionservice.DiagnosisRepository;
import com.waseel.prescription.repository.prescriptionservice.MappingPayerIdRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.PhysicianRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;
import com.waseel.prescription.service.mapper.MapperService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles({ "test" })
class TawuniyaEPrescriptionApprovalTests {

	@Autowired
	private MapperService mapperService;

	@MockBean
	private ServiceInfoRepository serviceInfoRepository;

	@MockBean
	private ServiceResponseInfoRepository serviceResponseInfoRepository;

	@MockBean
	private DiagnosisRepository diagnosisRepository;
	@MockBean
	private MemberInfoRepository memberInfoRepository;
	@MockBean
	private PhysicianRepository physicianRepository;
	@MockBean
	private PrescriptionRequestRepository prescriptionRequestRepository;
	@MockBean
	private ServiceRejectionRepository serviceRejectionRepository;
	@MockBean
	private MappingPayerIdRepository mappingPayerIdRepository;
	@MockBean
	private CommonDenialsRepository commonDenialsRepository;

	private DispenseDrugsRequestModel dispenseDrugsRequestModel;
	private PrescriptionRequest prescriptionRequest;
	private List<DiagnosisCodes> diagnosisCodesList;
	private MemberInfo memberInfo;
	private Physician physician;
	private List<ServiceInfo> serviceInfoList;
	private ServiceResponseInfo serviceResponseInfo;
	private List<ServiceResponseInfo> serviceResponseInfoList;
	private final String ePrescriptionReferenceNumber = "2023-1";
	private final String requestId = "8890e048-a1ba-44c7-b0c7-86393cc5773b";
	private final String payerId = "102";
	private String providerId = "12";
	private static final String currency = Currency.SAR.value();
	private Date date = new Date();
	private String scientificCode1 = "7000000589-200-100000073863";
	private String scientificCode2 = "14000001367-525-100000073664";
	private final String memberNationality = "Saudi Arabia";

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		dispenseDrugsRequestModel = generateDispenseDrugsRequestModel();
		prescriptionRequest = generatePrescriptionRequest();
		diagnosisCodesList = generateDiagnosisCodesList();
		memberInfo = generateMemberInfo();
		prescriptionRequest.setMemberInfo(memberInfo);
		physician = generatePhysicianDetails();
		serviceInfoList = generateListOfServiceInfo();
		serviceResponseInfo = generateServiceResponseInfo();
		serviceResponseInfoList = generateServiceResponseInfoList();
	}

	@BeforeEach
	public void mockCommonData() {
		Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(prescriptionRequest);
		Mockito.when(diagnosisRepository.save(Mockito.any())).thenReturn(diagnosisCodesList.get(0));
		Mockito.when(physicianRepository.save(Mockito.any())).thenReturn(physician);
		Mockito.when(serviceInfoRepository.save(Mockito.any())).thenReturn(serviceInfoList.get(0))
				.thenReturn(serviceInfoList.get(1));
		Mockito.when(serviceResponseInfoRepository.save(Mockito.any())).thenReturn(serviceResponseInfoList.get(0))
				.thenReturn(serviceResponseInfoList.get(1));
		Mockito.when(diagnosisRepository.findByRequestIdAndIsNotDeleted(requestId)).thenReturn(diagnosisCodesList);
		Mockito.when(physicianRepository.findByRequestId(requestId)).thenReturn(Optional.of(physician));
		Mockito.when(serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId)).thenReturn(serviceInfoList);
		Mockito.when(
				serviceResponseInfoRepository.findByRequestIdAndServiceID(requestId, serviceInfoList.get(0).getId()))
				.thenReturn(Optional.of(serviceResponseInfoList.get(0)));
		Mockito.when(
				serviceResponseInfoRepository.findByRequestIdAndServiceID(requestId, serviceInfoList.get(1).getId()))
				.thenReturn(Optional.of(serviceResponseInfoList.get(1)));
		Mockito.when(
				serviceRejectionRepository.findByRequestIdAndServiceResponseId(requestId, serviceResponseInfo.getId()))
				.thenReturn(Optional.of(new ArrayList<>()));
	}

	@Test
	@DisplayName("Pending Prescription result")
	void testGetEPrescriptionRequestModelForPendingPrescription() {
		EPrescriptionRequestModel result = mapperService.getEPrescriptionRequestModelFromPrescriptionRequest(
				prescriptionRequest, RequestType.DISPENSED.value(), dispenseDrugsRequestModel);
		assertNotNull(result);
		assertEquals(RequestStatusType.PENDING.value(), result.getePrescriptionStatus());
		assertEquals(ePrescriptionReferenceNumber, result.getePrescriptionReferenceNumber());
		assertNotNull(result.getDiagnosisCodes());
		List<EPrescriptionDrugList> drugList = result.getDrugList();
		assertNotNull(drugList);
		PbmValidationResult pbmValidationResult1 = drugList.get(0).getPbmValidationResult();
		assertNotNull(pbmValidationResult1);
		PbmValidationResult pbmValidationResult2 = drugList.get(1).getPbmValidationResult();
		assertNotNull(pbmValidationResult2);
		assertEquals(ServiceStatus.PENDING.name(), pbmValidationResult1.getStatus());
		assertEquals(ServiceStatus.DISPENSED.name(), pbmValidationResult2.getStatus());
		assertEquals(scientificCode1, drugList.get(0).getScientificCode());
		assertEquals(scientificCode2, drugList.get(1).getScientificCode());
	}

	private List<ServiceResponseInfo> generateServiceResponseInfoList() {
		List<ServiceResponseInfo> serviceResInfoList = new ArrayList<>();
		serviceResInfoList.add(generateServiceResponseInfo(scientificCode1, 1L));
		serviceResInfoList.add(generateServiceResponseInfo(scientificCode2, 2L));
		return serviceResInfoList;
	}

	private ServiceResponseInfo generateServiceResponseInfo(String scientificCode, long id) {
		return new ServiceResponseInfo(id, requestId, new BigDecimal(3), new BigDecimal(3), 15.3, new BigDecimal(10.2),
				new BigDecimal(30.2), ServiceStatus.APPROVED.name(), "", id);
	}

	private ServiceResponseInfo generateServiceResponseInfo() {
		return new ServiceResponseInfo(1l, requestId, new BigDecimal(3), new BigDecimal(3), 15.3, new BigDecimal(10.2),
				new BigDecimal(30.2), ServiceStatus.APPROVED.name(), "", 1L);
	}

	private List<ServiceInfo> generateListOfServiceInfo() {
		List<ServiceInfo> serviceInfoList = new ArrayList<>();
		serviceInfoList.add(new ServiceInfo(1L, null, 12L, FrequencyType.AT_BED_TIME.value(), "", new BigDecimal(6),
				12D, UnitType.UNIT.value(), 12D, "", date, date, scientificCode1));
		serviceInfoList.add(new ServiceInfo(2L, null, 12L, FrequencyType.AT_BED_TIME.value(), "", new BigDecimal(6),
				12D, UnitType.UNIT.value(), 12D, "", date, date, scientificCode2));
		return serviceInfoList;
	}

	private Physician generatePhysicianDetails() {
		return new Physician(1L, "32", requestId, "Dr.Test", PhysicianCategory.CONSULTANT.value(), "Test");
	}

	private MemberInfo generateMemberInfo() {
		return new MemberInfo("Test", 12l, "Test", new Date(), 55.1, 5.1, "Male", requestId, "Test", memberNationality);
	}

	private List<DiagnosisCodes> generateDiagnosisCodesList() {
		List<DiagnosisCodes> diagnosisCodes = new ArrayList<>();
		diagnosisCodes.add(new DiagnosisCodes("R25.2", "PRIMARY", ""));
		return diagnosisCodes;
	}

	private PrescriptionRequest generatePrescriptionRequest() {
		return new PrescriptionRequest(requestId, payerId, providerId,
				new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), RequestStatusType.APPROVED.value(), "",
				ePrescriptionReferenceNumber, new BigDecimal(12), new BigDecimal(12), BenefitCaseType.INPATIENT.value(),
				currency, currency);
	}

	public void generateMockUserInfo() {
		Map<String, Object> details = new HashMap<String, Object>();
		details.put("accId", "accId");
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

	private DispenseDrugsRequestModel generateDispenseDrugsRequestModel() {
		List<DispensableDrugs> drugList = new ArrayList<>();
		drugList.add(new DispensableDrugs("PREDNISOLONE", scientificCode1, "130-334-10", 12D, 3, new BigDecimal(12),
				new BigDecimal(12), currency, currency, true));
		drugList.add(new DispensableDrugs("PREDNISOLONE", scientificCode2, "100-334-10", 12D, 3, new BigDecimal(12),
				new BigDecimal(12), currency, currency, false));
		DispenseDrugsRequestModel model = new DispenseDrugsRequestModel(new BigDecimal(12), new BigDecimal(12),
				currency, currency, drugList);
		return model;
	}

}
