package com.waseel.pbm.payercustomizationservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import com.waseel.pbm.payercustomizationservice.enums.CustomizationModuleName;
import com.waseel.pbm.payercustomizationservice.enums.CustomizationRejectionCategory;
import com.waseel.pbm.payercustomizationservice.enums.CustomizationRequestMetaDataStatus;
import com.waseel.pbm.payercustomizationservice.enums.EntityNames;
import com.waseel.pbm.payercustomizationservice.exceptions.PayerCustomizationException;
import com.waseel.pbm.payercustomizationservice.model.CustomizationRequestModel;
import com.waseel.pbm.payercustomizationservice.model.CustomizationResponseModel;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestDetail;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestMetadata;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestsAudit;
import com.waseel.pbm.payercustomizationservice.repository.CustomizationRequestDetailsRepository;
import com.waseel.pbm.payercustomizationservice.repository.CustomizationRequestMetadataRepository;
import com.waseel.pbm.payercustomizationservice.repository.CustomizationRequestsAuditRepository;
import com.waseel.pbm.payercustomizationservice.service.CustomizationRequestService;
import com.waseel.pbm.payercustomizationservice.util.UserInfoUtil;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class AddCustomizationRequestTests {

	@Autowired
	private CustomizationRequestService customizationRequestService;

	@MockBean
	private CustomizationRequestMetadataRepository customizationRequestMetadataRepository;

	@MockBean
	private CustomizationRequestsAuditRepository customizationRequestsAuditRepository;

	@MockBean
	private CustomizationRequestDetailsRepository customizationRequestDetailsRepository;

	private CustomizationRequestMetadata customizationRequestMetadata = null;
	private CustomizationRequestsAudit customizationRequestsAudit = null;
	private final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
	private final String drugCode = "1-809-14";
	private final String drugName = "CETRO 10MG F.C TAB";
	private final Date date = new Date();
	private final String payerId = "102";
	private final Long id = 1L;
	private final String ePrescriptionReferenceNumber = "2023-01";
	private final String icdCode = "M75.3";
	private final String interactedDrugCode = "176-277-061";

	@BeforeAll
	void prepareCommonData() {
		mockHttpServletRequest(mockHttpServletRequest);
		generateMockUserInfo();
		customizationRequestMetadata = populateCustomizationRequestMetadataTable();
		customizationRequestsAudit = populateCustomizationRequestsAudit();
	}

	@BeforeEach
	void prepareCommonDataBeforeEachUnitTest() {
		Mockito.when(customizationRequestMetadataRepository.save(Mockito.any()))
				.thenReturn(customizationRequestMetadata);
		Mockito.when(customizationRequestsAuditRepository.save(Mockito.any())).thenReturn(customizationRequestsAudit);
		Mockito.when(customizationRequestDetailsRepository.saveAll(Mockito.any()))
				.thenReturn(customizationRequestDetailsIterable());
	}

	@Order(1)
	@Test
	@DisplayName("Add Customization Request of Drug to diagnosis for ContraIndication")
	void addCustomizationRequestOfDrugToDiagnosisForContraIndication() throws PayerCustomizationException {
		mockNewCustomizationRequest();
		CustomizationResponseModel customizationResponseModel = customizationRequestService
				.managePayerCustomizationRequest(populateCustomizationRequestOfDrugToDiagnosisForContraIndication(),
						mockHttpServletRequest);
		assertNotNull(customizationResponseModel);
		assertNotNull(customizationResponseModel.getCustomizationRequestId());
		assertEquals(id, customizationResponseModel.getCustomizationRequestId());
	}

	@Order(2)
	@Test
	@DisplayName("Add Customization Request of Drug to diagnosis for Indication")
	void addCustomizationRequestOfDrugToDiagnosisForIndication() throws PayerCustomizationException {
		mockNewCustomizationRequest();
		CustomizationResponseModel customizationResponseModel = customizationRequestService
				.managePayerCustomizationRequest(populateCustomizationRequestOfDrugToDiagnosisForIndication(),
						mockHttpServletRequest);
		assertNotNull(customizationResponseModel);
		assertNotNull(customizationResponseModel.getCustomizationRequestId());
		assertEquals(id, customizationResponseModel.getCustomizationRequestId());
	}

	@Order(3)
	@Test
	@DisplayName("Add Customization Request For Drug to Drug")
	void addCustomizationRequestForDrugToDrug() throws PayerCustomizationException {
		mockNewCustomizationRequest();
		CustomizationResponseModel customizationResponseModel = customizationRequestService
				.managePayerCustomizationRequest(populateCustomizationRequestForDrugToDrug(), mockHttpServletRequest);
		assertNotNull(customizationResponseModel);
		assertNotNull(customizationResponseModel.getCustomizationRequestId());
		assertEquals(id, customizationResponseModel.getCustomizationRequestId());
	}

	@Order(4)
	@Test
	@DisplayName("Add Customization Request For Duplicate Therapy")
	void addCustomizationRequestForDuplicateTherapy() throws PayerCustomizationException {
		mockNewCustomizationRequest();
		CustomizationResponseModel customizationResponseModel = customizationRequestService
				.managePayerCustomizationRequest(populateCustomizationRequestForDuplicateTherapy(),
						mockHttpServletRequest);
		assertNotNull(customizationResponseModel);
		assertNotNull(customizationResponseModel.getCustomizationRequestId());
		assertEquals(id, customizationResponseModel.getCustomizationRequestId());
	}

	@Order(5)
	@Test
	@DisplayName("Add Customization Request For Gender")
	void addCustomizationRequestForGender() throws PayerCustomizationException {
		mockNewCustomizationRequest();
		CustomizationResponseModel customizationResponseModel = customizationRequestService
				.managePayerCustomizationRequest(populateCustomizationRequestForGender(), mockHttpServletRequest);
		assertNotNull(customizationResponseModel);
		assertNotNull(customizationResponseModel.getCustomizationRequestId());
		assertEquals(id, customizationResponseModel.getCustomizationRequestId());
	}

	@Order(6)
	@Test
	@DisplayName("Request in progress")
	void customizationRequestInProgress() {
		try {
			mockInProgressCustomizationRequest();
			customizationRequestService.managePayerCustomizationRequest(populateCustomizationRequestForGender(),
					mockHttpServletRequest);
		} catch (PayerCustomizationException customizationException) {
			CustomizationResponseModel customizationResponseModel = customizationException
					.getInvalidCustomizationResponse();
			assertNotNull(customizationResponseModel);
			assertNotNull(customizationResponseModel.getErrorMessage());
			assertEquals(1, customizationResponseModel.getErrorMessage().size());
			assertEquals(customizationResponseModel.getErrorMessage().get(0).getMessage(),
					"Customization Request is inprogress by payer:[" + payerId + "]");
		}
	}

	private CustomizationRequestMetadata populateCustomizationRequestMetadataTable() {
		return new CustomizationRequestMetadata(id, drugCode, drugName, false, date, "", payerId,
				"Medication 1-809-14 is not indicated with diagnosis code M75.3",
				CustomizationRequestMetaDataStatus.PC_PENDING_REQUEST.value());
	}

	private void mockHttpServletRequest(MockHttpServletRequest servletRequest) {
		servletRequest.setMethod("POST");
	}

	public void generateMockUserInfo() {
		Map<String, Object> details = new HashMap<String, Object>();
		details.put("accId", payerId);
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

	private CustomizationRequestsAudit populateCustomizationRequestsAudit() {
		return new CustomizationRequestsAudit(id,
				UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication()), id,
				EntityNames.CUSTOMIZATION_REQUEST_METADATA.name(), "POST", date, "",
				UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
	}

	private CustomizationRequestModel populateCommonFieldsForCustomizationRequest(String moduleName,
			String rejectionReason) {
		return new CustomizationRequestModel(ePrescriptionReferenceNumber, drugCode, drugName, moduleName,
				rejectionReason);
	}

	private CustomizationRequestModel populateCustomizationRequestOfDrugToDiagnosisForContraIndication() {
		CustomizationRequestModel customizationRequestModel = populateCommonFieldsForCustomizationRequest(
				CustomizationModuleName.DRUG_TO_DISEASE_INTERACTION_RULE.value(),
				"Medication  " + drugCode + " has SEVERE CONTRAINDICATION with diagnosis code " + icdCode
						+ ", HIGH ALERT: MEMBER HEALTH MIGHT SEVERELY HARMED");
		customizationRequestModel
				.setRejectionCategory(CustomizationRejectionCategory.DIAGNOSIS_CONTRAINDICATION.value());
		return customizationRequestModel;
	}

	private CustomizationRequestModel populateCustomizationRequestOfDrugToDiagnosisForIndication() {
		CustomizationRequestModel customizationRequestModel = populateCommonFieldsForCustomizationRequest(
				CustomizationModuleName.DRUG_TO_DISEASE_INTERACTION_RULE.value(),
				"Medication " + drugCode + " is not indicated with diagnosis code " + icdCode);
		customizationRequestModel.setRejectionCategory(CustomizationRejectionCategory.DIAGNOSIS_INDICATION.value());
		return customizationRequestModel;
	}

	private CustomizationRequestModel populateCustomizationRequestForDrugToDrug() {
		CustomizationRequestModel customizationRequestModel = populateCommonFieldsForCustomizationRequest(
				CustomizationModuleName.DRUG_TO_DRUG_INTERACTION_RULE.value(),
				"Requested drug " + drugCode + " with " + interactedDrugCode
						+ " has Severe Interactions, HIGH ALERT: MEMBER HEALTH MIGHT SEVERELY HARMED");
		return customizationRequestModel;
	}

	private CustomizationRequestModel populateCustomizationRequestForDuplicateTherapy() {
		CustomizationRequestModel customizationRequestModel = populateCommonFieldsForCustomizationRequest(
				CustomizationModuleName.DUPLICATE_THERAPY_RULE.value(),
				"Therapeutic Duplication : between drug  " + drugCode + " and drug  " + interactedDrugCode);
		return customizationRequestModel;
	}

	private CustomizationRequestModel populateCustomizationRequestForGender() {
		CustomizationRequestModel customizationRequestModel = populateCommonFieldsForCustomizationRequest(
				CustomizationModuleName.DRUG_TO_GENDER_INTERACTION_RULE.value(),
				"Gender rule violates the condition : MALE for drug " + drugCode);
		customizationRequestModel.setGender("MALE");
		return customizationRequestModel;
	}

	private Iterable<CustomizationRequestDetail> customizationRequestDetailsIterable() {
		CustomizationRequestDetail customizationRequestDetail = new CustomizationRequestDetail(id, id, "key", "value",
				"label");
		List<CustomizationRequestDetail> customizationRequestDetails = new ArrayList<>();
		customizationRequestDetails.add(customizationRequestDetail);
		Iterable<CustomizationRequestDetail> iterable = customizationRequestDetails;
		return iterable;
	}

	private void mockNewCustomizationRequest() {
		Mockito.when(customizationRequestMetadataRepository.findByPayerIdAndDrugCodeAndIsDeletedAndStatus(Mockito.any(),
				Mockito.any(), Mockito.anyBoolean(), Mockito.any())).thenReturn(Optional.empty());
	}

	private void mockInProgressCustomizationRequest() {
		Mockito.when(customizationRequestMetadataRepository.findByPayerIdAndDrugCodeAndIsDeletedAndStatus(Mockito.any(),
				Mockito.any(), Mockito.anyBoolean(), Mockito.any()))
				.thenReturn(Optional.of(customizationRequestMetadata));
	}
}
