package com.waseel.pbm.pbmadminservice.drugformulary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import com.waseel.pbm.pbmadminservice.enums.DrugFormularyMessage;
import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyMetaDataResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyMetadataRequestModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyMetadata;
import com.waseel.pbm.pbmadminservice.repository.businessrules.AuditLogRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyMetadataRepository;
import com.waseel.pbm.pbmadminservice.service.DrugFormularyService;

@SpringBootTest
@ActiveProfiles("test")
class UpdateDrugFormularyTests {

	@Autowired
	private DrugFormularyService drugFormularyService;

	@MockBean
	private DrugFormularyMetadataRepository drugFormularyMetadataRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Validator validator;

	@MockBean
	private AuditLogRepository auditLogRepository;

	private Long formularyId = 1L;
	private String payerId = "102";
	private Date date = new Date();
	private DrugFormularyMetadata drugFormularyMetadata;
	private DrugFormularyMetadataRequestModel drugFormularyMetadataRequestModel;

	@BeforeEach
	void setupData() {
		generateMockUserInfo();
		drugFormularyMetadata = generateDrugFormularyMetadata();
		drugFormularyMetadataRequestModel = new DrugFormularyMetadataRequestModel("TestFormulary");
		Mockito.when(drugFormularyMetadataRepository.save(drugFormularyMetadata)).thenReturn(drugFormularyMetadata);
		Mockito.when(
				drugFormularyMetadataRepository.findByFormularyIdAndPayerIdAndIsDeleted(formularyId, payerId, false))
				.thenReturn(Optional.of(drugFormularyMetadata));
	}

	@Test
	@DisplayName("Success response")
	void successResponse() throws ParseException, AdminException {
		DrugFormularyMetaDataResponseModel response = drugFormularyService
				.updateDrugFormularyMetadataDetails(formularyId, drugFormularyMetadataRequestModel);
		assertNotNull(response);
		assertEquals("Updated Successfully.", response.getStatus());
		assertEquals("TestFormulary", drugFormularyMetadata.getFormularyName());
	}

	@Test
	@DisplayName("Invalid response")
	void invalidResponse() {
		try {
			drugFormularyService.updateDrugFormularyMetadataDetails(9L, drugFormularyMetadataRequestModel);
		} catch (AdminException e) {
			assertEquals("FormularyId is not found or exists.", e.getMessage());
		}
	}

	@Test
	@DisplayName("Request model Not Empty validations")
	void notEmptyValidationTest() {
		drugFormularyMetadataRequestModel.setFormularyName(null);
		List<ConstraintViolation<DrugFormularyMetadataRequestModel>> sortedViolations = getSortedViolations(
				drugFormularyMetadataRequestModel);
		Assertions.assertFalse(sortedViolations.isEmpty());
		Assertions.assertEquals(1, sortedViolations.size());
		List<String> messages = new ArrayList<>();
		for (ConstraintViolation<DrugFormularyMetadataRequestModel> violation : sortedViolations) {
			String propertyPath = violation.getPropertyPath().toString() + " ";
			String expectedMessage = propertyPath
					+ messageSource.getMessage("notNullOrEmpty", null, Locale.getDefault());
			messages.add(expectedMessage);
		}
		Assertions.assertEquals("formularyName should not be null or empty",
				StringUtils.strip(messages.toString(), "[]"));
	}

	@Test
	@DisplayName("Request model length validations")
	void lengthValidationTest() {
		drugFormularyMetadataRequestModel.setFormularyName(getFormularyNameLengthMoreThan100());
		List<ConstraintViolation<DrugFormularyMetadataRequestModel>> sortedViolations = getSortedViolations(
				drugFormularyMetadataRequestModel);
		Assertions.assertFalse(sortedViolations.isEmpty());
		Assertions.assertEquals(1, sortedViolations.size());
		List<String> messages = new ArrayList<>();
		for (ConstraintViolation<DrugFormularyMetadataRequestModel> violation : sortedViolations) {
			String propertyPath = violation.getPropertyPath().toString() + " ";
			String expectedMessage = propertyPath
					+ messageSource.getMessage("noMoreThan100LengthValidation", null, Locale.getDefault());
			messages.add(expectedMessage);
		}
		Assertions.assertEquals("formularyName shouldn't be more than 100",
				StringUtils.strip(messages.toString(), "[]"));
	}

	@Test
	@DisplayName("Duplicate formulary name")
	void duplicateFormularyName() {
		drugFormularyMetadataRequestModel.setFormularyName("TestFormulary2");
		Mockito.when(drugFormularyMetadataRepository.findByFormularyNameIgnoreCaseAndIsDeleted(Mockito.any(),
				Mockito.anyBoolean())).thenReturn(Optional.of(drugFormularyMetadata));
		try {
			drugFormularyService.updateDrugFormularyMetadataDetails(formularyId, drugFormularyMetadataRequestModel);
		} catch (AdminException e) {
			String error = e.getMessage();
			assertNotNull(error);
			assertEquals(DrugFormularyMessage.FORMULARY_NAME_ALREADY_EXISTS.value(), error);
			drugFormularyMetadataRequestModel.setFormularyName("TestFormulary");
		}
	}

	private String getFormularyNameLengthMoreThan100() {
		return "TESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTEST";
	}

	private List<ConstraintViolation<DrugFormularyMetadataRequestModel>> getSortedViolations(
			DrugFormularyMetadataRequestModel requestModel) {
		Set<ConstraintViolation<DrugFormularyMetadataRequestModel>> violations = validator.validate(requestModel);
		List<ConstraintViolation<DrugFormularyMetadataRequestModel>> sortedViolations = new ArrayList<>(violations);
		Collections.sort(sortedViolations, Comparator.comparing(violation -> violation.getPropertyPath().toString()));
		return sortedViolations;
	}

	private DrugFormularyMetadata generateDrugFormularyMetadata() {
		return new DrugFormularyMetadata(formularyId, payerId, "Test", date, "Test", date, false, "NA");
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
}
