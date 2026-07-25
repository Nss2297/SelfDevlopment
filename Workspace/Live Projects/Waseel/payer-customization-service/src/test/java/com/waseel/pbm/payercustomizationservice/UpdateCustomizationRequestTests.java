package com.waseel.pbm.payercustomizationservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import com.waseel.pbm.payercustomizationservice.enums.CustomizationModuleName;
import com.waseel.pbm.payercustomizationservice.enums.CustomizationRequestStatus;
import com.waseel.pbm.payercustomizationservice.exceptions.PayerCustomizationException;
import com.waseel.pbm.payercustomizationservice.model.CustomizationRequestModel;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestDetail;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestMetadata;
import com.waseel.pbm.payercustomizationservice.persist.PCAge;
import com.waseel.pbm.payercustomizationservice.persist.PCDrugToDiagnosis;
import com.waseel.pbm.payercustomizationservice.persist.PCDuplicateTherapy;
import com.waseel.pbm.payercustomizationservice.persist.PCGender;
import com.waseel.pbm.payercustomizationservice.persist.PCQuantityLimitCheck;
import com.waseel.pbm.payercustomizationservice.persist.PcDrugToDrug;
import com.waseel.pbm.payercustomizationservice.persist.mongodb.PCSAuditTrail;
import com.waseel.pbm.payercustomizationservice.repository.CustomizationRequestDetailsRepository;
import com.waseel.pbm.payercustomizationservice.repository.CustomizationRequestMetadataRepository;
import com.waseel.pbm.payercustomizationservice.repository.PCAgeRepository;
import com.waseel.pbm.payercustomizationservice.repository.PCDrugToDiagnosisRepository;
import com.waseel.pbm.payercustomizationservice.repository.PCDrugToDrugRepository;
import com.waseel.pbm.payercustomizationservice.repository.PCDuplicateTherapyRepository;
import com.waseel.pbm.payercustomizationservice.repository.PCGenderRepository;
import com.waseel.pbm.payercustomizationservice.repository.PCQuantityLimitCheckRepository;
import com.waseel.pbm.payercustomizationservice.repository.mongodb.PCSAuditTrailRepository;
import com.waseel.pbm.payercustomizationservice.service.CustomizationListService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles({ "test" })
public class UpdateCustomizationRequestTests {

	private Long customizationRequestId = 1L;
	private Long customizationRequestDetailsId = 682L;

	@MockBean
	private PCSAuditTrailRepository pcsAuditTrailRepository;
	@MockBean
	private CustomizationRequestMetadataRepository customizationRequestMetadataRepository;
	@MockBean
	private CustomizationRequestDetailsRepository customizationRequestDetailsRepository;
	@MockBean
	private PCDrugToDiagnosisRepository pcDrugToDiagnosisRepository;
	@MockBean
	private PCAgeRepository pcAgeRepository;
	@MockBean
	private PCDrugToDrugRepository pcDrugToDrugRepository;
	@MockBean
	private PCDuplicateTherapyRepository pcDuplicateTherapyRepository;
	@MockBean
	private PCGenderRepository pcGenderRepository;
	@MockBean
	private PCQuantityLimitCheckRepository pcQuantityLimitCheckRepository;

	@Autowired
	private CustomizationListService customizationListService;

	private CustomizationRequestDetail updatedCustomizationRequestDetail = null;

	private CustomizationRequestModel customizationRequestModel;

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		updatedCustomizationRequestDetail = populateUpdatedCustomizationRequestDetail();
	}

	@BeforeEach
	public void setUpData() {
		setUpCustomizationRequest();
		Mockito.when(pcsAuditTrailRepository.save(Mockito.any())).thenReturn(new PCSAuditTrail());
		Mockito.when(customizationRequestDetailsRepository.save(Mockito.any()))
				.thenReturn(updatedCustomizationRequestDetail);
	}

	private void setUpCustomizationRequest() {
		customizationRequestModel = new CustomizationRequestModel();
		customizationRequestModel.setStatus(CustomizationRequestStatus.ACCEPTED.value());
		customizationRequestModel.setRejectionReason("Test");
	}

	@Test
	@DisplayName("Invalid Customization Request Status")
	void updateCustomizationRequestWithInvalidStatus() {
		try {
			customizationRequestModel.setStatus("INVALID");
			customizationListService.updateCustomizationRequest(customizationRequestModel, customizationRequestId);
		} catch (PayerCustomizationException e) {
			e.printStackTrace();
			assertEquals("Status should be Accepted Or Rejected",
					e.getInvalidCustomizationResponse().getErrorMessage().get(0).getMessage());
		}
	}

	@Test
	@DisplayName("Not Exists Customization Request ID")
	void updateCustomizationRequestWithNotExistsCustomizationRequestId() {
		try {
			Mockito.when(customizationRequestMetadataRepository.findById(customizationRequestId))
					.thenReturn(Optional.empty());
			customizationListService.updateCustomizationRequest(customizationRequestModel, customizationRequestId);
		} catch (PayerCustomizationException e) {
			e.printStackTrace();
			assertEquals("Customization request does not exists",
					e.getInvalidCustomizationResponse().getErrorMessage().get(0).getMessage());
		}
	}

	@Test
	@DisplayName("Rejected Customization Request Status")
	void updateCustomizationRequestWithRejectedStatus() throws PayerCustomizationException {
		customizationRequestModel.setStatus(CustomizationRequestStatus.REJECTED.value());
		Mockito.when(customizationRequestMetadataRepository.findById(customizationRequestId))
				.thenReturn(generateCustomizationRequestMetadata("Test"));
		customizationListService.updateCustomizationRequest(customizationRequestModel, customizationRequestId);
		Mockito.when(customizationRequestMetadataRepository.save(Mockito.any()))
				.thenReturn(new CustomizationRequestMetadata());
	}

	@Test
	@DisplayName("Accepted Customization Request Status With PC Drug To Diagnosis Module")
	void updateCustomizationRequestWithAcceptedStatusWithPCDrugToDiagnosisModule() throws PayerCustomizationException {
		Mockito.when(customizationRequestMetadataRepository.findById(customizationRequestId)).thenReturn(
				generateCustomizationRequestMetadata(CustomizationModuleName.DRUG_TO_DISEASE_INTERACTION_RULE.name()));
		customizationListService.updateCustomizationRequest(customizationRequestModel, customizationRequestId);
		Mockito.when(customizationRequestMetadataRepository.save(Mockito.any()))
				.thenReturn(new CustomizationRequestMetadata());
		Mockito.when(pcDrugToDiagnosisRepository.findLatestId()).thenReturn(1L);
		Mockito.when(pcDrugToDiagnosisRepository.save(Mockito.any())).thenReturn(new PCDrugToDiagnosis());
	}

	@Test
	@DisplayName("Accepted Customization Request Status With PC Drug To Drug Module")
	void updateCustomizationRequestWithAcceptedStatusWithPCDrugToDrugModule() throws PayerCustomizationException {
		Mockito.when(customizationRequestMetadataRepository.findById(customizationRequestId)).thenReturn(
				generateCustomizationRequestMetadata(CustomizationModuleName.DRUG_TO_DRUG_INTERACTION_RULE.name()));
		customizationListService.updateCustomizationRequest(customizationRequestModel, customizationRequestId);
		Mockito.when(customizationRequestMetadataRepository.save(Mockito.any()))
				.thenReturn(new CustomizationRequestMetadata());
		Mockito.when(pcDrugToDrugRepository.findLatestId()).thenReturn(1L);
		Mockito.when(pcDrugToDrugRepository.save(Mockito.any())).thenReturn(new PcDrugToDrug());
	}

	@Test
	@DisplayName("Accepted Customization Request Status With PC Duplicate Therapy Module")
	void updateCustomizationRequestWithAcceptedStatusWithPCDuplicateTherapyModule() throws PayerCustomizationException {
		Mockito.when(customizationRequestMetadataRepository.findById(customizationRequestId)).thenReturn(
				generateCustomizationRequestMetadata(CustomizationModuleName.DUPLICATE_THERAPY_RULE.name()));
		customizationListService.updateCustomizationRequest(customizationRequestModel, customizationRequestId);
		Mockito.when(customizationRequestMetadataRepository.save(Mockito.any()))
				.thenReturn(new CustomizationRequestMetadata());
		Mockito.when(pcDuplicateTherapyRepository.findLatestId()).thenReturn(1L);
		Mockito.when(pcDuplicateTherapyRepository.save(Mockito.any())).thenReturn(new PCDuplicateTherapy());
	}

	@Test
	@DisplayName("Accepted Customization Request Status With PC Gender Module")
	void updateCustomizationRequestWithAcceptedStatusWithPCGenderModule() throws PayerCustomizationException {
		Mockito.when(customizationRequestMetadataRepository.findById(customizationRequestId)).thenReturn(
				generateCustomizationRequestMetadata(CustomizationModuleName.DRUG_TO_GENDER_INTERACTION_RULE.name()));
		customizationListService.updateCustomizationRequest(customizationRequestModel, customizationRequestId);
		Mockito.when(customizationRequestMetadataRepository.save(Mockito.any()))
				.thenReturn(new CustomizationRequestMetadata());
		Mockito.when(pcGenderRepository.findLatestId()).thenReturn(1L);
		Mockito.when(pcGenderRepository.save(Mockito.any())).thenReturn(new PCGender());
	}

	@Test
	@DisplayName("Accepted Customization Request Status With PC Age Module")
	void updateCustomizationRequestWithAcceptedStatusWithPCAgeModule() throws PayerCustomizationException {
		Mockito.when(customizationRequestMetadataRepository.findById(customizationRequestId)).thenReturn(
				generateCustomizationRequestMetadata(CustomizationModuleName.DRUG_TO_AGE_INTERACTION_RULE.name()));
		customizationListService.updateCustomizationRequest(customizationRequestModel, customizationRequestId);
		Mockito.when(customizationRequestMetadataRepository.save(Mockito.any()))
				.thenReturn(new CustomizationRequestMetadata());
		Mockito.when(pcAgeRepository.findLatestId()).thenReturn(1L);
		Mockito.when(pcAgeRepository.save(Mockito.any())).thenReturn(new PCAge());
	}

	@Test
	@DisplayName("Accepted Customization Request Status With PC Quantity Limit Check Module")
	void updateCustomizationRequestWithAcceptedStatusWithPCQuantityLimitCheckModule()
			throws PayerCustomizationException {
		Mockito.when(customizationRequestMetadataRepository.findById(customizationRequestId)).thenReturn(
				generateCustomizationRequestMetadata(CustomizationModuleName.QUANTITY_LIMIT_CHECK_RULE.name()));
		customizationListService.updateCustomizationRequest(customizationRequestModel, customizationRequestId);
		Mockito.when(customizationRequestMetadataRepository.save(Mockito.any()))
				.thenReturn(new CustomizationRequestMetadata());
		Mockito.when(pcQuantityLimitCheckRepository.findLatestId()).thenReturn(1L);
		Mockito.when(pcQuantityLimitCheckRepository.save(Mockito.any())).thenReturn(new PCQuantityLimitCheck());
	}

	private Optional<CustomizationRequestMetadata> generateCustomizationRequestMetadata(String module) {
		return Optional.of(new CustomizationRequestMetadata(1, "Test", "Test", false, new Date(), module, "101", "Test",
				"Test", new ArrayList<>()));
	}

	private CustomizationRequestDetail populateUpdatedCustomizationRequestDetail() {
		return new CustomizationRequestDetail(customizationRequestDetailsId, customizationRequestId, "CUSTOMIZABLE", "1", "Label");

	}

	private void generateMockUserInfo() {
		Map<String, Object> details = new HashMap<String, Object>();
		details.put("accId", "accId");
		details.put("accName", "accName");
		details.put("accCode", "accCode");
		details.put("username", "username");
		details.put("email", "email");
		details.put("authority", "authority");
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(authentication.getPrincipal()).thenReturn(details);
	}
}
