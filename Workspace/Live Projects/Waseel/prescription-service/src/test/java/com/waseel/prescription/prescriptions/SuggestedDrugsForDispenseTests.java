package com.waseel.prescription.prescriptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Year;
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
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.dispense.PrescriptionDispenseResponseModel;
import com.waseel.prescription.model.dispense.PrescriptionDrug;
import com.waseel.prescription.model.dispense.SuggestedDrug;
import com.waseel.prescription.model.dispense.SuggestedDrugsModel;
import com.waseel.prescription.model.enums.BenefitCaseType;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.PolicyConsumptionStatus;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.exclusion.DrugExclusionResponseModel;
import com.waseel.prescription.model.formulary.DrugFormularyResponseModel;
import com.waseel.prescription.model.policyconsumption.DrugListModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.persist.businessrules.BenefitCodePhyscSpecAssc;
import com.waseel.prescription.persist.businessrules.BenefitCodes;
import com.waseel.prescription.persist.businessrules.Department;
import com.waseel.prescription.persist.businessrules.PhysicianInfo;
import com.waseel.prescription.persist.businessrules.Speciality;
import com.waseel.prescription.persist.mdss.DrugService;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.Physician;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionApprovalDrug;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.repository.businessrules.BenefitCodePhyscSpecAsscRepository;
import com.waseel.prescription.repository.businessrules.DeptSpecPhyscAsscRepository;
import com.waseel.prescription.repository.businessrules.PhysicianInfoRepository;
import com.waseel.prescription.repository.businessrules.SpecialityRepository;
import com.waseel.prescription.repository.hira.SwitchAccountRepository;
import com.waseel.prescription.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.prescription.repository.mdss.DrugServiceRepository;
import com.waseel.prescription.repository.prescriptionservice.PhysicianRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionApprovalDrugRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.service.clienthandler.PolicyConsumptionRestHandler;
import com.waseel.prescription.service.clienthandler.RestHandler;
import com.waseel.prescription.service.prescriptions.DrugSuggestionsService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles({ "test" })
@TestMethodOrder(OrderAnnotation.class)
class SuggestedDrugsForDispenseTests {

	@Autowired
	private DrugSuggestionsService drugSuggestionsService;

	@MockBean
	private RestHandler restHandler;

	@MockBean
	private PrescriptionRequestRepository prescriptionRequestRepository;

	@MockBean
	private PhysicianRepository physicianRepository;

	@MockBean
	private ServiceInfoRepository serviceInfoRepository;

	@MockBean
	private PhysicianInfoRepository physicianInfoRepository;

	@MockBean
	private DeptSpecPhyscAsscRepository deptSpecPhyscAsscRepository;

	@MockBean
	private SwitchAccountRepository switchAccountRepository;

	@MockBean
	private DrugServiceRepository drugServiceRepository;

	@MockBean
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	@MockBean
	PrescriptionApprovalDrugRepository prescriptionApprovedDrugRepository;

	@MockBean
	private PolicyConsumptionRestHandler policyConsumptionRestHandler;

	@MockBean
	private BenefitCodePhyscSpecAsscRepository benefitCodePhyscSpecAsscRepository;

	@MockBean
	private SpecialityRepository specialityRepository;

	private static final String currentYear = Year.now().toString();
	private static final String ePrescriptionReferenceNumber = currentYear + "-1";
	private static final String payerId = "102";
	private static final String drugCode1 = "1501233101";
	private static final String drugCode2 = "1808210952";
	private static final String drugCode3 = "425-277-20";
	private static final String drugCode4 = "1-5614-21";
	private static final String drugName1 = "REBIF 22 MCG PRE-FILLED SYRINGE";
	private static final String drugName2 = "Laroza";
	private static final String drugName3 = "LATUDA 18.5MG F.C.TABLET";
	private static final String drugName4 = "DEBILUR";
	private static final String scientificCode1 = "7000000687-6000000-200000016494";
	private static final String scientificCode2 = "7000000687-6000000-200000016495";
	private static final String scientificCode3 = "7000000687-6000000-200000016496";
	private static final String scientificCode4 = "7000001633-70-100000074039";
	private static final String scientificName1 = "INTERFERON BETA-1A";
	private static final String scientificName2 = "INTERFERON BETA-2A";
	private static final String scientificName3 = "LURASIDONE";
	private static final String scientificName4 = "LURASIDONE";
	private static final String physicianName = "Dr. test";
	private static final Long drugListId = 1L;
	private static final String invalidPolicyStatusDescription = "1808210952 had invalid request.";
	private static final String brandedBenefitCase = "REPLACEABLE_BRAND";
	private static final String genericBenefitCase = "IRREPLACEABLE_BRAND";
	private PrescriptionRequest prescriptionRequest = null;
	private static final String providerId = "801";
	private static final String requestId = "0fb78ee7-9e62-4d9e-aee7-18f099e126f1";
	private static final Timestamp timestamp = (new Timestamp(Calendar.getInstance().getTimeInMillis()));
	protected static final int CONTENT_CACHE_LIMIT = 3;
	private final MockHttpServletRequest request = new MockHttpServletRequest();
	ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(this.request, CONTENT_CACHE_LIMIT);
	private static final Date date = new Date();
	private static final String memberName = "Salim";
	private static final String currency = Currency.SAR.value();
	private static final String policyStatusDescription = "Member policy details for dispensible drugs.";
	private static final String maxPatientShareAmount = "300";
	private static final String patientSharePercentage = "10";
	private static final Long id = 1L;
	private BenefitCodePhyscSpecAssc benefitCodePhyscSpecAssc = null;
	private static final String benefitCode = "Dental Benefit";
	private Speciality speciality = null;
	private static final String specialityName = "Anesthesia Cardiology";

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		prescriptionRequest = generatePrescriptionRequest();
		benefitCodePhyscSpecAssc = populateBenefitCodePhyscSpecAssc();
		speciality = populateSpeciality();
	}

	@BeforeEach
	public void setUpMockData() {
		commonMockData();
	}

	private void commonMockData() {
		Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(Mockito.any()))
				.thenReturn(Optional.of(prescriptionRequest));
		Mockito.when(serviceInfoRepository.findDrugsByRequestIdAndIsDeletedAndScientificCodeNullAndStatus(Mockito.any(),
				Mockito.any())).thenReturn(Optional.of(generateServiceInfoForGenericDrugs()));
		Mockito.when(serviceInfoRepository.findDrugsByRequestIdAndIsDeletedAndScientificCodeNullAndStatus(Mockito.any(),
				Mockito.any())).thenReturn(Optional.of(generateServiceInfoForBrandedDrugs()));
		Mockito.when(drugServiceMetaDataRepository.getActiveDrugServiceList(Mockito.any()))
				.thenReturn(Optional.of(drugListId));
		Mockito.when(drugServiceRepository.findByDrugListIdAndOtherCodesValueIn(Mockito.anyLong(), Mockito.any()))
				.thenReturn(Optional.of(generateDrugServiceForBrandedDrugs()));
		Mockito.when(drugServiceRepository.findByDrugListIdAndScientificCodeIn(Mockito.anyLong(), Mockito.any()))
				.thenReturn(Optional.of(generateDrugServiceForGenericDrugs()));
		Mockito.when(physicianRepository.findByRequestId(Mockito.any()))
				.thenReturn(Optional.of(generatePhysicianDetails()));
		Mockito.when(restHandler.sendPrescriptionRequestToDrugExclusion(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(generateDrugExclusionResponse());
		Mockito.when(restHandler.sendPrescriptionRequestToDrugFormularyService(Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(generateDrugFormularyResponse());
		Mockito.when(prescriptionApprovedDrugRepository.findByEprescriptionReferenceNumber(Mockito.any()))
				.thenReturn(Optional.of(generatePrescriptionApprovalDrug()));
		Mockito.when(physicianInfoRepository.findByRegistrationNumber(Mockito.any()))
				.thenReturn(Optional.of(generatePhysicianInfo()));
		Mockito.when(deptSpecPhyscAsscRepository.findByPhysicianInfoId(Mockito.anyLong()))
				.thenReturn(Optional.of(generateDepartment()));
		Mockito.when(
				benefitCodePhyscSpecAsscRepository.findBySpecialityIdAndIsEnabled(Mockito.any(), Mockito.anyBoolean()))
				.thenReturn(Optional.of(benefitCodePhyscSpecAssc));
		Mockito.when(specialityRepository.findBySpecialityNameAndIsDeleted(Mockito.any(), Mockito.anyBoolean()))
				.thenReturn(Optional.of(speciality));
	}

	@Order(1)
	@Test
	@DisplayName("Rejected status from policy consumption.")
	void rejectedFromPolicyConsumptionTest() {
		try {
			PolicyResponseModel policyResponseModel = generatePolicyConsumptionResponse();
			policyResponseModel.setStatus(PolicyConsumptionStatus.REJECTED.getValue());
			policyResponseModel.setStatusDescription(invalidPolicyStatusDescription);
			Mockito.when(policyConsumptionRestHandler.getPayerAndPatientShareForDispensibleDrugs(Mockito.any(),
					Mockito.any())).thenReturn(policyResponseModel);
			drugSuggestionsService.getSuggestedDrugs(ePrescriptionReferenceNumber, payerId, false);
		} catch (PrescriptionException prescriptionException) {
			PrescriptionDispenseResponseModel invalidDispenseResposne = prescriptionException
					.getDispensedResponseModel();
			assertNotNull(invalidDispenseResposne);
			assertNotNull(invalidDispenseResposne.getePrescriptionReferenceNumber());
			assertEquals(ePrescriptionReferenceNumber, invalidDispenseResposne.getePrescriptionReferenceNumber());
			assertNotNull(invalidDispenseResposne.getStatus());
			assertNotEquals(PolicyConsumptionStatus.APPROVED.getValue(),
					invalidDispenseResposne.getePrescriptionReferenceNumber());
			assertNotNull(invalidDispenseResposne.getStatusDescription());
			assertEquals(invalidPolicyStatusDescription, invalidDispenseResposne.getStatusDescription());
		}
	}

	@Order(2)
	@Test
	@DisplayName("Success Response.")
	void successResponseTest() throws PrescriptionException {
		Mockito.when(
				policyConsumptionRestHandler.getPayerAndPatientShareForDispensibleDrugs(Mockito.any(), Mockito.any()))
				.thenReturn(generatePolicyConsumptionResponse());
		SuggestedDrugsModel suggestedDrugsModel = drugSuggestionsService.getSuggestedDrugs(ePrescriptionReferenceNumber,
				payerId, false);
		assertNotNull(suggestedDrugsModel);
		assertNotNull(suggestedDrugsModel.getPrescriptionDrugs());
		List<PrescriptionDrug> prescriptionDrugs = suggestedDrugsModel.getPrescriptionDrugs();
		assertFalse(prescriptionDrugs.isEmpty());
		prescriptionDrugs.stream().forEach(drug -> {
			assertNotNull(drug.getSuggestedDrugs());
			List<SuggestedDrug> suggestedDrugs = drug.getSuggestedDrugs();
			suggestedDrugs.stream().forEach(suggestedDrug -> {
				if (suggestedDrug.getSfdaCode().equals(drugCode1) || suggestedDrug.getSfdaCode().equals(drugCode2)) {
					assertEquals(brandedBenefitCase, suggestedDrug.getBenefitCase());
				} else {
					assertEquals(genericBenefitCase, suggestedDrug.getBenefitCase());
				}
			});
		});
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

	private PrescriptionRequest generatePrescriptionRequest() {
		PrescriptionRequest prescriptionRequest = new PrescriptionRequest(requestId, payerId, providerId, timestamp,
				timestamp, RequestStatusType.APPROVED.value(), null, ePrescriptionReferenceNumber, new BigDecimal(0),
				new BigDecimal(0), BenefitCaseType.INPATIENT.value(), currency, currency);
		prescriptionRequest.setMemberInfo(generateMemberInfo());
		return prescriptionRequest;
	}

	private MemberInfo generateMemberInfo() {
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
		return memberInfo;
	}

	private List<ServiceInfo> generateServiceInfoForGenericDrugs() {
		List<ServiceInfo> list = new ArrayList<>();
		list.add(new ServiceInfo(1L, "package", 318.00, new BigDecimal(3), new BigDecimal(31.0), "test", date, date, 8L,
				"test", "test", requestId, scientificCode1));
		list.add(new ServiceInfo(2L, "package", 318.00, new BigDecimal(3), new BigDecimal(31.0), "test", date, date, 8L,
				"test", "test", requestId, scientificCode2));
		return list;
	}

	private List<ServiceInfo> generateServiceInfoForBrandedDrugs() {
		List<ServiceInfo> list = new ArrayList<>();
		list.add(new ServiceInfo(1L, drugCode3, "package", 318.00, new BigDecimal(3), new BigDecimal(31.0), "test",
				date, date, 8L, "test", "test", requestId));
		list.add(new ServiceInfo(2L, drugCode4, "package", 318.00, new BigDecimal(3), new BigDecimal(31.0), "test",
				date, date, 8L, "test", "test", requestId));
		return list;
	}

	private List<DrugService> generateDrugServiceForBrandedDrugs() {
		List<DrugService> brandedDrugDetails = new ArrayList<>();
		DrugService drugService1 = getDrugDetails(drugCode1, drugName1, scientificCode1, scientificName1);
		DrugService drugService2 = getDrugDetails(drugCode2, drugName2, scientificCode2, scientificName2);
		brandedDrugDetails.add(drugService1);
		brandedDrugDetails.add(drugService2);
		return brandedDrugDetails;
	}

	private List<DrugService> generateDrugServiceForGenericDrugs() {
		List<DrugService> brandedDrugDetails = new ArrayList<>();
		DrugService drugService1 = getDrugDetails(drugCode3, drugName3, scientificCode3, scientificName3);
		DrugService drugService2 = getDrugDetails(drugCode4, drugName4, scientificCode4, scientificName4);
		brandedDrugDetails.add(drugService1);
		brandedDrugDetails.add(drugService2);
		return brandedDrugDetails;
	}

	private DrugService getDrugDetails(String drugCode, String drugName, String scientificCode, String scientificName) {
		DrugService drugService = new DrugService();
		drugService.setCategory("PHARMACEUTICAL");
		drugService.setCode("06285147014149");
		drugService.setDisplay(drugName);
		drugService.setDosageForm("TABLETS");
		drugService.setDrugListId(1l);
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

	private Physician generatePhysicianDetails() {
		return new Physician(1L, "33", requestId, physicianName, "SPECIALIST", "Adult ENT");
	}

	private DrugExclusionResponseModel generateDrugExclusionResponse() {
		return new DrugExclusionResponseModel(requestId, getDrugList(), "", "");
	}

	private List<com.waseel.prescription.model.exclusion.DrugList> getDrugList() {
		List<com.waseel.prescription.model.exclusion.DrugList> drugList = new ArrayList<>();
		com.waseel.prescription.model.exclusion.DrugList firstDrug = new com.waseel.prescription.model.exclusion.DrugList(
				"APPROVED", drugCode1, null);
		drugList.add(firstDrug);
		com.waseel.prescription.model.exclusion.DrugList secondDrug = new com.waseel.prescription.model.exclusion.DrugList(
				"APPROVED", drugCode2, null);
		drugList.add(secondDrug);
		com.waseel.prescription.model.exclusion.DrugList thirdDrug = new com.waseel.prescription.model.exclusion.DrugList(
				"APPROVED", drugCode3, null);
		drugList.add(thirdDrug);
		com.waseel.prescription.model.exclusion.DrugList fourthDrug = new com.waseel.prescription.model.exclusion.DrugList(
				"APPROVED", drugCode4, null);
		drugList.add(fourthDrug);
		return drugList;
	}

	private List<DrugFormularyResponseModel> generateDrugFormularyResponse() {
		List<DrugFormularyResponseModel> response = new ArrayList<>();
		DrugFormularyResponseModel drugFormularyResponseForFirstDrug = getDrugFormularyResponse(drugCode1);
		response.add(drugFormularyResponseForFirstDrug);
		DrugFormularyResponseModel drugFormularyResponseForSecondDrug = getDrugFormularyResponse(drugCode2);
		response.add(drugFormularyResponseForSecondDrug);
		DrugFormularyResponseModel drugFormularyResponseForThirdDrug = getDrugFormularyResponse(drugCode3);
		response.add(drugFormularyResponseForThirdDrug);
		DrugFormularyResponseModel drugFormularyResponseForFourthDrug = getDrugFormularyResponse(drugCode4);
		response.add(drugFormularyResponseForFourthDrug);
		return response;
	}

	private DrugFormularyResponseModel getDrugFormularyResponse(String drugcode) {
		return new DrugFormularyResponseModel("APPROVED", "", "", drugcode);
	}

	private List<PrescriptionApprovalDrug> generatePrescriptionApprovalDrug() {
		List<PrescriptionApprovalDrug> approvalDrugs = new ArrayList<>();
		PrescriptionApprovalDrug firstApprovedDrug = getApprovedDrugDetails(drugCode1, scientificCode1);
		approvalDrugs.add(firstApprovedDrug);
		PrescriptionApprovalDrug secondApprovedDrug = getApprovedDrugDetails(drugCode2, scientificCode2);
		approvalDrugs.add(secondApprovedDrug);
		PrescriptionApprovalDrug thirdApprovedDrug = getApprovedDrugDetails(drugCode3, scientificCode3);
		approvalDrugs.add(thirdApprovedDrug);
		PrescriptionApprovalDrug fourthApprovedDrug = getApprovedDrugDetails(drugCode4, scientificCode4);
		approvalDrugs.add(fourthApprovedDrug);
		return approvalDrugs;
	}

	private PrescriptionApprovalDrug getApprovedDrugDetails(String drugCode, String scientificCode) {
		return new PrescriptionApprovalDrug(id, ePrescriptionReferenceNumber, timestamp, scientificCode, "APPROVED",
				drugCode);
	}

	private Department generateDepartment() {
		return new Department(1L, "department");
	}

	private PhysicianInfo generatePhysicianInfo() {
		return new PhysicianInfo(1L, 801L, "78654321", "Dr. Khan", null);
	}

	private PolicyResponseModel generatePolicyConsumptionResponse() {
		return new PolicyResponseModel(requestId, PolicyConsumptionStatus.APPROVED.getValue(), policyStatusDescription,
				String.valueOf(HttpStatus.OK.value()), policyStatusDescription, "", "", getDrugListModel(),
				maxPatientShareAmount, patientSharePercentage, maxPatientShareAmount, patientSharePercentage, currency,
				currency);
	}

	private List<DrugListModel> getDrugListModel() {
		List<DrugListModel> drugs = new ArrayList<>();
		DrugListModel firstDrug = new DrugListModel(drugCode1, new BigDecimal(20), "%", new BigDecimal(200), "SAR",
				brandedBenefitCase);
		drugs.add(firstDrug);
		DrugListModel secondDrug = new DrugListModel(drugCode2, new BigDecimal(30), "%", new BigDecimal(200), "SAR",
				brandedBenefitCase);
		drugs.add(secondDrug);
		DrugListModel thirdDrug = new DrugListModel(drugCode3, new BigDecimal(20), "%", new BigDecimal(200), "SAR",
				brandedBenefitCase);
		drugs.add(thirdDrug);
		DrugListModel fourthDrug = new DrugListModel(drugCode4, new BigDecimal(50), "%", new BigDecimal(200), "SAR",
				brandedBenefitCase);
		drugs.add(fourthDrug);
		return drugs;
	}

	private BenefitCodePhyscSpecAssc populateBenefitCodePhyscSpecAssc() {
		BenefitCodePhyscSpecAssc benefitCodePhyscSpecAssc = new BenefitCodePhyscSpecAssc(id, BigDecimal.ONE, id, true);
		benefitCodePhyscSpecAssc.setBenefitCodes(new BenefitCodes(id, benefitCode));
		return benefitCodePhyscSpecAssc;
	}

	private Speciality populateSpeciality() {
		return new Speciality(BigDecimal.ONE, specialityName, date, Boolean.FALSE);
	}
}
